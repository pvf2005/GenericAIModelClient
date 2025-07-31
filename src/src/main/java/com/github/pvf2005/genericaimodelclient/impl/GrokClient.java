package com.github.pvf2005.genericaimodelclient.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.github.pvf2005.genericaimodelclient.GenericAIModelClient;

public class GrokClient implements GenericClientImpl {
	GenericAIModelClient genericClient=null;
	
	@SuppressWarnings("serial")
	private static final Map<String, Boolean> modelsWithReasoningSetting  = new HashMap<String, Boolean>() {{
	    put("grok-3-mini", Boolean.TRUE);
	    put("grok-3-mini-fast", Boolean.TRUE);
	}};

	@Override
	public boolean init(GenericAIModelClient genericClient) {
		this.genericClient=genericClient;
		return true;
	}

	public boolean isReasoningSettingSupported(String model) {
		return modelsWithReasoningSetting.containsKey(model);
	}
	
	@Override
	public Response chat(Request req) {
		
		JSONObject json=new JSONObject();
		json.put("model", this.genericClient.getModel());
		JSONArray msgArr=new JSONArray();
		for(Message msg:req.getMessages()) {
			JSONObject ob=new JSONObject();
			ob.put("role", msg.getRole());
			ob.put("content", msg.getContent());
			msgArr.put(ob);
		}
		json.put("messages", msgArr);
		json.put("stream", false);

		if(req.getTemperature()!=null)json.put("temperature", Double.parseDouble(req.getTemperature()));
		if(req.getSeed()!=null)json.put("seed", Integer.parseInt(req.getSeed()));
		if(req.getReasoningEffort()!=null && !isReasoningSettingSupported(this.genericClient.getModel()))
			json.put("reasoning_effort", req.getReasoningEffort());
		
		req.setClientRequestStr(json.toString());
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(URI.create(this.genericClient.getEndpoint()))
				  .POST(HttpRequest.BodyPublishers.ofString(req.getClientRequestStr()))
				  .header("Authorization", "Bearer "+this.genericClient.getKey())
				  .header("Content-Type", "application/json")
				  .build();

		Response r=new Response();
		
		try {
			
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			String responseStr=response.body();
			r.setRawResponseStr(responseStr);

			//client.close();
			
			JSONObject res=new JSONObject(responseStr);
			r.setJson(res);
			
			Message msg=new Message();
			msg.setRole(res.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("role"));
			msg.setContent(res.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content"));
			r.setMessage(msg);

			r.setValid(true);
		} catch (Exception e) {
			e.printStackTrace();
			r.setException(e);
		}		
		
		return r;
	}

}

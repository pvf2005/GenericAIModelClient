package com.github.pvf2005.genericaimodelclient.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.github.pvf2005.genericaimodelclient.GenericAIModelClient;

public class OllamaClient implements GenericClientImpl {
	GenericAIModelClient genericClient=null;

	@Override
	public boolean init(GenericAIModelClient genericClient) {
		this.genericClient=genericClient;
		return true;
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
		req.setClientRequestStr(json.toString());
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(URI.create(this.genericClient.getEndpoint()))
				  .POST(HttpRequest.BodyPublishers.ofString(req.getClientRequestStr()))
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
			msg.setRole(res.getJSONObject("message").getString("role"));
			msg.setContent(res.getJSONObject("message").getString("content"));
			r.setMessage(msg);
			
			r.setValid(true);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			r.setException(e);
		}		
		
		return r;
	}

}

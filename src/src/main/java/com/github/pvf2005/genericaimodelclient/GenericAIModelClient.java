package com.github.pvf2005.genericaimodelclient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.apache.commons.io.FileUtils;

import com.github.pvf2005.genericaimodelclient.impl.GenericClientImpl;
import com.github.pvf2005.genericaimodelclient.impl.GrokClient;
import com.github.pvf2005.genericaimodelclient.impl.OllamaClient;
import com.github.pvf2005.genericaimodelclient.impl.OpenAIClient;
import com.github.pvf2005.genericaimodelclient.impl.PublicAIClient;
import com.github.pvf2005.genericaimodelclient.impl.Request;
import com.github.pvf2005.genericaimodelclient.impl.Response;

public class GenericAIModelClient {

	private String type=null;
	private String model=null;
	private String endpoint=null;
	private String key=null;
	private String saveRequestsFolder=null;
	private String saveResponsesFolder=null;
	
	private GenericClientImpl client=null;
	
	public GenericAIModelClient() {;}

	private boolean initClient() {
		if(this.client!=null)return true;
		this.client=null;
		if(type.equalsIgnoreCase("ollama")) {
			this.client=new OllamaClient();
		}else if(type.equalsIgnoreCase("grok")) {
			this.client=new GrokClient();
		}else if(type.equalsIgnoreCase("openai")) {
			this.client=new OpenAIClient();
		}else if(type.equalsIgnoreCase("publicai")) {
			this.client=new PublicAIClient();
		}
		
		if(this.client==null)return false;
		this.client.init(this);
		return true;
	}
	
	private void saveRequestResponse(Request req, Response res) {
		String id=req.getId();
		if(id==null) {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
			String formattedNow = now.format(formatter);
			Random rand=new Random();
			id=formattedNow+"_"+Math.abs(rand.nextLong());
		}
		if(this.saveRequestsFolder!=null) {
			try {
				FileUtils.writeStringToFile(Paths.get(this.saveRequestsFolder, "req_"+id+".txt").toFile(),req.getClientRequestStr(),Charset.forName("UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(this.saveResponsesFolder!=null) {
			try {
				FileUtils.writeStringToFile(Paths.get(this.saveResponsesFolder, "resp_"+id+".txt").toFile(),res.getRawResponseStr(),Charset.forName("UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Response chat(Request req) {
		initClient();
		long start=System.currentTimeMillis();
		Response r=this.client.chat(req);
		long duration=System.currentTimeMillis()-start;
		r.setDurationMillis(duration);
		saveRequestResponse(req,r);
		return r;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSaveRequestsFolder() {
		return saveRequestsFolder;
	}

	public void setSaveRequestsFolder(String saveRequestsFolder) {
		this.saveRequestsFolder = saveRequestsFolder;
	}

	public String getSaveResponsesFolder() {
		return saveResponsesFolder;
	}

	public void setSaveResponsesFolder(String saveResponsesFolder) {
		this.saveResponsesFolder = saveResponsesFolder;
	}

	
	
}

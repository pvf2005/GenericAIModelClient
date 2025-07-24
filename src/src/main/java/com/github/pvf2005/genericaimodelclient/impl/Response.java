package com.github.pvf2005.genericaimodelclient.impl;

import org.json.JSONObject;

public class Response {

	private String rawResponseStr=null;
	private JSONObject json=null;
	private Message message=null;
	private boolean isValid=false;
	private Exception exception=null;
	private long durationMillis=0;
	
	public Response() {;}

	public String getRawResponseStr() {
		return rawResponseStr;
	}

	protected void setRawResponseStr(String rawResponseStr) {
		this.rawResponseStr = rawResponseStr;
	}

	public JSONObject getJson() {
		return json;
	}

	protected void setJson(JSONObject json) {
		this.json = json;
	}

	public Message getMessage() {
		return message;
	}

	protected void setMessage(Message message) {
		this.message = message;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public long getDurationMillis() {
		return durationMillis;
	}

	public void setDurationMillis(long duration) {
		this.durationMillis = duration;
	}
	
	
	
}

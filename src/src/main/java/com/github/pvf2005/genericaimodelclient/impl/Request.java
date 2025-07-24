package com.github.pvf2005.genericaimodelclient.impl;

import java.util.ArrayList;
import java.util.List;

public class Request {

	private List<Message> messages=null;
	private String clientRequestStr=null;
	private String id=null;
	
	public Request() {;}
	
	public void addMessage(Message m) {
		if(messages==null)messages=new ArrayList<>();
		messages.add(m);
	}

	public void addMessage(String role, String content) {
		this.addMessage(new Message(role,content));
	}	
	
	public void addMessage(String content) {
		this.addMessage("user",content);
	}	

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getClientRequestStr() {
		return clientRequestStr;
	}

	public void setClientRequestStr(String clientRequestStr) {
		this.clientRequestStr = clientRequestStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}

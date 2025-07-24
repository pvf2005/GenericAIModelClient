package com.github.pvf2005.genericaimodelclient.impl;

public class Message {

	private String role=null;
	private String content=null;
	
	public Message(String role, String content) {
		this.role=role;
		this.content=content;
	}
	
	public Message() {;}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}

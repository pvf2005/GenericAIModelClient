package com.github.pvf2005.genericaimodelclient.impl;

import com.github.pvf2005.genericaimodelclient.GenericAIModelClient;

public interface GenericClientImpl {

	public boolean init(GenericAIModelClient genericClient);
	
	public Response chat(Request req);
	
}

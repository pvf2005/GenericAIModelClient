package com.github.pvf2005.genericmodelclient.test;

import com.github.pvf2005.genericaimodelclient.GenericAIModelClient;
import com.github.pvf2005.genericaimodelclient.impl.Request;
import com.github.pvf2005.genericaimodelclient.impl.Response;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GenericAIModelClient client=new GenericAIModelClient();
        
        /*client.setType("ollama");
        client.setModel("vicuna:latest");
        client.setEndpoint("http://127.0.0.1:11434/api/chat");*/
        
        /*
        client.setType("grok");
        client.setModel("grok-4");
        client.setEndpoint("https://api.x.ai/v1/chat/completions");
        client.setKey(""); // GROK Key here
        */
        
        
        client.setType("openai");
        client.setModel("gpt-4o");
        client.setEndpoint("https://api.openai.com/v1/responses");
        client.setKey(""); // OpenAI Key here
        
        
        //client.setSaveRequestsFolder(".");
        //client.setSaveResponsesFolder(".");
        
        Request req=new Request();
        req.addMessage("Hello! What is your name?");
        
        Response r=client.chat(req);
        System.out.println("Response:");
        System.out.println(r.getMessage().getContent());
    }
}

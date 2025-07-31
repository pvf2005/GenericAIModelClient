# GenericAIModelClient
A generic client for different AI models API. Currently supports Ollama, Grok and OpenAI.
Ideally there should be no difference in how an application behaves when calling a LLM model.
In this project the subtle differences between the APIs are handled in the background. 

For the OpenAI API the project checks for messages with role "system" and replaces it with "developer" considering the indications from here https://platform.openai.com/docs/api-reference/chat/create "With o1 models and newer, developer messages replace the previous system messages".

# Maven usage

Add this to pom.xml:
```
<repositories>
    <repository>
        <id>pvf2005-mvn-repo</id>
        <url>https://raw.githubusercontent.com/pvf2005/mvn-repo/main/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>

  <dependencies>
    <dependency>
      <groupId>com.github.pvf2005</groupId>
      <artifactId>genericaimodelclient</artifactId>
      <version>0.0.2</version>
    </dependency>
 </dependencies>
```

Make sure not to duplicate the <dependencies> element if already exists.

# Example code

```
       GenericAIModelClient client=new GenericAIModelClient();
        
        client.setType("ollama");
        client.setModel("vicuna:latest");
        client.setEndpoint("http://127.0.0.1:11434/api/chat");
        
        /*
        client.setType("grok");
        client.setModel("grok-4");
        client.setEndpoint("https://api.x.ai/v1/chat/completions");
        client.setKey(""); // GROK Key here
        */
        
        
        //client.setSaveRequestsFolder(".");
        //client.setSaveResponsesFolder(".");
        
        Request req=new Request();
        req.addMessage("Hello! What is your name?");
        
        Response r=client.chat(req);
        System.out.println("Response:");
        System.out.println(r.getMessage().getContent());
```
Also take a look at the "test" package.

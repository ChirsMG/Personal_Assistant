package com.glassware.personalassistant.server.Gateway;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PersonalAssistantGatewayTest {
    @Mock
    ItemHandler itemHandler;
    ListHandler listHandler;
    EventHandler eventHandler;
    ImageHandler imageHandler;
    TaskHandler taskHandler;

    private void testEndpoint(String endpoint, RequestHandler handler)throws Exception{
        String url = "http://localhost:8000/"+endpoint;
        HttpClient client = HttpClientBuilder.create().build();

        HttpPatch patchRequest= new HttpPatch(url);
        HttpPost postRequest= new HttpPost(url);
        postRequest.setHeader("Content-Type","text");
        postRequest.setEntity(new StringEntity("test post"));

        HttpGet getRequest= new HttpGet(url);
        HttpDelete deleteRequest= new HttpDelete(url);

        HttpResponse response=client.execute(getRequest);
        HttpResponse postResponse=client.execute(postRequest);

        HttpResponse patchResponse=client.execute(patchRequest);
        HttpResponse delResponse=client.execute(deleteRequest);

//        verify(handler,times(1)).handleRequest(anyString(),eq("get"));
//        verify(handler,times(1)).handleRequest(anyString(),eq("post"));
//        verify(handler,times(1)).handleRequest(anyString(),eq("put"));
//        verify(handler,times(1)).handleRequest(anyString(),eq("delete"));

    }

    @Before
    public void setUp(){
//        itemHandler=mock(ItemHandler.class);
    }

    @Test
    public void main() throws Exception {
        //Mock RequestHandler
        // make request to server
        Thread gateway=new Thread(){
            public void run(){
                try{
                    String[] args={};
                    PersonalAssistantGateway.main(args);
                }catch (InterruptedException e){
                    System.out.println(e);
                }
            }
        };
        gateway.start();
        try {
            testEndpoint("item", itemHandler);
        }finally{
            gateway.interrupt();

        }
        // assert relevant HttpExchange properties
    }


}
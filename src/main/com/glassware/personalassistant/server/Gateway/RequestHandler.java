package com.glassware.personalassistant.server.Gateway;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

abstract class RequestHandler implements  HttpHandler{
    private final static Logger LOGGER = Logger.getLogger(PersonalAssistantGateway.class.getName());

    protected abstract String handleRequest(InputStream requestBody, String method);


    @Override
    public void handle(HttpExchange exchange) throws IOException{

        LOGGER.info("requestURI:"+exchange.getRequestURI());
        String response=this.handleRequest(exchange.getRequestBody(),exchange.getRequestMethod());
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,response.getBytes().length);

        OutputStream responseMessage=exchange.getResponseBody();

        responseMessage.write(response.getBytes());
        responseMessage.close();
        exchange.close();
    }
}
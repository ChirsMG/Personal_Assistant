package com.glassware.personalassistant.server.REST;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

abstract class RestHandler implements HttpHandler {
    Logger LOGGER = Logger.getLogger(RestHandler.class.getName());

    protected abstract String handleRequest(InputStream requestBody, String method, String[] pathArgs,String uriParams);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("handling exchange " + exchange.getRequestURI());
        LOGGER.info("requestURI:" + exchange.getRequestURI());
        //Route based on path
        String path = exchange.getRequestURI().getPath();
        String[] pathArgs = path.split("/");

        //TODO authenticate based on headers
        String response = handleRequest(exchange.getRequestBody(), exchange.getRequestMethod(),pathArgs,exchange.getRequestURI().getQuery()); //TODO pass remaining path??
//        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
        OutputStream responseMessage = exchange.getResponseBody();
        Headers headers = exchange.getResponseHeaders();
        System.out.println("response: "+response);
        headers.set("Content-Type","application/json");
        System.out.println("sending response: "+response);
        System.out.println(exchange.getRequestHeaders().keySet());
        System.out.println(exchange.getRequestHeaders().get("Content-Type"));
        exchange.sendResponseHeaders(203,response.length());
        responseMessage.write(response.getBytes());
        responseMessage.close();
        exchange.close();
    }
}

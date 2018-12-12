package com.glassware.personalassistant.server.Gateway;

import com.sun.net.httpserver.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

abstract class RequestHandler implements HttpHandler {
    private final static Logger LOGGER = Logger.getLogger(PersonalAssistantGateway.class.getName());

    protected abstract String handleRequest(String requestBody, String method);

    protected String loadBody(InputStream body) {
        BufferedReader d = new BufferedReader(new InputStreamReader(body));
        String content = d.lines().collect(Collectors.joining("\n"));
        LOGGER.info("request content: " + content);
        return content;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        LOGGER.info("requestURI:" + exchange.getRequestURI());
        LOGGER.info("request METHOD:" + exchange.getRequestMethod());
        String response = this.handleRequest(loadBody(exchange.getRequestBody()), exchange.getRequestMethod());
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);

        OutputStream responseMessage = exchange.getResponseBody();

        responseMessage.write(response.getBytes());
        responseMessage.close();
        exchange.close();
    }
}
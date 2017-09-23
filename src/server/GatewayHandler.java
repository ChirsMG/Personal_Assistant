package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

class GatewayHandler implements  HttpHandler{
    private final static Logger LOGGER = Logger.getLogger(GatewayHandler.class.getName());

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        LOGGER.info("using handler");

        System.out.println("path: "+exchange.getHttpContext().getPath());
        System.out.println(exchange.getRequestBody());

        OutputStream responseMessage=exchange.getResponseBody();
        byte[] response="Hello World".getBytes();
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,response.length);
        responseMessage.write(response);
        responseMessage.close();
        exchange.close();
    }
}
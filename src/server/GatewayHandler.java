package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

class GatewayHandler implements  HttpHandler{
    private final static Logger LOGGER = Logger.getLogger(GatewayHandler.class.getName());
    public GatewayHandler(){

    }
    @Override
    public void handle(HttpExchange exchange) throws IOException{
        LOGGER.info("using handler");

        System.out.println("path: "+exchange.getHttpContext().getPath());
        System.out.println(exchange.getRequestBody());

        OutputStream responseMessage=exchange.getResponseBody();

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
        responseMessage.write("Hello World".getBytes());
        responseMessage.flush();
        responseMessage.close();
        exchange.close();
    }
}
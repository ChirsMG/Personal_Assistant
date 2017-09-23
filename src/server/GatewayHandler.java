package server;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

class GatewayHandler implements  HttpHandler{
    private final static Logger LOGGER = Logger.getLogger(PersonalAssistantGateway.class.getName());

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        LOGGER.info("using handler");
        byte[] response="Hello World".getBytes();
        System.out.println("path: "+exchange.getHttpContext().getPath());
        System.out.println(exchange.getRequestBody());
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,response.length);

        OutputStream responseMessage=exchange.getResponseBody();

        responseMessage.write(response);
        responseMessage.close();
        exchange.close();
    }
}
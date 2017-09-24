package server;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

abstract class GatewayHandler implements  HttpHandler{
    private final static Logger LOGGER = Logger.getLogger(PersonalAssistantGateway.class.getName());

    protected abstract String handleRequest(InputStream requestBody);


    @Override
    public void handle(HttpExchange exchange) throws IOException{

        String response=this.handleRequest(exchange.getRequestBody());
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,response.getBytes().length);

        OutputStream responseMessage=exchange.getResponseBody();

        responseMessage.write(response.getBytes());
        responseMessage.close();
        exchange.close();
    }
}
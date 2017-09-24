package server;

import java.io.InputStream;

public class ListHandler extends GatewayHandler {

    @Override
    protected String handleRequest(InputStream body){

        return "List request recieved";

    }
}
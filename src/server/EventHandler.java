package server;

import java.io.InputStream;

public class EventHandler extends GatewayHandler {

    @Override
    protected String handleRequest(InputStream body){

        return "Event request recieved";

    }
}

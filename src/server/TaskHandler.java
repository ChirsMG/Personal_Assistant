package server;

import java.io.InputStream;

public class TaskHandler extends GatewayHandler {

    @Override
    protected String handleRequest(InputStream body){

        return "event request recieved";

    }
}

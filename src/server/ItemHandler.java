package server;

import java.io.InputStream;

public class ItemHandler extends GatewayHandler {

    @Override
     protected String handleRequest(InputStream body){

        return "item request recieved";

    }
}

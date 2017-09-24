package server;

import java.io.InputStream;

public class ImageHandler extends GatewayHandler {

    @Override
    protected String handleRequest(InputStream requestInput){

        return "image request revieved";
    }
}

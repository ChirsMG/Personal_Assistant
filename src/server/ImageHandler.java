package server;

import java.io.InputStream;

public class ImageHandler extends GatewayHandler {

    @Override
    protected String handleRequest(InputStream requestInput, String method){
        /**
         * will need to handle
         * GET - read
         * POST - Create
         * DELETE - delete
         *
         * **CAN'T UPDATE IMAGES
         */

        return "image request revieved with method: "+method;
    }
}

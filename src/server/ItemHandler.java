package server;

import java.io.InputStream;

public class ItemHandler extends GatewayHandler {

    @Override
     protected String handleRequest(InputStream body, String method){
        /**
         * will need to handle
         * GET - read
         * PUT - update
         * POST - Create
         * DELETE - delete
         */

        return "item request recieved  with method: "+method;

    }
}

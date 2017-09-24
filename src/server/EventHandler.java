package server;

import java.io.InputStream;

public class EventHandler extends GatewayHandler {

    @Override
    protected String handleRequest(InputStream body, String method){
        /**
         * will need to handle
         * GET - read
         * PUT - update
         * POST - Create
         * DELETE - delete
         */

        return "Event request recieved with method: "+method;

    }
}

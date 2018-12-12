package com.glassware.personalassistant.server.Gateway;

import java.io.InputStream;

public class EventHandler extends RequestHandler {

    @Override
    protected String handleRequest(String body, String method){
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

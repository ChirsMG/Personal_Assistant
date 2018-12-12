package com.glassware.personalassistant.server.REST;

import java.io.InputStream;

public class EventHandler extends RestHandler {

    @Override
    protected String handleRequest(InputStream body, String method,String[] pathArgs, String queryParams){
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

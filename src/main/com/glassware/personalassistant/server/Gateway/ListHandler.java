package com.glassware.personalassistant.server.Gateway;

import java.io.InputStream;

public class ListHandler extends RequestHandler {

    @Override
    protected String handleRequest(InputStream body, String method){
        /**
         * will need to handle
         * GET - read
         * PUT - update
         * POST - Create
         * DELETE - delete
         */

        return "List request recieved  with method: "+method;

    }
}
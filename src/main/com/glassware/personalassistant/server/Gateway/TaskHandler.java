package com.glassware.personalassistant.server.Gateway;

import java.io.InputStream;

public class TaskHandler extends RequestHandler {

    @Override
    protected String handleRequest(InputStream body, String method){
        /**
         * will need to handle
         * GET - read
         * PUT - update
         * POST - Create
         * DELETE - delete
         */

        return "event request recievedwith method: "+method;

    }
}

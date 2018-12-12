package com.glassware.personalassistant.server.REST;



import java.io.InputStream;

public class TaskHandler extends RestHandler {

    @Override
    protected String handleRequest(InputStream requestBody, String method, String[] pathArgs,String queryParams) {
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

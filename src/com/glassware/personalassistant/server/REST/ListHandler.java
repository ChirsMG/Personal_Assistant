package com.glassware.personalassistant.server.REST;

import com.glassware.personalassistant.server.REST.RestHandler;

import java.io.InputStream;

public class ListHandler extends RestHandler {

    @Override
    protected String handleRequest(InputStream body, String method,String[] pathArgs, String queryParams){
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
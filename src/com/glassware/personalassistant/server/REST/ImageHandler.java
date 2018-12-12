package com.glassware.personalassistant.server.REST;

import java.io.InputStream;

public class ImageHandler extends RestHandler {

    @Override
    protected String handleRequest(InputStream requestInput, String method,String[] pathArgs,String queryParams){
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

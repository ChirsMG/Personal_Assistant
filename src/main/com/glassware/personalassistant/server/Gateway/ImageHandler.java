package com.glassware.personalassistant.server.Gateway;

import java.io.InputStream;

public class ImageHandler extends RequestHandler {

    @Override
    protected String handleRequest(String requestInput, String method){
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

package com.glassware.personalassistant.server.Gateway;

import java.io.InputStream;

public class ItemHandler extends RequestHandler {
    //TODO - evaluate CODE SMELL
    /*
        Test seems very generic which seems to indicate that either an abstract or generic class
        should be used instead
     */

    void getItem(){

    }
    void updateItem(){

    }
    void deleteItem(){

    }
    void createItem(){

    }


    @Override
     protected String handleRequest(InputStream body, String method){
        /**
         * will need to handle
         * GET - read
         * PUT - update
         * POST - Create
         * DELETE - delete
         */

        String response= "item request recieved  with method: "+method;

        return response;

    }
}

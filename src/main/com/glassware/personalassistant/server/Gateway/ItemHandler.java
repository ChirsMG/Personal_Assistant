package com.glassware.personalassistant.server.Gateway;

import com.glassware.personalassistant.server.Consumers.RetrievedObjectsConsumer;
import com.glassware.personalassistant.server.Item;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO- evaluate making static
/*
    class does not store values or change functionality
 */
public class ItemHandler extends RequestHandler {

    private static String TOPIC="";
    //TODO - evaluate CODE SMELL
    /*
        Class seems very generic which seems to indicate that either an abstract or generic class
        should be used instead
     */

    Result getItem( String id) {
        // create task
        // produce task

        //load item
        return new Result();

    }

    Result updateItem(String id, Map keyValUpdates) {

        // create task
        return new Result();
    }


    Result deleteItem(String id) {
        // create task
        return new Result();

    }

    Result createItem(String itemJSON) {
        // create task
        return new Result();

    }

    private Item loadItem(String id){
        List<Item> items=new ArrayList<>();
        RetrievedObjectsConsumer<Item> consumer= new RetrievedObjectsConsumer<Item>();
        try {
            consumer.runConsumers(TOPIC, id);
        }catch(Exception e){
            return null;
        }
        return items.get(0);
    }


    @Override
    protected String handleRequest(String body, String method) {
        /**
         * will need to handle
         * GET - read
         * PUT - update
         * POST - Create
         * DELETE - delete
         */

        String response = "item request received  with method: " + method;

        return response;

    }
}

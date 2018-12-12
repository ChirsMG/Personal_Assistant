package com.glassware.personalassistant.server;


import com.glassware.personalassistant.server.Consumers.ItemQueryConsumer;
import com.glassware.personalassistant.server.Producers.InstructionProducer;
import com.glassware.personalassistant.server.Producers.ItemProducer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ItemService {
    StorageConnector dbConnection;


    ItemService connection(StorageConnector conection){
        this.dbConnection=conection;
        return this;
    }


    void create(Item newItem){
        //TODO REFACTOR SIGNATURE
        ItemProducer producer=new ItemProducer();
        try {
            producer.runProducer("create", newItem);
        }catch(Exception e){
            //todo get specific with exception

        }
    }
    void delete(String id){
        InstructionProducer producer=new InstructionProducer();
        try {
//            producer.runProducer("delete", id);
        }catch(Exception e){
            //todo get specific with exception

        }

    }

    /**
     * retrieves an Item
     * @param id - the id of the item to be retrieved
     */
    void get(String id){
        //get from DB
       Map<String,String> result=new HashMap<String,String>();
       /*
            TODO REFACTOR INTO GET FROM DB
        */
        InstructionProducer producer=new InstructionProducer();
        ItemQueryConsumer consumer=new ItemQueryConsumer();
        try {
            consumer.runItemConsumers();
//            producer.runProducer("get", id);
           // consumer.items.get(id);

        }catch(Exception e){

        }
        result.put("id","tmp001");
        result.put("name","temporary item 1");

      Item  item=new Item(result.get("id"),result.get("name"));
    }

    /**
     * retrieves multiple items
     * @param ids the of ids to be retrieved
     */
    void get(List<String> ids){
        List<Item> items = new ArrayList<Item>();
        List<Map<String,String>> result=new ArrayList<Map<String,String>>();

        result.forEach(item->{
            items.add(new Item(item.get("id"),item.get("name")));
        });
    }

    void update(String id, String jsonPayload){
//        ItemProducer producer=new ItemProducer();
//        try {
//            producer.runProducer("create", newItem, 0);
//        }catch(Exception e){
//            //todo get specific with exception
//
//        }
        //TODO figure out best way to update a document
    }
}
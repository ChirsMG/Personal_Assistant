package server;


import com.glassware.personalassistant.server.StorageConnector;

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


    }
    void delete(String id){
        //todo delete from DB OR archive

    }

    /**
     * retrieves an Item
     * @param id - the id of the item to be retrieved
     */
    void get(String id){
        //get from DB
       Map<String,String> result=new HashMap<String,String>();
       /*
            TO DO REFACTOR INTO GET FORM DB
        */
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
        //TODO figure out best way to update a document
    }
}
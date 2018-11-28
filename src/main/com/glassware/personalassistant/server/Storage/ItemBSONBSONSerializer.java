package com.glassware.personalassistant.server.Storage;

import com.glassware.personalassistant.server.Item;
import org.bson.conversions.Bson;

import java.util.Map;

public class ItemBSONBSONSerializer extends ObjectBSONSerializer {
    String id;
    String name;
    String description;
    Map<String, Object> content;

    ItemBSONBSONSerializer(){
       super();
    }
    //JSON representation
    /*
        {
            id:<id>,
            name:<name>,
            description:<description>,
            documents:[
                <id>,
                <id>
            ],
            content:[
                {
                    key:value
                },
                {
                    key:value
                }
            ]
        }

     */
    void mapObject(Item item){
        super.mapObject(item);
        this.document.put("description",item.getDescription());
        //todo map content
    }
    Bson toBson(){
      return this.document;
    };
}

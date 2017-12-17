package server;

import org.bson.Document;
import org.bson.BsonValue;
import org.bson.conversions.Bson;

import java.util.Map;

public class ItemSerializer extends ObjectSerializer{
    String id;
    String name;
    String description;
    Map<String, Object> content;

    ItemSerializer(){
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
        this.document.put("description",item.description);
        //todo map content
    }
    Bson toBson(){
      return this.document;
    };
}

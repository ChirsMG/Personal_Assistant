package server;

import org.bson.Document;

abstract public class ObjectSerializer {
    Document document;

    ObjectSerializer(){
        this.document=new Document();
    }


    void mapObject(MappableObject object){
        document.put("_id",object.id);
    }

}

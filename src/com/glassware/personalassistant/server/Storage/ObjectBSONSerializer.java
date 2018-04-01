package com.glassware.personalassistant.server.Storage;

import com.glassware.personalassistant.server.MappableObject;
import org.bson.Document;

abstract public class ObjectBSONSerializer {
    Document document;

    ObjectBSONSerializer(){
        this.document=new Document();
    }


    void mapObject(MappableObject object){
        document.put("_id",object.id);
    }

}

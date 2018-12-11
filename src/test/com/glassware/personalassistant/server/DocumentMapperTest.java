package com.glassware.personalassistant.server;

import com.sun.media.jfxmedia.logging.Logger;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DocumentMapperTest {
    Item Obj1;
    @Test
    void mapObject() {
        Document testDoc= new DocumentMapper().mapObject(Obj1.id,Obj1);
        System.out.println(testDoc.toString());
        System.out.println(testDoc.toJson());
        Logger.logMsg(Logger.INFO,testDoc.toString());
        Logger.logMsg(Logger.INFO,testDoc.toJson());
    }

    @BeforeEach
    void setUp() {
       Obj1= new Item("0001","Object 1");
        Obj1.description("test object number 1");
        Obj1.addContent(new HashMap<String,Object>(){{
            put("type","com.glassware.personalassistant.server.Item");
        }});
        Obj1.addContent("test Array",new ArrayList<String>(){{
            add("foo");
            add("bar");
            add("stuff");
            add("things");
        }});
        Obj1.addContent("nestedMap", new HashMap<String,Object>(){{
            put("value1","nestedMapValue");
        }});

        // For future object types
        Map<String, Object> Object2 = new HashMap<String, Object>();
        Map<String, Object> Object3 = new HashMap<String, Object>();
        Map<String, Object> Object4 = new HashMap<String, Object>();
        Map<String, Object> Object5 = new HashMap<String, Object>();


    }
}
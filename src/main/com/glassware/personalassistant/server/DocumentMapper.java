package com.glassware.personalassistant.server;

import com.sun.media.jfxmedia.logging.Logger;
import org.bson.Document;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DocumentMapper {

    Document document;

    DocumentMapper() {
        this.document = new Document();
    }

    private DocumentMapper toAttr(String name, String value) {
        this.document.put(name, value);
        return this;
    }

    private DocumentMapper toAttr(String name, int value) {
        this.document.put(name, value);
        return this;
    }

    private DocumentMapper toAttr(String name, boolean value) {
        this.document.put(name, value);
        return this;
    }

    private DocumentMapper toAttr(String name, Map map) {
        this.document.put(name, map);
        return this;
    }

    private DocumentMapper toAttr(String name, List list) {

        if (list.get(0) instanceof String || list.get(0) instanceof Integer || list.get(0) instanceof Float) {
            this.document.put(name, list.toString());
            return this;
        } else {
            List<Object> ids = new ArrayList<Object>();
            list.forEach(item -> {
                try {
                    Object val = item.getClass().getField("_id").get(item); //There has to be be better way...
                    ids.add(val);
                } catch (Exception e) {
                    // if we can't do anything then don't
                }
            });
            this.document.put(name, ids);
        }
        return this;
    }

    private DocumentMapper toAttr(String name, Object obj) {
        mapObject(name, obj);
        return this;
    }


    //TODO refactor - TODO check for Memory Leak
    public Document mapObject(String name, Object obj) {
        final boolean throwFlag;
        obj.getClass();

        //CODE SMELL - reflection feels like overkill here
        Item.class.getFields();
        List<Field> fields = Arrays.asList(obj.getClass().getDeclaredFields());
        fields.forEach(field -> {
                    try {
//                        if (field.isAccessible()) {
                            Class fieldClass=field.getType();
                            System.out.println(fieldClass.cast(field.get(obj)));
                            toAttr(field.getName(), fieldClass.cast(field.get(obj)));
//                        }
                    } catch (IllegalAccessException e) {
                        //this should never happen due to accessibility check - so I am logging and eating
                        Logger.logMsg(Logger.ERROR, "UNPREDICTABLE ILLEGAL ACCESS in MapObject()");
                    }
                }
        );

        //this feels inefficient. Get it working then optimize //NOTE: maybe create nested mappers?
        Document newDoc = new Document();
        newDoc.put("name", this.document);
        this.document = newDoc;

        return this.document;
    }

}

package server;

import java.util.HashMap;
import java.util.Map;

public class Item {
    String id;
    String name;
    String description;
    Map<String, Object> content;

    /**
     * Constructor- builds item object
     * @param id - item id can be temporary until created in DB but represents the key in DB if exists
     * @param name - the name of item
     */
    Item(String id,String name){
        this.id=id;
        this.name=name;
    }

    /**
     *  adds the description to the item object
     * @param description - description/ content of the item
     * @return self
     */
    Item description(String description){
        this.description=description;
        return this;
    }

    /**
     * builds content by item
     * @param id - the map key for the item used for loading inline content
     * @param contentItem - the object representing the content
     * @return self
     */
    Item addContent(String id, Object contentItem){
        if(this.content==null){
            this.content=new HashMap<String,Object>();
        }
        this.content.put(id,contentItem);
        return this;
    }

    /**
     *  merges map into content
     * @param content - {Map<String,Object>} to merge into item content map, original items erased on collision
     * @return self
     */
    Item addContent(Map<String,Object> content){
        if(this.content==null){
            this.content=new HashMap<String,Object>();
        }
        this.content.putAll(content);
        return this;
    }

    /***
     * sets item content - overwrites old content use addContent(Map) to merge
     * @param newContent - {Map<String,Object>} to be set as content
     * @return self
     */
    Item content(Map<String, Object> newContent){
        this.content=newContent;
        return this;
    }



}
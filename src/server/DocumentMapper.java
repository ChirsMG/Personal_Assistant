package server;

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

    private DocumentMapper toAttr(String name, List list){

        if(list.get(0) instanceof String || list.get(0) instanceof Integer || list.get(0) instanceof Float ) {
            this.document.put(name, list.toString());
            return this;
        }else{
            List <Object> ids=new ArrayList<Object>();
            list.forEach(item->{
                try {
                    Object val=item.getClass().getField("_id").get(item); //There has to be be better way...
                    ids.add(val);
                }catch(Exception e){
                    // if we can't do anything then don't
                }
            });
            this.document.put(name,ids);
        }
        return this;
    }
    private DocumentMapper toAttr(String name, Object obj){
        mapObject(name,obj);
        return this;
    }
    public DocumentMapper mapObject(String name, Object obj) {
        List<Field> fields = Arrays.asList(obj.getClass().getFields());
        fields.forEach(field -> {

                }
        );
        return this;
    }

}

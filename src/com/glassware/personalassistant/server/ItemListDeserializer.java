package com.glassware.personalassistant.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.List;
import java.util.Map;

public class ItemListDeserializer implements Deserializer<List<Item>> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }
    @Override
    public List<Item> deserialize(String s, byte[] bytes) {
        TypeReference<List<Item> > type= new TypeReference<List<Item> >(){};
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        List<Item>  items = null;
        try {
            items = mapper.readValue(bytes,type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;

    }

    @Override
    public void close() {

    }
}

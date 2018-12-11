package com.glassware.personalassistant.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ItemDeserializer implements Deserializer<Item> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Item deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Item item = null;
        try {
            item = mapper.readValue(bytes, Item.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;

    }

    @Override
    public void close() {

    }
}

package com.glassware.personalassistant.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glassware.personalassistant.server.Storage.Instruction;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class InstructionDeserializer  implements Deserializer<Instruction> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public Instruction deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Instruction item = null;
        try {
            item = mapper.readValue(bytes, Instruction.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;

    }

    @Override
    public void close() {

    }
}

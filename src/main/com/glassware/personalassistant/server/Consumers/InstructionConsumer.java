package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.Instruction;
import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ObjectSerializer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;


import java.util.HashMap;
import java.util.Map;

public class InstructionConsumer<T> extends Consumable<Instruction<T>> {
    public Map<String,Instruction<T>> recordsOut;


    public InstructionConsumer(Map storageLocation) {
        recordsOut = storageLocation;
    }


    public void runConsumers(String topic) throws InterruptedException {
        Consumer<Long, Instruction<T>> consumer = createConsumer(topic);

        while (true) {
            final ConsumerRecords<Long, Instruction<T>> consumerRecords = consumer.poll(100);

            if (consumerRecords.count() == 0) {
                break;
            }

            consumerRecords.forEach(record -> {
                recordsOut.put(record.value().getId(),record.value());
            });
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }
}



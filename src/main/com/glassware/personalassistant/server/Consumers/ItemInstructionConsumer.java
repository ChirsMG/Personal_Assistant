package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ObjectSerializer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;


import java.util.HashMap;
import java.util.Map;

public class ItemInstructionConsumer extends Consumable<Item> {
    public Map<String,Item> items;


    public ItemInstructionConsumer() {
        this.valueDeserializerClass = StringDeserializer.class.getName();
        items=new HashMap<>();
    }


    public void runItemConsumers() throws InterruptedException {
        Consumer<Long, Item> readConsumer = createConsumer("com.glassware.personalassistant.server.Item-read");
        Consumer<Long, Item> deleteConsumer = createConsumer("com.glassware.personalassistant.server.Item-delete");

        while (true) {
            final ConsumerRecords<Long, Item> consumerRecords = readConsumer.poll(100);

            if (consumerRecords.count() == 0) {
                break;
            }

            consumerRecords.forEach(record -> {
                items.put(record.value().getId(),record.value());
            });
            readConsumer.commitAsync();
        }
        readConsumer.close();
        System.out.println("DONE");
    }
}



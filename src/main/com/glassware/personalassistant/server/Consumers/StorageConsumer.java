package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.Consumers.Consumable;
import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ObjectSerializer;
import javafx.util.Pair;
import org.apache.kafka.clients.consumer.*;

import java.util.Queue;

public class StorageConsumer extends Consumable<Item> {

    private Queue<Pair<String,Object>> instructionQueue;

    public StorageConsumer() {
        this.valueDeserializerClass = ObjectSerializer.class.getName();
    }


    void runConsumers() throws InterruptedException {
        Consumer<Long, Item> consumer = createConsumer("com.glassware.personalassistant.server.Item-create");

        while (true) {
            final ConsumerRecords<Long, Item> consumerRecords = consumer.poll(100);

            if (consumerRecords.count() == 0) {
                break;
            }

            consumerRecords.forEach(record -> {
                Item item=record.value();
            });
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }
}

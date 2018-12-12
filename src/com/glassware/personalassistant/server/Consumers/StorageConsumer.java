package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.Consumers.Consumable;
import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ItemDeserializer;
import com.glassware.personalassistant.server.ObjectSerializer;
import javafx.util.Pair;
import org.apache.kafka.clients.consumer.*;

import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class StorageConsumer extends Consumable<Item> {

    public Queue<Pair<String,Object>> instructionQueue; //todo privatize??

    public StorageConsumer() {
        this.valueDeserializerClass = ItemDeserializer.class.getName();
        instructionQueue=new LinkedBlockingQueue<Pair<String,Object>>();
    }


    public void runConsumers() throws InterruptedException {
        Consumer<Long, Item> consumer = createConsumer("Item-create");


        while (true) {
            final ConsumerRecords<Long, Item> consumerRecords = consumer.poll(100);

            if (consumerRecords.count() == 0) {
                break;
            }

            consumerRecords.forEach(record -> {
                Item item=record.value();
                instructionQueue.add(new Pair<>("create",item)); //todo constant
            });
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }
}

package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.ItemListDeserializer;
import com.glassware.personalassistant.server.Item;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryResultConsumer<T> extends Consumable<List<Item>> {
    private final static long TIMEOUT = 180; //seconds

    public List<T> requestedItems;


    public QueryResultConsumer(List requestedItems) {
        this.valueDeserializerClass = ItemListDeserializer.class.getName();
        this.requestedItems = requestedItems;
    }

    public List<Item> consume(String id) {
        if (id == null) return null;
        Consumer<Long, List<Item>> consumer = createConsumer("ItemQuery-" + id);
        System.out.printf("Query id- %s \n", id);
        ConsumerRecord<Long, List<Item>> currentRecord = null;
        List<Item> requestedItems = null;
        Instant start = Instant.now();
        try {
            while (true) {
                final ConsumerRecords<Long, List<Item>> consumerRecords = consumer.poll(100);

                //todo improve break condition


                for (ConsumerRecord<Long, List<Item>> record : consumerRecords) {
                    System.out.printf("finding recent record \n");
                    if (currentRecord == null || record.timestamp() > currentRecord.timestamp()) {
                        currentRecord = record;
                        requestedItems = currentRecord.value();
                    }
                }

                if (requestedItems != null) {
                    System.out.printf("item record found \n");
                    break;
                }
                consumer.commitAsync();
                if (Duration.between(start, Instant.now()).getSeconds() > TIMEOUT) {
                    System.out.printf("Query Result Consumer timed out \n");
                    break;
                }

            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        consumer.close();
        return requestedItems;
    }

}

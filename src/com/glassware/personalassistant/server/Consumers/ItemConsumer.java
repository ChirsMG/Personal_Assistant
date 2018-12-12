package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ItemDeserializer;
import javafx.util.Pair;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;

public class ItemConsumer extends Consumable<Item> {
    private final static long TIMEOUT =60; //seconds

    public Map<String,Item> items;


    public ItemConsumer() {
        this.valueDeserializerClass = ItemDeserializer.class.getName();
        items=new HashMap<>();
    }

    public Item consume(String id){
        Consumer<Long, Item> consumer = createConsumer("Item-"+id);
        System.out.printf("consumer topic: Item-%s \n",id);
        ConsumerRecord<Long, Item> currentRecord=null;
        Item requestedItem=null;
        Instant start = Instant.now();

        while (true) {
            final ConsumerRecords<Long, Item> consumerRecords = consumer.poll(100);

            //todo improve break condition

            if ( Duration.between(start, Instant.now()).getSeconds()> TIMEOUT) {
                System.out.printf("item consumer timed out \n");
                break;
            }

            for(ConsumerRecord<Long,Item> record : consumerRecords){
                System.out.printf("finding recent record \n");
                if(currentRecord==null || record.timestamp()> currentRecord.timestamp()){
                    currentRecord=record;
                    requestedItem=currentRecord.value();
                }
            }

            if (requestedItem!=null){
                System.out.printf("item record found \n");
                break;
            }

            consumer.commitAsync();
        }

        consumer.close();
        return requestedItem;
    }

}


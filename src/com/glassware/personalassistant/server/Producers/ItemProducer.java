package com.glassware.personalassistant.server.Producers;

import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ObjectSerializer;
import com.sun.media.jfxmedia.logging.Logger;
import org.apache.kafka.clients.producer.*;

import java.util.concurrent.ExecutionException;

public class ItemProducer extends Producible<Item> {
    final int sendMessageCount=1;
    public ItemProducer() {
        this.valueSerializerClass = ObjectSerializer.class.getName();
    }

    public void runProducer(String topic, Item item) throws ExecutionException, InterruptedException {
        long time = System.currentTimeMillis();
        final Producer<Long, Item> producer = createProducer();

        String fullTopic="Item-"+topic;
        System.out.printf("topic: "+fullTopic);
        try {
            for (long index = time; index < time + sendMessageCount; index++) { //TODO this is weird refactor
                final ProducerRecord<Long, Item> record =
                        new ProducerRecord(fullTopic, index, item);//todo refactor index into id of item


                RecordMetadata metadata = producer.send(record).get();

                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);

            }
        }finally {
            producer.flush();
            producer.close();
        }
    }

    public void runProducer(String topic, String id) throws ExecutionException, InterruptedException {
        //
        long time = System.currentTimeMillis();
        final Producer<Long, Item> producer = createProducer();

        String fullTopic="Item-"+topic;
        try {
            for (long index = time; index < time + sendMessageCount; index++) { //TODO this is weird refactor
                final ProducerRecord<Long, Item> record =
                        new ProducerRecord(fullTopic, index, id);//todo refactor index into id of item

                RecordMetadata metadata = producer.send(record).get();

                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);

            }
        }finally {
            producer.flush();
            producer.close();
        }
    }

    public void produceNewItem(Item item) throws ExecutionException, InterruptedException{
        long time = System.currentTimeMillis();
        final Producer<Long, Item> producer = createProducer();

        String fullTopic="ItemCreate";
        System.out.printf("topic: "+fullTopic);
        try {
            for (long index = time; index < time + sendMessageCount; index++) { //TODO this is weird refactor
                final ProducerRecord<Long, Item> record =
                        new ProducerRecord(fullTopic, index, item);//todo refactor index into id of item

                RecordMetadata metadata = producer.send(record).get();

                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);

            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        } finally
        {
            producer.flush();
            producer.close();
        }
    }
}

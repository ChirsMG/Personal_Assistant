package com.glassware.personalassistant.server.Producers;

import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ObjectSerializer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class QueryResultProducer extends Producible<List<Item>> {
    final int sendMessageCount = 1;

    public QueryResultProducer() {
        this.valueSerializerClass = ObjectSerializer.class.getName();
    }

    public void runProducer(String topic, List<Item> items) throws ExecutionException, InterruptedException {
        long time = System.currentTimeMillis();
        final Producer<Long,List<Item>> producer = createProducer();

        String fullTopic = "ItemQuery-" + topic;
        System.out.printf("topic: " + fullTopic);
        try {
            for (long index = time; index < time + sendMessageCount; index++) { //TODO this is weird refactor
                final ProducerRecord<Long,List<Item>> record =
                        new ProducerRecord(fullTopic, index, items);//todo refactor index into id of item


                RecordMetadata metadata = producer.send(record).get();

                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);

            }
        }catch (Exception e){
            System.out.println(e);
        }
        finally
        {
            producer.flush();
            producer.close();
        }
    }
}
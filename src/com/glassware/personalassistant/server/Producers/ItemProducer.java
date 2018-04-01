package com.glassware.personalassistant.server.Producers;

import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ObjectSerializer;
import org.apache.kafka.clients.producer.*;

public class ItemProducer extends Producible<Item> {

    public ItemProducer() {
        this.valueSerializerClass = ObjectSerializer.class.getName();
    }

    public void runProducer(String topic, Item item, final int sendMessageCount) throws Exception {
        long time = System.currentTimeMillis();
        final Producer<Long, Item> producer = createProducer();

        String fullTopic="Item-"+topic;
        try {
            for (long index = time; index < time + sendMessageCount; index++) {
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
}

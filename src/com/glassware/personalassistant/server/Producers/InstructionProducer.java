package com.glassware.personalassistant.server.Producers;

import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ObjectSerializer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class InstructionProducer extends Producible<String> {
    public InstructionProducer() {
        this.valueSerializerClass = StringSerializer.class.getName();
    }

    public void runProducer(String topic, String id, final int sendMessageCount) throws Exception {
        long time = System.currentTimeMillis();
        final Producer<Long, String> producer = createProducer();

        String fullTopic = "Item-" + topic;
        try {
            for (long index = time; index < time + sendMessageCount; index++) {
                final ProducerRecord<Long, String> record =
                        new ProducerRecord(fullTopic, index, id);//todo refactor index into id of item

                RecordMetadata metadata = producer.send(record).get();

                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);

            }
        } finally {
            producer.flush();
            producer.close();
        }
    }
}

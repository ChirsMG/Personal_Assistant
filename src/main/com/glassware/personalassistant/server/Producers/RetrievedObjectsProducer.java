package com.glassware.personalassistant.server.Producers;

import com.glassware.personalassistant.server.Instruction;
import com.glassware.personalassistant.server.InstructionStatus;
import com.glassware.personalassistant.server.ObjectSerializer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class RetrievedObjectsProducer<T> extends Producible<T> {

    public RetrievedObjectsProducer() {
        this.valueSerializerClass = ObjectSerializer.class.getName();
    }

    public void runProducer(String topic, T object) throws Exception {
        long time = System.currentTimeMillis();
        final Producer<Long, T> producer = createProducer();

        String fullTopic = topic;
        try {
            for (long index = time; index < time; index++) {
                final ProducerRecord<Long, T> record =
                        new ProducerRecord(fullTopic, index, object);//todo refactor index into id of item

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

package com.glassware.personalassistant.server.Producers;

import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ObjectSerializer;
import com.glassware.personalassistant.server.ServiceTask;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class RequestProducer extends Producible<ServiceTask> {

    public RequestProducer() {
        this.valueSerializerClass = ObjectSerializer.class.getName();
    }

    public void runProducer(String topic, ServiceTask task, final int sendMessageCount) throws Exception {
        long time = System.currentTimeMillis();
        final Producer<Long, ServiceTask> producer = createProducer();

        String fullTopic="com.glassware.personalassistant.server.Item-"+topic;
        try {
            for (long index = time; index < time + sendMessageCount; index++) {
                final ProducerRecord<Long, ServiceTask> record =
                        new ProducerRecord(fullTopic, index, task);//todo refactor index into id of item

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

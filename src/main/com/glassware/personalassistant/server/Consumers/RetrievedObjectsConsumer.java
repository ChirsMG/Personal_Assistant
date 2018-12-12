package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.Instruction;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RetrievedObjectsConsumer<T> extends Consumable<T> {

    public List<T> runConsumers(String topic,String id) throws InterruptedException {
        Consumer<Long, T> consumer = createConsumer(topic);
        List<T> recordsOut= new ArrayList<>();
        while (true) {
            final ConsumerRecords<Long, T> consumerRecords = consumer.poll(100);

            if (consumerRecords.count() == 0) {
                break;
            }

            consumerRecords.forEach(record -> {
                recordsOut.add(record.value());
            });
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
        return recordsOut;

    }
}

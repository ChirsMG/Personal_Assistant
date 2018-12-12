package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.InstructionStatus;
import com.glassware.personalassistant.server.ServiceTask;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.HashMap;
import java.util.Map;

public class RequestConsumer extends Consumable<ServiceTask>{


    public Map<String,ServiceTask> runConsumers(String topic) throws InterruptedException {
        Consumer<Long, ServiceTask> consumer = createConsumer(topic);
        Map<String,ServiceTask> recordsOut = new HashMap<>();
        while (true) {
            final ConsumerRecords<Long, ServiceTask> consumerRecords = consumer.poll(100);

            if (consumerRecords.count() == 0) {
                break;
            }

            consumerRecords.forEach(record -> {
                recordsOut.put(record.value().getTaskId(),record.value());
            });
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
        return recordsOut;
    }

}

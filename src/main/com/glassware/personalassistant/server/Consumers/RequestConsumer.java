package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.InstructionStatus;
import com.glassware.personalassistant.server.ServiceTask;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Map;

public class RequestConsumer extends Consumable<ServiceTask>{
    public Map<String,ServiceTask> recordsOut;


    public RequestConsumer(Map<String,ServiceTask> storageLocation) {
        recordsOut = storageLocation;
    }


    public void runConsumers(String topic) throws InterruptedException {
        Consumer<Long, ServiceTask> consumer = createConsumer(topic);

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
    }

}

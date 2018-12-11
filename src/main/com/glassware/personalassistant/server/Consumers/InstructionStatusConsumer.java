package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.InstructionStatus;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Map;

public class InstructionStatusConsumer  extends Consumable<InstructionStatus>{
    public Map<String,InstructionStatus> recordsOut;


    public InstructionStatusConsumer(Map<String,InstructionStatus> storageLocation) {
        recordsOut = storageLocation;
    }


    public void runConsumers(String topic) throws InterruptedException {
        Consumer<Long, InstructionStatus> consumer = createConsumer(topic);

        while (true) {
            final ConsumerRecords<Long, InstructionStatus> consumerRecords = consumer.poll(100);

            if (consumerRecords.count() == 0) {
                break;
            }

            consumerRecords.forEach(record -> {
                recordsOut.put(record.value().getInstrucationId(),record.value());
            });
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }

}

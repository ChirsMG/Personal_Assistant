package com.glassware.personalassistant.server.Producers;

import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ObjectSerializer;
import com.glassware.personalassistant.server.Storage.Instruction;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class InstructionProducer extends Producible<Instruction> {
    final int sendMessageCount=1;
    public InstructionProducer() {
        this.valueSerializerClass = ObjectSerializer.class.getName();
    }

    /**
     * creates Kafka producer and returns a UUID that identifies the request.  This is used to retrieve the result by the consumer
     * @param topic
     * @param instruction
     * @return - {String} UUID
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String runProducer(String topic, Instruction instruction) throws ExecutionException, InterruptedException{
        long time = System.currentTimeMillis();
        final Producer<Long, Instruction> producer = createProducer();
//        String instructionUuid = UUID.randomUUID().toString();
//        instruction=instruction.id(instructionUuid);

        String fullTopic = "ItemInstruction";
        System.out.printf("topic: "+fullTopic+"\n");
        try {
            for (long index = time; index < time +sendMessageCount; index++) {
                final ProducerRecord<Long, Instruction> record = new ProducerRecord(fullTopic, index, instruction);//todo refactor index into id of item

                RecordMetadata metadata = producer.send(record).get();

                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);

            }
        } catch(Exception e) {
            System.out.printf("producer error -" +e);
        }finally
         {
            producer.flush();
            producer.close();
        }
        return instruction.id();
    }
}

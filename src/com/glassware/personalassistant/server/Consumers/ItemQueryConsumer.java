package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.InstructionDeserializer;
import com.glassware.personalassistant.server.Storage.Instruction;
import com.glassware.personalassistant.server.Storage.StorageConstants.*;
import javafx.util.Pair;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import java.util.concurrent.LinkedBlockingQueue;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ItemQueryConsumer extends Consumable<Instruction> {
    private final static long TIMEOUT =15; //seconds
    public LinkedBlockingQueue<Pair<Operation,Instruction>> instructions;  // <UUID, <CRUD operation, Instruction Object>>

    public void setInstructions(LinkedBlockingQueue<Pair<Operation, Instruction>> instructions) {
        this.instructions = instructions;
    }

    public ItemQueryConsumer() {
        this.valueDeserializerClass = InstructionDeserializer.class.getName();
        instructions=new LinkedBlockingQueue<>();
    }

    public ItemQueryConsumer instructionQueue(LinkedBlockingQueue q){
        this.instructions=q;
        return this;
    }


    private void parseQuery(String query){

    }

//TODO make operation a configuration on the class - and rename
    public void runItemConsumers() throws InterruptedException {
        Consumer<Long, Instruction> readConsumer = createConsumer("ItemInstruction");
        Consumer<Long, Instruction> deleteConsumer = createConsumer("Item-delete");
        Instant start = Instant.now();

        System.out.println("consuming item instructions");
        while (true) {
            try {
                final ConsumerRecords<Long, Instruction> consumerRecords = readConsumer.poll(100);
                //todo - better break condition
//                if (consumerRecords.count() == 0 && Duration.between(start, Instant.now()).getSeconds()> TIMEOUT) {
//                    System.out.println("Instruction Consumer Timed out");
//                    break;
//                }
                
                consumerRecords.forEach(record -> {
                        Instruction recordObj = record.value();
                        System.out.println("Instruction query: "+recordObj.query());
                        try {
                            instructions.put(new Pair<>(Operation.READ, recordObj));
                        }catch(InterruptedException e){
                            System.out.println("consumer record exception: "+e.toString());
                            //TODO - determine what to do if operation fails to be added
                        }
                });

                readConsumer.commitAsync();

//                if(instructions.size()!=0){ //TODO this is problematic
//                    System.out.println("instructions found");
//                    break;
//                }
            }catch(Exception e){
                System.out.println("exception in IQC: "+e.toString());
                readConsumer.close();
                break;
            }
        }
        System.out.println("DONE");
    }
}



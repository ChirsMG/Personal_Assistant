package com.glassware.personalassistant.server.Consumers;

import com.glassware.personalassistant.server.InstructionDeserializer;
import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ItemDeserializer;
import com.glassware.personalassistant.server.Storage.Instruction;
import com.glassware.personalassistant.server.Storage.StorageConstants;
import java.util.concurrent.BlockingQueue;
import javafx.util.Pair;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class ItemModifierConsumer extends Consumable<Item> {
    private final static long TIMEOUT = 15; //seconds
    private BlockingQueue<Pair<StorageConstants.Operation,Item>> modifications;

    public ItemModifierConsumer modificationQueue(BlockingQueue q){
        this.modifications=q;
        return this;
    }

    public ItemModifierConsumer() {
        this.valueDeserializerClass = ItemDeserializer.class.getName();
    }
    public void runModifierConsumers() throws InterruptedException {
        Consumer<Long, Item> createItemConsumer = createConsumer("ItemCreate");
        Consumer<Long, Item> updateConsumer = createConsumer("ItemUpdate");
        Instant start = Instant.now();
//        System.out.println("entering consumer loop");
        while (true) {
            try {
//                System.out.println("polling consumers");
                final ConsumerRecords<Long, Item> createRecords = createItemConsumer.poll(100);
//                final ConsumerRecords<Long, Item> updateRecords = updateConsumer.poll(100);


//                System.out.println("attempting to read creation records");
                for (ConsumerRecord<Long, Item> createRecord : createRecords) {
                    System.out.println("create instruction received");
                    Item resultingItem=createRecord.value();
//                    System.out.println(createRecord.value().getClass());
                    //Don't know why I have to cast
                    modifications.put(new Pair<>(StorageConstants.Operation.CREATE, resultingItem));
                }

//                System.out.println("attempting to read update records");
//                for (ConsumerRecord<Long, Item> updateRecord : updateRecords) {
//                    System.out.println("update instruction received");
//                    modifications.put(new Pair<>(StorageConstants.Operation.UPDATE, updateRecord.value()));
//                }

                createItemConsumer.commitAsync();
//                if(modifications.size()!=0){ //TODO this is problematic
//                    System.out.println("modifications found");
//                    break;
//                }
//                if (Duration.between(start, Instant.now()).getSeconds()> TIMEOUT) {
//                    System.out.println("Modifier Consumer Timed out");
//                    break;
//                }
            }catch(InterruptedException e){
                System.out.println("Interrupting");
                throw e;
            } catch(Exception e){
                System.out.println("exception: "+e.getMessage());
                e.printStackTrace();
                break;
            }
        }
        createItemConsumer.close();
        updateConsumer.close();
    }
}

package com.glassware.personalassistant.server.Storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glassware.personalassistant.server.Consumers.ItemModifierConsumer;
import com.glassware.personalassistant.server.Consumers.ItemQueryConsumer;
import com.glassware.personalassistant.server.Consumers.ModifierConsumerRunnable;
import com.glassware.personalassistant.server.Consumers.QueryConsumerRunnable;
import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.MongoDBConnector;
import com.glassware.personalassistant.server.Producers.QueryResultProducer;
import com.glassware.personalassistant.server.REST.PersonalAssistantGateway;
import com.glassware.personalassistant.server.Storage.StorageConstants.*;
import javafx.util.Pair;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class StorageService {
    private final static String BOOTSTRAP_SERVERS_CONFIG = "";
    private final static String KEY_SERIALIZER_CLASS_CONFIG = "";
    private final static String VALUE_SERIALIZER_CLASS_CONFIG = "";
    private final static int TIMEOUT = 5;
    private static Logger LOGGER = Logger.getLogger(PersonalAssistantGateway.class.getName());

//    private static Callable<LinkedBlockingQueue> modifierCallable = new
//    };

//    private static Callable<LinkedBlockingQueue> instructionCallable = () -> {
//        ItemQueryConsumer storageConsumer;
//        try {
//            storageConsumer = new ItemQueryConsumer();
//            storageConsumer.runItemConsumers();
//        } catch (Exception e) {
//            System.out.println("exception in instructionCallable: " + e.getMessage());
//            return new LinkedBlockingQueue<Pair>(); //return empty queue
//        }
//        return storageConsumer.instructions;
//    };


    public static void main(String[] argv) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        System.out.println("storage service started.");
        MongoDBConnector mongoConnector = new MongoDBConnector()
                .storageName("test").initialize();

        mongoConnector.collection("Items");

        LinkedBlockingQueue<Pair<Operation, Instruction>> instructionQueue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<Pair<Operation, Item>> modificationQueue = new LinkedBlockingQueue<>();
        Future modifierFuture;
        Future instructionFuture;
        System.out.println("starting storage loop...");
        int i = 0;
//        while(true){// removed for debugging
        try {
            //todo fix - futures may be null resulting in NPE
//                System.out.println("running item  modifier consumer");
            modifierFuture = executor.submit(new ModifierConsumerRunnable<Pair<Operation, Item>>(modificationQueue));
//                System.out.println("running item  instruction consumer");
            instructionFuture = executor.submit(new QueryConsumerRunnable<Pair<Operation, Instruction>>(instructionQueue));
        } catch (Exception e) {
            System.out.println("Consumer exception in storage service: " + e.getMessage());
            return;
        }
        while (true) { //FOR DEBUG
//            System.out.printf("storage loop iteration: %d \n", i);
            //TODO consider following
                /*
                    maybe I should type instruction object
                    CODE SMELL around static context    `
                 */
//            System.out.println("constructing consumers");

//            System.out.println("waiting on a consumer futures");
//            while (!modifierFuture.isDone() && !instructionFuture.isDone() ) {
//                try {
//                    modificationQueue = modifierFuture.get(5500, TimeUnit.MILLISECONDS);
//                    instructionQueue = instructionFuture.get(5500, TimeUnit.MILLISECONDS);
//                }catch (Exception e) {
//                    System.out.println(e);
//                    System.out.println("Exception: " + e.getMessage());
//                    e.printStackTrace();
//                }
//
//            }
//            System.out.println("one or more futures completed");
//            System.out.println("instructionQueue length: " + instructionQueue.size());
//            System.out.println("modificationQueue length: " + modificationQueue.size());
//            Iterator storageItr = instructionQueue.iterator();
//            Iterator modificationItr = modificationQueue.iterator();
//                System.out.println("entering iterator loop");
                try {

                    if (!instructionQueue.isEmpty()) {
                        QueryResultProducer queryProducer = new QueryResultProducer();
                        System.out.println("reading storage iterator");
                        Pair<Operation, Instruction> instruction = instructionQueue.remove();
                        switch (instruction.getKey()) {
                            case DELETE:
                                mongoConnector.delete(instruction.getValue().toString()); //TODO explain or refactor
                                break;
                            case READ:
                                System.out.println("handling read operation from instruction: " + instruction.getValue().query());
                                List<Item> foundItems = new LinkedList<>();
                                List<String> jsonResult = mongoConnector.read(instruction.getValue().query().toString());
                                System.out.printf("read from mongodb %d items \n", jsonResult.size());
                                for (String jsonDoc : jsonResult) {
                                    foundItems.add(new ObjectMapper().readValue(jsonDoc, Item.class));
                                }
                                queryProducer.runProducer(instruction.getValue().id(), foundItems);
                                break;
                            default:
                                System.out.println("unexpected operation");
                                LOGGER.info("Unexpected instruction in item queue");
                                break;
                        }
                    }
                    if (!modificationQueue.isEmpty()) {
                        System.out.println("reading modification iterator");
                        Pair<Operation, Item> modification = modificationQueue.remove();
                        switch (modification.getKey()) {
                            case CREATE:
                                mongoConnector.write(modification.getValue());
                                break;
                            case UPDATE:
                                //TODO
                                break;
                            default:
                                LOGGER.info("Unexpected instruction in item queue");
                                break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Exception in storage service: " + e.getMessage());
                    e.printStackTrace();
                    LOGGER.info("instruction in use");
                    LOGGER.info("threw exception: " + e);
                    break;
                }
                //Ensure future completion
            if (instructionFuture.isDone() || instructionFuture.isCancelled()) {
                System.out.println("instruction thread ended");
                break;
            }

            if (modifierFuture.isDone() || modifierFuture.isCancelled()) {
                System.out.println("modifier thread ended");
                break;
            }
//            if (!(modifierFuture.isCancelled() || modifierFuture.isDone())) {
//                System.out.println("force completing modification future");
////                modifierFuture.cancel(false);
//                while (!modifierFuture.isDone()) {
//                    continue;
//                }
//            }
            i++; //FOR DEBUG
        }

    }
}

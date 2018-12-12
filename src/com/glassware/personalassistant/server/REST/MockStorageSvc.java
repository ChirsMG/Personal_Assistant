package com.glassware.personalassistant.server.REST;

import com.glassware.personalassistant.server.Consumers.ItemQueryConsumer;
import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.Producers.QueryResultProducer;
import com.glassware.personalassistant.server.Storage.Instruction;
import com.glassware.personalassistant.server.Storage.StorageConstants;
import javafx.util.Pair;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class MockStorageSvc {


    final static Item item001 = new Item("0001", "a test item");
    final static Item item002 = new Item("0002", "another test item");
    final static Item item003 = new Item("0003", "a third test item");
    final static Item item004 = new Item("0004", "the final test item");
    final static List<String> ALL_IDS = new ArrayList<String>() {{
        add("0001");
        add("0002");
        add("0003");
        add("0004");
    }};
    final static Map<String, Item> items;

    static {
        items = new HashMap<>();
        items.put(item001.id, item001);
        items.put(item002.id, item002);
        items.put(item003.id, item003);
        items.put(item004.id, item004);
    }

    private List<String> parseQuery(String query) {
        //special commands check
        if (query.compareTo("ALL") == 0) {
            return ALL_IDS;
        }

        List resultList = new ArrayList();
        Object queryObj;
        //List
        try {
            queryObj = new JSONParser().parse(query);
        } catch (ParseException e) {
            System.out.printf("parsing exception %s \n", e);
            resultList.add("Parsing Error");
            return resultList;
        }
        //CODE SMELL
        if (queryObj instanceof List) {
            for (String item : (List<String>) queryObj) {
                resultList.add(item);
            }
        }
        return resultList;
        //Conditional
    }

    void Start() {
        ItemQueryConsumer consumer = new ItemQueryConsumer();
        QueryResultProducer producer = new QueryResultProducer();
        try {
            consumer.runItemConsumers();
        } catch (InterruptedException e) {

            //todo not sure what an interrupt exception means
        }
        //todo add delete support
        List<String> readInstructions = new ArrayList();
//        readInstructions.addAll(consumer.instructions.keySet());
        Iterator storageItr = consumer.instructions.iterator();
        while (storageItr.hasNext()) {
            Pair<StorageConstants.Operation, Instruction> instructionPair = consumer.instructions.remove();
            System.out.printf("entry: %s", storageItr.toString());

            StorageConstants.Operation operation = instructionPair.getKey();
            Instruction instruction = instructionPair.getValue();
            //since mock service assume READ for now //TODO add delete support in mock
            List<String> result = parseQuery(instruction.query());

            List<Item> retrievedItems = new ArrayList();
            for (String id : result) {
                try {
                    retrievedItems.add(items.get(id));
                } catch (Exception e) {
                    //todo
                }
            }
            try {

                producer.runProducer(instruction.id(), retrievedItems);
            } catch (Exception e) {
                //TODO what to do? if it fails
            }
        }


    }

}



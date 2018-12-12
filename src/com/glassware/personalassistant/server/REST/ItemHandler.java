package com.glassware.personalassistant.server.REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glassware.personalassistant.server.Consumers.QueryResultCallable;
import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.Producers.InstructionProducer;
import com.glassware.personalassistant.server.Producers.ItemProducer;
import com.glassware.personalassistant.server.Storage.Instruction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ItemHandler extends RestHandler {
    private final static Logger LOGGER = Logger.getLogger(ItemHandler.class.getName());
    private final static String REQUEST_ALL = "ALL";

    //Returns
    private String buildGetQuery(Map<String, Object> parameters) {
        // handle raw query
        System.out.println("parameters: " + parameters);
        try {
            if (parameters.containsKey("query")) {
                //todo validate query
                return parameters.get("query").toString(); //TODO build from object??
            } else if (parameters.containsKey("id")) {
                return (parameters.get("id") instanceof List) ? (new ObjectMapper().writeValueAsString(parameters.get("id"))) : (parameters.get("id").toString());
            }
            return new ObjectMapper().writeValueAsString(parameters.get("id"));
        } catch (Exception e) {
            return "error - " + e.getMessage();
        }
    }


    private Map parseQueryString(String query) {
        System.out.println("parsing query: " + query);
        Map queryMap = new HashMap();
        System.out.println(query.split("&").length);
        for (String paramPair : query.split("&")) {
            String[] param = paramPair.split("=");
            List<String> newList;
            if (queryMap.containsKey(param[0])) {
                if (queryMap.get(param[0]) instanceof List) {
                    newList = (List) queryMap.get(param[0]);
                } else {
                    newList = new ArrayList<String>();
                }
                newList.add(param[1]);
                queryMap.put(param[0], newList);
            } else {
                queryMap.put(param[0], param[1]);
            }


        }
        return queryMap;
    }


    protected String loadBody(InputStream body) {
        BufferedReader d = new BufferedReader(new InputStreamReader(body));
        String content = d.lines().collect(Collectors.joining("\n"));
        LOGGER.info("request content: " + content);
        return content;
    }

    @Override
    protected String handleRequest(InputStream httpBody, String method, String[] pathArgs, String queryParams) { //TODO enum method
        /**
         * will need to handle
         * GET - read
         * PUT - update
         * POST - Create
         * DELETE - delete
         */
        ExecutorService pool = Executors.newFixedThreadPool(3);
        String body = loadBody(httpBody);
        ItemProducer producer = new ItemProducer();
        InstructionProducer getProducer = new InstructionProducer();

        String instructionId = null;
        switch (method.toLowerCase()) {
            case "post":
                System.out.println("body: " + body);
                UUID id;
                try {
                    Item item = new ObjectMapper().readValue(body, Item.class);
                    id = UUID.randomUUID();
                    item = item.id(id.toString());
                    producer.produceNewItem(item);
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "exception", e);
                    return "Error";
                    //TODO log error and respond with error
                } catch (ExecutionException e) {
                    LOGGER.log(Level.SEVERE, "exception", e);
                    return "Error";
                    //TODO log error and respond with error
                } catch (InterruptedException e) {
                    LOGGER.log(Level.SEVERE, "exception", e);
                    return "Error";
                    //TODO log error and respond with error
                }
                System.out.println("returning id to client");
                return "{\"status\":\"Success\",\"id\":\"" + id + "\"}"; //Todo format better?
            //TODO try and confirm status with server for mro info
            case "put":
                break;
            case "get":
                System.out.println("handling get request");
                Object requestObj;
                List<Item> retrievedItems = new ArrayList<>();
                Future<List<Item>> consumerFuture;
                try {
                    System.out.println(queryParams);
                    Map parameters = parseQueryString(queryParams);
                    System.out.println("parameters: " + parameters);
                    assert parameters != null;
                    System.out.println("Query: " + buildGetQuery(parameters));
                    Instruction instruction = new Instruction().query(buildGetQuery(parameters));

                    instructionId = UUID.randomUUID().toString();
                    instruction=instruction.id(instructionId);
                    QueryResultCallable queryCallable=new QueryResultCallable<Item>(retrievedItems, instructionId);


                    if (body.toString().compareTo(REQUEST_ALL) == 0) {
                        //TODO yes I know this is a HUGE anti pattern
                        instruction.query(body);
                        instructionId = getProducer.runProducer("read", instruction);
                    } else {
                        instruction.query(buildGetQuery(parameters));
                        // a list of requested ids is received
                        instructionId = getProducer.runProducer("read", instruction);
                    }
                    queryCallable.setQueryId(instructionId);
                    consumerFuture = pool.submit(queryCallable);

                } catch (ExecutionException e) {
                    LOGGER.log(Level.SEVERE, "exception", e);
                    //TODO log error and respond with error
                    return "ERROR- Exception";
                } catch (InterruptedException e) {
                    LOGGER.log(Level.SEVERE, "exception", e);
                    //TODO log error and respond with error
                    return "ERROR- Exception";
                }
                System.out.println("request mostly handled");
                HashMap<String, Item> items = new HashMap<>();
                //todo handle errors with unable to retrieve
                try {
                    System.out.println("Getting id");
//                    QueryResultConsumer consumer = new QueryResultConsumer();
                    retrievedItems = consumerFuture.get();
                    if (retrievedItems == null) {
                        System.out.println("no items found");
                        return "{\"status\":\"items not found\"}";
                    } else {
                        for (Item i : retrievedItems) {
                            items.put(i.id(), i);
                        }
                    }
                    return (new ObjectMapper().writeValueAsString(retrievedItems));
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "exception", e);
                }
                break;
            case "delete":
                break;
            default:
                break;
        }

        return "item request received  with method: " + method;

    }
}

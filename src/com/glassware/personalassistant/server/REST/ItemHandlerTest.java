package com.glassware.personalassistant.server.REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.glassware.personalassistant.server.Consumers.Consumable;
import com.glassware.personalassistant.server.Consumers.StorageConsumer;
import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ItemDeserializer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import javafx.util.Pair;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class ItemHandlerTest extends Consumable<Item> {
    private final ExecutorService pool = Executors.newFixedThreadPool(10);
    private final static Logger LOGGER = Logger.getLogger(ItemHandlerTest.class.getName());

    private final static String ITEM_JSON = "{\"id\":12345,\"name\":\"Test No.1\",\"description\":\"test object No. 1\"}";
    private final static String GET_REQUEST_JSON_1 = "[\"0001\",\"0002\",\"0003\"]";
    private final static String REQUEST_ALL = "ALL";
    private ItemHandler handler;
    InputStream itemStream, getStream, getAllStream;
    Consumer<Long, Item> consumer;
    Future result;


    private Future runTestConsumer() {
        return pool.submit(new Callable<String>() {
            @Override
            public String call() {
                consumer = createConsumer("Item-create");
                final ConsumerRecords<Long, Item> consumerRecords = consumer.poll(100);

                consumerRecords.forEach(record -> {
                    Item item = record.value();
                    assert item == new ItemDeserializer().deserialize("someString", ITEM_JSON.getBytes());
                });
                consumer.commitAsync();
                return "true";
            }
        });

    }

    private Future runMockStrgSvc() {
        return pool.submit(new Callable<String>() {
            @Override
            public String call() {
                MockStorageSvc mockStorage = new MockStorageSvc();
                mockStorage.Start();
                return "DONE";
            }
        });

    }


    @Before
    public void setUp() {
        handler = new ItemHandler();
        itemStream = new ByteArrayInputStream(ITEM_JSON.getBytes());
        getStream = new ByteArrayInputStream(GET_REQUEST_JSON_1.getBytes());

        result = runMockStrgSvc();
    }

    @Test
    public void handleRequest() throws Exception {
        String[] idArgs = {"12345"};
        Future result = runTestConsumer();
        handler.handleRequest(itemStream, "Post", idArgs,"");
        assert result.get() == "true";
    }

    @Test
    public void getSelectItemsTest() throws Exception {
        String[] idArgs = {"001"};
        String requestResult = handler.handleRequest(getStream, "GET", null,"");

        LOGGER.info(requestResult);
        while (!result.isDone()) {
        }
        LOGGER.info(result.toString());


        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        Map<String, Item> items = objectMapper.readValue(requestResult, typeFactory.constructMapType(HashMap.class, String.class, Item.class));

        assert items.size() == 3;
        assert items.get("0003").getClass() == Item.class;
        assert items.get("0002").getClass() == Item.class;
        assert items.get("0001").getClass() == Item.class;
        assert items.containsKey("0004") == false;

    }

    @Test
    public void getAllItemsTest() throws Exception {
        String[] idArgs = {"001"};
        String requestResult = handler.handleRequest(new ByteArrayInputStream(REQUEST_ALL.getBytes()), "GET", null,"");

        LOGGER.info(requestResult);
        while (!result.isDone()) {
        }
        LOGGER.info(result.toString());


        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        Map<String, Item> items = objectMapper.readValue(requestResult, typeFactory.constructMapType(HashMap.class, String.class, Item.class));

        assert items.size() == 4;
        assert items.get("0003").getClass() == Item.class;
        assert items.get("0002").getClass() == Item.class;
        assert items.get("0001").getClass() == Item.class;
        assert items.get("0004").getClass() == Item.class;

    }


}
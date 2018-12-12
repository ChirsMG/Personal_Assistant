package com.glassware.personalassistant.server.Gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glassware.personalassistant.server.Consumers.RequestConsumer;
import com.glassware.personalassistant.server.Consumers.RetrievedObjectsConsumer;
import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.Producers.RequestProducer;
import com.glassware.personalassistant.server.ServiceTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

class ItemHandlerTest {
    final static String updateBody = "";
    private final static String TEST_ID_1 = "0001";
    private static String TEST_ITEM_STRNG = "";
    private static String MALFORMED_ITEM_STRNG = "";

    private static String TEST_UPDATE_SUCCESS = "Updated request received successfully";
    private static String TEST_DELETE_SUCCESS = "Delete request received successfully";
    private static String TEST_CREATE_SUCCESS = "Create request received successfully";

    private static String TEST_CREATE_FAILURE = "Create request failed to be processed";

    //TODO store in constants file
    private static String GET_SUCCESS = "2000";
    private static String CREATE_SUCCESS = "2001";
    private static String UPDATE_SUCCESS = "2002";
    private static String DELETE_SUCCESS = "2003";

    private static String GET_FAILURE = "5000";
    private static String CREATE_FAILURE = "5001";
    private static String UPDATE_FAILURE = "5002";
    private static String DELETE_FAILURE = "5003";

    private static String TOPIC="";
    private static int MESSAGE_COUNT=0;

    ItemHandler handler;
    ObjectMapper mapper;
    Item testItem;

    @Mock
    RequestProducer producer;


    @BeforeEach
    public void setUp() throws Exception{
        mapper= new ObjectMapper();
        testItem = new Item(TEST_ID_1,"Test Item");

        RetrievedObjectsConsumer<Item> consumer=mock(RetrievedObjectsConsumer.class);
        handler = new ItemHandler();
        List<Item> testItems=new ArrayList<>();
        testItems.add(testItem);
        when(consumer.runConsumers(eq(TOPIC),anyString())).thenReturn(testItems);
    }

    @Test
    public void getItem() throws Exception {
        Result result=handler.getItem(TEST_ID_1);
        assert result.statusCode == UPDATE_SUCCESS;
        assert result.statusMsg == TEST_UPDATE_SUCCESS;
        assert result.body == mapper.writeValueAsString(testItem);
    }

    @Test

    public void updateItem() throws Exception {
        Map updates = new HashMap<String, Object>();

        //expect return confirmation
        Result result = handler.updateItem(TEST_ID_1, updates);
        assert result.statusCode == UPDATE_SUCCESS;
        assert result.statusMsg == TEST_UPDATE_SUCCESS;
        assert result.body == mapper.writeValueAsString(testItem);
    }

    @Test
    public void deleteItem() throws Exception {
        //expect return confirmation
        Result result = handler.deleteItem(TEST_ID_1);
        assert result.statusCode == DELETE_SUCCESS;
        assert result.statusMsg == TEST_DELETE_SUCCESS;
        assert result.body == "";
    }

    @Test
    public void createItem() throws Exception {
        //expect return confirmation

        Result result = handler.createItem(TEST_ITEM_STRNG);
        assert result.statusCode == CREATE_SUCCESS;
        assert result.statusMsg == TEST_CREATE_SUCCESS;
        assert result.body == "";
    }

    @Test
    public void malformedItem() throws Exception {

        Result result = handler.createItem(MALFORMED_ITEM_STRNG);
        assert result.statusCode == CREATE_FAILURE;
        assert result.statusMsg == TEST_CREATE_FAILURE;
        assert result.body == "";
    }

    @Test
    public void handleRequest() throws Exception {
        Map updates = new HashMap<String, Object>();

        // spy ?or mock CRUD operations
        //Check GET method routing
        //Check PUT/POST method routing
        //Check DELETE method routing
        //Check PATCH method routing
        ItemHandler mockedHandler=spy(ItemHandler.class);
        mockedHandler.handleRequest("","GET");
        mockedHandler.handleRequest("","POST");
        mockedHandler.handleRequest("","DELETE");
        mockedHandler.handleRequest("","PATCH");
        verify(mockedHandler,times(1)).getItem(TEST_ID_1);
        verify(mockedHandler,times(1)).createItem(mapper.writeValueAsString(testItem));
        verify(mockedHandler,times(1)).deleteItem(TEST_ID_1);
        verify(mockedHandler,times(1)).updateItem(TEST_ID_1,updates);

        assert false;
    }

}
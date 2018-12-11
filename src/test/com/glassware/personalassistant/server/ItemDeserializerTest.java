package com.glassware.personalassistant.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests Serializer and Deserializer in single unit test
 *
 * Justification: The serialization and deserialization necessarily work in conjunction.
 * testing them separately adds little value.  The primary value comes from their functionality
 * as a unit
 */
public class ItemDeserializerTest {
    private final static String ID_1="TEST_ID_1";

    Item testItem1;
    ObjectSerializer serializer;
    ItemDeserializer deserializer;

    @Before
    public void setUp() throws Exception {
        serializer= new ObjectSerializer();
        deserializer= new ItemDeserializer();
        testItem1 = new Item(ID_1,"Test Item");
    }

    @Test
    public void deserialize(){
        byte[] bytes=serializer.serialize("does not matter",testItem1);
        Item resultItem=deserializer.deserialize("does not mater",bytes);
        assert resultItem.getId().equals(testItem1.getId());
        assert resultItem.name.equals(testItem1.name);
        assert resultItem.content.equals(testItem1.content);
        assert resultItem.getDescription().equals(testItem1.getDescription());
    }

}
package com.glassware.personalassistant.server.Gateway;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemHandlerTest {
    @Before
    public void setUp(){
        //Mock producers
    }


    @Test
    public void getItem() throws Exception {
        //Cache Hit
        //Cache Miss
        assert false;
    }

    @Test

    public void updateItem() throws Exception {
        // Cache Miss
        // Cache Hit
        // Instruction test
        assert false;
    }

    @Test
    public void deleteItem() throws Exception {
        // Instruction test
        // Cache Hit
        // Cache Miss - expect no error
        assert false;
    }

    @Test
    public void createItem() throws Exception {
        // Added to cache
        // Instruction sent
        assert false;
    }

    @Test
    public void handleRequest() throws Exception {
        // spy ?or mock CRUD operations
        // check for proper function call for request
        assert false;
    }

}
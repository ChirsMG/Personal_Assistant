package com.glassware.personalassistant.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glassware.personalassistant.server.Storage.EmbeddedMongoDB;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MongoDBConnectorTest {

    @Mock
    private MongoClient mockClient;
    @Mock
    private MongoCollection mockCollection;
    @Mock
    private MongoDatabase mockDB;
    @Mock
    private MongoIterable mongoIterable;
    @Mock
    private FindIterable iterable;
    @Mock
    private MongoCursor cursor;

    @InjectMocks
    private MongoDBConnector connector;

    private EmbeddedMongoDB embeddedMongo;
    private Document newDoc;

    @Before
    public void initMocks() throws Exception {
        newDoc = new Document("id",UUID.randomUUID().toString()).append("name", "Test No.1").append("description", "a test");
//        EmbeddedMongoDB embeddedMongo = new EmbeddedMongoDB();
//        embeddedMongo.start();

//
//        when(mockClient.getDatabase(anyString())).thenReturn(mockDB);
//        when(mockDB.getCollection(anyString())).thenReturn(mockCollection);
//        when(mockDB.listCollectionNames()).thenReturn(mongoIterable);
//        when(mockCollection.find(new Document("name", "Test No.1"))).thenReturn(iterable);
//        doCallRealMethod().when(iterable).forEach(Matchers.<Block<String>>any());
//        when(iterable.iterator()).thenReturn(cursor);
//        when(cursor.hasNext()).thenReturn(true).thenReturn(false);
//        when(cursor.next()).thenReturn(newDoc);
        connector = new MongoDBConnector().storageName("test").initialize();
//        MockitoAnnotations.initMocks(this);


    }

    @After
    public void tearDown() {

//        embeddedMongo.stop();
    }


    @Test
    public void storageName() throws Exception {
    }

    @Test
    public void collection() throws Exception {
        connector.collection("test");
        assert true;
    }

    @Test
    public void read() throws Exception {
        write1();
        List<Item> items=new ArrayList<>();
        List<String> result=connector.read(newDoc.get("id").toString());
        result.forEach(item->{
            try {
                items.add(new ObjectMapper().readValue(item, Item.class));
            }catch(Exception e){
                System.out.print(e);
            }
        });

        System.out.println("read result: "+result);
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void write() throws Exception {
    }

    @Test
    public void write1() throws Exception {
        connector.collection("test");
        Boolean result=connector.write(newDoc);
        assert result==true;
        System.out.println("result "+result);
    }

}
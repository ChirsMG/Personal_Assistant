package server;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.model.*;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import org.bson.BSON;
import org.bson.Document;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;
import java.util.Map;


public class MongoDBConnector implements StorageConnector {
    private final static Logger LOGGER = Logger.getLogger(PersonalAssistantGateway.class.getName());


    private MongoClient client;
    private MongoDatabase database;
    private String storageName;
    private MongoCollection<Document> collection;
    private CreateCollectionOptions collectionOptions;


    MongoDBConnector() {
        this.client = new MongoClient();
        this.database = client.getDatabase(this.storageName);
    }

    public void storageName(String name) {
        this.storageName = name;

    }

    /**
     * creates collection if it does not exist
     *
     * @param collection - the collection name to be validated
     */
    public void collection(String collection) {
        MongoIterable<String> collectionList = this.database.listCollectionNames();
        collectionList.forEach((Block<String>) name -> {
            if (name.equals(collection)) {
                this.collection = database.getCollection(collection);
                ;
            }
        });

        if (this.collection == null) {
            this.database.createCollection(collection, new CreateCollectionOptions().autoIndex(true));
        }

        this.collection = this.database.getCollection(collection);


    }

    /**
     * finds documents in collection that match query
     *
     * NOTE: takes in a string of JSON to maintain consistent signature across multiple
     * storage solutions
     * *
     *
     * @param query - the JSON string defining a BSON filter to query the database against
     * @return - {FindIterable<Document>} that matches the query
     */
    @Override
    public Object read(String query) {
        //ASSUMES: query string is JSON formatted BSON
        JSONParser parser = new JSONParser();
        Map<String,Object> queryObj;
        try {
            queryObj=(Map<String,Object>)parser.parse(query);
        } catch (Exception e) {
            //Not sure what exception is thrown but some code examples indicate it might
            queryObj=new HashMap<String,Object>();
            LOGGER.log(Level.INFO,e.getMessage());
            return null;
        }
        Document filter=new Document(queryObj);
        FindIterable<Document> result = this.collection.find(filter);
        return result;


    }

    @Override
    public boolean delete(String query) {
        //ASSUMES: query string is JSON formatted BSON
        JSONParser parser = new JSONParser();
        Map<String,Object> queryObj;
        try {
            queryObj=(Map<String,Object>)parser.parse(query);
        } catch (Exception e) {
            //Not sure what exception is thrown but some code examples indicate it might
            queryObj=new HashMap<String,Object>();
            LOGGER.log(Level.INFO,e.getMessage());
            return false;
        }
        Document filter=new Document(queryObj);
        DeleteResult result = this.collection.deleteMany(filter);
        return result.wasAcknowledged();
    }

    @Override
    public boolean write(Map<String, Object> item) {
        Document docToInsert = new Document();
        docToInsert.putAll(item);
        try {
            //trys to match Id and updated else insert (upsert)
            this.collection.updateOne(Filters.eq("_id", docToInsert.getObjectId("_id")), docToInsert, new UpdateOptions().upsert(true));
            return true;
        } catch (MongoException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    /**
     * inserts a document as provide into collection or
     * updates the document if one with the same id is found
     *
     * @param doc - the document to insert
     * @return true- on success , false- on failure
     */
    public boolean write(Document doc) {
        try {
            this.collection.insertOne(doc);
            return true;
        } catch (MongoException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

}

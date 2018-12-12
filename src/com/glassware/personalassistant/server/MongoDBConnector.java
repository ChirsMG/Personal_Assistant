package com.glassware.personalassistant.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glassware.personalassistant.server.REST.PersonalAssistantGateway;
import com.mongodb.Block;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.model.*;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import jdk.internal.org.objectweb.asm.TypeReference;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Map;

import static com.glassware.personalassistant.server.Storage.StorageConstants.REQUEST_ALL_AVAILABLE;

//TODO create evaluation before continuing
public class MongoDBConnector implements StorageConnector<MongoDBConnector> {
    private Logger LOGGER = Logger.getLogger(PersonalAssistantGateway.class.getName());


    private MongoClient client;
    private MongoDatabase database;
    private String storageName;
    private MongoCollection<Document> collection;
    private CreateCollectionOptions collectionOptions;

    private boolean isUUID(String tester) {
//    boolean result=tester.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");
        return tester.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");
    }

    private Bson toBsonQuery(String DtoQuery) throws ParseException {
        Object query;
        Bson filter;
        System.out.println("parsing query");
        if (DtoQuery.equals(REQUEST_ALL_AVAILABLE)) {
            return null;
        }
        if (isUUID(DtoQuery)) {
            System.out.println("query is UUID");
            filter = Filters.eq("id", DtoQuery);
            return filter;
        }
        query = new JSONParser().parse(DtoQuery);
        List<ObjectId> ids = new ArrayList<>();

        if (query instanceof List) {
            System.out.println("query is list of ids");
            for (String id : (List<String>) query) {
                System.out.println(id);
                ids.add(new ObjectId(id));
            }
            filter = Filters.in("id", ids);
        } else {
            return null;
        }
        return filter;
    }

    public MongoDBConnector() {
        this.client = new MongoClient();
    }

    public MongoDBConnector(String host, int port) {
        this.client = new MongoClient(host, port);
    }

    public MongoDBConnector initialize() {
        this.database = client.getDatabase(this.storageName);
        if (this.database == null) {
            throw new IllegalArgumentException("not a valid DB name");
        }
        return this;
    }

    public MongoDBConnector storageName(String name) {
        this.storageName = name;
        return this;
    }

    /**
     * creates collection if it does not exist
     *
     * @param collection - the collection name to be validated
     */
    public MongoDBConnector collection(String collection) {
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

        return this;
    }

    /**
     * finds documents in collection that match query
     * <p>
     * NOTE: takes in a string of JSON to maintain consistent signature across multiple
     * storage solutions
     * *
     *
     * @param query - the JSON string defining a DTO filter to query the database against
     * @return - {FindIterable<Document>} that matches the query
     */
    @Override
    public List<String> read(String query) {
        try {
            List<String> jsonResults = new ArrayList<>();
            Bson filter = toBsonQuery(query);
            MongoCursor<Document> result;
            if (filter == null) {
                result = this.collection.find().iterator();
            } else {
                MongoCursor<Document> testResult = this.collection.find().iterator();
                while (testResult.hasNext()) {
                    System.out.println(testResult.next());
                }

                result = this.collection.find(filter).iterator();
            }

            while (result.hasNext()) {
                jsonResults.add(result.next().toJson());
            }
            if (jsonResults.size() == 0) {
                System.out.println("No results found");
            }
            System.out.println("Read results: " + jsonResults);
            return jsonResults;
        } catch (Exception e) {
            System.out.println("read failed");
            System.out.println(e.getCause());
            System.out.println(e);
            return null;
        }


    }

    @Override
    public boolean delete(String query) {
        //ASSUMES: query string is JSON formatted BSON
        JSONParser parser = new JSONParser();
        Map<String, Object> queryObj;
        try {
            queryObj = (Map<String, Object>) parser.parse(query);
        } catch (Exception e) {
            //Not sure what exception is thrown but some code examples indicate it might
            queryObj = new HashMap<String, Object>();
            LOGGER.log(Level.INFO, e.getMessage());
            return false;
        }
        Document filter = new Document(queryObj);
        DeleteResult result = this.collection.deleteMany(filter);
        return result.wasAcknowledged();
    }

    @Override
    public boolean write(Object item) {
        Document docToInsert = new Document();

        ObjectMapper objectMapper = new ObjectMapper();
        String objectJson;
        try {
            if (item instanceof String) {
                //assume JSON
                objectJson = (String) item;
            } else {
                objectJson = objectMapper.writeValueAsString(item);
            }
                        docToInsert = docToInsert.parse(objectJson);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "exception thrown writing to JSON");
            LOGGER.log(Level.SEVERE, e.getMessage());
            return false;
        }

        try {
            //trys to match Id and updated else insert (upsert)
            this.collection.replaceOne(Filters.eq("id", docToInsert.get("id")), docToInsert, new UpdateOptions().upsert(true));//todo support partial modifications
            return true;
        } catch (MongoException e) {
            System.out.println("Exception: " + e);
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

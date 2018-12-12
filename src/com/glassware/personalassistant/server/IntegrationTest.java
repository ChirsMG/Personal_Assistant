package com.glassware.personalassistant.server;

import com.glassware.personalassistant.server.REST.GatewaySvc;
import com.glassware.personalassistant.server.REST.MockStorageSvc;
import com.glassware.personalassistant.server.REST.PersonalAssistantGateway;
import com.glassware.personalassistant.server.Storage.Instruction;
import com.glassware.personalassistant.server.Storage.StorageService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import sun.net.www.http.HttpClient;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class IntegrationTest {
    List<String> writtenIds;
    String serviceResult;
    private final ExecutorService pool = Executors.newFixedThreadPool(10);
    private final String itemTestJson1 = "{\"name\":\"testItem-00001\",\"description\": \"item is for testing purposes only this should not exist out side of testing environments\"}";
    private Instant start;
    private static int TIMEOUT=120;

    private Future startGateway() {
        return pool.submit(new Callable<String>() {
            @Override
            public String call() {
                System.out.println("Starting gateway service");
                String[] args = new String[0];
                PersonalAssistantGateway gatewaySvc = new PersonalAssistantGateway();
                try {
                    gatewaySvc.main(args);
                } catch (Exception e) {
                    return "FAILED";
                }
                return "DONE";
            }
        });

    }

    private Future startStorageSvc() {
        return pool.submit(new Callable<String>() {
            @Override
            public String call() {
                System.out.println("Starting storage service");
                String[] args = new String[0];
                StorageService storageSvc = new StorageService();
                try {
                    storageSvc.main(args);
                } catch (Exception e) {
                    System.out.println("Storage Service Failed");
                    return "FAILED";
                }
                return "DONE";
            }
        });

    }

    @Before
    public void setUp() {
        System.out.println("Setting up");
        startGateway();
        startStorageSvc();
        start = Instant.now();
        writtenIds = new LinkedList<>();
    }


    @Test
    /**
     * tests that write of one Item succeeds and the items id is returned.
     * The id is a UUID 36 characters in length
     */
    public void writeOneItem() throws Exception {
        String id = "ALL";
        URL url = new URL("http://localhost:8000/item");
        HttpURLConnection conn = (HttpURLConnection) url
                .openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("POST");
        OutputStream out = conn.getOutputStream();
        out.write(itemTestJson1.getBytes());
        out.close();
        conn.connect();
        System.out.println("response code: " + conn.getResponseCode());
        InputStream result = conn.getInputStream();
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(result))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        JSONObject resultObj = (JSONObject) new JSONParser().parse(stringBuilder.toString());
        String itemId = resultObj.get("id").toString();
        assert itemId.length() == 36;
        assert itemId.charAt(8) == '-';
        assert itemId.charAt(13) == '-';
        assert itemId.charAt(18) == '-';
        assert itemId.charAt(23) == '-';
        writtenIds.add(itemId);
        System.out.print(stringBuilder.toString());

        while(Duration.between(start,Instant.now()).getSeconds()< TIMEOUT){
            continue;
        }
    }

    @Test
    public void getAllItems() throws Exception {
        String id = "ALL";
        URL url = new URL("http://localhost:8000/item?query=" + id);
        HttpURLConnection conn = (HttpURLConnection) url
                .openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        conn.connect();
        System.out.println(conn.getResponseCode());
        System.out.println("header fields " + conn.getHeaderFields());
        System.out.println(conn.getContentEncoding());
        System.out.println(conn.getContentType());
        System.out.println(conn.getContentLength());
        InputStream result = conn.getInputStream();
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(result))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        Object obj= new JSONParser().parse(stringBuilder.toString());
        assert (obj instanceof List);
        assert ((List) obj).size()>0;
        System.out.print(stringBuilder.toString());

        while(Duration.between(start,Instant.now()).getSeconds()< TIMEOUT){
            continue;
        }
    }

    @Test
    public void getOneItem() throws Exception {
        writeOneItem();
        Thread.sleep(20000);
        System.out.println("writtenIds: "+writtenIds);
        String id = writtenIds.get(0);
        URL url = new URL("http://localhost:8000/item?id=" + id);
        HttpURLConnection conn = (HttpURLConnection) url
                .openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        conn.connect();
        System.out.println(conn.getResponseCode());
        System.out.println("header fields " + conn.getHeaderFields());
        System.out.println(conn.getContentEncoding());
        System.out.println(conn.getContentType());
        System.out.println(conn.getContentLength());
        InputStream result = conn.getInputStream();
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(result))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        Object obj= new JSONParser().parse(stringBuilder.toString());
        assert (obj instanceof List);
        assert ((List) obj).size()==1;
        System.out.print("result string: "+stringBuilder.toString());
    }

}




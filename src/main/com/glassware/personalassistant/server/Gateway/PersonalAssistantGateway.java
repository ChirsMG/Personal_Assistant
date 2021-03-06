package com.glassware.personalassistant.server.Gateway;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonalAssistantGateway{
    //CONFIGURATION -TODO MOVE TO FILE
    private final static Logger LOGGER = Logger.getLogger(PersonalAssistantGateway.class.getName());
    private static final Map<String, Class<? extends RequestHandler>> ENDPOINTS = new HashMap<String, Class<? extends RequestHandler>>();
    static {
        ENDPOINTS.put("/item", ItemHandler.class);
        ENDPOINTS.put("/list", ListHandler.class);
        ENDPOINTS.put("/image", ImageHandler.class);
        ENDPOINTS.put("/event", EventHandler.class);
        ENDPOINTS.put("/task", TaskHandler.class);
    }


    public static void main(String[] args) throws Exception{

        ENDPOINTS.forEach((key, val) -> {
            HttpServer server;
            RequestHandler handler;
            try {
                //load gateway handler from classname
                handler=val.newInstance();
            }catch(Exception e){
               LOGGER.log(Level.SEVERE,"error - handler class not found, name: " + val);
               return;
            }

            try {
                server = HttpServer.create(new InetSocketAddress(8000), 0);
                server.createContext(key, handler);
                server.setExecutor(null);
                server.start();
                LOGGER.info("server context created");
                System.out.println(server.toString());
                LOGGER.info("CanonicalHostName: " + server.getAddress().getAddress().getCanonicalHostName());
                LOGGER.info("Host String : " + server.getAddress().getHostString());
                LOGGER.info("address:" + server.getAddress().getAddress().getHostAddress());
                LOGGER.info("port: " + Integer.toString(server.getAddress().getPort()));
                LOGGER.info("hostName:" + server.getAddress().getHostName());

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Exception: " + e.getMessage());

            }
        });
        int i=0;
        while(i<300){
            try {
                LOGGER.info("sleeping");
                System.out.println("sleeping");
                Thread.sleep(20000);
                i++;
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                i=400;
            }
        }

    }



//    void listen()
//    {



//        ServerSocket server = new Server  public static void main(String[] args) throws Exception
//        {
//            ServerSocket server = new ServerSocket(80);
//            Socket conn = server.accept();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            // don't use buffered writer because we need to write both "text" and "binary"
//            OutputStream out = conn.getOutputStream();
//            int count = 0;
//            while (true) {
//                count++;
//                String line = reader.readLine();
//                if (line == null) {
//                    System.out.println("Connection closed");
//                    break;
//                }
//                System.out.println("" + count + ": " + line);
//                if (line.equals("")) {
//                    System.out.println("Writing response...");
//
//                    // need to construct response bytes first
//                    byte[] response = "<html><body>Hello World</body></html>".getBytes("ASCII");
//
//                    String statusLine = "HTTP/1.1 200 OK\r\n";
//                    out.write(statusLine.getBytes("ASCII"));
//
//                    String contentLength = "Content-Length: " + response.length + "\r\n";
//                    out.write(contentLength.getBytes("ASCII"));
//
//                    // signal end of headers
//                    out.write("\r\n".getBytes("ASCII"));
//
//                    // write actual response and flush
//                    out.write(response);
//                    out.flush();
//                }
//            }
//        }
//    }
}
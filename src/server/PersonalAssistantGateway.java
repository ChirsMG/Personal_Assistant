package server;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonalAssistantGateway{
    //CONFIGURATION -TODO MOVE TO FILE
    private final static Logger LOGGER = Logger.getLogger(PersonalAssistantGateway.class.getName());




    ////

    public static void main(String[] args){
        HttpServer server;
        //listen for connection
        try {
             server = HttpServer.create(new InetSocketAddress(8000), 0);
             server.createContext("/test", new GatewayHandler());
             server.setExecutor(null);
             server.start();
            LOGGER.info("server context created");
            System.out.println(server);
            LOGGER.info("CanonicalHostName: "+server.getAddress().getAddress().getCanonicalHostName());
            LOGGER.info("Host String : "+server.getAddress().getHostString());
            LOGGER.info("address:"+server.getAddress().getAddress().getHostAddress());
            LOGGER.info("port: "+server.getAddress().getPort());
            LOGGER.info("hostName:"+server.getAddress().getHostName());

        }catch(Exception e){
            LOGGER.log(Level.SEVERE,"Exception: "+e.getMessage());

        }
        while(true){
            try {
                LOGGER.info("sleeping");
                System.out.println("sleeping");
                Thread.sleep(20000);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

    }



    void listen()
    {



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
    }
}
package server;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class monitorClassTest {
    private final String TEST_SERVICE_NAME = "";
    Monitor monitor;
    @BeforeEach
    void setUp(){
        this.monitor=new Monitor();
    }
    @Test
    void testUnknownService(){
        //todo
        this.monitor.startService("Foo");
    }
    @Test
    void testNullService(){
        this.monitor.startService(null);
        //todo
    }
    @Test
    void testStartService(){
        //todo
        this.monitor.startService(TEST_SERVICE_NAME);//TODO need servicename
    }
}
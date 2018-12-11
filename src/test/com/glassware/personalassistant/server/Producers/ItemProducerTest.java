package com.glassware.personalassistant.server.Producers;

import com.glassware.personalassistant.server.Item;
import com.glassware.personalassistant.server.ItemDeserializer;
import com.glassware.personalassistant.server.PAKafkaAdmin;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.*;
import org.apache.kafka.common.serialization.*;
import org.junit.*;

import java.util.*;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class ItemProducerTest {
    private final static String BOOTSTRAP_SERVERS =
            "localhost:9092,localhost:9093,localhost:9094";
    private final static String CREATE_TOPIC = "com.glassware.personalassistant.server.Item-create";
    private Consumer<Long, Item> testConsumer;
    final Logger logger = Logger.getLogger(ItemProducerTest.class.getName());

    @Before
    public void setUp() {
        PAKafkaAdmin admin = new PAKafkaAdmin();
        admin.setUp();
        admin.createTopic(CREATE_TOPIC, 1, (short) 1);
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "PersonalAssistant");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                ItemDeserializer.class.getName());
        testConsumer = new KafkaConsumer<>(props);
        testConsumer.subscribe(Collections.singletonList(CREATE_TOPIC));
    }

    @After
    public void tearDown(){

    };

    @Test
    public void runProducer() throws Exception {
        Item newItem = new Item("test-01", "Test");
        ItemProducer producer = new ItemProducer();

        ConsumerRecords<Long, Item> records = testConsumer.poll(1000);
        int i=0;
        while(records.isEmpty()|| i>=50) {
            producer.runProducer("create", newItem, 1);
            records = testConsumer.poll(1000);
            i++;
        }

        List recList = records.records(new TopicPartition(CREATE_TOPIC, 0));
        ConsumerRecord<Long,Item> consumerRec= (ConsumerRecord) recList.get(0);
        logger.info(consumerRec.value().toString());
        assertEquals("there were more than one record or no record at all", 1, records.count());

    }

}
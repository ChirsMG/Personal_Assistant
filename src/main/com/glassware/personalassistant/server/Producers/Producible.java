package com.glassware.personalassistant.server.Producers;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

import java.util.Collections;
import java.util.Properties;

public class Producible<T> {
    private String TOPIC = "my-example-topic";
    private final static String BOOTSTRAP_SERVERS =
            "localhost:9092,localhost:9093,localhost:9094";
    protected String keySerializerClass = LongSerializer.class.getName();
    protected String valueSerializerClass = StringSerializer.class.getName();

    protected Producer<Long, T> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializerClass);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClass);
        return new KafkaProducer<>(props);

        //Consumer suscribes to a topic
        //Producer produces a record to a topic
        // may need to break up up producer consuymer fubnction dfor services

    }
}

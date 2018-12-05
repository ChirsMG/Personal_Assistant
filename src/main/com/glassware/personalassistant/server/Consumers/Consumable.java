package com.glassware.personalassistant.server.Consumers;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;

import java.util.Collections;
import java.util.Properties;

public class Consumable<T> {
    private final static String BOOTSTRAP_SERVERS =
            "localhost:9092,localhost:9093,localhost:9094";

    protected String keySerializerClass = LongDeserializer.class.getName();
    protected String valueDeserializerClass = StringDeserializer.class.getName();

    protected Consumer<Long, T> createConsumer(String topic) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "None");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                keySerializerClass);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                valueDeserializerClass);
        Consumer<Long, T> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;

    }
}

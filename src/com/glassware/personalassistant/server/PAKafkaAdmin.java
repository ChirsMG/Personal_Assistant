package com.glassware.personalassistant.server;

import org.apache.kafka.clients.admin.*;

import java.util.*;

import static java.util.Arrays.asList;

public class PAKafkaAdmin {
    KafkaAdminClient admin;
     void setUp() {
         Properties config = new Properties();
         config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");

         this.admin = (KafkaAdminClient)AdminClient.create(config);
     }

     void createTopic(String topic, int partitions, short replication){
         Map<String, String> configs = new HashMap<>();
         admin.createTopics(asList(new NewTopic(topic, partitions, replication).configs(configs)));
     }
}

package com.glassware.personalassistant.server.Storage;

import com.glassware.personalassistant.server.Consumers.StorageConsumer;

public class StorageService {
    private final static String BOOTSTRAP_SERVERS_CONFIG="";
    private final static String  KEY_SERIALIZER_CLASS_CONFIG="";
    private final static String  VALUE_SERIALIZER_CLASS_CONFIG="";

    public static void main(String[] argv)throws Exception {

        while(true){

                StorageConsumer storageConsumer = new StorageConsumer();
        }

    }
}

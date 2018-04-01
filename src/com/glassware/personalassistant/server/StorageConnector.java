package com.glassware.personalassistant.server;

import java.util.Map;

interface StorageConnector {

    Object read(String query);
    boolean write(Map<String,Object> item);
    boolean delete(String query);
    void storageName(String name);
}

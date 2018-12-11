package com.glassware.personalassistant.server.Storage;

import java.util.List;
import java.util.Map;

public interface StorageConnector<T> {

    Object read(String query);
    boolean write(Map<String, Object> item);
    boolean delete(String query);
    T storageName(String name);
}

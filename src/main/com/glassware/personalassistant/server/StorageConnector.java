package com.glassware.personalassistant.server;

import java.util.List;
import java.util.Map;

public interface StorageConnector<T> {

    List<String> read(String query);
    boolean write(Object item);
    boolean delete(String query);
    T storageName(String name);
}

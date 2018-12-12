package com.glassware.personalassistant.server.Storage;

public class Instruction {

    private String id;
    private String query;
    private StorageConstants.Operation operation;

    public Instruction id(String id) {
        this.id = id;
        return this;
    }

    public String id() {
        return id;
    }

    public Instruction query(String query) {
        this.query = query;
        return this;
    }
    public String query() {
        return query;
    }
}

package com.glassware.personalassistant.server.Consumers;

import java.util.List;
import java.util.concurrent.Callable;

public class QueryResultCallable<T> implements Callable<List<T>> {

    private List<T> list;
    private String id;

    public QueryResultCallable(List<T> q, String id) {
        this.id = id;
        this.list = q;
    }

    public QueryResultCallable<T> setQueryId(String id) {
        System.out.println("query id set in query result thread");
        this.id = id;
        return this;
    }

    public List<T> call() {
        QueryResultConsumer queryResultConsumer = new QueryResultConsumer<T>(list);
        try {
            while (this.id == null) {
                System.out.println("Ensure that id is set before running");
                continue;
            }
           this.list= queryResultConsumer.consume(this.id);

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("In Item Consumer");
        }
        return this.list;
    }

}

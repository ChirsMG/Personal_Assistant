package com.glassware.personalassistant.server.Consumers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueryConsumerRunnable<T> implements Runnable {

    private LinkedBlockingQueue<T> queue;

    public QueryConsumerRunnable(LinkedBlockingQueue<T> q) {
        this.queue = q;
    }

    @Override
    public void run() {
        ItemQueryConsumer itemQueryConsumer = new ItemQueryConsumer()
                .instructionQueue(queue);
        try {
            itemQueryConsumer.runItemConsumers();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("In Item Consumer");
        }
    }

}

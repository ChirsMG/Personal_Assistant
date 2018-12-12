package com.glassware.personalassistant.server.Consumers;

import java.util.concurrent.BlockingQueue;

public class ConsumerRunnable<T> implements Runnable {

    private BlockingQueue<T> queue;

    public ConsumerRunnable(BlockingQueue<T> q){
        this.queue=q;
    }
    @Override
    public void run(){

    }

}

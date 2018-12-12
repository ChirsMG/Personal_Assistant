package com.glassware.personalassistant.server.Consumers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ModifierConsumerRunnable<T> implements Runnable {

    private LinkedBlockingQueue<T> queue;

    public ModifierConsumerRunnable(LinkedBlockingQueue<T> q){
        this.queue=q;
    }

    @Override
    public void run(){
        ItemModifierConsumer itemModifierConsumer = new ItemModifierConsumer()
                .modificationQueue(queue);
        try {
            itemModifierConsumer.runModifierConsumers();
        }catch (Exception e){
            System.out.println(e);
            System.out.println("In Item Consumer");
        }
    }

}

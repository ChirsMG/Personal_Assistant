package com.glassware.personalassistant.server;

import java.util.UUID;

public class ServiceTask {

    private String taskName; //TODO enum;
    private String taskID;
    private String taskPayload;

    ServiceTask (String name){
        this.taskName = name;
        this.taskID=UUID.randomUUID().toString();
    }

    ServiceTask (String name, String payload){
        this(name);
        this.taskPayload=payload;
    }

    public void setTaskPayload(String payload){
        this.taskPayload=payload;
    }

    public String getPayload(){
        return this.taskPayload;
    }


    public String getTaskId() {
        return this.taskID;
    }
}

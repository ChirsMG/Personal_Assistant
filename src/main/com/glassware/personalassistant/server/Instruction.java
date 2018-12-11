package com.glassware.personalassistant.server;

public class Instruction<T>{
    private String Id;
    private String crudType;

    Instruction (String crudType){
        this.crudType=crudType;
    }


    public String getId(){

        return Id;
    }
}

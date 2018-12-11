package com.glassware.personalassistant.server;

public class InstructionStatus {
    String instructionId;
    String status;

    InstructionStatus(String id){
        this.instructionId=id;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public String getInstrucationId(){
        return this.instructionId;
    }
}

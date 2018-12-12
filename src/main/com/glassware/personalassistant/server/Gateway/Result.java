package com.glassware.personalassistant.server.Gateway;

public class Result {
    String statusCode;
    String statusMsg;
    String body;

    Result(){
        this.body="";
        this.statusCode="";
        this.statusMsg="";
    }

}

package com.example.springcore.prac.mycontroller;

import lombok.Data;

@Data
public class Message {

    private StatusEnum status;
    private String message;
    private Object data;

    public Message(StatusEnum status, String message, int data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
}

package com.example.webforumapp.models.replys;

public class MessageInfo {

    private String senderUserName;

    private String message;


    public MessageInfo(String message, String senderUserName){
        this.message = message;
        this.senderUserName = senderUserName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }
}

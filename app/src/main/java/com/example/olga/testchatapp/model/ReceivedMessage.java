package com.example.olga.testchatapp.model;

/**
 * Created by olga on 28.12.16.
 */

public class ReceivedMessage {

    private String userName;
    private String message;
    private long messageTime;

    public ReceivedMessage() {
    }

    public ReceivedMessage(String userName, String message, long messageTime) {
        this.userName = userName;
        this.message = message;
        this.messageTime = messageTime;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    public long getMessageTime() {
        return messageTime;
    }
}

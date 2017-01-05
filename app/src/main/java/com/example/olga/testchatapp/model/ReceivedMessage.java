package com.example.olga.testchatapp.model;

/**
 * Created by olga on 28.12.16.
 */

public class ReceivedMessage {

    private User user;
    private String message;
    private long messageTime;

    public ReceivedMessage(User user, String message, long messageTime) {
        this.user = user;
        this.message = message;
        this.messageTime = messageTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}

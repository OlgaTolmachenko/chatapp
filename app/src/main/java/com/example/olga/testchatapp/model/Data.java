package com.example.olga.testchatapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by olga on 28.12.16.
 */

public class Data {

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public Data(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

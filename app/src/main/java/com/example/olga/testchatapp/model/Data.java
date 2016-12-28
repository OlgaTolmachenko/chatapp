package com.example.olga.testchatapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by olga on 28.12.16.
 */

public class Data {

    @SerializedName("userName")
    private String userName;

    @SerializedName("message")
    private String message;

    public Data(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

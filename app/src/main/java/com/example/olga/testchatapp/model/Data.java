package com.example.olga.testchatapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by olga on 28.12.16.
 */

public class Data {

    @SerializedName("message")
    private String message;

    @SerializedName("email")
    private String email;

    public Data(String message, String email) {
        this.message = message;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

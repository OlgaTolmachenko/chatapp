package com.example.olga.testchatapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by olga on 22.12.16.
 */

public class Message {

    @SerializedName("to")
    private String to;

    @SerializedName("data")
    private Data data;

    public Message(String to, Data data) {
        this.to = to;
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

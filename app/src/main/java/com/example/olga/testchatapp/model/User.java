package com.example.olga.testchatapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by olga on 28.12.16.
 */

public class User {

    @SerializedName("email")
    private String email;

    @SerializedName("color")
    private int color;

    public User(String email, int color) {
        this.email = email;
        this.color = color;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean equals(User user) {
        return this.email.equals(user.email);
    }
}

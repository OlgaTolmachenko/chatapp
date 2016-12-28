package com.example.olga.testchatapp.model;

/**
 * Created by olga on 28.12.16.
 */

public class MessageResponse {

    private String message_id;
    private String error;

    public MessageResponse(String message_id, String error) {
        this.message_id = message_id;
        this.error = error;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

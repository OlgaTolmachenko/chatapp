package com.example.olga.testchatapp;

import android.app.Application;

import com.example.olga.testchatapp.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 26.12.16.
 */
public final class MessageApp extends Application{

    private static MessageApp instance = new MessageApp();

    private static List<Message> messageList = new ArrayList<>();

    public static MessageApp getInstance() {
        return instance;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(Message message) {
        MessageApp.messageList.add(message);
    }
//
//    public void setMessageList(int position, Message message) {
//        MessageApp.messageList.add(position, message);
//    }
}

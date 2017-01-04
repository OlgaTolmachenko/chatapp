package com.example.olga.testchatapp;

import android.app.Application;

import com.example.olga.testchatapp.model.ReceivedMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 26.12.16.
 */
public final class MessageApp extends Application{

    private static MessageApp instance = new MessageApp();

    private static List<ReceivedMessage> messageList = new ArrayList<>();

    private static boolean activityVisible;

    public static MessageApp getInstance() {
        return instance;
    }

    public List<ReceivedMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(ReceivedMessage message) {
        MessageApp.messageList.add(message);
    }

    public boolean isActivityVisible() {
        return activityVisible;
    }

    public void activityResumed() {
        activityVisible = true;
    }

    public void activityPaused() {
        activityVisible = false;
    }
}

package com.example.olga.testchatapp;

import android.app.Application;

import com.example.olga.testchatapp.model.ReceivedMessage;
import com.example.olga.testchatapp.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by olga on 26.12.16.
 */
public final class MessageApp extends Application {

    private static MessageApp instance;
    private  List<ReceivedMessage> messageList = new ArrayList<>();
    private static boolean activityVisible;
    private  HashMap<String, User> userMap = new HashMap<>();

    public static MessageApp getInstance() {
        if(instance == null){
            instance = new MessageApp();
        }
        return instance;
    }

    public List<ReceivedMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(ReceivedMessage message) {
        messageList.add(message);
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

    public HashMap<String, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(String key, User user) {
        userMap.put(key, user);
    }
}

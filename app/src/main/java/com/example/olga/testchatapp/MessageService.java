package com.example.olga.testchatapp;

import android.util.Log;


import com.example.olga.testchatapp.model.Message;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageService extends FirebaseMessagingService {

    private final String  TAG = getClass().getSimpleName();

    private static List<Message> messageList;

    public MessageService() {
        messageList = new ArrayList<>();
    }

    public static List<Message> getMessageList() {
        return messageList;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        remoteMessage.getSentTime();
        Message incomingMessage = new Message(remoteMessage.getFrom(), remoteMessage.getNotification().getBody(), remoteMessage.getSentTime());

        messageList.add(incomingMessage);

    }
}


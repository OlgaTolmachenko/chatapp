package com.example.olga.testchatapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.example.olga.testchatapp.model.Message;
import com.example.olga.testchatapp.util.Constants;
import com.example.olga.testchatapp.util.MessageAdapter;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageService extends FirebaseMessagingService {

    private final String  TAG = getClass().getSimpleName();
    private MessageAdapter messageAdapter;

    private List<Message> messageList;

    public MessageService() {
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Message incomingMessage = new Message(remoteMessage.getFrom(), remoteMessage.getNotification().getBody(), remoteMessage.getSentTime());


        Intent intent = new Intent("SEND_MESSAGE");
        intent.putExtra(Constants.USERNAME, incomingMessage.getUserName());
        intent.putExtra("message", incomingMessage.getMessage());
        intent.putExtra("time", incomingMessage.getMessageTime());

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


//        MessageApp.getInstance().setMessageList(incomingMessage);

        Log.d("AAAAAAAAAAaa", "Messages in SERVICE : " + MessageApp.getInstance().getMessageList().size());


        for (Message message : MessageApp.getInstance().getMessageList()) {
            Log.d("AAAAAAAAAAaa", "Current message: " + message.getMessage());
        }

    }
}


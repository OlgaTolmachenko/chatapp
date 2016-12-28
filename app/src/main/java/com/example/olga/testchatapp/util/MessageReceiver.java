package com.example.olga.testchatapp.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.olga.testchatapp.MessageApp;
import com.example.olga.testchatapp.model.Message;
import com.example.olga.testchatapp.model.ReceivedMessage;

import static com.example.olga.testchatapp.util.Constants.USERNAME;

/**
 * Created by olga on 26.12.16.
 */

public class MessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String userName = intent.getStringExtra(USERNAME);
        String message = intent.getStringExtra("message");
        long time = intent.getLongExtra("time", 0L);

        ReceivedMessage currentMessage = new ReceivedMessage(userName, message, time);

        MessageApp.getInstance().setMessageList(currentMessage);
    }
}

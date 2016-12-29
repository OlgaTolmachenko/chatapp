package com.example.olga.testchatapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


import com.example.olga.testchatapp.model.Message;
import com.example.olga.testchatapp.model.ReceivedMessage;
import com.example.olga.testchatapp.model.User;
import com.example.olga.testchatapp.util.Constants;
import com.example.olga.testchatapp.util.MessageAdapter;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageService extends FirebaseMessagingService {

    ReceivedMessage incomingMessage;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        incomingMessage = new ReceivedMessage(remoteMessage.getData().get("userName"), remoteMessage.getData().get("message"), remoteMessage.getSentTime());

        Intent intent = new Intent("SEND_MESSAGE");
        intent.putExtra(Constants.USERNAME, incomingMessage.getUserName());
        intent.putExtra("message", incomingMessage.getMessage());
        intent.putExtra("time", incomingMessage.getMessageTime());

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

//        sendNotification(remoteMessage.getNotification().getBody()) ;


    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}


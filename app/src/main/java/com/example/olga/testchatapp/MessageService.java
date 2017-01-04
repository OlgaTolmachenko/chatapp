package com.example.olga.testchatapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;

import com.example.olga.testchatapp.model.ReceivedMessage;
import com.example.olga.testchatapp.util.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageService extends FirebaseMessagingService {

    ReceivedMessage incomingMessage;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        incomingMessage = new ReceivedMessage(
                remoteMessage.getData().get("userName"),
                remoteMessage.getData().get("message"),
                remoteMessage.getSentTime()
        );

        if (userMap.isEmpty()) {
            userMap.put(currentUser.getEmail(), currentUser);
        }

        if (!userMap.containsKey(currentMessage.getUserName())) {
            userMap.put(currentMessage.getUserName(), currentUser);
        }

        LocalBroadcastManager.getInstance(this).sendBroadcast(getSendIntent(incomingMessage));

        if (!MessageApp.getInstance().isActivityVisible()) {
            sendNotification(incomingMessage, this);
            MessageApp.getInstance().setMessageList(incomingMessage);


        }
    }

    @NonNull
    private Intent getSendIntent(ReceivedMessage incomingMessage) {
        Intent intent = new Intent("SEND_MESSAGE");
        intent.putExtra(Constants.USERNAME, incomingMessage.getUserName());
        intent.putExtra("message", incomingMessage.getMessage());
        intent.putExtra("time", incomingMessage.getMessageTime());
        return intent;
    }

    private void sendNotification(ReceivedMessage incomingMessage, Context context) {
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_email_white_18dp)
                .setContentTitle("TestChatApp")
                .setContentText(incomingMessage.getMessage())
                .setAutoCancel(true);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );

        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
        LocalBroadcastManager.getInstance(this).sendBroadcast(getSendIntent(incomingMessage));
    }
}


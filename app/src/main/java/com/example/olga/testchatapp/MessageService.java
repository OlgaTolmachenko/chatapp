package com.example.olga.testchatapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;

import com.example.olga.testchatapp.model.ReceivedMessage;
import com.example.olga.testchatapp.model.User;
import com.example.olga.testchatapp.util.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import retrofit2.converter.gson.GsonConverterFactory;

public class MessageService extends FirebaseMessagingService {

    ReceivedMessage incomingMessage;
    Map<String, User> userMap = new HashMap<>();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        User user = remoteMessage.getData().getU;


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        User user = gson.fromJson(remoteMessage.getData().get("user"), User.class);

        incomingMessage = new ReceivedMessage(
                user.getEmail(),
                remoteMessage.getData().get("message"),
                remoteMessage.getSentTime()
        );

        userMap.put(user.getEmail(), user);

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
        intent.putExtra("map", (Serializable) userMap);
        return intent;
    }

    private void sendNotification(ReceivedMessage incomingMessage, Context context) {
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
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


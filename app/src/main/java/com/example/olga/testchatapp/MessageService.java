package com.example.olga.testchatapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.olga.testchatapp.model.ReceivedMessage;
import com.example.olga.testchatapp.model.User;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import static com.example.olga.testchatapp.util.Constants.ALPHA;
import static com.example.olga.testchatapp.util.Constants.COLOR_MASK;
import static com.example.olga.testchatapp.util.Constants.EMAIL;
import static com.example.olga.testchatapp.util.Constants.MESSAGE;
import static com.example.olga.testchatapp.util.Constants.NOTIFY;

public class MessageService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d("LOG", "onMessageReceived: ");

        String email = remoteMessage.getData().get(EMAIL);
        String message = remoteMessage.getData().get(MESSAGE);
        long time = remoteMessage.getSentTime();

        User user;

        if (MessageApp.getInstance().getUserMap().isEmpty() || !MessageApp.getInstance().getUserMap().containsKey(email)) {
            user = new User(email, generateColor());
            MessageApp.getInstance().setUserMap(email, user);
        } else {
            user =  MessageApp.getInstance().getUserMap().get(email);
        }

        ReceivedMessage receivedMessage = new ReceivedMessage(user, message, time);
        MessageApp.getInstance().setMessageList(receivedMessage);

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(NOTIFY));

        if (!MessageApp.getInstance().isActivityVisible()) {
            sendNotification(receivedMessage, this);
        }
    }

    private int generateColor() {
        Random rnd = new Random();
        int r = rnd.nextInt(COLOR_MASK);
        int g = rnd.nextInt(COLOR_MASK);
        int b = rnd.nextInt(COLOR_MASK);
        return Color.argb(ALPHA, r, g, b);
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
    }
}


package com.example.olga.testchatapp;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;

import com.example.olga.testchatapp.model.ReceivedMessage;
import com.example.olga.testchatapp.util.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

public class MessageService extends FirebaseMessagingService {

    ReceivedMessage incomingMessage;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        incomingMessage = new ReceivedMessage(remoteMessage.getData().get("userName"), remoteMessage.getData().get("message"), remoteMessage.getSentTime());


        ActivityManager mngr = (ActivityManager) getSystemService( ACTIVITY_SERVICE );

        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

        if(taskList.get(0).numActivities == 1 &&
                taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            sendNotification(incomingMessage);
        }


        LocalBroadcastManager.getInstance(this).sendBroadcast(getSendIntent(incomingMessage));


    }

    @NonNull
    private Intent getSendIntent(ReceivedMessage incomingMessage) {
        Intent intent = new Intent("SEND_MESSAGE");
        intent.putExtra(Constants.USERNAME, incomingMessage.getUserName());
        intent.putExtra("message", incomingMessage.getMessage());
        intent.putExtra("time", incomingMessage.getMessageTime());
        return intent;
    }

    private void sendNotification(ReceivedMessage incomingMessage) {
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_email_white_18dp)
                .setContentTitle("TestChatApp")
                .setContentText(incomingMessage.getMessage());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.USERNAME, incomingMessage.getUserName());
        intent.putExtra("message", incomingMessage.getMessage());
        intent.putExtra("time", incomingMessage.getMessageTime());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}


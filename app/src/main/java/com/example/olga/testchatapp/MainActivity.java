package com.example.olga.testchatapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.olga.testchatapp.io.ChatNetworking;
import com.example.olga.testchatapp.model.Data;
import com.example.olga.testchatapp.model.Message;
import com.example.olga.testchatapp.model.ReceivedMessage;
import com.example.olga.testchatapp.model.User;
import com.example.olga.testchatapp.util.ChatItemsDecor;
import com.example.olga.testchatapp.util.MessageAdapter;
import com.example.olga.testchatapp.util.UserAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.olga.testchatapp.util.Constants.COLOR_MASK;
import static com.example.olga.testchatapp.util.Constants.MESSAGE;
import static com.example.olga.testchatapp.util.Constants.SEND_MESSAGE;
import static com.example.olga.testchatapp.util.Constants.TIME;
import static com.example.olga.testchatapp.util.Constants.TOPIC;
import static com.example.olga.testchatapp.util.Constants.USERNAME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final Long DEFAULT_TIME = 0L;
    private final int ALPHA = 128;


    private ImageButton btnSend;
    private RecyclerView messageRecycler;
    private RecyclerView.LayoutManager messageLM;
    private MessageAdapter messageAdapter;
    private String email;
    private EditText messageField;
    private UserAuth userAuth;
    private User user;

    Map<String, User> userMap;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

        ReceivedMessage currentMessage = getReceivedMessage(intent);
//        User currentUser = new User(currentMessage.getUserName(), generateColor());

//        if (userMap.isEmpty()) {
//            userMap.put(currentUser.getEmail(), currentUser);
//        }
//
//        if (!userMap.containsKey(currentMessage.getUserName())) {
//            userMap.put(currentMessage.getUserName(), currentUser);
//        }
            if (intent.getExtras() != null) {
                userMap = (HashMap<String, User>) intent.getSerializableExtra("map");
            }

            if (MessageApp.getInstance().isActivityVisible()) {
                MessageApp.getInstance().setMessageList(currentMessage);
            }
            messageAdapter.setUserMap(userMap);
            messageAdapter.notifyItemInserted(MessageApp.getInstance().getMessageList().size());
            messageRecycler.smoothScrollToPosition(MessageApp.getInstance().getMessageList().size());
        }
    };

    @NonNull
    private ReceivedMessage getReceivedMessage(Intent intent) {
        String userName = intent.getStringExtra(USERNAME);
        String message = intent.getStringExtra(MESSAGE);
        long time = intent.getLongExtra(TIME, DEFAULT_TIME);

        return new ReceivedMessage(userName, message, time);
    }

    private int generateColor() {
        Random rnd = new Random();
        int r = rnd.nextInt(COLOR_MASK);
        int g = rnd.nextInt(COLOR_MASK);
        int b = rnd.nextInt(COLOR_MASK);
        return Color.argb(ALPHA, r, g, b);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this)
                    .registerReceiver(
                            broadcastReceiver,
                            new IntentFilter(SEND_MESSAGE));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userAuth = new UserAuth(this);

        if (isUserExists()) {
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }

        user = new User(email, generateColor());

        messageField = (EditText) findViewById(R.id.messageField);


        messageRecycler = (RecyclerView) findViewById(R.id.messageRecycler);
        messageLM = new LinearLayoutManager(this);
        messageRecycler.setLayoutManager(messageLM );
        messageAdapter = new MessageAdapter(
                MessageApp.getInstance().getMessageList(),
                email);

        messageRecycler.setAdapter(messageAdapter);
        messageRecycler.addItemDecoration(new ChatItemsDecor(
                getResources().getDimensionPixelOffset(R.dimen.item_spin),
                getResources().getDimensionPixelOffset(R.dimen.dimen_8dp)));

        btnSend = (ImageButton) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemLogout:
                userAuth.logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onPause() {
        super.onStop();
        MessageApp.getInstance().activityPaused();
    }

    @Override
    protected void onResume() {
        super.onStop();
        MessageApp.getInstance().activityResumed();
    }

    @Override
    public void onClick(View v) {

        if (isUserExists()) {
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        sendMessage();

        messageField.setText("");
    }

    private boolean isUserExists() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    private void sendMessage() {

        Message message = new Message(TOPIC, new Data(messageField.getText().toString(), user));
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
        new ChatNetworking().sendMessage(message);
    }
}

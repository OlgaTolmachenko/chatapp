package com.example.olga.testchatapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.olga.testchatapp.util.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.olga.testchatapp.util.Constants.USERNAME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnSend;
    private RecyclerView messageRecycler;
    private RecyclerView.LayoutManager messageLM;
    private MessageAdapter messageAdapter;
    private String email;
    private EditText messageField;
    private User currentUser;

    Map<String, Integer> userColorMap = new HashMap<>();

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String lastEmail = intent.getStringExtra(USERNAME);
            if (!TextUtils.isEmpty(lastEmail)){
                if(!userColorMap.containsKey(lastEmail)) {
                    Random rnd = new Random();
                    int r = rnd.nextInt(255);
                    int g = rnd.nextInt(255);
                    int b = rnd.nextInt(255);
                    int color = Color.argb(128, r, g, b);
                    userColorMap.put(lastEmail, color);
                    currentUser = new User(lastEmail, color);
                }
            }

            String userName = intent.getStringExtra(USERNAME);
            String message = intent.getStringExtra("message");
            long time = intent.getLongExtra("time", 0L);

            ReceivedMessage currentMessage = new ReceivedMessage(userName, message, time);

            MessageApp.getInstance().setMessageList(currentMessage);
            messageAdapter.notifyItemInserted(MessageApp.getInstance().getMessageList().size());
            messageRecycler.smoothScrollToPosition(MessageApp.getInstance().getMessageList().size());

            Log.d("Log2", "Message received: " + MessageApp.getInstance().getMessageList().size());
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(
                        broadcastReceiver,
                        new IntentFilter("SEND_MESSAGE"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageField = (EditText) findViewById(R.id.messageField);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }

        messageRecycler = (RecyclerView) findViewById(R.id.messageRecycler);
        messageLM = new LinearLayoutManager(this);
        messageRecycler.setLayoutManager(messageLM );
        messageAdapter = new MessageAdapter(
                MessageApp.getInstance().getMessageList(),
                //TODO check getEmail()
                email,
                userColorMap, currentUser);

        messageRecycler.setAdapter(messageAdapter);
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
                logout();
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
    public void onClick(View v) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            email = currentUser.getEmail();
        }

        Message message = new Message("/topics/news", new Data(email, messageField.getText().toString()));
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/news");
        new ChatNetworking().sendMessage(message);

        messageField.setText("");
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, SignInActivity.class));
    }


}

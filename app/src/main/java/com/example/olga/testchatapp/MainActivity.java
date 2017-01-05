package com.example.olga.testchatapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.olga.testchatapp.io.ChatNetworking;
import com.example.olga.testchatapp.model.Data;
import com.example.olga.testchatapp.model.Message;
import com.example.olga.testchatapp.model.User;
import com.example.olga.testchatapp.util.ChatItemsDecor;
import com.example.olga.testchatapp.util.MessageAdapter;
import com.example.olga.testchatapp.util.UserAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.example.olga.testchatapp.util.Constants.NOTIFY;
import static com.example.olga.testchatapp.util.Constants.SEND_MESSAGE;
import static com.example.olga.testchatapp.util.Constants.TOPIC;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnSend;
    private RecyclerView messageRecycler;
    private RecyclerView.LayoutManager messageLM;
    private MessageAdapter messageAdapter;
    private String email;
    private EditText messageField;
    private UserAuth userAuth;

    private FirebaseAuth.AuthStateListener authStateListener;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(NOTIFY)) {
            messageAdapter.notifyItemInserted(MessageApp.getInstance().getMessageList().size());
            messageRecycler.smoothScrollToPosition(MessageApp.getInstance().getMessageList().size());
        }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);

        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this)
                    .registerReceiver(
                            broadcastReceiver,
                            new IntentFilter(NOTIFY));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                }
            }
        };

        setContentView(R.layout.activity_main);


        if (isUserExists()) {
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }




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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);
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
                new UserAuth(this).logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MessageApp.getInstance().activityPaused();
    }

    @Override
    protected void onResume() {
        super.onResume();

        MessageApp.getInstance().activityResumed();
    }

    @Override
    public void onClick(View v) {
        sendMessage(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        messageField.setText("");
    }

    private boolean isUserExists() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    private void sendMessage(String email) {
        Message message = new Message(TOPIC, new Data(messageField.getText().toString(), email));
        new ChatNetworking().sendMessage(message);
    }
}

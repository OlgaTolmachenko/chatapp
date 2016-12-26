package com.example.olga.testchatapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.olga.testchatapp.model.Message;
import com.example.olga.testchatapp.util.MessageAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Calendar;

import static com.example.olga.testchatapp.util.Constants.USERNAME;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnSend;
    private RecyclerView messageRecycler;
    private RecyclerView.LayoutManager messageLM;
    private MessageAdapter messageAdapter;
    private String userName;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String userName = intent.getStringExtra(USERNAME);
            String message = intent.getStringExtra("message");
            long time = intent.getLongExtra("time", 0L);

            Message currentMessage = new Message(userName, message, time);

            MessageApp.getInstance().setMessageList(currentMessage);
            messageAdapter.notifyDataSetChanged();

            Log.d("AAAAAAAAAAaa", "Message received: " + MessageApp.getInstance().getMessageList().size());
        }
    };

//    private void addItem(int position, Message currentMessage) {
//        MessageApp.getInstance().setMessageList(position, currentMessage);
//        messageAdapter.notifyItemInserted(position);
//    }

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

        if (FirebaseInstanceId.getInstance().getToken() == null) {
            startActivity(new Intent(this, SignInActivity.class));
        }

        messageRecycler = (RecyclerView) findViewById(R.id.messageRecycler);
        messageLM = new LinearLayoutManager(this);
        messageRecycler.setLayoutManager(messageLM );
        messageAdapter = new MessageAdapter(MessageApp.getInstance().getMessageList());
        messageRecycler.setAdapter(messageAdapter);

        Log.d("AAAAAAAAAAaa", "Messages in MAIN ACTIVITY : " + MessageApp.getInstance().getMessageList().size());

        for (Message message : MessageApp.getInstance().getMessageList()) {
            Log.d("AAAAAAAAAAaa", "Current message in MAIN ACTIVITY : " + message.getMessage());
        }


//        userName = getPreferences(MODE_PRIVATE).getString(USERNAME, "userLol");
//        Log.d("AAAAAAAAAAA", userName + " -- username");

        btnSend = (ImageButton) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        EditText messageField = (EditText) findViewById(R.id.messageField);
        Message messageToSend = new Message(userName,
                messageField.getText().toString(),
                Calendar.getInstance().getTimeInMillis());

        FirebaseDatabase.getInstance()
                .getReference()
                .push()
                .setValue(messageToSend);

        Log.d("MainActivity", "Message: " + messageToSend.getMessage() + " " + messageToSend.getUserName() + " " + messageToSend.getMessageTime());

        messageField.setText("");
    }
}

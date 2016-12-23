package com.example.olga.testchatapp;

import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.Calendar;
import java.util.List;

import static com.example.olga.testchatapp.util.Constants.USERNAME;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnSend;
    private RecyclerView messageRecycler;
    private RecyclerView.LayoutManager messageLM;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = getPreferences(MODE_PRIVATE).getString(USERNAME, "userLol");
        Log.d("AAAAAAAAAAA", userName + " -- username");

        btnSend = (ImageButton) findViewById(R.id.btnSend);
        messageList = MessageService.getMessageList();

        messageRecycler = (RecyclerView) findViewById(R.id.messageRecycler);
        messageLM = new LinearLayoutManager(this);
        messageRecycler.setLayoutManager(messageLM );
        messageAdapter = new MessageAdapter(messageList);
        messageRecycler.setAdapter(messageAdapter);

        btnSend.setOnClickListener(this);
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

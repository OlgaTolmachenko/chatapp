package com.example.olga.testchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.olga.testchatapp.model.Message;
import com.example.olga.testchatapp.util.MessageAdapter;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnSend;
    private RecyclerView messageRecycler;
    private RecyclerView.LayoutManager messageLM;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    private final int SIGN_IN_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = (ImageButton) findViewById(R.id.btnSend);
        messageList = MessageService.getMessageList();

        messageRecycler = (RecyclerView) findViewById(R.id.messageRecycler);
        messageLM = new LinearLayoutManager(this);
        messageRecycler.setLayoutManager(messageLM );
        messageAdapter = new MessageAdapter(messageList);
        messageRecycler.setAdapter(messageAdapter);

        btnSend.setOnClickListener(this);

        if (!isAuthorised()) {
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder().build(),
                    SIGN_IN_REQUEST_CODE);
        } else {
            //displayMessages
        }
    }

    @Override
    public void onClick(View v) {
        EditText messageField = (EditText) findViewById(R.id.messageField);
        Message messageToSend = new Message("user",
                messageField.getText().toString(),
                Calendar.getInstance().getTimeInMillis());

        FirebaseDatabase.getInstance()
                .getReference()
                .push()
                .setValue(messageToSend);

        Log.d("MainActivity", "Message: " + messageToSend.getMessage() + " " + messageToSend.getUserName() + " " + messageToSend.getMessageTime());

        messageField.setText("");
    }

    private boolean isAuthorised() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, "LOGGED IN SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.olga.testchatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.olga.testchatapp.util.UserAuth;

import static com.example.olga.testchatapp.util.Constants.USERNAME;

/**
 * Created by olga on 23.12.16.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userNameField;
    private EditText emailField;
    private EditText passwordField;
    private Button btnSignUp;
    private UserAuth userAuth;

    private SharedPreferences userSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNameField = (EditText) findViewById(R.id.userName);
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        userAuth = new UserAuth(this);
        userAuth.getCurrentFirebaseUser();


        userSettings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = userSettings.edit();
        editor.putString(USERNAME, userNameField.getText().toString().trim());
        editor.putString("email", emailField.getText().toString().trim());
        editor.commit();

        btnSignUp.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userAuth.addAuthStateListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userAuth.removeAuthStateListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                userAuth.signUp(emailField.getText().toString().trim(), passwordField.toString().trim());
                break;
        }
    }
}

package com.example.olga.testchatapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.olga.testchatapp.R;
import com.example.olga.testchatapp.util.UserAuth;

/**
 * Created by olga on 23.12.16.
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignIn;
    private Button btnSignUp;
    private EditText emailField;
    private EditText passwordField;
    private UserAuth userAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userAuth = new UserAuth(this);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);


        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                userAuth.signIn(emailField.getText().toString().trim(), passwordField.getText().toString().trim());
                break;
            case R.id.btnSignUp:
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        }
    }
}

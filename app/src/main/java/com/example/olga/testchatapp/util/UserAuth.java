package com.example.olga.testchatapp.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.olga.testchatapp.MainActivity;
import com.example.olga.testchatapp.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by olga on 23.12.16.
 */



public class UserAuth {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Context context;

    public UserAuth(Context context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();

    }

    public boolean isUserSignedIn() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };
    }

    public void authenticate() {

    }
}

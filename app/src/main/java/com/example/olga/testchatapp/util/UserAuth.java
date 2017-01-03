package com.example.olga.testchatapp.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.olga.testchatapp.MainActivity;
import com.example.olga.testchatapp.SignInActivity;
import com.example.olga.testchatapp.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by olga on 23.12.16.
 */

public class UserAuth {

    private final String ENTER_DATA = "Enter data";

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Activity context;
    private FirebaseUser user;

    public UserAuth(Activity context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();
    }

    public void getCurrentFirebaseUser() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    Log.d("Log2", "onAuthStateChanged: signed_out");
                }
            }
        };
    }

    public void signUp(String email, String password) {
        if (hasEmailAndPassword(email, password)) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(context, getCompleteListener());
        } else {
            showFailureToast(ENTER_DATA);
        }
    }

    public void signIn(String email, String password) {
        if (hasEmailAndPassword(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(context, getCompleteListener());
        } else {
            showFailureToast(ENTER_DATA);
        }
    }

    private void showFailureToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private OnCompleteListener<AuthResult> getCompleteListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    showFailureToast(task.getException().getMessage());
                } else {
                    context.startActivity(new Intent(context, MainActivity.class));
                }
            }
        };
    }

    public void addAuthStateListener() {
        auth.addAuthStateListener(authStateListener);
    }

    public void removeAuthStateListener() {
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }
    private boolean hasEmailAndPassword(String email, String password) {
        return !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        context.startActivity(new Intent(context, SignInActivity.class));
    }
}

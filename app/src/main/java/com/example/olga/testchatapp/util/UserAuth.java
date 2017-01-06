package com.example.olga.testchatapp.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.olga.testchatapp.activity.MainActivity;
import com.example.olga.testchatapp.activity.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by olga on 23.12.16.
 */

public class UserAuth {

    private final String ENTER_DATA = "Enter data";

    private FirebaseAuth auth;
    private Activity context;

    public UserAuth(Activity context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();
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

    private boolean hasEmailAndPassword(String email, String password) {
        return !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        context.startActivity(new Intent(context, SignInActivity.class).setFlags(FLAG_ACTIVITY_CLEAR_TASK|FLAG_ACTIVITY_NEW_TASK));
    }
}

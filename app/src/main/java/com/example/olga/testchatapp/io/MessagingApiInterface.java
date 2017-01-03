package com.example.olga.testchatapp.io;

import com.example.olga.testchatapp.model.Message;
import com.example.olga.testchatapp.model.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.example.olga.testchatapp.BuildConfig.key;

/**
 * Created by olga on 28.12.16.
 */

public interface MessagingApiInterface {

    @Headers({"Content-Type:application/json",
            key
    })
    @POST("fcm/send")
    Call<MessageResponse> sendMessage(@Body Message message);
}

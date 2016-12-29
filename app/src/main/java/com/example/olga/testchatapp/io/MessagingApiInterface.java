package com.example.olga.testchatapp.io;

import com.example.olga.testchatapp.model.Message;
import com.example.olga.testchatapp.model.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by olga on 28.12.16.
 */

public interface MessagingApiInterface {

    @Headers({"Content-Type:application/json",
            "Authorization:key=AAAA7pyHIDI:APA91bEpu7oWbk-rUZRxchvNZQsVys-7mZ9uxCg0cW" +
                    "wclQKXtgvaHuQ51Y8XyedYqLYSEE1S1u0eiQ6N2yILJW8fyoNrjjmKIfeqA1qV1qCs6vN0I1y" +
                    "aHBkXKaW2zdTvBmHeD-4PKjuUin3lSgziAXUbX2tM1FNVTA"})
    @POST("fcm/send")
    Call<MessageResponse> sendMessage(@Body Message message);
}

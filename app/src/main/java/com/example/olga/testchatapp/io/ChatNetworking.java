package com.example.olga.testchatapp.io;

import android.util.Log;

import com.example.olga.testchatapp.model.Message;
import com.example.olga.testchatapp.model.MessageResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.olga.testchatapp.util.Constants.URL;

/**
 * Created by olga on 29.12.16.
 */

public class ChatNetworking {

    public void sendMessage(Message message) {

        MessagingApiInterface service = buildRetrofit().create(MessagingApiInterface.class);

        Call<MessageResponse> call = service.sendMessage(message);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                Log.d("Log2", "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Log.d("Log2", "onFailure: " + t.getMessage());
            }
        });
    }

    private HttpLoggingInterceptor getLogger(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private OkHttpClient buildOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getLogger())
                .build();
    }

    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildOkHttpClient())
                .build();
    }
}

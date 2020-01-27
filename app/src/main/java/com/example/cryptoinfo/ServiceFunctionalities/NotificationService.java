package com.example.cryptoinfo.ServiceFunctionalities;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.cryptoinfo.Entities.BitcoinStatus;
import com.example.cryptoinfo.APIRetrofit.JsonPlaceholderApi;
import com.example.cryptoinfo.Activities.MainActivity;
import com.example.cryptoinfo.R;
import com.example.cryptoinfo.APIRetrofit.RetrofitClientInstance;
import com.example.cryptoinfo.Additional.Util;

import br.com.goncalves.pugnotification.notification.PugNotification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class NotificationService extends IntentService {

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Hit the api mentioned below every hour
       // if the current bitcoin price has fallen below the specified price
        // entered by the user, the app should send a notification
        // which would open the app when clicked.

       isNotificationNeeded();

    }

    private void isNotificationNeeded() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final float price = preferences.getFloat("price", Float.MAX_VALUE);

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

        JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);


        //async call
        Call<BitcoinStatus> call = jsonPlaceholderApi.getBitcoinStatus();
        call.enqueue(new Callback<BitcoinStatus>() {
            @Override
            public void onResponse(Call<BitcoinStatus> call, Response<BitcoinStatus> response) {
                float open_price=Float.MAX_VALUE;
                if(response.isSuccessful()){
                    BitcoinStatus stats = response.body();
                    if(stats.getOpen()!=null)
                    open_price = Util.getFloat(stats.getOpen());
                    if(open_price >= price){
                       fireNotification();

                    }

                }
            }

            @Override
            public void onFailure(Call<BitcoinStatus> call, Throwable t) {

            }
        });


    }

    private void fireNotification() {

        String title= getString(R.string.notif_title);
        PugNotification.with(this) //service extends from context indirectly
                .load()
                .title(title)
                .message(getString(R.string.notif_message))
                .bigTextStyle(getString(R.string.notif_bigText))
                .smallIcon(R.drawable.pugnotification_ic_launcher)
                .largeIcon(R.drawable.pugnotification_ic_launcher)
                .flags(Notification.DEFAULT_ALL)
                .autoCancel(true)
                .click(MainActivity.class)
                .simple()
                .build();


    }

}
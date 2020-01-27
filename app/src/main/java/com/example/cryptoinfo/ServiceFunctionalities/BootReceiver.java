package com.example.cryptoinfo.ServiceFunctionalities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.cryptoinfo.Additional.Util;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       // Log.i("TAG","onreceive called");
        Util.scheduleAlarm(context);
    }
}

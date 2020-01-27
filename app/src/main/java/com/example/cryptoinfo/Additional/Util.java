package com.example.cryptoinfo.Additional;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.cryptoinfo.ServiceFunctionalities.NotificationService;

public class Util {

    public static void scheduleAlarm(Context context){
        //invoking service
        try {

            AlarmManager manager = (AlarmManager) context.getSystemService(android.content.Context.ALARM_SERVICE);
            Intent intent = new Intent(context, NotificationService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, AlarmManager.INTERVAL_HOUR, pendingIntent);
        }catch (Exception e){}
    }

    public static float getFloat(String data)
    {
        return Float.parseFloat(data);
    }

    public static int getInt(String data){
        return Integer.parseInt(data);
    }

    public static boolean isValid(String data){
        if(data.matches("[0-9]+[.]?[0-9]+"))return true;
        return false;
    }
}

package com.androidbelieve.drawerwithswipetabs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by karth on 18-12-2016.
 */

public final class NotificationServiceStarterRepeater extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            Log.v("Starting","Alarm repeater");
            String pid="";
            SharedPreferences sp=context.getSharedPreferences("LOG",Context.MODE_PRIVATE);
            pid=sp.getString("abcxyz","");
            if(pid.equals(""))
                Log.v("PId is","\"\" LOl");
            try {
                EncryptDecrypt encryptDecrypt = new EncryptDecrypt("kthiksramAndroidDevs");
                pid=encryptDecrypt.decrypt(pid);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Log.e(getClass().getSimpleName(),"Error ");
            }
            NotificationReceiver.setupAlarm(context,pid.trim());
    }
}
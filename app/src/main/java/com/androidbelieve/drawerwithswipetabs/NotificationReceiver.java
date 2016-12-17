package com.androidbelieve.drawerwithswipetabs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.facebook.AccessToken;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by karth on 18-12-2016.
 */

public class NotificationReceiver extends BroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    private static final int NOTIFICATIONS_INTERVAL_IN_HOURS = 2;

    public static void setupAlarm(Context context) {
        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                new Intent(ACTION_START_NOTIFICATION_SERVICE),
                PendingIntent.FLAG_NO_CREATE) != null);
       if(!alarmUp) {
           AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
           PendingIntent alarmIntent = getStartPendingIntent(context);
           alarmManager.setInexactRepeating(AlarmManager.RTC,
                   getTriggerAt(new Date()),
                   NOTIFICATIONS_INTERVAL_IN_HOURS * AlarmManager.INTERVAL_HOUR,
                   alarmIntent);
           Log.v("Alarm Status","Starting up");
       }
        else
       {
           Log.v("Alarm Status","Already running");
       }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent serviceIntent = null;
        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)&&FirstActivity.isLogin()) {
            Log.i(getClass().getSimpleName(), "onReceive from alarm, starting notification service");
            serviceIntent = CheckNotificationService.createIntentStartNotificationService(context);
            serviceIntent.putExtra("pid", AccessToken.getCurrentAccessToken().getUserId());
        }
        if (serviceIntent != null) {
            context.startService(serviceIntent);
        }
        else
        {

        }
    }

    private static long getTriggerAt(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        //calendar.add(Calendar.HOUR, NOTIFICATIONS_INTERVAL_IN_HOURS);
        return calendar.getTimeInMillis();
    }
    private static PendingIntent getStartPendingIntent(Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
       // intent.putExtra("pid", AccessToken.getCurrentAccessToken().getUserId());
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    public static PendingIntent getDeleteIntent(Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}

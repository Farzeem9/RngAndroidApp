package com.androidbelieve.drawerwithswipetabs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by karth on 18-12-2016.
 */

public final class NotificationServiceStarterRepeater extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

            NotificationReceiver.setupAlarm(context);

    }
}
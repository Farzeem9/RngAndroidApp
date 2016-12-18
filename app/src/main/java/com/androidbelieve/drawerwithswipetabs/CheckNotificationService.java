package com.androidbelieve.drawerwithswipetabs;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by karth on 18-12-2016.
 */

public class CheckNotificationService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static int nonoticount=1;
    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, CheckNotificationService.class);
        intent.setAction(ACTION_START);
        return intent;
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String pid=intent.getStringExtra("pid");

        if(pid!=null) {
            Log.v("intent received",pid);
            final String link=Config.link+"checknoti.php?pid="+pid;
            Thread thread=new Thread()
            {
                @Override
                public void run()
                {
                    try
                    {
                        URL url=new URL(link);
                        Log.v("linkingeneric",link);
                        URLConnection con= url.openConnection();

                        Log.v("Wrote post parameter","okay");

                        BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }

                        Log.v("link:sb",link+"\t"+sb.toString());
                        if(sb.toString().equals("1"))
                        {
                            createNoti();
                            Log.v("Create Noti","okay");
                        }
                        else
                        {
                            noNoti();
                            Log.v("Create No Noti","okay");
                        }

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally {
                        CheckNotificationService.this.stopSelf();
                    }
                }
            };

            thread.start();

        }
    }
    public void noNoti()
    {
        Log.v("Current nonoticount",Integer.toString(nonoticount));
        if(nonoticount++%6==0)
        {
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("RnG Notification")
                    .setAutoCancel(true)
                    .setColor(getResources().getColor(R.color.darkred))
                    .setContentText("We miss you!Please check our latest deals!")
                    .setSmallIcon(R.drawable.gift);
            builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

            //LED
            builder.setLights(Color.RED, 3000, 3000);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(alarmSound);

            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    NOTIFICATION_ID,
                    new Intent(this, FirstActivity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            builder.setDeleteIntent(NotificationReceiver.getDeleteIntent(this));
            final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    public CheckNotificationService() {
        super(CheckNotificationService.class.getSimpleName());
    }
    private void createNoti()
    {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("RnG Notification")
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.darkred))
                .setContentText("You have new notifications!Enter the app to know more!")
                .setSmallIcon(R.drawable.gift);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                new Intent(this, NotificationActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(NotificationReceiver.getDeleteIntent(this));
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        //LED
        builder.setLights(Color.GREEN, 3000, 3000);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);


        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}

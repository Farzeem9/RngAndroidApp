package com.androidbelieve.drawerwithswipetabs;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Karan Bheda on 28-Oct-16.
 */
public class SendNoti extends AsyncTask<String,String,String> {
    String link;
    SendNoti(String link)
    {
        this.link=link;
    }
    @Override
    protected String doInBackground(String... params) {
        try
        {
           URL url=new URL(link);
            URLConnection con=url.openConnection();

            BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer sb=new StringBuffer();
            String lin=null;
            while((lin=br.readLine())!=null)
            {
                sb.append(lin);
            }
            Log.v("Result Send Noti",sb.toString());
            return sb.toString();


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result)
    {
        if(result==null||result.equals("no noti"))
        {
            Log.v("Notification","no noti sent");
        }
        else
        {
            Log.v("Noti Status","Notifiction sent!");
        }

    }
}

package com.androidbelieve.drawerwithswipetabs;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by karthik on 12/10/16.
 */

public class FAd extends AsyncTask<String, Void, String> {

    String link;
    int i;
    RecyclerView.Adapter adapter;
    private ArrayList<MyAds> albumList;
    FAd(String link, RecyclerView.Adapter adapter, ArrayList<MyAds> albumList)
    {
        this.link=link;
        this.adapter=adapter;
        this.albumList=albumList;
        i=0;

    }
    FAd(String link, int i, RecyclerView.Adapter adapter, ArrayList<MyAds> albumList)
    {
        this.link=link;
        this.adapter=adapter;
        this.albumList=albumList;

    }
    @Override
    protected String doInBackground(String... params) {


        String result = null;

        try {
            // json is UTF-8 by default

            String off="&OFF="+ Integer.toString(i);
            URL url = new URL(link+off);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
            Log.v("Result", result);
        } catch (Exception e) {
            // Oops
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        try {

            JSONObject jobj = new JSONObject(result);
            prepareAlbum(jobj.getJSONArray("result"),result);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    void prepareAlbum(JSONArray jarray, String result) {

        if (!result.equals("{\"result\":[]}")) {
            for (int i = 0; i < jarray.length(); i++) {
                try {
                    JSONObject ad = jarray.getJSONObject(i);
                    String name = ad.getString("PROD_NAME");
                    String aid = ad.getString("AID");
                    String timestamp=ad.getString("DURATION");
                    String amount = ad.getString("RENT");
                    String subcat = ad.getString("SUBCAT");
                    String rentw = ad.getString("RENTW");
                    String rentm = ad.getString("RENTM");
                    String crent = ad.getString("crent");
                    String[] temp = crent.split(",");
                    MyAds a = new MyAds("Active",name, amount, timestamp, aid,subcat,rentw,rentm,temp[0]);
                    albumList.add(a);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            adapter.notifyDataSetChanged();
        }

    }
}

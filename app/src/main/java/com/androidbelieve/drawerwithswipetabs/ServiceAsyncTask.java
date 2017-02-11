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
import java.util.ArrayList;

/**
 * Created by Manohar on 23-11-2016.
 */
public class ServiceAsyncTask extends AsyncTask<String, Void, String>{

        String link;
        int i;
        RecyclerView.Adapter adapter;
        private ArrayList<MyAds> albumList;
        ServiceAsyncTask(String link, RecyclerView.Adapter adapter, ArrayList<MyAds> albumList)
        {
            this.link=link;
            this.adapter=adapter;
            this.albumList=albumList;
            i=0;

        }
    ServiceAsyncTask(String link, int i, RecyclerView.Adapter adapter, ArrayList<MyAds> albumList)
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
                        String name = ad.getString("SNAME");
                        String aid = ad.getString("SID");
                        String timestamp=ad.getString("TIMESTAMP");
                        String amount = ad.getString("AMOUNT");
                        String subcat=ad.getString("SUBCATEGORY");
                        MyAds a = new MyAds("Active",name, amount, timestamp, aid, subcat);
                        albumList.add(a);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                adapter.notifyDataSetChanged();
            }

        }
}



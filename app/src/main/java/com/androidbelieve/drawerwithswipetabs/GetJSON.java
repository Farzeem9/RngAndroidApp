package com.androidbelieve.drawerwithswipetabs;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;



public class GetJSON extends AsyncTask<String, Void, String> {

    String link;
    int i;
    AlbumsAdapter adapter;
    private List<Album> albumList;
    GetJSON(String link,AlbumsAdapter adapter,List<Album> albumList)
    {
        this.link=link;
        this.adapter=adapter;
        this.albumList=albumList;
        i=0;

    }
    GetJSON(String link,int i,AlbumsAdapter adapter,List<Album> albumList)
    {
        this.link=link;
        this.adapter=adapter;
        this.albumList=albumList;
        this.i=i;
    }
    @Override
    protected String doInBackground(String... params) {

        String result = null;

        try {
            // json is UTF-8 by default

            String off="&OFF="+Integer.toString(i);
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
            prepareAlbum(jobj.getJSONArray("result"));

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    void prepareAlbum(JSONArray jarray)
    {
        for(int i=0;i<jarray.length();i++)
        {
            try {
                JSONObject ad = jarray.getJSONObject(i);
                String pid=ad.getString("PID");
                String name = ad.getString("PROD_NAME");
                String aid = ad.getString("AID");
                String subcat=ad.getString("CATEGORY");
                int amount = Integer.parseInt(ad.getString("AMOUNT"));

                Album a=new Album(subcat,pid,name,amount,i,aid);
                albumList.add(a);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }

}

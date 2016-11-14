package com.androidbelieve.drawerwithswipetabs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by 20000136 on 11/11/2016.
 */

public class GenericAsyncTask extends AsyncTask<String,String,String> {
    private boolean hasPost=false,setProgressView=false;
    private Context context;
    private ProgressDialog progress;
    String postdata=null;
    String link,message,result;
    public AsyncResponse delegate = null;
    GenericAsyncTask(Context context,String link,String messsage,AsyncResponse asyncResponse)
    {
        this.context=context;
        this.link=link;
        this.message=message;
        delegate=asyncResponse;
    }
    void setPostParams(String...Params)
    {
        hasPost=true;
        StringBuffer sb=new StringBuffer();
        int n=Params.length;
        if(n%2==1)
            return;
        sb.append(URLEncoder.encode(Params[0])+"="+URLEncoder.encode(Params[1]));
        for(int i=2;i<n-1;i+=2)
            sb.append("&"+URLEncoder.encode(Params[i])+"="+URLEncoder.encode(Params[i+1]));
        Log.v("sbtostring",sb.toString());
        postdata=sb.toString();
    }
    void setExtraPost(String...Params)
    {   if(!hasPost)
        return;
        int n=Params.length;
        StringBuffer sb=new StringBuffer(postdata);
        sb.append("&"+URLEncoder.encode(Params[0])+"="+URLEncoder.encode(Params[1]));
        for(int i=1;i<n;i++)
            sb.append("&"+URLEncoder.encode(Params[i])+"="+URLEncoder.encode(Params[i+1]));
        Log.v("sbtostring",sb.toString());
        postdata=sb.toString();
    }
    void setProgressView(boolean b)
    {
        this.setProgressView=true;
    }

    @Override
    protected void onPreExecute() {
        if(setProgressView)
        {
            progress = new ProgressDialog(context);
            progress.setMessage("Communicating with server..");
            progress.setIndeterminate(true);
            progress.setProgress(0);
            progress.setCancelable(false);
            progress.show();

        }
    }

    void removeProgress()
    {
        if(setProgressView)
            progress.dismiss();
    }


    @Override
    protected String doInBackground(String... strings) {
        if(strings.length>0)
            link+="?"+URLEncoder.encode(strings[0])+"="+URLEncoder.encode(strings[1]);
        for(int i=2;i<strings.length-1;i+=2)
            link+="&"+URLEncoder.encode(strings[i])+"="+URLEncoder.encode(strings[i+1]);
        try
        {
            URL url=new URL(link);
            Log.v("linkingeneric",link);
            URLConnection con= url.openConnection();
            if(hasPost)
            {
                Log.v("Postdata",postdata);
                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(postdata);
                wr.flush();

            }

            BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }


            return sb.toString();




        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        removeProgress();
        if(s!=null)
        {
//            new AlertDialog.Builder(context).setTitle("Alert").setMessage("Complete").setIcon(android.R.drawable.ic_dialog_alert).show();
        }
        else if(s==null)
        {
  //          new AlertDialog.Builder(context).setTitle("Alert").setMessage("There seems to have been an error").setIcon(android.R.drawable.ic_dialog_alert).show();
        }
        if(s!=null)
            delegate.processFinish(s);
    }
}

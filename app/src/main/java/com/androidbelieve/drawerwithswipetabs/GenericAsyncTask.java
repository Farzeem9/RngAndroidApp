package com.androidbelieve.drawerwithswipetabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

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
    void setImagePost(final ArrayList<Bitmap> images,final int x)
    {

        progress = new ProgressDialog(context);
        progress.setMessage("Communicating with server..");
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setCancelable(false);
        progress.show();

     Thread thread=new Thread()
     {
        @Override
         public void run()
        {
            Looper.prepare();

            int i=x;
            for(Bitmap b:images)
            {


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG,80,stream);
                String encodedString = Base64.encodeToString(stream.toByteArray(), 0);
                try {
                    stream.flush();
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //StringBuffer sb=new StringBuffer(postdata);
                //sb.append("&"+URLEncoder.encode("image"+Integer.toString(i++))+"="+URLEncoder.encode(encodedString));
                try {
                    postdata+="&"+ URLEncoder.encode("image" + Integer.toString(i++), "UTF-8") + "=" + URLEncoder.encode(encodedString, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }



            }
            execute();
        }
     };

        thread.start();

    }
    void setExtraPost(String...Params)
    {   if(!hasPost)
        return;
        int n=Params.length;
        Log.v("post param",Params[1]);
        StringBuffer sb=new StringBuffer(postdata);
        sb.append("&"+URLEncoder.encode(Params[0])+"="+URLEncoder.encode(Params[1]));
        for(int i=2;i<n;i++)
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
        if(progress==null)
            return;
        if(progress.isShowing())
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
                //Log.v("Postdata",postdata);
                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(postdata);
                wr.flush();

            }
            Log.v("Wrote post parameter","okay");

            BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            Log.v("link:sb",link+"\t"+sb.toString());

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
            delegate.processFinish(s);
    }
}

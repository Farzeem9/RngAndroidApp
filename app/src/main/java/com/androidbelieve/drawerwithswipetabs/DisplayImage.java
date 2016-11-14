

package com.androidbelieve.drawerwithswipetabs;
import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Base64InputStream;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class DisplayImage extends AsyncTask<String, Void, String> {

        String link;
        ImageView i;
        Bitmap b;
        DisplayImage(String link,ImageView i)
        {
            this.link=link;
            this.i=i;
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Base64InputStream bis=new Base64InputStream(input, Base64.DEFAULT);
                b = BitmapFactory.decodeStream(bis);

            } catch (Exception e) {
                e.printStackTrace();
                return "n";
            }
            return "y";
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("y")) {
                i.setImageBitmap(b);
            }


        }
}

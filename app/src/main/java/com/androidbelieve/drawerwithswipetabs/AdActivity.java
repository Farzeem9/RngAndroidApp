package com.androidbelieve.drawerwithswipetabs;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AdActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {

    private SliderLayout mDemoSlider;
    private TextView name,desc,rent,date,city,age,deposit,crent,maxrent;
    private String aid;
    private MenuItem star;
    private Button rating_comments;
    private RatingBar ratingBar;
    private String canrent;
    private boolean set=false;
    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private RadioButton less,more,equal;
    private boolean selected=false;
    private String rentperiod;
    private GetAd getAd;
    private GenericAsyncTask genericAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_ad);
        Config.GenTime(this);
        radioGroup= (RadioGroup) findViewById(R.id.rg_period);
        less= (RadioButton) findViewById(R.id.less);
        equal= (RadioButton) findViewById(R.id.equal);
        more= (RadioButton) findViewById(R.id.more);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        rating_comments= (Button) findViewById(R.id.btn_rate_comment);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("MANNNNNYNYYYYYY");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        aid = getIntent().getStringExtra("AID");
        name=(TextView)findViewById(R.id.tv_name);
        desc=(TextView)findViewById(R.id.tv_desc);
        rent=(TextView)findViewById(R.id.tv_rent);
        city=(TextView)findViewById(R.id.tv_location);
        age=(TextView)findViewById(R.id.tv_prod_age);
        deposit=(TextView)findViewById(R.id.tv_prod_dep);
        date=(TextView)findViewById(R.id.tv_date);
        crent=(TextView)findViewById(R.id.crent);
        maxrent=(TextView)findViewById(R.id.tv_max_rent);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar1);
        ratingBar.setMax(5);
        ratingBar.setFocusable(false);
        ratingBar.setFocusableInTouchMode(false);
        ratingBar.setClickable(false);

        rating_comments.setClickable(false);
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching ad Please wait");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        getAd=new GetAd(aid,AccessToken.getCurrentAccessToken().getUserId());
        getAd.execute();
        genericAsyncTask=new GenericAsyncTask(this, Config.link+"sendrating.php?aid=" + aid, "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                int i=Integer.parseInt((String)output);
                ratingBar.setProgress(i);
                rating_comments.setClickable(true);
                progressDialog.dismiss();
            }
        });
        genericAsyncTask.execute();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String abc= checkedId+"";
                //Toast.makeText(AdActivity.this,abc, Toast.LENGTH_SHORT).show();
                RadioButton rb=(RadioButton)findViewById(checkedId);
                rentperiod=rb.getText().toString();
                rb=(RadioButton)findViewById(R.id.less);
                rb.setFocusableInTouchMode(false);
                rb.setError(null);
                selected=true;
            }
        });

    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
        if(getAd!=null)
        if(!(getAd.getStatus()== AsyncTask.Status.FINISHED))
            getAd.cancel(true);
        if(genericAsyncTask!=null)
        if(!(genericAsyncTask.getStatus()== AsyncTask.Status.FINISHED))
            genericAsyncTask.cancel(true);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }*/


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void onRent(final String message)
    {
            AsyncTask<String,String,String> s=new AsyncTask<String, String, String>() {
                String link=Config.link+"reqNoti.php?message="+ URLEncoder.encode(message)+"%20for%20"+URLEncoder.encode(rentperiod);
                @Override
                protected String doInBackground(String... params) {
                    try {
                        URL url = new URL(link+params[0]+"&aid="+params[1]+"&ad=AD");

                        URLConnection connection=url.openConnection();

                        BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line=null;
                        StringBuffer sb=new StringBuffer();
                        while((line=br.readLine())!=null)
                            sb.append(line);

                        Log.v("Result",sb.toString());
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

                    AlertDialog.Builder alertbox = new AlertDialog.Builder(AdActivity.this);
                    alertbox.setTitle("Request");
                    if(result==null)
                    {
                        alertbox.setMessage("There was some error in server, Please try again later");
                        alertbox.show();
                        return;
                    }

                    if(result.contains("SUCCESS"))
                        alertbox.setMessage("Request sent!");
                    else if(result.contains("Same user"))
                        alertbox.setMessage("You cannot request to your own ad!");
                    else if(result.contains("FAILURE"))
                        alertbox.setMessage("Request was already sent before");
                    else
                        alertbox.setMessage("There was some error in server, Please try again later");

                    alertbox.show();


                }
            };
            s.execute("&pid="+AccessToken.getCurrentAccessToken().getUserId(),aid);
        }

    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    class GetAd extends AsyncTask<String, String, String> {
        String aid,pid;
        GetAd(String aid,String pid) {
            this.aid = aid;
            this.pid=pid;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {

                String link = Config.link+"fetchad.php?aid=" + aid+"&pid="+pid;
                Log.v("link",link);
                URL url = new URL(link);
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
                fillAdd(jobj.getJSONArray("result"));
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    static String Month(Date d)
    {
        int i=d.getMonth();
        switch(i)
        {
            case 0: return "Jan";
            case 1: return "Feb";
            case 2: return "Mar";
            case 3: return "Apr";
            case 4: return "May";
            case 5: return "Jun";
            case 6: return "July";
            case 7: return "Aug";
            case 8: return "Sept";
            case 9: return "Oct";
            case 10: return "Nov";
            case 11: return "Dec";
            default:return "Dec";
        }

    }


    void fillAdd(JSONArray jarray)
    {
        try {
            JSONObject c = jarray.getJSONObject(0);
            String prod_name=c.getString("PROD_NAME");
            String rent_name=c.getString("RENT");
            String desc_str=c.getString("DESC");
            String timestamp=c.getString("TIMESTAMP");
            String city=c.getString("LOCATION");
            String age=c.getString("AGE");
            String deposit=c.getString("DEPOSIT");
            String cat=c.getString("SUBCAT");
            canrent=c.getString("CANRATE");
            String[] crent=c.getString("crent").split(",");
            String temp="";
            for(String x:crent)
            temp+=x+" ";
            this.crent.setText(temp);

            String maxrent=c.getString("maxrent");
            Log.v("maxrent",maxrent);
            int num=Character.digit(maxrent.charAt(maxrent.length()-1),10);
            //maxrent="Around "+Integer.toString(num)+maxrent.substring(0,maxrent.length()-1);
            String temp2=new String("Around "+Integer.toString(num)+maxrent.substring(0,maxrent.length()-1));
            Log.v("maxrent",temp2);

            this.maxrent.setText(temp2);
            ((TextView)findViewById(R.id.tv_subcat)).setText(cat);
            String ddate;
            Date today=new Date();
            Date yesterday=new Date();
            yesterday.setTime(today.getTime()-((long)864E5));
            Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
            date.setTime(date.getTime()+19800000);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            Log.v("timeStamp",timestamp);
            Log.v("date",date.toString());
            if(sdf.format(today).equals(sdf.format(date)))
                ddate="Today ";
            else if(sdf.format(yesterday).equals(sdf.format(date)))
                ddate="Yesterday ";
            else
            {
                ddate=new SimpleDateFormat("d MMMM").format(date);
                if(!(today.getYear()==date.getYear()))
                    ddate+=" "+date.getYear()+" ";
            }
            this.date.setText(ddate);
            this.city.setText(city);
            this.age.setText(age + " Years");
            this.deposit.setText("₹ "+ deposit);
            JSONArray links=c.getJSONArray("LINKS");
            ArrayList<String> alllinks=new ArrayList<>();
            for(int i=0;i<links.length();i++)
                alllinks.add(links.getJSONObject(i).getString("link"));

            name.setText(prod_name);
            desc.setText(desc_str);
            rent.setText("₹ "+ rent_name);
            for(String name : alllinks){
                //Log.v("");
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView.image(name).setScaleType(BaseSliderView.ScaleType.Fit);

                mDemoSlider.addSlider(textSliderView);
            }
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
    public void onShare(View view){
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Subject Here");
        startActivity(Intent.createChooser(intent,"Sharing Option"));
    }
    public void onRateAndComment(View view){
        Intent i=new Intent(this,RateActivity.class);
        i.putExtra("aid",aid);
        i.putExtra("CANRATE",canrent);
        startActivity(i);
    }

    void getMenus(Menu menu)
    {
        star=menu.findItem(R.id.action_wishlist);
        GenericAsyncTask g=new GenericAsyncTask(this, Config.link+"checkwishlist.php?aid=" + aid + "&pid=" + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                String out=(String)output;
                if(out.equals("1"))
                {
                    star.setIcon(R.drawable.added_to_wishlist);
                    set=!set;
                    Log.v("output of async",out);
                }
                else
                {
                    star.setIcon(R.drawable.add_to_whishlist);
                    set=false;
                    Log.v("output of async",out);
                }
            }
        });
        g.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_show_ad, menu);
        getMenus(menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                return true;
            case R.id.action_wishlist:
                GenericAsyncTask g=new GenericAsyncTask(this, Config.link+"wishlist.php?aid=" + aid + "&pid=" + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                    if(set)
                        star.setIcon(R.drawable.add_to_whishlist);
                    else
                        star.setIcon(R.drawable.added_to_wishlist);

                    set=!set;
                    }
                });
                g.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onUrgentRent(View view){
        if(!selected) {
            //radioGroup.requestFocusInWindow();

            RadioButton rb=(RadioButton)findViewById(R.id.less);
            rb.setFocusableInTouchMode(true);
            rb.setError("Please select one option!");
            rb.requestFocus();

            return;
        }

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        alertbox.setTitle("Urgent Rent");
        alertbox.setMessage("Do you want to urgently rent this time ?");

        alertbox.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        onRent("rent");
                    }
                });

        alertbox.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        onRent("urgently rent");
                    }
                });
        alertbox.show();
    }
}

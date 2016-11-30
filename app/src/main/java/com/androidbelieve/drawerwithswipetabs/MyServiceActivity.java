package com.androidbelieve.drawerwithswipetabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyServiceActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener{
    private SliderLayout mDemoSlider;
    private ArrayList<Bitmap> images;
    private boolean imageshown=false;
    private ArrayList<String>links=new ArrayList<>();
    private ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    private ViewPager viewPager;
    private TextView name,desc,rent,date,subcat,age,projlinks;
    private String sid;
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
    private GenericAsyncTask genericAsyncTask;
    private HorizontalAdapter HorizontalAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_my_service);
        radioGroup= (RadioGroup) findViewById(R.id.rg_period);
        less= (RadioButton) findViewById(R.id.less);
        equal= (RadioButton) findViewById(R.id.equal);
        more= (RadioButton) findViewById(R.id.more);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        rating_comments= (Button) findViewById(R.id.btn_rate_comment);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("MANNNNNYNYYYYYY");
        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.ic_media_previous));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sid = getIntent().getStringExtra("SID");
        name=(TextView)findViewById(R.id.tv_sname);
        desc=(TextView)findViewById(R.id.tv_desc);
        rent=(TextView)findViewById(R.id.tv_rent);
        subcat=(TextView)findViewById(R.id.tv_subcat);
        //age=(TextView)findViewById(R.id.tv_prod_age);
        projlinks=(TextView)findViewById(R.id.tv_link);
        date=(TextView)findViewById(R.id.tv_date);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar1);
        ratingBar.setMax(5);
        ratingBar.setFocusable(false);
        ratingBar.setFocusableInTouchMode(false);
        ratingBar.setClickable(false);
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching ad Please wait");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        GenericAsyncTask genericAsyncTaskgetservice=new GenericAsyncTask(this, Config.link+"getserviceforedit.php?sid=" + sid, "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                try {
                    Log.v("AB","CD");
                    fillAdd(new JSONObject((String)output).getJSONArray("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        genericAsyncTaskgetservice.execute();

        //getAd=new GetAd(sid, AccessToken.getCurrentAccessToken().getUserId());
        //getAd.execute();
//        rating_comments.setClickable(false);
        //final ProgressDialog progressDialog=new ProgressDialog(this);
       // progressDialog.setMessage("Fetching ad Please wait");
       // progressDialog.setIndeterminate(true);
      //  progressDialog.show();

/*        genericAsyncTask=new GenericAsyncTask(this, "http://rng.000webhostapp.com/sendrating.php?sid=" + sid, "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                int i=Integer.parseInt((String)output);
                ratingBar.setProgress(i);
                rating_comments.setClickable(true);
                progressDialog.dismiss();
            }
        });*/
        //genericAsyncTask.execute();

/*        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String abc= checkedId+"";
                Toast.makeText(MyServiceActivity.this,abc, Toast.LENGTH_SHORT).show();
                RadioButton rb=(RadioButton)findViewById(checkedId);
                rentperiod=rb.getText().toString();
                selected=true;
            }
        });*/
    }
    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        //mDemoSlider.stopAutoCycle();
        super.onStop();
        //if(getAd!=null)
          //  if(!(getAd.getStatus()== AsyncTask.Status.FINISHED))
            //    getAd.cancel(true);
        //if(genericAsyncTask!=null)
          //  if(!(genericAsyncTask.getStatus()== AsyncTask.Status.FINISHED))
            //    genericAsyncTask.cancel(true);
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
            String link=Config.link+"reqNoti.php?pid=&message="+message+" for "+rentperiod;
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(link+params[0]+"&sid="+params[1]);
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

            }
        };
        s.execute(AccessToken.getCurrentAccessToken().getUserId(),sid);
    }

    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    class GetAd extends AsyncTask<String, String, String> {
        String sid,pid;
        GetAd(String sid,String pid) {
            this.sid = sid;
            this.pid=pid;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {

                String link = Config.link+"getserviceforedit.php?sid=" + sid+"&pid="+pid;
                Log.v("link",link);
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                Log.v("INSIDE","GETAD");
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

            String prod_name=c.getString("SNAME");
            String rent_name=c.getString("RENT");
            String desc_str=c.getString("DESC");
            String cat=c.getString("CAT");

            String timestamp=c.getString("TIMESTAMP");
            String city=c.getString("CITY");
            String subcat=c.getString("SUBCAT");

            JSONArray links=c.getJSONArray("SLINKS");
            String allprojlinks="";
            for(int i=0;i<links.length();i++) {
                this.links.add(links.getJSONObject(i).getString("link"));
                allprojlinks+=links.getJSONObject(i).getString("link")+"\n";
            }

            projlinks.setText(allprojlinks);

            JSONArray ilinks=c.getJSONArray("LINKS");
            ArrayList<String> alllinks=new ArrayList<>();
            for(int i=0;i<ilinks.length();i++)
                alllinks.add(ilinks.getJSONObject(i).getString("link"));

            //canrent=c.getString("CANRATE");

            Date today=new Date();
            String ddate;
            Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
            Log.v("timeStamp",timestamp);
            Log.v("date",date.toString());
            if(today.getDay()==date.getDay())
                ddate="Today ";
            else if(today.getDay()==date.getDay()+1)
                ddate="Yesterday ";
            else
            {

                ddate=date.getDay()+" "+Month(date)+" ";
                if(!(today.getYear()==date.getYear()))
                    ddate+=date.getYear()+" ";
            }
            this.date.setText(ddate);
            this.subcat.setText(subcat);
            name.setText(prod_name);
            desc.setText(desc_str);
            rent.setText("₹ "+ rent_name);
            final int length[]={alllinks.size()};
            for(String x:alllinks) {

                Picasso.with(this).load(x).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        images.add(bitmap);
                        HorizontalAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
                Log.v("link in picasso",x);
            }


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
        i.putExtra("sid",sid);
        i.putExtra("CANRATE",canrent);
        startActivity(i);
    }

    void getMenus(Menu menu)
    {
        star=menu.findItem(R.id.action_wishlist);
        GenericAsyncTask g=new GenericAsyncTask(this, "http://rng.000webhostapp.com/checkwishlist.php?aid=" + sid + "&pid=" + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                String out=(String)output;
                if(out.equals("1"))
                {
                    star.setIcon(android.R.drawable.btn_star_big_on);
                    set=!set;
                    Log.v("output of async",out);
                }
                else
                {
                    star.setIcon(android.R.drawable.btn_star_big_off);
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
                GenericAsyncTask g=new GenericAsyncTask(this, "http://rng.000webhostapp.com/wishlist.php?aid=" + sid + "&pid=" + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        if(set)
                            star.setIcon(android.R.drawable.btn_star_big_off);
                        else
                            star.setIcon(android.R.drawable.btn_star_big_on);

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
            radioGroup.requestFocus(View.LAYOUT_DIRECTION_LOCALE);
            return;
        }

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        alertbox.setTitle("Urgent Rent");
        alertbox.setMessage("Do you want to urgently rent this time ?");

        alertbox.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        onRent("");
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
    class HorizontalAdapter  extends RecyclerView.Adapter<MyServiceActivity.HorizontalAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<Bitmap> images;
        private int thumbnail=0;
        public HorizontalAdapter(Context mContext, final ArrayList<Bitmap> images) {
            this.mContext = mContext;
            this.images=images;
            Log.v("Adapter created","Created");

        }
        void setPosition(int i)
        {
            thumbnail=i;
        }

        @Override
        public MyServiceActivity.HorizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_pic, parent, false);
            Log.v("oncreateViewholder","currect");
            return new MyServiceActivity.HorizontalAdapter.MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final MyServiceActivity.HorizontalAdapter.MyViewHolder holder, final int position) {
            holder.i.setImageBitmap(images.get(position));
            Log.v("inside","holder setting bitmap");
            /**
             *Set all onclicks here
             *
             */
            holder.i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reInstantiatePager();
                    reInstantiatePager();
                    imageshown=true;
                    viewPager.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(position);

                }
            });
            holder.relativeLayout.setBackgroundResource(R.drawable.empty);

            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    images.remove(position);
                    if(thumbnail==position) {
                        thumbnail = 0;
                    }
                    notifyDataSetChanged();
                    reInstantiatePager();                }
            });
        }
        @Override
        public int getItemCount() {
            return images.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            Button set;
            ImageView i;
            ImageButton del;
            RelativeLayout relativeLayout;
            public MyViewHolder(View view) {
                super(view);
                del = (ImageButton) view.findViewById(R.id.yes_bt);
                del.setVisibility(View.GONE);
                i = (ImageView) view.findViewById(R.id.act_image);
                relativeLayout=(RelativeLayout)view.findViewById(R.id.rel_lay);
                //set=(Button)view.findViewById(R.id.button);
            }
        }
    }
    void reInstantiatePager()
    {
        viewPager.setAdapter(null);
        viewPager.setAdapter(new ImageFragmentPagerAdapter(getSupportFragmentManager(), images, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidePager();
            }
        }));

    }
    void hidePager()
    {
        imageshown=false;
        Log.v("Clicked","Hidden?");
        viewPager.setVisibility(View.GONE);

    }

}

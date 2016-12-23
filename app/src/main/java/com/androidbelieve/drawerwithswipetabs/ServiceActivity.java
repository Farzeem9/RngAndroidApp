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
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ServiceActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener {

    //private SliderLayout mDemoSlider;
    private ArrayList<Bitmap> images;
    private boolean imageshown=false;
    private ArrayList<String>links=new ArrayList<>();
    private ImageFragmentPagerAdapter imageFragmentPagerAdapter;
    private ViewPager viewPager;
    private TextView name,desc,rent,date,subcat,age,projlinks,default_text,tv_city;
    private String sid;
    private MenuItem star;
    private Button rating_comments;
    private RatingBar ratingBar;
    private String canrent;
    private boolean set=false;
    private Toolbar toolbar;
    private boolean selected=false;
    private String rentperiod;
    private GenericAsyncTask genericAsyncTask;
    private HorizontalAdapter HorizontalAdapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        if(!imageshown)
        super.onBackPressed();
        else
        {
            hidePager();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_service);
        String sid=getIntent().getStringExtra("sid");
        this.sid=sid;
        default_text = (TextView) findViewById(R.id.default_text);
        rating_comments= (Button) findViewById(R.id.btn_rate_comment);
        images=new ArrayList<>(5);

        viewPager=(ViewPager)findViewById(R.id.pager);
        imageFragmentPagerAdapter = new ImageFragmentPagerAdapter(getSupportFragmentManager(), images, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidePager();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.rr);
        HorizontalAdapter=new ServiceActivity.HorizontalAdapter(getApplicationContext(),images);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(HorizontalAdapter);

        viewPager.setAdapter(imageFragmentPagerAdapter);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("MANNNNNYNYYYYYY");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_name));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sid = getIntent().getStringExtra("sid");
        name=(TextView)findViewById(R.id.tv_sname);
        desc=(TextView)findViewById(R.id.tv_desc);
        rent=(TextView)findViewById(R.id.tv_rent);
        subcat=(TextView)findViewById(R.id.tv_subcat);
        tv_city=(TextView)findViewById(R.id.tv_city);
        //age=(TextView)findViewById(R.id.tv_prod_age);

        projlinks=(TextView)findViewById(R.id.tv_link);
        //lvlinks=(ListView)findViewById(R.id.lv_links);
        //setListViewHeightBasedOnChildren(lvlinks);
        //linksAdapter=new ServiceFragment.LinksAdapter();

        date=(TextView)findViewById(R.id.tv_date);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar1);
        ratingBar.setMax(5);
        ratingBar.setFocusable(false);
        ratingBar.setFocusableInTouchMode(false);
        ratingBar.setClickable(false);

        rating_comments.setClickable(false);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching ad Please wait");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        //getAd=new GetAd(sid,AccessToken.getCurrentAccessToken().getUserId());
        //getAd.execute();
        GenericAsyncTask genericAsyncTaskgetservice=new GenericAsyncTask(this, Config.link+"getserviceforedit.php?pid="+AccessToken.getCurrentAccessToken().getUserId()+"&sid=" + sid, "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                try {
                    fillAdd(new JSONObject((String)output).getJSONArray("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        genericAsyncTaskgetservice.execute();
        genericAsyncTask=new GenericAsyncTask(this, Config.link+"sendratingservice.php?sid=" + sid, "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                int i=Integer.parseInt((String)output);
                ratingBar.setProgress(i);
                rating_comments.setClickable(true);
                if(progressDialog.isShowing())
                progressDialog.setMessage("Fetching images!");
            }
        });
       genericAsyncTask.execute();



    }

    @Override
    protected void onDestroy() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        super.onDestroy();
        if(genericAsyncTask!=null)
            if(!(genericAsyncTask.getStatus()== AsyncTask.Status.FINISHED))
                genericAsyncTask.cancel(true);
    }

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
                    URL url = new URL(link+params[0]+"&aid="+params[1]+"&ad=SERVICE");
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

                AlertDialog.Builder alertbox = new AlertDialog.Builder(ServiceActivity.this);
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
                    alertbox.setMessage("You cannot request to your own service!");
                else if(result.contains("FAILURE"))
                    alertbox.setMessage("Request was already sent before");
                else
                    alertbox.setMessage("There was some error in server, Please try again later");
                alertbox.show();


            }
        };
        s.execute("&pid="+AccessToken.getCurrentAccessToken().getUserId(),sid);
    }

    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
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

            tv_city.setText(city);
            JSONArray links=c.getJSONArray("SLINKS");
            Linkify.addLinks(projlinks,Linkify.WEB_URLS);
            String allprojlinks="";
            for(int i=0;i<links.length();i++) {
                this.links.add(links.getJSONObject(i).getString("link"));
                allprojlinks+=links.getJSONObject(i).getString("link")+" \n";
            }
            if(allprojlinks.isEmpty())
                projlinks.setText("No Links to Preview");
            else
                projlinks.setText(allprojlinks);
            JSONArray ilinks=c.getJSONArray("LINKS");
            ArrayList<String> alllinks=new ArrayList<>();
            for(int i=0;i<ilinks.length();i++)
                alllinks.add(ilinks.getJSONObject(i).getString("link"));

            canrent=c.getString("CANRATE");

            Date today=new Date();
            String ddate;
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

                //this.date=date.getDay()+" "+Month(date)+" ";
                ddate=new SimpleDateFormat("d MMMM").format(date);
                if(!(today.getYear()==date.getYear()))
                    ddate+=" "+date.getYear()+" ";
            }
            this.date.setText(ddate);
            this.subcat.setText(subcat);
            name.setText(prod_name);
            toolbar.setTitle(prod_name);
            desc.setText(desc_str);
            rent.setText("₹ "+ rent_name);
            final int length[]={alllinks.size()};
            if(alllinks.size()==0)
            {
                //Set photos to null
                recyclerView.setVisibility(View.GONE);
                default_text.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            }
            /*for(String x:alllinks) {

                Picasso.with(this).load(x).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        if(length[0]--==1){
                            progressDialog.dismiss();
                        }
                        Log.v("new Bitmap loaded","okay");
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
*/
            HorizontalAdapter.addLinks(alllinks);
            progressDialog.dismiss();
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
        GenericAsyncTask g=new GenericAsyncTask(this, Config.link+"checkwishlistservice.php?sid=" + sid + "&pid=" + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
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
                GenericAsyncTask g=new GenericAsyncTask(this, Config.link+"servicewishlist.php?sid=" + sid + "&pid=" + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        if(set)
                            star.setIcon(R.drawable.add_to_whishlist);
                        else
                            star.setIcon(R.drawable.added_to_wishlist);
                    Log.v("Async out",(String)output);
                        set=!set;
                    }
                });
                g.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    void hidePager()
    {
        imageshown=false;
        Log.v("Clicked","Hidden?");
        viewPager.setVisibility(View.GONE);

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
    public void onUrgentRent(View view){
        if(!selected) {
            RadioButton rb=(RadioButton)findViewById(R.id.less);
            rb.setFocusableInTouchMode(true);
            rb.setError("Please select at least one option!");
            rb.requestFocus();
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

    /*class LinksAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return links.size();
        }

        @Override
        public Object getItem(int i) {
            return links.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ServiceActivity.LinksHolder holder = null;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.card_link, null);//Null for whole xml document
                holder = new ServiceActivity.LinksHolder();
                holder.ltv = (TextView) convertView.findViewById(R.id.tv_link_display);
                holder.itv = (ImageView)convertView.findViewById(R.id.bt_link_remove);
                convertView.setTag(holder);

            } else {
                holder = (ServiceActivity.LinksHolder) convertView.getTag();
            }
            String cur_link=links.get(i).toString();
            Log.v("Current",cur_link);
            holder.ltv.setText(cur_link);
            holder.ltv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String a="http://"+links.get(i).toString();
                    Intent intent=new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(a));
                    startActivity(intent);
                }
            });
            holder.itv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    links.remove(i);
                    lvlinks.setAdapter(linksAdapter);
                }
            });
            return convertView;
        }
    }
    class LinksHolder {
        TextView ltv;
        ImageView itv;
    }
    */
    class HorizontalAdapter  extends RecyclerView.Adapter<ServiceActivity.HorizontalAdapter.MyViewHolder> {
        private Context mContext;
        private ArrayList<Bitmap> images;
        private ArrayList<String> links;
        private int thumbnail=0;
        public HorizontalAdapter(Context mContext, final ArrayList<Bitmap> images) {
            this.mContext = mContext;
            this.images=images;
            links=new ArrayList<>(5);
            Log.v("Adapter created","Created");

        }
        void setPosition(int i)
        {
            thumbnail=i;
        }

        @Override
        public ServiceActivity.HorizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_pic, parent, false);
            Log.v("oncreateViewholder","currect");
            return new ServiceActivity.HorizontalAdapter.MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final ServiceActivity.HorizontalAdapter.MyViewHolder holder, final int position) {
         //   holder.i.setImageBitmap(images.get(position));
            //Picasso.with(mContext).load(links.get(position)).error(R.drawable.car).fit().placeholder(R.drawable.image_placeholder).into(holder.i);
            Target t=new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    if(position<images.size())
                    images.add(position,bitmap);
                    else
                    images.add(bitmap);
                    holder.i.setImageBitmap(bitmap);
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
                    holder.i.setAnimation(null);
                    Log.v("Bitmap set!","okay");
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    holder.i.setImageDrawable(errorDrawable);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    RotateAnimation anim = new RotateAnimation(0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
                    anim.setInterpolator(new LinearInterpolator());
                    anim.setRepeatCount(Animation.INFINITE);
                    anim.setDuration(700);
                    holder.i.startAnimation(anim);

                }
            };
            holder.i.setTag(t);

            Picasso.with(mContext).load(links.get(position)).error(R.drawable.car).placeholder(R.drawable.loading).into(t);
            Log.v("inside","holder setting bitmap");
            /**
             *Set all onclicks here
             *
             */
            holder.setIsRecyclable(false);
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
            return this.links.size();
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
        public void addLinks(ArrayList<String> newLinks)
        {
            this.links.addAll(newLinks);
            Log.v("links added","okay");
            HorizontalAdapter.notifyDataSetChanged();
        }
    }

}

package com.androidbelieve.drawerwithswipetabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.apptik.widget.MultiSlider;

public class FilterServiceActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<Filter> subcat=new ArrayList<>(),rent=new ArrayList<>();
    private RecyclerView recyclerView;
    private FilterAdapter adapter;
    private String cat;
    private void fillAds(String cat)
    {
        if (cat.equals("Graphics and Design")) {
            //   Toast.makeText(getContext(), "Graphics and Design", Toast.LENGTH_SHORT).show();
            subcat.clear();
            subcat.add(new Filter("Logo Design"));
            subcat.add(new Filter("Business Card & Strategies"));
            subcat.add(new Filter("Illustration"));
            subcat.add(new Filter("Cartoons & Caricatures"));
            subcat.add(new Filter("Flyers & Posters"));
            subcat.add(new Filter("Book Cover & Packaging"));
            subcat.add(new Filter("Web & Mobile Design"));
            subcat.add(new Filter("Social Media Design"));
            subcat.add(new Filter("Banner Ads"));
            subcat.add(new Filter("Photoshop Editing"));
            subcat.add(new Filter("2D & 3D Models"));
            subcat.add(new Filter("T-Shirts"));
            subcat.add(new Filter("Presentation Design"));
            subcat.add(new Filter("Infographics"));
            subcat.add(new Filter("Vector Tracing"));
            subcat.add(new Filter("Invitation"));
            subcat.add(new Filter("Others"));
            //categories2.remove("Select a Sub-Category");
        }
        else if (cat.equals("Digital Marketing")) {
            subcat.clear();
            //spinner2.
            subcat.add(new Filter("Social Media Marketing"));
            subcat.add(new Filter("SEO"));
            subcat.add(new Filter("Web Traffic"));
            subcat.add(new Filter("Content Marketing"));
            subcat.add(new Filter("Video Advertising"));
            subcat.add(new Filter("Email Marketing"));
            subcat.add(new Filter("SEM"));
            subcat.add(new Filter("Marketing Strategy"));
            subcat.add(new Filter("Web Analytics"));
            subcat.add(new Filter("Influencer Marketing"));
            subcat.add(new Filter("Local Listing"));
            subcat.add(new Filter("Domain Research"));
            subcat.add(new Filter("Mobile Advertising"));
            subcat.add(new Filter("Others"));

        }
        else if (cat.equals("Writing and Translation")) {
            subcat.clear();
            subcat.add(new Filter("Resumes & Cover Letters"));
            subcat.add(new Filter("ProofReading & Editing"));
            subcat.add(new Filter("Translation"));
            subcat.add(new Filter("Creative Writing"));
            subcat.add(new Filter("Business Copywriting"));
            subcat.add(new Filter("Research & Summaries"));
            subcat.add(new Filter("Articles & Blog Posts"));
            subcat.add(new Filter("Press Release"));
            subcat.add(new Filter("Transcription"));
            subcat.add(new Filter("Legal Writing"));
            subcat.add(new Filter("Others"));
            }
        else if (cat.equals("Video and Animation")) {
            // Toast.makeText(getContext(), "Video and Animation", Toast.LENGTH_SHORT).show();
            subcat.clear();
            subcat.add(new Filter("Whiteboard & Explainer Videos"));
            subcat.add(new Filter("Intros & Animated Logos"));
            subcat.add(new Filter("Promotional & Brand Videos"));
            subcat.add(new Filter("Editing & Post Production"));
            subcat.add(new Filter("Lyrics & Music Videos"));
            subcat.add(new Filter("Spokesperson & Testimonial"));
            subcat.add(new Filter("Animated Character & Modeling"));
            subcat.add(new Filter("Video Greetings"));
            subcat.add(new Filter("Others"));
            }
        if (cat.equals("Music and Audio")) {
            subcat.clear();
            subcat.add(new Filter("Voice Over"));
            subcat.add(new Filter("Mixing & Mastering"));
            subcat.add(new Filter("Producers & Consumers"));
            subcat.add(new Filter("Singer-Songwriters"));
            subcat.add(new Filter("Session Musicians & Singers"));
            subcat.add(new Filter("Jingles & Drops"));
            subcat.add(new Filter("Sound Effects"));
            subcat.add(new Filter("Others"));
             // categories2.remove("Select a Sub-Category");
        }
        else if (cat.equals("Programming and Tech")) {
            // Toast.makeText(getContext(), "Programming and Tech", Toast.LENGTH_SHORT).show();
            subcat.clear();
            subcat.add(new Filter("Wordpress"));
            subcat.add(new Filter("Website Builder & CMS"));
            subcat.add(new Filter("Website Programming"));
            subcat.add(new Filter("E-Commerce"));
            subcat.add(new Filter("Mobile Apps & Web"));
            subcat.add(new Filter("Desktop Application"));
            subcat.add(new Filter("Support & IT"));
            subcat.add(new Filter("Data Analyst & Reports"));
            subcat.add(new Filter("Convert Files"));
            subcat.add(new Filter("Databases"));
            subcat.add(new Filter("User Testing"));
            subcat.add(new Filter("QA"));
            subcat.add(new Filter("Others"));
            // categories2.remove("Select a Sub-Category");
        }
        else if (cat.equals("Advertising")) {
            //Toast.makeText(getContext(), "Advertising", Toast.LENGTH_SHORT).show();
            subcat.clear();
            subcat.add(new Filter("Music Promotion"));
            subcat.add(new Filter("Radio"));
            subcat.add(new Filter("Banner Advertising"));
            subcat.add(new Filter("Outdoor Advertising"));
            subcat.add(new Filter("Flyer & Handouts"));
            subcat.add(new Filter("Hold your sign"));
            subcat.add(new Filter("Human Billboards"));
            subcat.add(new Filter("Pet Models"));
            subcat.add(new Filter("Others"));
            //  categories2.remove("Select a Sub-Category");
        }
        else if (cat.equals("Business")) {
            //Toast.makeText(getContext(), "Business", Toast.LENGTH_SHORT).show();
            subcat.clear();
            subcat.add(new Filter("Virtual Assistant"));
            subcat.add(new Filter("Market Research"));
            subcat.add(new Filter("Business Plans"));
            subcat.add(new Filter("Branding Services"));
            subcat.add(new Filter("Legal Consulting"));
            subcat.add(new Filter("Financial Consulting"));
            subcat.add(new Filter("Business Tips"));
            subcat.add(new Filter("Presentations"));
            subcat.add(new Filter("Career Advice"));
            subcat.add(new Filter("Others"));
            //categories2.remove("Select a Sub-Category");
        }
        else if (cat.equals("Lifestyle")) {
            //    Toast.makeText(getContext(), "Lifestyle", Toast.LENGTH_SHORT).show();
            subcat.clear();
            subcat.add(new Filter("Animal Care & Pets"));
            subcat.add(new Filter("Relationship Advice"));
            subcat.add(new Filter("Diet & Weight Loss"));
            subcat.add(new Filter("Health & Fitness"));
            subcat.add(new Filter("Weddiing Planning"));
            subcat.add(new Filter("Makeup,Styling & Beauty"));
            subcat.add(new Filter("Online Private Lessons"));
            subcat.add(new Filter("Astrology & Fortune Telling"));
            subcat.add(new Filter("Spiritual & Healing"));
            subcat.add(new Filter("Cooking Recipes"));
            subcat.add(new Filter("Parenting Tips"));
            subcat.add(new Filter("Travel"));
            subcat.add(new Filter("Others"));
            //  categories2.remove("Select a Sub-Category");
        }
        else if (cat.equals("Gifts")) {
            //  Toast.makeText(getContext(), "Gifts", Toast.LENGTH_SHORT).show();

            subcat.clear();
            subcat.add(new Filter("Greeting Cards"));
            subcat.add(new Filter("Unusual Cards"));
            subcat.add(new Filter("Arts and Crafts"));
            subcat.add(new Filter("Handmade Jewellery"));
            subcat.add(new Filter("Gifts for Geeks"));
            subcat.add(new Filter("Postcards From"));
            subcat.add(new Filter("Recycled Crafts"));
            subcat.add(new Filter("Others"));
        }
        rent.add(new Filter("Days"));
        rent.add(new Filter("Weeks"));
        rent.add(new Filter("Months"));
    }


    private ListView listView;
    String[] filters={"Sub-Categories","Starting Range"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        cat=getIntent().getStringExtra("CAT");
        Log.v("CAT",cat);
        fillAds(cat);
        adapter=new FilterAdapter(FilterServiceActivity.this,subcat,false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView=(RecyclerView)findViewById(R.id.list2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ArrayAdapter filterAdapter=new ArrayAdapter<String>(this,R.layout.filter_list_view,R.id.tv_filter,filters);
        listView= (ListView) findViewById(R.id.list1);
        listView.setAdapter(filterAdapter);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Filter By");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter=new FilterAdapter(FilterServiceActivity.this,subcat,false);
                    recyclerView.setAdapter(adapter);
                    LinearLayout ll= (LinearLayout) findViewById(R.id.ll_slider);
                    ll.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
                else if(position==1)
                {
                    final TextView min1 = (TextView) findViewById(R.id.minValue1);
                    final TextView max1 = (TextView) findViewById(R.id.maxValue1);
                    MultiSlider multiSlider1 = (MultiSlider) findViewById(R.id.range_slider1);
                    LinearLayout ll= (LinearLayout) findViewById(R.id.ll_slider);
                    ll.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    min1.setText(String.valueOf(multiSlider1.getThumb(0).getValue()));
                    max1.setText(String.valueOf(multiSlider1.getThumb(1).getValue()));
                    multiSlider1.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
                        @Override
                        public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                            if (thumbIndex == 0) {
                                min1.setText(String.valueOf(value));
                            } else {
                                max1.setText(String.valueOf(value));
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void apply()
    {
        String subcats="'";
        for(Filter x:subcat)
        {
            if(x.getSelected())
                subcats+=x.getName()+"','";
        }
        String finalsubcatquery;
        if(subcats.equals("'"))
            finalsubcatquery="";
        else
            finalsubcatquery=" and subcat IN ("+subcats+"')";

        String finalfilter=finalsubcatquery;
        Intent intent=new Intent();
        // intent.putStringArrayListExtra("im")
        intent.putExtra("data",finalfilter);
        setResult(Activity.RESULT_OK,intent);
        finish();

        Log.v("final subcats",finalfilter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_apply:
                apply();
                return true;
            case R.id.nav_clear:
            {
                subcat.clear();
                rent.clear();
                fillAds(cat);
                adapter.notifyDataSetChanged();
            }
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

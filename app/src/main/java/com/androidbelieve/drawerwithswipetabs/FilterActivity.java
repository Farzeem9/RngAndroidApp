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

import java.util.ArrayList;

import io.apptik.widget.MultiSlider;

public class FilterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<Filter> subcat=new ArrayList<>(),rent=new ArrayList<>();
    private RecyclerView recyclerView;
    private FilterAdapter adapter;
    private String cat;
    private void fillAds(String cat)
    {
        subcat.clear();
        if(cat.equals("Electronics & Appliances"))
        {
            subcat.add(new Filter("Mobile Phone"));
            subcat.add(new Filter("Tablet"));
            subcat.add(new Filter("Accessories"));
            subcat.add(new Filter("Computer & Laptop"));
            subcat.add(new Filter("TV,Video-Audio"));
            subcat.add(new Filter("Printer"));
            subcat.add(new Filter("Computer Accessories"));
            subcat.add(new Filter("Camera & Lenses"));
            subcat.add(new Filter("Kitchen Appliance"));
            subcat.add(new Filter("Speakers"));
            subcat.add(new Filter("Projectors"));
            subcat.add(new Filter("Others"));
        }
        if(cat.equals("Furniture")) {
            subcat.add(new Filter("Sofa"));
            subcat.add(new Filter("Dining"));
            subcat.add(new Filter("Bed"));
            subcat.add(new Filter("Wardrobe"));
            subcat.add(new Filter("Home Décor & Garden"));
            subcat.add(new Filter("Others"));
        }
        if(cat.equals("Books, Sports & Hobbies")) {
            subcat.add(new Filter("Book"));
            subcat.add(new Filter("Musical Instrument"));
            subcat.add(new Filter("Sports Equipment"));
            subcat.add(new Filter("Travel & Camping"));
            subcat.add(new Filter("Gaming"));
            subcat.add(new Filter("Party Equipment"));
            subcat.add(new Filter("Others"));
        }
        if(cat.equals("Fashion")) {
            subcat.add(new Filter("Men"));
            subcat.add(new Filter("Women"));
            subcat.add(new Filter("Kid"));
        }
        if(cat.equals("Real Estate")) {
            subcat.add(new Filter("Residential"));
            subcat.add(new Filter("Commercial"));
            subcat.add(new Filter("Others"));
        }
        if(cat.equals("Tools & Equipments")){
            subcat.add(new Filter("Power Tool"));
            subcat.add(new Filter("Spanner"));
            subcat.add(new Filter("Others"));
        }
        if(cat.equals("Cars")) {
            subcat.add(new Filter("Cars"));
            subcat.add(new Filter("Commercial vehicle"));
            subcat.add(new Filter("Others"));
        }
        if(cat.equals("Bike")){
            subcat.add(new Filter("Bike"));
            subcat.add(new Filter("Scooter"));
            subcat.add(new Filter("Bicycle"));
            subcat.add(new Filter("Others"));
        }

        rent.add(new Filter("Days"));
        rent.add(new Filter("Weeks"));
        rent.add(new Filter("Months"));
    }


    private ListView listView;
    String[] filters={"Sub-Categories","Rent Type","Price","Deposit"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        cat=getIntent().getStringExtra("CAT");
        Log.v("CAT",cat);
        fillAds(cat);
        adapter=new FilterAdapter(FilterActivity.this,subcat,false);
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
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_name));
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
                fillAds(cat);
                adapter=new FilterAdapter(FilterActivity.this,subcat,false);
                recyclerView.setAdapter(adapter);
                LinearLayout ll= (LinearLayout) findViewById(R.id.ll_slider);
                ll.setVisibility(View.GONE);
                LinearLayout ll2= (LinearLayout) findViewById(R.id.ll_slider2);
                ll2.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
            else if(position==1)
            {
                recyclerView.setVisibility(View.VISIBLE);
                adapter=new FilterAdapter(FilterActivity.this,rent,true);
                recyclerView.setAdapter(adapter);
                LinearLayout ll= (LinearLayout) findViewById(R.id.ll_slider);
                ll.setVisibility(View.GONE);
                LinearLayout ll2= (LinearLayout) findViewById(R.id.ll_slider2);
                ll2.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
            else if(position==2)
            {
                final TextView min1 = (TextView) findViewById(R.id.minValue1);
                final TextView max1 = (TextView) findViewById(R.id.maxValue1);
                MultiSlider multiSlider1 = (MultiSlider) findViewById(R.id.range_slider1);
                multiSlider1.setMin(0);
                multiSlider1.setMax(500000);
                LinearLayout ll= (LinearLayout) findViewById(R.id.ll_slider);
                ll.setVisibility(View.VISIBLE);
                LinearLayout ll2= (LinearLayout) findViewById(R.id.ll_slider2);
                ll2.setVisibility(View.GONE);
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
                else if(position==3)
            {
                final TextView min2 = (TextView) findViewById(R.id.minValue2);
                final TextView max2 = (TextView) findViewById(R.id.maxValue2);
                MultiSlider multiSlider2 = (MultiSlider) findViewById(R.id.range_slider2);
                multiSlider2.setVisibility(View.VISIBLE);
                multiSlider2.setMin(0);
                multiSlider2.setMax(500000);
                LinearLayout ll= (LinearLayout) findViewById(R.id.ll_slider);
                ll.setVisibility(View.GONE);
                LinearLayout ll2= (LinearLayout) findViewById(R.id.ll_slider2);
                ll2.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                min2.setText(String.valueOf(multiSlider2.getThumb(0).getValue()));
                max2.setText(String.valueOf(multiSlider2.getThumb(1).getValue()));
                multiSlider2.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
                    @Override
                    public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                        if (thumbIndex == 0) {
                            min2.setText(String.valueOf(value));
                        } else {
                            max2.setText(String.valueOf(value));
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

        String rent="";
        for(Filter x:this.rent)
        {
            if(x.getSelected())
            {
                rent=x.getName();
            }
        }
        if(!rent.equals(""))
            rent="and crent LIKE '%"+rent+"%'";

        String rentrange="";

            rentrange=" and rent > "+((TextView)findViewById(R.id.minValue1)).getText().toString()+" and rent < "+((TextView)findViewById(R.id.maxValue1)).getText().toString();
        String deposit="";
            deposit=" and PROD_DEPOSIT > "+((TextView)findViewById(R.id.minValue2)).getText().toString()+" and rent < "+((TextView)findViewById(R.id.maxValue2)).getText().toString();;


        String finalfilter=finalsubcatquery+rent+rentrange+deposit;
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

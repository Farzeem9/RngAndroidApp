package com.androidbelieve.drawerwithswipetabs;

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
import android.widget.ListView;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<Filter> subcat=new ArrayList<>(),rent=new ArrayList<>(),rent_price=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<FilterAdapter.MyViewHolder> adapter;
    private void fillAds()
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
        subcat.add(new Filter("Cars"));
        subcat.add(new Filter("Commercial vehicle"));
        subcat.add(new Filter("Bike"));
        subcat.add(new Filter("Scooter"));
        subcat.add(new Filter("Bicycle"));
        subcat.add(new Filter("Sofa"));
        subcat.add(new Filter("Dining"));
        subcat.add(new Filter("Bed"));
        subcat.add(new Filter("Wardrobe"));
        subcat.add(new Filter("Home Décor & Garden"));
        subcat.add(new Filter("Book"));
        subcat.add(new Filter("Musical Instrument"));
        subcat.add(new Filter("Sports Equipment"));
        subcat.add(new Filter("Travel & Camping"));
        subcat.add(new Filter("Gaming"));
        subcat.add(new Filter("Party Equipment"));
        subcat.add(new Filter("Men"));
        subcat.add(new Filter("Women"));
        subcat.add(new Filter("Kid"));
        subcat.add(new Filter("Residential"));
        subcat.add(new Filter("Commercial"));
        subcat.add(new Filter("Power Tool"));
        subcat.add(new Filter("Spanner"));
        subcat.add(new Filter("Others"));

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
        fillAds();
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
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace_black_24dp));
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
                adapter=new FilterAdapter(FilterActivity.this,subcat,false);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            else if(position==1)
            {
                recyclerView.setVisibility(View.VISIBLE);
                adapter=new FilterAdapter(FilterActivity.this,rent,true);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            else if(position==2)
            {
                recyclerView.setVisibility(View.GONE);
            }
                else if(position==3)
            {
                recyclerView.setVisibility(View.GONE);
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
        String finalsubcatquery="("+subcats+"')";
        Log.v("final subcats",finalsubcatquery);
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
                fillAds();
                adapter.notifyDataSetChanged();
            }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

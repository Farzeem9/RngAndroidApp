package com.androidbelieve.drawerwithswipetabs;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.facebook.FacebookSdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class Service_Category extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ServiceCategoryAdapter adapter;
    private ArrayList<ServiceAlbum> albumList;
    private Toolbar toolbar;
    private Boolean b=true;
    private Button filter;
    private GenericAsyncTask genericAsyncTask;
    private String sort="",filters="",cat="";
    private InfiniteScrollviewService infScrollviewListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__category);
        cat=getIntent().getStringExtra("Category");
        filter= (Button) findViewById(R.id.btn_filter);
        //TextView textView=(TextView)findViewById(R.id.cat_name);
        //textView.setText(cat);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        toolbar= (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(cat);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_name));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        albumList = new ArrayList<>();
        adapter = new ServiceCategoryAdapter(this, albumList);
        infScrollviewListener=new InfiniteScrollviewService(adapter,albumList,cat);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new Service_Category.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getBaseContext(),FilterServiceActivity.class);
                in.putExtra("CAT",cat);
                startActivityForResult(in,0);
            }
        });
        findViewById(R.id.btn_sort).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b) {
                    FrameLayout frameLayout = (FrameLayout) findViewById(R.id.containerView);
                    frameLayout.setVisibility(View.VISIBLE);
                /*FragmentManager mFragmentManager = getChildFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                SortFragment sf=new SortFragment();
                mFragmentTransaction.replace(R.id.containerView,sf);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                */
                    b=!b;
                    //findViewById(R.id.rel_lay).setAlpha(0.5f);
                    frameLayout.setAlpha(1f);
                }
                else
                {
                    findViewById(R.id.rel_lay).setAlpha(1f);
                    FrameLayout frameLayout = (FrameLayout) findViewById(R.id.containerView);
                    frameLayout.setVisibility(View.GONE);

                    b=!b;
                }
            }
        });
        findViewById(R.id.pricehtl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort("order+by+STARTRANGE+desc");
            }
        });
        findViewById(R.id.pricelth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort("+order+by+STARTRANGE");
            }
        });

        findViewById(R.id.p_age).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort("+order+by+SID");
            }
        });

        findViewById(R.id.rating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort("&rating=1");
            }
        });

        genericAsyncTask=new GenericAsyncTask(this, Config.link+"showservice.php?category=" + URLEncoder.encode(cat)+"&order="+sort+"&filter="+URLEncoder.encode(filters), "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                try {
                    prepareAlbum(new JSONObject((String)output).getJSONArray("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (NullPointerException e)
                {
                    Log.v("nullpointer","maybe asynctask cancelled?");
                }
            }
        });
        genericAsyncTask.execute();
        recyclerView.setOnScrollListener(infScrollviewListener);
    }

    public void sort(String query)
    {
        this.sort=query;
        try
        {
           genericAsyncTask.cancel(true);
        }
        catch (Exception e)
        {

        }
        infScrollviewListener.stopAsyncTask();
        albumList.clear();
        infScrollviewListener=new InfiniteScrollviewService(adapter,albumList,cat);
        infScrollviewListener.updateSortandFilter(sort,filters);
        recyclerView.setOnScrollListener(infScrollviewListener);
        Log.v("link",Config.link+"showservice.php?category="+ URLEncoder.encode(cat)+"&order="+sort+"&filter="+URLEncoder.encode(filters));
        genericAsyncTask=new GenericAsyncTask(this, Config.link+"showservice.php?category=" + URLEncoder.encode(cat)+"&order="+sort+"&filter="+URLEncoder.encode(filters), "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                try {
                    prepareAlbum(new JSONObject((String)output).getJSONArray("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (NullPointerException e)
                {
                    Log.v("nullpointer","maybe asynctask cancelled?");
                }
            }
        });
        genericAsyncTask.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Log.v("search clicked","okay");
                startActivity(new Intent(this,SearchViewActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(genericAsyncTask!=null)
            if(genericAsyncTask.getStatus()!= AsyncTask.Status.FINISHED)
                genericAsyncTask.cancel(true);
    }



    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    void prepareAlbum(JSONArray jarray)
    {
        for(int i=0;i<jarray.length();i++)
        {
            try {
                JSONObject ad = jarray.getJSONObject(i);
                String name = ad.getString("SNAME");
                String sid = ad.getString("SID");
                int amount = Integer.parseInt(ad.getString("AMOUNT"));
                String timestamp=ad.getString("TIMESTAMP");
                String subcat=ad.getString("SUBCATEGORY");
                String city=ad.getString("CITY");
                String pid=ad.getString("PID");
                albumList.add(new ServiceAlbum(pid,name,amount,R.drawable.broly,sid,timestamp,subcat,city));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        FacebookSdk.sdkInitialize(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FacebookSdk.sdkInitialize(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null||resultCode!=RESULT_OK)
        {
            return;
        }
        if(!data.getStringExtra("data").equals("")&& requestCode==0)
        {
            filters=data.getStringExtra("data");
            sort(sort);

        }

    }


}

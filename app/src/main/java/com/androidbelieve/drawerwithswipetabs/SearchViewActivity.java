package com.androidbelieve.drawerwithswipetabs;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchViewActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener {

    private Toolbar toolbar;
    private GenericAsyncTask searchtask;
    private AlbumsAdapter adapter;
    private ArrayList<Album> albumList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewservice;
    private ServiceCategoryAdapter adapterservice;
    private ArrayList<ServiceAlbum> albumListservice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        albumList=new ArrayList<>();
        adapter=new AlbumsAdapter(this,albumList);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        albumListservice = new ArrayList<>();
        adapterservice = new ServiceCategoryAdapter(this, albumListservice);

        recyclerViewservice=(RecyclerView)findViewById(R.id.recycler_view_services);
        RecyclerView.LayoutManager mLayoutManagerservice = new GridLayoutManager(this, 1);
        recyclerViewservice.setLayoutManager(mLayoutManagerservice);
        recyclerViewservice.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerViewservice.setItemAnimator(new DefaultItemAnimator());
        recyclerViewservice.setAdapter(adapterservice);


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //searchView.setSearchableInfo(searchManager.getSearchableInfo( new ComponentName(this, SearchableActivity.class)));
        searchView.setIconifiedByDefault(false);
        return true;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, "Searching by: "+ query, Toast.LENGTH_SHORT).show();
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String uri = intent.getDataString();
            Toast.makeText(this, "Suggestion: "+ uri, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        if(searchtask==null)
        {
            searchtask=new GenericAsyncTask(this, Config.link + "search.php?search=" + URLEncoder.encode(query), "", new AsyncResponse() {
                @Override
                public void processFinish(Object output) {
                    String result=(String)output;

                   try {
                       JSONObject jobj = new JSONObject(result);
                       prepareAlbum(jobj.getJSONArray("result"));
                       prepareAlbumService(jobj.getJSONArray("resultservice"));

                   }
                   catch (Exception e)
                   {
                       e.printStackTrace();
                   }
                }
            });
            searchtask.execute();
        }
        else if(searchtask.getStatus()!= AsyncTask.Status.FINISHED)
        {
            searchtask.cancel(true);
            searchtask=new GenericAsyncTask(this, Config.link + "search.php?search=" + URLEncoder.encode(query), "", new AsyncResponse() {
                @Override
                public void processFinish(Object output) {
                    String result=(String)output;

                    try {
                        JSONObject jobj = new JSONObject(result);
                        prepareAlbum(jobj.getJSONArray("result"));
                        prepareAlbumService(jobj.getJSONArray("resultservice"));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            searchtask.execute();
        }
        else
        {
            searchtask=new GenericAsyncTask(this, Config.link + "search.php?search=" + URLEncoder.encode(query), "", new AsyncResponse() {
                @Override
                public void processFinish(Object output) {
                    String result=(String)output;

                    try {
                        JSONObject jobj = new JSONObject(result);
                        prepareAlbum(jobj.getJSONArray("result"));
                        prepareAlbumService(jobj.getJSONArray("resultservice"));

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            searchtask.execute();
        }
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        albumListservice.clear();
        albumList.clear();
        adapter.notifyDataSetChanged();
        adapterservice.notifyDataSetChanged();

        return false;
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

    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    void prepareAlbumService(JSONArray jarray)
    {
        albumListservice.clear();
        if(jarray.length()==0)
        {
            findViewById(R.id.No_service).setVisibility(View.VISIBLE);
        }
        else
            findViewById(R.id.No_service).setVisibility(View.GONE);

        for(int i=0;i<jarray.length();i++)
        {
            try {
                JSONObject ad = jarray.getJSONObject(i);
                String name = ad.getString("SNAME");
                String sid = ad.getString("SID");
                int amount = Integer.parseInt(ad.getString("AMOUNT"));
                String timestamp=ad.getString("TIMESTAMP");
                String subcat=ad.getString("SUBCATEGORY");
                String pid=ad.getString("PID");
                albumListservice.add(new ServiceAlbum(pid,name,amount,R.drawable.broly,sid,timestamp,subcat));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        adapterservice.notifyDataSetChanged();
    }
    void prepareAlbum(JSONArray jarray)
    {
        albumList.clear();
        if(jarray.length()==0)
        {
            findViewById(R.id.No_ads).setVisibility(View.VISIBLE);
        }
        else
            findViewById(R.id.No_ads).setVisibility(View.GONE);
        for(int i=0;i<jarray.length();i++)
        {
            try {
                JSONObject ad = jarray.getJSONObject(i);
                String pid=ad.getString("PID");
                String name = ad.getString("PROD_NAME");
                String aid = ad.getString("AID");
                String subcat=ad.getString("CATEGORY");
                int amount = Integer.parseInt(ad.getString("AMOUNT"));

                Album a=new Album(subcat,pid,name,amount,i,aid);
                albumList.add(a);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }



}
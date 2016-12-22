package com.androidbelieve.drawerwithswipetabs;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchViewActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener {

    private Toolbar toolbar;
    private ProgressDialog progress;
    private GenericAsyncTask searchtask;
    private AlbumsAdapter adapter;
    private ArrayList<Album> albumList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewservice;
    private ServiceCategoryAdapter adapterservice;
    private ArrayList<ServiceAlbum> albumListservice;
    private SearchAdsFragment adsFragment;
    private SearchServicesFragment servicesFragment;
    TabLayout tabLayout;
    ViewPager viewPager;
    public static int int_items = 2 ;
    private String searchquery;
    private String sort="",filter="";

    public void sort(String query)
    {
        this.sort=query;
        if(searchquery!=null)
        {
            performSearch();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    void performSearch()
    {
        if(searchquery!=null)
        {
            if(searchtask==null)
            { progress.show();
                searchtask=new GenericAsyncTask(this, Config.link + "search.php?search=" + URLEncoder.encode(searchquery)+"&order="+sort+"&filter="+URLEncoder.encode(filter)+"&orderservice="+URLEncoder.encode(sort)+"&filter="+URLEncoder.encode(filter), "", new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        String result=(String)output;

                        try {
                            JSONObject jobj = new JSONObject(result);
                            adsFragment.prepareAlbum(jobj.getJSONArray("result"));
                            servicesFragment.prepareAlbumService(jobj.getJSONArray("resultservice"));
                            progress.dismiss();
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
                progress.show();
                searchtask.cancel(true);
                searchtask=new GenericAsyncTask(this, Config.link + "search.php?search=" + URLEncoder.encode(searchquery)+"&order="+sort+"&filter="+URLEncoder.encode(filter), "", new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        String result=(String)output;

                        try {
                            JSONObject jobj = new JSONObject(result);
                            adsFragment.prepareAlbum(jobj.getJSONArray("result"));
                            servicesFragment.prepareAlbumService(jobj.getJSONArray("resultservice"));
                            progress.dismiss();
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
                progress.show();
                searchtask=new GenericAsyncTask(this, Config.link + "search.php?search=" + URLEncoder.encode(searchquery)+"&order="+sort+"&filter="+URLEncoder.encode(filter), "", new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        String result=(String)output;

                        try {
                            JSONObject jobj = new JSONObject(result);
                            adsFragment.prepareAlbum(jobj.getJSONArray("result"));
                            servicesFragment.prepareAlbumService(jobj.getJSONArray("resultservice"));
                            progress.dismiss();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                searchtask.execute();
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null)
            return;
        if(!data.getStringExtra("data").equals(""))
        {
            filter=data.getStringExtra("data");
            performSearch();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search_view);
        searchItem.collapseActionView();
        progress=new ProgressDialog(this);
        progress.setMessage("Communicating with server..");
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setCancelable(false);


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
        searchquery=query;
            if(searchtask==null)
            { progress.show();
                searchtask=new GenericAsyncTask(this, Config.link + "search.php?search=" + URLEncoder.encode(query)+"&order="+sort+"&filter="+URLEncoder.encode(filter)+"&orderservice="+URLEncoder.encode(sort)+"&filter="+URLEncoder.encode(filter), "", new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        String result=(String)output;

                        try {
                            JSONObject jobj = new JSONObject(result);
                            adsFragment.prepareAlbum(jobj.getJSONArray("result"));
                            servicesFragment.prepareAlbumService(jobj.getJSONArray("resultservice"));
                            progress.dismiss();
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
                progress.show();
                searchtask.cancel(true);
                searchtask=new GenericAsyncTask(this, Config.link + "search.php?search=" + URLEncoder.encode(query)+"&order="+sort+"&filter="+URLEncoder.encode(filter), "", new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        String result=(String)output;

                        try {
                            JSONObject jobj = new JSONObject(result);
                            adsFragment.prepareAlbum(jobj.getJSONArray("result"));
                            servicesFragment.prepareAlbumService(jobj.getJSONArray("resultservice"));
                            progress.dismiss();
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
                progress.show();
                searchtask=new GenericAsyncTask(this, Config.link + "search.php?search=" + URLEncoder.encode(query)+"&order="+sort+"&filter="+URLEncoder.encode(filter), "", new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        String result=(String)output;

                        try {
                            JSONObject jobj = new JSONObject(result);
                            adsFragment.prepareAlbum(jobj.getJSONArray("result"));
                            servicesFragment.prepareAlbumService(jobj.getJSONArray("resultservice"));
                            progress.dismiss();
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
        /*albumListservice.clear();
        albumList.clear();
        adapter.notifyDataSetChanged();
        adapterservice.notifyDataSetChanged();
*/
        return false;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : adsFragment= new SearchAdsFragment();
                        return adsFragment;
                case 1 : servicesFragment= new SearchServicesFragment();
                        return servicesFragment;
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Ads";
                case 1 :
                    return "Services";
            }
            return null;
        }
    }

}
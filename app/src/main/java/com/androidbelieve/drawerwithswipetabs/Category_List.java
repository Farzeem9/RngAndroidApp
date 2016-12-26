package com.androidbelieve.drawerwithswipetabs;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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

import java.net.URLEncoder;
import java.util.ArrayList;
public class Category_List extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private ArrayList<Album> albumList;
    private Toolbar toolbar;
    private CollapsingToolbarLayout toolbarLayout;
    private GetJSON getJSON;
    private Boolean b=true;
    private Button filter;
    private InfScrollviewListener infScrollviewListener;
    private String sort="",filters="",cat="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__list);
        cat=getIntent().getStringExtra("Category");
        //TextView textView=(TextView)findViewById(R.id.cat_name);
        //textView.setText(cat);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        toolbar= (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(cat);
        filter= (Button) findViewById(R.id.btn_filter);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_name));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getBaseContext(),FilterActivity.class);
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
                    b=!b;
                    findViewById(R.id.rel_lay).setAlpha(0.5f);
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
                sort("order+by+rent+desc");
            }
        });
        findViewById(R.id.pricelth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort("+order+by+rent");
            }
        });
        findViewById(R.id.p_age).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort("+order+by+PROD_AGE");
            }
        });
        findViewById(R.id.rating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort("&rating=1");
            }
        });

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        infScrollviewListener=new InfScrollviewListener(adapter,albumList,cat);
        recyclerView.setOnScrollListener(infScrollviewListener);


        getJSON=new GetJSON(Config.link+"viewads.php?category="+ URLEncoder.encode(cat)+"&order="+sort+"&filter="+URLEncoder.encode(filters),adapter,albumList);
        getJSON.execute();


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

    public void sort(String query)
    {
        this.sort=query;
        try
        {
            getJSON.cancel(true);
        }
        catch (Exception e)
        {

        }
        infScrollviewListener.stopAsyncTask();
        albumList.clear();
        infScrollviewListener=new InfScrollviewListener(adapter,albumList,cat);
        infScrollviewListener.updateSortandFilter(sort,filters);
        recyclerView.setOnScrollListener(infScrollviewListener);
        Log.v("link",Config.link+"viewads.php?category="+ URLEncoder.encode(cat)+"&order="+sort+"&filter="+URLEncoder.encode(filters));
        getJSON=new GetJSON(Config.link+"viewads.php?category="+ URLEncoder.encode(cat)+"&order="+sort+"&filter="+URLEncoder.encode(filters),adapter,albumList);
        getJSON.execute();

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

    @Override
    protected void onStop() {
        if(!(getJSON.getStatus()== AsyncTask.Status.FINISHED))
            getJSON.cancel(true);
        infScrollviewListener.stopAsyncTask();
        super.onStop();
    }
}
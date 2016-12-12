package com.androidbelieve.drawerwithswipetabs;

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
import android.view.View;
import android.widget.TextView;

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
    private String cat;
    private Toolbar toolbar;
    private GenericAsyncTask genericAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__category);
        cat=getIntent().getStringExtra("Category");
        //TextView textView=(TextView)findViewById(R.id.cat_name);
        //textView.setText(cat);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        toolbar= (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(cat);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_backspace_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        albumList = new ArrayList<>();
        adapter = new ServiceCategoryAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new Service_Category.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        genericAsyncTask=new GenericAsyncTask(this, Config.link+"showservice.php?category=" + URLEncoder.encode(cat), "", new AsyncResponse() {
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
        recyclerView.setOnScrollListener(new InfiniteScrollviewService(adapter,albumList,cat));
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
                String pid=ad.getString("PID");
                albumList.add(new ServiceAlbum(pid,name,amount,R.drawable.broly,sid,timestamp,subcat));
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
}

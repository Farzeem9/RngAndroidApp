package com.androidbelieve.drawerwithswipetabs;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceWishlistFragment extends Fragment {
    private ViewGroup rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private ServiceCategoryAdapter adapter;
    private ArrayList<ServiceAlbum> albumList;
    private GenericAsyncTask genericAsyncTask;
    public ServiceWishlistFragment()
    {
        //Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.services_wishlist_tab,null);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view_wishlist);
        recyclerView.setHasFixedSize(true);
        albumList = new ArrayList<>();
        adapter = new ServiceCategoryAdapter(getContext(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ServiceWishlistFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        genericAsyncTask=new GenericAsyncTask(getContext(), "http://rng.000webhostapp.com/sendwishlistservice.php"+"?pid="+AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {       //insert link later
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

        return rootView;
    }
    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

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
                String subcat=ad.getString("SUBCAT");

                albumList.add(new ServiceAlbum(name,amount,R.drawable.broly,sid,timestamp,subcat));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }
}
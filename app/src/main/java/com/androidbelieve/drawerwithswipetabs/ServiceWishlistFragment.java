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
import java.util.List;

public class ServiceWishlistFragment extends Fragment {
    private ViewGroup rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    //private ServiceCategoryAdapter adapter;
    //private ArrayList<ServiceAlbum> albumList;
    private GenericAsyncTask genericAsyncTask;
    private List<Ads> list_ad;
    private WishlistAdapter adapter;
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
        list_ad = new ArrayList<>();
        adapter = new WishlistAdapter(getContext(), list_ad,true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ServiceWishlistFragment.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        Log.v("Done","");
        genericAsyncTask=new GenericAsyncTask(getContext(), Config.link+"sendwishlistservice.php"+"?pid="+AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                try {
                    Log.v("PREPARING ALBUM","okay");
                    prepareAlbum(new JSONObject((String)output).getJSONArray("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("here","");
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
                    String amount =ad.getString("STARTRENT");
                    String timestamp=ad.getString("TIMESTAMP");
                    String subcat=ad.getString("CATEGORY");
                    list_ad.add(new Ads(subcat,name,amount,timestamp,sid,""));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

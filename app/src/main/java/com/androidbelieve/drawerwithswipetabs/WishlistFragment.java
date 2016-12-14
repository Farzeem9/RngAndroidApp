package com.androidbelieve.drawerwithswipetabs;
/*
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WishlistFragment extends Fragment {

    private ViewGroup rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private List<Ads> list_ad;
    private WishlistAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.wishlist_fragment,null);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view_wishlist);
        recyclerView.setHasFixedSize(true);
        list_ad = new ArrayList<>();

        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        adapter=new WishlistAdapter(getContext(),list_ad);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(llm);
        GenericAsyncTask g=new GenericAsyncTask(getContext(), "http://rng.000webhostapp.com/sendwishlist.php?pid=" + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                try {
                    setupListItems(new JSONObject((String)output).getJSONArray("result"),(String)output);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        g.execute();
        return rootView;
    }
    public void setupListItems(JSONArray jarray,String result){
        list_ad.clear();
        if (!result.equals("{\"result\":[]}")) {
            for (int i = 0; i < jarray.length(); i++) {
                try {
                    JSONObject ad = jarray.getJSONObject(i);
                    String name = ad.getString("PROD_NAME");
                    String aid = ad.getString("AID");
                    String timestamp=ad.getString("DURATION");
                    String amount = ad.getString("RENT");
                    Ads a = new Ads("Active",name, amount, timestamp,aid);
                    list_ad.add(a);

                } catch (Exception e) {
                       e.printStackTrace();
                }
            }

            adapter.notifyDataSetChanged();
        }
    }

    public WishlistFragment() {
        // Required empty public constructor
    }



}
*/
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

public class WishlistFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.wishlist_fragment,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return x;

    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new AdWishlistFragment();
                case 1 : return new ServiceWishlistFragment();
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
                    return "Ad";
                case 1 :
                    return "Services";
            }
            return null;
        }
    }
}

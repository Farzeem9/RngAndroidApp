package com.androidbelieve.drawerwithswipetabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class WishlistFragment extends Fragment {

    private ViewGroup rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private List<Ads> list_ad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.wishlist_fragment,null);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view_wishlist);
        recyclerView.setHasFixedSize(true);
        list_ad = new ArrayList<>();

        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        setupListItems();

        if (list_ad.size() > 0 & recyclerView != null) {
            recyclerView.setAdapter(new WishlistAdapter(getContext(),list_ad));
        }
        recyclerView.setLayoutManager(llm);
        return rootView;
    }
    public void setupListItems(){
        list_ad.clear();
        Ads ad1= new Ads();
        ad1.setDate("1 Oct");
        ad1.setPrice("5000");
        ad1.setSpecs("Hello");
        ad1.setStatus("Hello");
        ad1.setImage_ads(R.drawable.broly);
        list_ad.add(ad1);

        Ads ad2= new Ads();
        ad2.setDate("1 Oct");
        ad2.setPrice("5000");
        ad2.setStatus("22222");
        ad2.setSpecs("2222");
        ad2.setImage_ads(R.drawable.broly);
        list_ad.add(ad2);

        Ads ad3= new Ads();
        ad3.setDate("1 Oct");
        ad3.setPrice("5000");
        ad3.setStatus("333");
        ad3.setSpecs("333");
        ad3.setImage_ads(R.drawable.broly);
        list_ad.add(ad3);

        Ads ad4= new Ads();
        ad4.setDate("1 Oct");
        ad4.setStatus("444");
        ad4.setPrice("5000");
        ad4.setSpecs("4444");
        ad4.setImage_ads(R.drawable.broly);
        list_ad.add(ad4);

        Ads ad5= new Ads();
        ad5.setDate("1 Oct");
        ad5.setStatus("444");
        ad5.setPrice("5000");
        ad5.setSpecs("4444");
        ad5.setImage_ads(R.drawable.broly);
        list_ad.add(ad5);
    }

    public WishlistFragment() {
        // Required empty public constructor
    }



}
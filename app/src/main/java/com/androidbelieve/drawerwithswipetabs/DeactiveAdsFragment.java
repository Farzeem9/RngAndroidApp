package com.androidbelieve.drawerwithswipetabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;

import java.util.ArrayList;

public class DeactiveAdsFragment extends Fragment {
    private ViewGroup rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private ArrayList<MyAds> list_ad;
    MyDeactiveAdsAdapter adsAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.pendingads_layout,null);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view_pending_ads);
        recyclerView.setHasFixedSize(true);
        list_ad = new ArrayList<>();
        adsAdapter=new MyDeactiveAdsAdapter(getContext(),list_ad);
        new FAd("http://rng.000webhostapp.com/activeads.php?pid="+ AccessToken.getCurrentAccessToken().getUserId()+"&status=DEACTIVE",adsAdapter,list_ad).execute();
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(adsAdapter);
        //setupListItems();

        //if (list_ad.size() > 0 & recyclerView != null) {
        //  recyclerView.setAdapter(new MyActiveAdsAdapter(getContext(),list_ad));
        //}
        recyclerView.setOnScrollListener(new onListenerScroll(adsAdapter,list_ad));
        recyclerView.setLayoutManager(llm);
        return rootView;
    }
}
package com.androidbelieve.drawerwithswipetabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;

import java.util.ArrayList;

public class ServicePendingFragment extends Fragment {

    private ViewGroup rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private ArrayList<MyAds> list_service;
    MyPendingServiceAdapter serviceAdapter;
    public ServicePendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // FacebookSdk.sdkInitialize(getContext());

        rootView = (ViewGroup) inflater.inflate(R.layout.pendingads_layout,null);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view_pending_ads);
        recyclerView.setHasFixedSize(true);
        list_service = new ArrayList<>();
        serviceAdapter=new MyPendingServiceAdapter(getContext(),list_service);
        new ServiceAsyncTask(Config.link+"myservices.php?pid="+ AccessToken.getCurrentAccessToken().getUserId()+"&status=PENDING",serviceAdapter,list_service).execute();
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(serviceAdapter);
        //setupListItems();
        recyclerView.setOnScrollListener(new onListenerScroll(serviceAdapter,list_service));
        recyclerView.setLayoutManager(llm);
        return rootView;

    }

}

package com.androidbelieve.drawerwithswipetabs;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by karthik on 12/10/16.
 */

public class onListenerScroll extends RecyclerView.OnScrollListener {


    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int lastlast=0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private RecyclerView.Adapter adapter;
    private ArrayList<MyAds> albumList;
    public onListenerScroll(RecyclerView.Adapter adapter, ArrayList<MyAds> albumList)
    {

        this.adapter=adapter;
        this.albumList=albumList;

    }



    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        super.onScrolled(recyclerView, dx, dy);
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            totalItemCount = linearLayoutManager.getItemCount();
            lastVisibleItem = linearLayoutManager
                    .findLastVisibleItemPosition();
            if (!loading&& (totalItemCount <= (lastVisibleItem + visibleThreshold))&&(totalItemCount!=lastlast)) {
//                new MyAdsAsyncTask(Config.link+"viewads.php", lastVisibleItem+1, adapter, albumList).execute();
  //              loadingpic = true;
 //               lastlast=totalItemCount;
//                Log.d("Loading","Loading more values!");

            }
        }
    }
}

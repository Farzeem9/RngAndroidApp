package com.androidbelieve.drawerwithswipetabs;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import java.util.List;



public class InfScrollviewListener extends RecyclerView.OnScrollListener {


    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    public InfScrollviewListener(AlbumsAdapter adapter, List<Album> albumList)
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
            if (!loading&& totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                new GetJSON("http://rng.000webhostapp.com/viewads.php", lastVisibleItem+1, adapter, albumList).execute();
                loading = true;

            }
        }
    }
}

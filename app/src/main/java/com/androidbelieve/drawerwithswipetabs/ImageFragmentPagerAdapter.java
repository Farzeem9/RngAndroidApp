package com.androidbelieve.drawerwithswipetabs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by 20000136 on 11/18/2016.
 */

public class ImageFragmentPagerAdapter extends FragmentPagerAdapter {

    View.OnClickListener itemClickListener;
    private ArrayList<Bitmap> images;
    private final android.support.v4.app.FragmentManager fm;
    private FragmentTransaction mCurTransaction = null;
    public ImageFragmentPagerAdapter(FragmentManager fm, ArrayList<Bitmap> images, View.OnClickListener itemClickListener) {
        super(fm);
        this.images=images;
        this.itemClickListener=itemClickListener;
        this.fm=fm;
    }



    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        FragmentTransaction fragmentTransaction= fm.beginTransaction();
        fragmentTransaction.remove((Fragment)object);
        fragmentTransaction.commit();
    }

    @Override
    public Fragment getItem(int position) {
        Log.v("Getting item",images.get(position).toString());
        return SwipeFragment.newInstance(position,images.get(position), itemClickListener);
    }
}


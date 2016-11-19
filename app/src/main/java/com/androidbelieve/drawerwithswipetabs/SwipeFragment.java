package com.androidbelieve.drawerwithswipetabs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by 20000136 on 11/18/2016.
 */

public class SwipeFragment extends Fragment {
    Bitmap bitmap;
    View.OnClickListener itemClickListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View swipeView = inflater.inflate(R.layout.swipe_fragment, container, false);
        ImageView imageView = (ImageView) swipeView.findViewById(R.id.imageView);
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
        imageView.setImageBitmap(bitmap);
        imageView.setOnClickListener(itemClickListener);
        return swipeView;
    }

    static SwipeFragment newInstance(int position, Bitmap bitmap, AdapterView.OnClickListener itemClickListener) {

        SwipeFragment swipeFragment = new SwipeFragment();
        swipeFragment.bitmap=bitmap;
        swipeFragment.itemClickListener=itemClickListener;
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        swipeFragment.setArguments(bundle);
        return swipeFragment;
    }
}

package com.androidbelieve.drawerwithswipetabs;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ViewGroup rootView;
    String adap[];
    HomeAdapter ha;
    private ArrayList<String> cats;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.home_layout, container, false);
        //Button button=(Button)rootView.findViewById(R.id.bt_service);
        adap=new String[100];
        cats=new ArrayList<String>();
        recyclerView= (RecyclerView) rootView.findViewById(R.id.rr);
        ha= new HomeAdapter(getContext(),cats);
        /*int n;
        int[][] ids={{R.id.tv_mobiles,R.id.tv_cars,R.id.tv_books,R.id.tv_pots,R.id.tv_bikes},{R.id.btn_mobile,R.id.btn_cars,R.id.btn_books,R.id.btn_pots,R.id.btn_bikes}};
        n=ids[0].length;
        ImageButton[] imageButtons =new ImageButton[n];
        final TextView[] textViews=new TextView[n];

        for(int i=0;i<n;i++)
        {
            imageButtons[i]=(ImageButton)rootView.findViewById(ids[1][i]);
            textViews[i]=(TextView)rootView.findViewById(ids[0][i]);
            final int j=i;
            imageButtons[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                  Intent intent=new Intent(getActivity(),Category_List.class);
                    Log.v("text",textViews[j].getText().toString());
                    intent.putExtra("Category",textViews[j].getText());
                    startActivity(intent);
                }
            });
        }
*/
        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               
                startActivity(new Intent(getActivity(),NewAdActivity.class));
            }
        });
        GenericAsyncTask genericAsyncTask=new GenericAsyncTask(getContext(),"http://rng.000webhostapp.com/category.php?","message",new AsyncResponse()
        {
            @Override
            public void processFinish(Object output)
            {
                String out=(String)output;
                adap=out.split(";;");

                for(int i=0;i<adap.length;i++ )
                {
                    Toast.makeText(getContext(), adap[i], Toast.LENGTH_SHORT).show();
                    //Log.v("HELLo",adap[i]);
                    cats.add(adap[i]);
                }
                ha.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(ha);
        genericAsyncTask.execute();
        return rootView;
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem menuItem=menu.findItem(R.id.action_notification);
        super.onCreateOptionsMenu(menu,inflater);
    }*/
}


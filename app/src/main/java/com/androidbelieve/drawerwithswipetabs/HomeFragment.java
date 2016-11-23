package com.androidbelieve.drawerwithswipetabs;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ViewGroup rootView;
    String adap[],service[];
    HomeAdapter ha;
    HomeAdapter homeAdapter;
    private ArrayList<String> cats;
    private ArrayList<String> servicecats;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewservice;
    private GenericAsyncTask genericAsyncTask;
    private GenericAsyncTask genericAsyncTaskService;
    private FloatingActionButton fabservice;
    private FloatingActionButton fabad;
    private boolean shown=false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.home_layout, container, false);
        cats=new ArrayList<String>();
        servicecats=new ArrayList<String>();
        recyclerView= (RecyclerView) rootView.findViewById(R.id.rr);
        recyclerViewservice=(RecyclerView)rootView.findViewById(R.id.serv_rel_lay);

        ha= new HomeAdapter(getContext(),cats,Category_List.class);
        homeAdapter=new HomeAdapter(getContext(),servicecats,Service_Category.class);
        recyclerView.setAdapter(ha);
        recyclerViewservice.setAdapter(homeAdapter);
        fabservice=(FloatingActionButton)rootView.findViewById(R.id.fabnewservice);
        fabad=(FloatingActionButton)rootView.findViewById(R.id.fabnewad);
        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

        if(!shown) {
            Animation fabad_show = AnimationUtils.loadAnimation(getContext(), R.anim.fabad_show);
            fabad.startAnimation(fabad_show);
            fabad.setVisibility(View.VISIBLE);
            fabservice.startAnimation(fabad_show);
            fabservice.setVisibility(View.VISIBLE);
            shown=true;
            fabad.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), NewAdActivity.class);
                    i.putExtra("fragment", "newad");
                    startActivity(i);
                }
            });
            fabservice.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), NewAdActivity.class);
                    i.putExtra("fragment", "service");
                    startActivity(i);
                }
            });

        }
        else
        {
            Animation fabad_hide = AnimationUtils.loadAnimation(getContext(), R.anim.fabad_hide);
            fabad.startAnimation(fabad_hide);
            fabad.setVisibility(View.INVISIBLE);
            fabservice.startAnimation(fabad_hide);
            fabservice.setVisibility(View.INVISIBLE);
            shown=false;

        }
               // startActivity(new Intent(getActivity(),NewAdActivity.class));
            }
        });

        genericAsyncTask=new GenericAsyncTask(getContext(),"http://rng.000webhostapp.com/category.php","message",new AsyncResponse()
        {
            @Override
            public void processFinish(Object output)
            {
                String out=(String)output;
                if(!out.contains(";;"))
                    return;
                adap=out.split(";;");

                for(int i=0;i<adap.length;i++ )
                {
                    try {
                        cats.add(adap[i]);
                        ha.notifyDataSetChanged();
                    }
                        catch(NullPointerException e)
                        {
                            Log.v("Null pointer caught","Maybe activity was closed?");
                        }
                }
                Log.v("Cats",cats.toString());

            }
        });
        genericAsyncTaskService=new GenericAsyncTask(getContext(), "http://rng.000webhostapp.com/categoryservice.php", "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                String out=(String)output;
                if(!out.contains(";;"))
                    return;
                service=out.split(";;");
                for(int i=0;i<service.length;i++ )
                {
                    try {
                        //Log.v("HELLo",adap[i]);
                        servicecats.add(service[i]);
                        homeAdapter.notifyDataSetChanged();
                    }
                    catch(NullPointerException e)
                    {
                        Log.v("Null pointer caught","Maybe activity was closed?");
                    }
                }

            }
        });
        genericAsyncTask.execute();
        genericAsyncTaskService.execute();
        return rootView;
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if(genericAsyncTask!=null)
            if(genericAsyncTask.getStatus()!= AsyncTask.Status.FINISHED)
                genericAsyncTask.cancel(true);
    }

}


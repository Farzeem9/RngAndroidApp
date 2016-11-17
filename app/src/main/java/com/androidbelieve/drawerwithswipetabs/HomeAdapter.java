package com.androidbelieve.drawerwithswipetabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Karan Bheda on 14-Nov-16.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> categories;
    private Class c;
    public HomeAdapter(Context context, final ArrayList<String> categories,Class c) {
        this.context = context;
        this.categories=categories;
        this.c=c;
        Log.v("Adapter created","Created");
    }
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_home, parent, false);
        return new HomeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.MyViewHolder holder, int position) {
        holder.category.setText(categories.get(position));
        holder.imageButton.setImageResource(R.drawable.ic_arrow_back);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,c);
                Log.v("text",holder.category.getText().toString());
                intent.putExtra("Category",holder.category.getText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageButton imageButton;
        TextView category;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageButton=(ImageButton)itemView.findViewById(R.id.btn_cat);
            category=(TextView)itemView.findViewById(R.id.tv_cat);
        }
    }
}
package com.androidbelieve.drawerwithswipetabs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        String cat=categories.get(position);


        holder.category.setText(cat);

        if(holder.category.getText().toString().equals("Cars")){
            Drawable dr = context.getResources().getDrawable(R.drawable.car);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
        holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Bikes")){
            Drawable dr = context.getResources().getDrawable(R.drawable.bike);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Furniture")){
            Drawable dr = context.getResources().getDrawable(R.drawable.furniture);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Books, Sports & Hobbies")){
            Drawable dr = context.getResources().getDrawable(R.drawable.bookssportsandhobbies);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Fashion")){
            Drawable dr = context.getResources().getDrawable(R.drawable.fashion);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Electronics & Appliances")){
            Drawable dr = context.getResources().getDrawable(R.drawable.electronicsandappliances);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Tools & Equipments")){
            Drawable dr = context.getResources().getDrawable(R.drawable.myadsservice);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Video and Animation")){
            Drawable dr = context.getResources().getDrawable(R.drawable.videoandanimation);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Writing and Translation")){
            Drawable dr = context.getResources().getDrawable(R.drawable.writingandtranslate);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Programming and Tech")){
            Drawable dr = context.getResources().getDrawable(R.drawable.programingandtech);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Graphics and Design")){
            Drawable dr = context.getResources().getDrawable(R.drawable.graphicdesign);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }

        else if(holder.category.getText().toString().equals("Digital Marketing")){
            Drawable dr = context.getResources().getDrawable(R.drawable.digitalmarketing);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Real Estate")){
            Drawable dr = context.getResources().getDrawable(R.drawable.realestate);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }

        else if(holder.category.getText().toString().equals("Music and Audio")){
            Drawable dr = context.getResources().getDrawable(R.drawable.musicandaudio);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Gifts")){
            Drawable dr = context.getResources().getDrawable(R.drawable.gift);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Advertising")){
            Drawable dr = context.getResources().getDrawable(R.drawable.advertising);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Business")){
            Drawable dr = context.getResources().getDrawable(R.drawable.business);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
        else if(holder.category.getText().toString().equals("Lifestyle")){
            Drawable dr = context.getResources().getDrawable(R.drawable.lifestyle);
            Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
            Drawable d = new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap,75,75,true));
            holder.imageButton.setImageDrawable(d);
            //holder.imageButton.setImageResource(R.drawable.bell);

        }
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

package com.androidbelieve.drawerwithswipetabs;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<ServiceAlbum> albumList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,date,subcat;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            Log.v("Holder created" ,"here");
            title = (TextView) view.findViewById(R.id.tv_specs);
            count = (TextView) view.findViewById(R.id.tv_price);
            thumbnail = (ImageView) view.findViewById(R.id.iv_ads);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            date=(TextView)view.findViewById(R.id.tv_date);
            subcat=(TextView)view.findViewById(R.id.tv_status);
        }
    }


    public ServiceCategoryAdapter(Context mContext, final ArrayList<ServiceAlbum> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ad_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ServiceAlbum album = albumList.get(position);
        Log.v("Holder added" ,"here");
        holder.title.setText(album.getName());
        String temp="₹ " + album.getNumOfSongs();
        holder.count.setText(temp );
        holder.date.setText(album.getDate());
        Log.v("cat in holder",album.getsubCat());
        holder.subcat.setText(album.getsubCat());
        Drawable errord=mContext.getResources().getDrawable(album.getThumbnail());
        //new DisplayImage(album.getLink(),holder.thumbnail).execute();         //if adding again, remember to change Album class->link
        Picasso.with(mContext).load(album.getLink()).fit().error(errord).into(holder.thumbnail);
        final String aid=album.getSid();
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext,ServiceActivity.class);
                i.putExtra("sid",aid);
                mContext.startActivity(i);
            }
        });
        holder.setIsRecyclable(false);
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to Wishlist", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Report", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}

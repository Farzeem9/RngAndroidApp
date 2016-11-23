package com.androidbelieve.drawerwithswipetabs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;

import java.util.List;


public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {

    private Context mContext;
    private List<Ads> adList;
    private static Ads Ads;
    private static int pos;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView status, specs, price, date;
        public ImageView ads,overflow;


        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view_ads);
            status = (TextView) view.findViewById(R.id.tv_status);
            price = (TextView) view.findViewById(R.id.tv_price);
            specs = (TextView) view.findViewById(R.id.tv_specs);
            date = (TextView) view.findViewById(R.id.tv_date);
            ads = (ImageView) view.findViewById(R.id.iv_ads);
            overflow = (ImageView) view.findViewById(R.id.overflow);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(), "MANNY", Toast.LENGTH_SHORT).show();

                    return true;
                }
            });
        }
    }

    public WishlistAdapter(Context mContext, List<Ads> adList) {
        this.mContext = mContext;
        this.adList = adList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Ads ads = adList.get(position);
        holder.status.setText(ads.getStatus());
        holder.specs.setText(ads.getSpecs());
        holder.date.setText(ads.getDate());
        holder.price.setText("Rs " + ads.getPrice());
        holder.ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext,AdActivity.class);
                i.putExtra("AID",ads.getAid());
                mContext.startActivity(i);
            }
        });
        // loading album cover using Glide library
        new DisplayImage(ads.getlink(),holder.ads).execute();

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ads=ads;
                pos=position;
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
        inflater.inflate(R.menu.menu_wishlist, popup.getMenu());
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
                case R.id.action_remove:
                    GenericAsyncTask g=new GenericAsyncTask(mContext, "http://rng.000webhostapp.com/wishlist.php?aid=" + Ads.getAid() + "&pid=" + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
                        @Override
                        public void processFinish(Object output) {
                            if(output!=null) {
                                adList.remove(pos);
                                notifyDataSetChanged();
                            }
                        }
                    });
                    g.execute();
                    return true;
                case R.id.action_share:
                    Toast.makeText(mContext, "Share Add", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }


    }
    @Override
    public int getItemCount() {
        return adList.size();
    }
}
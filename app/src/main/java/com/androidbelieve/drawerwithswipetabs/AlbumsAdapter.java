package com.androidbelieve.drawerwithswipetabs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Album> albumList;
    static Album a;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,subcat,date;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            subcat=(TextView)view.findViewById(R.id.tv_subcat);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            date = (TextView) view.findViewById(R.id.tv_date);
        }
    }


    public AlbumsAdapter(Context mContext, final ArrayList<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Album album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.subcat.setText(album.getSubcat());
        holder.date.setText(album.getDate());
        String per = album.getper();
        if(per.equals("Days") && album.getNumOfSongs()!=0)
            holder.count.setText("₹ " + album.getNumOfSongs()+"/day" );
        else if(per.equals("Weeks") && album.getrentweek()!=0)
            holder.count.setText("₹ " + album.getrentweek()+"/week" );
        else if(per.equals("Months") && album.getrentmonth()!=0)
            holder.count.setText("₹ " + album.getrentmonth()+"/month" );
        Log.v("link of album",album.getLink());
        Log.v("aid",album.getAid());
        //new DisplayImage(album.getLink(),holder.thumbnail).execute();         //if adding again, remember to change Album class->link
        Picasso.with(mContext).load(album.getLink()).fit().into(holder.thumbnail);
        final String aid=album.getAid();
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i;
                if(album.getPid().equals(AccessToken.getCurrentAccessToken().getUserId()))
                i=new Intent(mContext,MyAdActivity.class);
                else
                i=new Intent(mContext,AdActivity.class);
                i.putExtra("AID",aid);
                mContext.startActivity(i);
            }
        });
        holder.setIsRecyclable(false);
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a=album;
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
        if(a.getPid().equals(AccessToken.getCurrentAccessToken().getUserId()))
            inflater.inflate(R.menu.menu_my_ad, popup.getMenu());
        else
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
                case R.id.action_share:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi. Sending this from RnG");
                    sendIntent.setType("text/plain");
                    mContext.startActivity(Intent.createChooser(sendIntent, "Hello"));
                    return true;
                case R.id.action_play_next:
                    GenericAsyncTask g=new GenericAsyncTask(mContext, Config.link+"report.php?aid=" + a.getAid() + "&pid=" + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
                        @Override
                        public void processFinish(Object output) {
                            String out=(String)output;
                            if(out.equals("1"))
                            {

                                AlertDialog.Builder alertbox = new AlertDialog.Builder(mContext);
                                alertbox.setTitle("Report");
                                alertbox.setMessage("Reported successfully");
                                alertbox.show();

                            }
                            else
                            {
                                AlertDialog.Builder alertbox = new AlertDialog.Builder(mContext);
                                alertbox.setTitle("Report");
                                alertbox.setMessage("You have already reported this ad!");
                                alertbox.show();
                                Log.v("output of async",out);
                            }
                        }
                    });
                    g.execute();
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

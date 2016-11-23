package com.androidbelieve.drawerwithswipetabs;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.LinkedList;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyHolder> {

    private Context mContext;
    private int unreadMessageCount;
    private LinkedList<Notification> notificationList;

    public class MyHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView notification,date;
        public Button yes,maybe,no;

        public MyHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cv_notification);
            notification = (TextView) view.findViewById(R.id.tv_noti);
            yes = (Button) view.findViewById(R.id.btn_yes);
            maybe = (Button) view.findViewById(R.id.btn_maybe);
            no = (Button) view.findViewById(R.id.btn_no);
            date = (TextView) view.findViewById(R.id.tv_date);

            /*view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(), "MANNY", Toast.LENGTH_SHORT).show();

                    return true;
                }
            });*/
        }
    }
    private NotificationActivity n;
    public NotificationAdapter(Context mContext, LinkedList<Notification> notificationList,NotificationActivity n) {
        this.mContext = mContext;
        this.notificationList = notificationList;
        this.n=n;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list_item, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
       final Notification notification = notificationList.get(position);
        final String type=notification.type();
        Log.v("Type",type);
        holder.notification.setText(notification.getNotification());
        holder.yes.setText(notification.getYes());
        holder.no.setText(notification.getNo());
        holder.maybe.setText(notification.getMaybe());
        holder.date.setText(notification.getDate()+"at "+notification.getTime());

        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Type inside",type);
                if(type.equals("BUTTON"))
                {
                    Log.v("Check this","Check");
                    Log.v("Link","http://rng.000webhostapp.com/sendnoti.php?nid="+notification.getNid());
                    SendNot s= new SendNot("http://rng.000webhostapp.com/sendnoti.php?nid="+notification.getNid());
                    s.nid=notification.getNid();
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#f7f7f7"));
                    holder.yes.setVisibility(Button.INVISIBLE);
                    holder.no.setVisibility(Button.INVISIBLE);
                    notification.setType("REPLIED");
                    notifyDataSetChanged();
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                        s.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    else
                        s.execute();

                }
            }
        });
        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.yes.setVisibility(Button.INVISIBLE);
                holder.no.setVisibility(Button.INVISIBLE);
                notification.setType("REPLIED");
                notifyDataSetChanged();
                n.removeButton(notification.getNid());
            }
        });
        if(notification.getType())
        {
            holder.yes.setVisibility(View.VISIBLE);
            holder.no.setVisibility(View.VISIBLE);
        }
        holder.setIsRecyclable(false);
    }

    public int getItemCount() {
        return notificationList.size();
    }
    public void updateList(String message,String type,String nid,String timestamp) throws ParseException {
        Log.v("Noti added","NotiAdded");
        notificationList.addFirst(new Notification(message,"yes","no","maybe",type,nid,timestamp));
        unreadMessageCount++;
        notifyDataSetChanged();
    }

    public class SendNot extends AsyncTask<String,String,String> {
        String link;
        String nid=null;
        SendNot(String link)
        {
            this.link=link;
            Log.v("Link",link);
            Log.v("Link constrctor",link);
        }
        @Override
        protected String doInBackground(String... params) {
//            Log.v("Link outside try",link);
            try
            {
//                Log.v("Link",link);
                URL url=new URL(link);
                URLConnection con=url.openConnection();

                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer sb=new StringBuffer();
                String lin=null;
                while((lin=br.readLine())!=null)
                {
                    sb.append(lin);
                }
                Log.v("Result Send Noti",sb.toString());
                return sb.toString();


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {

            if(result==null||result.equals("no noti"))
            {
                Log.v("Notification","no noti sent");

            }
            else if(result.equals("Already done"))
            {
                Toast.makeText(mContext, "Already Answered!!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(mContext, "Success! Try reloading notification!", Toast.LENGTH_SHORT).show();
                n.removeButton(nid);
                Log.v("Noti Status","Notifiction sent!");
               }

        }
    }

}
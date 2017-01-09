package com.androidbelieve.drawerwithswipetabs;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.ArrayList;

/**
 * Created by karth on 20-12-2016.
 */

public class FilterRadioAdapter extends RecyclerView.Adapter<FilterRadioAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Filter> list;
    int max=5000,deposit=6000;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filterradio, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Filter filter= list.get(position);
        holder.checkBox.setText(filter.getName());
        holder.checkBox.setSelected(filter.getSelected());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelect(position);
                filter.setSelected(true);
                new GenericAsyncTask(context, Config.link + "findmax.php?type=" + holder.checkBox.getText().toString(), "", new AsyncResponse() {
                    @Override
                    public void processFinish(Object output) {
                        String[] temp=((String)output).split(";;");
                        if(temp.length>1)
                        {
                            try {
                                max = Integer.parseInt(temp[0]);
                                deposit = Integer.parseInt(temp[1]);
                            }
                            catch(Exception e)
                            {
                                Log.e(getClass().getSimpleName(),"Error no valid int");
                                max=5000;
                                deposit=6000;
                            }
                        }
                    }
                }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

                notifyDataSetChanged();
            }
        });
    }

    public void setSelect(int position)
    {
        for(int i=0;i<list.size();i++)
        {
            list.get(i).setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public RadioButton checkBox;
        public MyViewHolder(View view) {
            super(view);
            //checkBox= (RadioButton) view.findViewById(R.id.radio);
        }
    }
    public FilterRadioAdapter(Context context, ArrayList<Filter> list) {
        this.context = context;
        this.list=list;
    }

}
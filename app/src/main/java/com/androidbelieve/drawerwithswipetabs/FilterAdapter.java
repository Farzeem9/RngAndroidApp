package com.androidbelieve.drawerwithswipetabs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.ArrayList;

/**
 * Created by karth on 20-12-2016.
 */


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Filter> list;
    int max=5000,deposit=6000;
    public void setSelect(int position)
    {
        for(int i=0;i<list.size();i++)
        {
            if(i!=position)
            list.get(i).setSelected(false);
        }
    }
    boolean isradio=false;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(isradio) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.filterradio, parent, false);
        }
        else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filtercheckbox, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Filter filter= list.get(position);
        holder.checkBox.setText(filter.getName());
        Log.v("infi","ifixc");
        Log.v(filter.getName(),Boolean.toString(filter.getSelected()));
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(filter.getSelected());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isradio) {
                    filter.setSelected(isChecked);
                }
                else
                {
                    setSelect(0);
                    filter.setSelected(isChecked);
                }
            }
        });



        /* holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isradio) {
                    if (holder.checkBox.isSelected()) {
                        holder.checkBox.setSelected(false);
                    } else {
                        holder.checkBox.setSelected(true);
                    }
                    filter.setSelected(holder.checkBox.isSelected());
                    Log.v(filter.getName(),Boolean.toString(filter.getSelected()));
                }
                else
                {
                    boolean x=!holder.checkBox.isSelected();
                    if(holder.checkBox.isSelected()) {
                        setSelect(position);
                    }
                    filter.setSelected(holder.checkBox.isSelected());
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
                    }).execute();

                }
            }
        });
*?
*/



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CheckBox checkBox;
        public RadioButton radiobutton;
        public MyViewHolder(View view) {
            super(view);
            if(!isradio) {
                checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            }
            else
            {
                checkBox=(CheckBox)view.findViewById(R.id.radio);
            }

        }
    }
    public FilterAdapter(Context context, ArrayList<Filter> list,boolean isradio) {
        this.isradio=isradio;
        this.context = context;
        this.list=list;
    }

}
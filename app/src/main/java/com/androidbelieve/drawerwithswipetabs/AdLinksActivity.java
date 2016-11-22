package com.androidbelieve.drawerwithswipetabs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdLinksActivity extends AppCompatActivity {

    private ImageView ivlinks;
    private EditText etlinks;
    private ImageButton ibdone;
    private LinearLayout lllinks;
    private ListView lvlinks;
    private LinksAdapter linksAdapter;
    private ArrayList<String> links;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_links);
        ivlinks=(ImageView)findViewById(R.id.iv_links);
        ibdone=(ImageButton)findViewById(R.id.ib_Done);
        etlinks=(EditText)findViewById(R.id.et_links);
        lvlinks=(ListView)findViewById(R.id.lv_links);
        lllinks=(LinearLayout)findViewById(R.id.ll_links);
        links=new ArrayList<String>();
        linksAdapter=new LinksAdapter();
        lvlinks.setAdapter(linksAdapter);
        ivlinks.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           lllinks.setVisibility(View.VISIBLE);
                                       }
                                   }
        );
        ibdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                links.add(etlinks.getText().toString());
                lvlinks.setAdapter(linksAdapter);
                //linksAdapter.notifyDataSetChanged();
                lllinks.setVisibility(View.GONE);

            }
        });
    }
    class LinksAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return links.size();
        }

        @Override
        public Object getItem(int i) {
            return links.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            LinksHolder holder = null;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.card_link, null);//Null for whole xml document
                holder = new LinksHolder();
                holder.ltv = (TextView) convertView.findViewById(R.id.tv_link_display);
                holder.itv = (ImageView)convertView.findViewById(R.id.bt_link_remove);
                convertView.setTag(holder);

            } else {
                holder = (LinksHolder) convertView.getTag();
            }
            String cur_link=getItem(i).toString();
            holder.ltv.setText(cur_link);
            holder.ltv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String a="http://"+links.get(i).toString();
                    Intent intent=new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(a));
                    startActivity(intent);
                }
            });
            holder.itv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    links.remove(i);
                    lvlinks.setAdapter(linksAdapter);
                }
            });
            return convertView;
        }
    }
    class LinksHolder {
        TextView ltv;
        ImageView itv;
    }
    public void onDone(View view){
        Intent intent=new Intent();
        intent.putStringArrayListExtra("links",links);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}

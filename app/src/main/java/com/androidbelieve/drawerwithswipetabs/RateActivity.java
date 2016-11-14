package com.androidbelieve.drawerwithswipetabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.AccessToken;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class RateActivity extends AppCompatActivity {
    private boolean x=false;
    private RatingBar ratingBar;
    private Button rate;
    private Spinner sp_comments;
    private String aid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        String canrent=getIntent().getStringExtra("CANRATE");
        aid=getIntent().getStringExtra("aid");
        ratingBar= (RatingBar) findViewById(R.id.ratingBar2);
        rate= (Button) findViewById(R.id.btn_rate_comment);
        ratingBar.setMax(5);
        if(canrent.equals("0")||!canrent.equals("1"))
        {
            ratingBar.setVisibility(View.GONE);
            rate.setVisibility(View.GONE);
        }
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GenericAsyncTask(getApplicationContext(),"http://rng.000webhostapp.com/ratings.php?aid="+aid+"&rating="+Integer.toString(ratingBar.getProgress())+"&pid="+AccessToken.getCurrentAccessToken().getUserId(),"",new AsyncResponse(){
                    @Override
                    public void processFinish(Object output) {

                    }
                }).execute();
            }
        });
        sp_comments= (Spinner) findViewById(R.id.sp_comments);
        List<String> comments = new ArrayList<String>();
        comments.add("Very Good");
        comments.add("Its okay");
        comments.add("Image quality is bad");
        comments.add("Please lower price");
        comments.add("Another such comment");

        //categories.add("Select a Category");

        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, comments);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sp_comments.setAdapter(dataAdapter);
        sp_comments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                if(x)
                                                new GenericAsyncTask(getApplicationContext(), "http://rng.000webhostapp.com/comments.php?aid=" + aid +"&commentid="+Integer.toString(position+1)+ "&pid=" + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
                                                    @Override
                                                    public void processFinish(Object output) {
                                                        finish();
                                                    }
                                                }).execute();
                                                  else
                                                    x=!x;
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }
                                          }
        );
        GenericAsyncTask g=new GenericAsyncTask(this, "http://rng.000webhostapp.com/currentcomment.php?aid=" + aid + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
            sp_comments.setSelection(Integer.parseInt((String)output)-1);
            }
        });
    }
}

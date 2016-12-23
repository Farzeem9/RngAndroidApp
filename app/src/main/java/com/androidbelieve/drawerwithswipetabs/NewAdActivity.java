package com.androidbelieve.drawerwithswipetabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NewAdActivity extends AppCompatActivity {

    private Toolbar toolbar=null;
    private Fragment fragment;
    @Override
    public void onBackPressed() {
        if(fragment.isVisible()) {
         if(fragment instanceof AdFragment)
         {
             AdFragment a=(AdFragment)fragment;
             if(a.imageshown)
             {
                a.hidePager();
             }
             else
             {
                 AlertDialog.Builder alertbox=new AlertDialog.Builder(this);
                 alertbox.setTitle("Exit");
                 alertbox.setMessage("Exit creating a new service?");
                 alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }
                 });
                 alertbox.setNegativeButton("no", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                     }
                 });
                 alertbox.show();
             }
         }
         if(fragment instanceof ServiceFragment)
         {
             ServiceFragment sf=(ServiceFragment)fragment;
             if(sf.imageshown)
             {
                 sf.hidePager();
             }
             else
             {
                 AlertDialog.Builder alertbox=new AlertDialog.Builder(this);
                 alertbox.setTitle("Exit");
                 alertbox.setMessage("Exit creating a new service?");
                 alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }
                 });
                 alertbox.setNegativeButton("no", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                     }
                 });
                 alertbox.show();
             }
         }
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_ad);
        String selection=getIntent().getStringExtra("fragment");
        String[] category = getResources().getStringArray(R.array.type);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);}
        //toolbar.setLogo(R.drawable.ic_dots);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_name));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(NewAdActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        if(selection.equals("newad"))
        {
            toolbar.setTitle("Post a Ad");
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragment=new AdFragment();
            fragmentTransaction.replace(R.id.fl_category,fragment).commit();
        }
        else
        {
            toolbar.setTitle("Post a Service");
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragment=new ServiceFragment();
            fragmentTransaction.replace(R.id.fl_category,fragment).commit();
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_our,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, AdActivity.class));
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*private FragmentManager.OnBackStackChangedListener backStackListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            setNavIcon();
        }
    };

    protected void setNavIcon() {
        int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        drawerToggle.setDrawerIndicatorEnabled(backStackEntryCount == 0);
    }*/
}
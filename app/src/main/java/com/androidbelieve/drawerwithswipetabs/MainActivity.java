package com.androidbelieve.drawerwithswipetabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    android.support.v7.widget.Toolbar toolbar;
    private boolean home=true;
    private MenuItem noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            x=1;
             mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
             mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;
             toolbar=(Toolbar)findViewById(R.id.toolbar);
             setSupportActionBar(toolbar);
             toolbar.setTitle("RnG");
             mFragmentManager = getSupportFragmentManager();
             mFragmentTransaction = mFragmentManager.beginTransaction();
             mFragmentTransaction.replace(R.id.containerView,new HomeFragment());
             mFragmentTransaction.addToBackStack(null);
             mFragmentTransaction.commit();
            NotificationReceiver.setupAlarm(getApplicationContext(),AccessToken.getCurrentAccessToken().getUserId());
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                 if (menuItem.getItemId() == R.id.nav_home) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView,new HomeFragment()).commit();
                     toolbar.setTitle("Home");
                     home=true;
                 }

                else if (menuItem.getItemId() == R.id.nav_myservices) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    toolbar.setTitle("My Services");
                    xfragmentTransaction.replace(R.id.containerView,new ServiceTabFragment()).commit();
                     home=false;
                     x=1;
                }
                 else if (menuItem.getItemId() == R.id.nav_myads) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     toolbar.setTitle("My Ads");
                     xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                     home=false;
                     x=1;
                 }
                else if (menuItem.getItemId() == R.id.nav_profile) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     toolbar.setTitle("Profile");
                     xfragmentTransaction.replace(R.id.containerView,new ProfileFragment()).commit();
                     home=false;
                     x=1;
                 }
                 else if (menuItem.getItemId() == R.id.nav_wishlist) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     toolbar.setTitle("Wishlist");
                     xfragmentTransaction.replace(R.id.containerView,new WishlistFragment()).commit();
                     home=false;
                     x=1;
                 }
                 else if (menuItem.getItemId() == R.id.nav_tandc)
                 {
                     Intent intent=new Intent(Intent.ACTION_VIEW);
                     intent.setData(Uri.parse("http://rentnget.co.in/"));
                     startActivityForResult(intent,0);
                 }
                 else if (menuItem.getItemId() == R.id.nav_faq)
                 {
                     Intent intent=new Intent(Intent.ACTION_VIEW);
                     intent.setData(Uri.parse("http://rentnget.co.in/"));
                     startActivityForResult(intent,0);
                 }
                 else if (menuItem.getItemId() == R.id.nav_share)
                 {
                     Intent sendIntent = new Intent();
                     sendIntent.setAction(Intent.ACTION_SEND);
                     sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi. Sending this from RnG");
                     sendIntent.setType("text/plain");
                     startActivity(Intent.createChooser(sendIntent, "Rent'N Get"));
                 }
                 return false;
            }

        });


                android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
                ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

                mDrawerLayout.setDrawerListener(mDrawerToggle);
                mDrawerToggle.syncState();


    }



    void getMenu(Menu menu)
    {
        noti=menu.findItem(R.id.action_notification);
        GenericAsyncTask genericAsyncTask= new GenericAsyncTask(this, Config.link + "checknoti.php?pid=" + AccessToken.getCurrentAccessToken().getUserId(), "", new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                String out=(String)output;
                if(out.equals("1"))
                {
                    noti.setIcon(R.drawable.newnotification);

                }
            }
        });
        genericAsyncTask.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main_our, menu);
        getMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                item.setIcon(R.drawable.nonotification);
                startActivity(new Intent(this, NotificationActivity.class));
                return true;
            case R.id.action_settings:
                FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                toolbar.setTitle("Profile");
                home=false;
                xfragmentTransaction.replace(R.id.containerView,new ProfileFragment()).commit();
                return true;
            case R.id.search:
                Log.v("search clicked","okay");
                startActivity(new Intent(this,SearchViewActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    int x=1;


    @Override
    public void onBackPressed() {

            if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                this.mDrawerLayout.closeDrawer(GravityCompat.START);
            }

        if(home&&x==0&&!this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            super.onBackPressed();
            finish();
        }
        else if(home&&x!=0&&!this.mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {
         x=0;
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT).show();
            Thread t = new Thread(){
                @Override
                public void run() {
                    try{
                        Thread.sleep(3000);
                        x=1;
                    }
                    catch(Exception E){};

                }
            };
            t.start();
        }
        else    {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView,new HomeFragment()).commit();
            toolbar.setTitle("Home");
            home=true;
        }
    }

}
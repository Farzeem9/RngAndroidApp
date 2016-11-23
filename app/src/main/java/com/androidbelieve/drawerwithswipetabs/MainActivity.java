package com.androidbelieve.drawerwithswipetabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    android.support.v7.widget.Toolbar toolbar;
    private boolean home=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                }
                 else if (menuItem.getItemId() == R.id.nav_myads) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     toolbar.setTitle("My Ads");
                     xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                     home=false;
                 }
                else if (menuItem.getItemId() == R.id.nav_profile) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     toolbar.setTitle("Profile");
                     xfragmentTransaction.replace(R.id.containerView,new ProfileFragment()).commit();
                     home=false;
                 }
                 else if (menuItem.getItemId() == R.id.nav_wishlist) {
                     FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                     toolbar.setTitle("Wishlist");
                     xfragmentTransaction.replace(R.id.containerView,new WishlistFragment()).commit();
                     home=false;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main_our, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                return true;
            case R.id.action_settings:
                FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                toolbar.setTitle("Profile");
                xfragmentTransaction.replace(R.id.containerView,new ProfileFragment()).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if(home) {
            super.onBackPressed();
            finish();
        }
        else    {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerView,new HomeFragment()).commit();
            toolbar.setTitle("Home");
            home=true;
        }
    }
}
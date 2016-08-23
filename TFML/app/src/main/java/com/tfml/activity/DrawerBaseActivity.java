package com.tfml.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.common.CommonUtils;
import com.tfml.common.SocialUtil;


public class DrawerBaseActivity extends BaseActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    NavigationView navigation;
    protected FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base);
        frameLayout = (FrameLayout) findViewById(R.id.framelayout_base_container);
        initInstances();
    }

    public void initInstances() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(DrawerBaseActivity.this, drawerLayout, R.string.refer_friend, R.string.refer_friend);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setItemIconTintList(null);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_new_scheme:
                        startActivity(new Intent(DrawerBaseActivity.this, SchemesActivity.class));
                        break;
                    case R.id.navigation_item_apply_loan:
                        startActivity(new Intent(DrawerBaseActivity.this, SchemesActivity.class));
                        break;
                    case R.id.navigation_item_refer_friend:
                        startActivity(new Intent(DrawerBaseActivity.this, SchemesActivity.class));
                        break;
                    case R.id.navigation_item_download:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.navigation_item_change_password:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.navigation_item_login:
                        startActivity(new Intent(DrawerBaseActivity.this, LoginActivity.class));
                        break;
                    case R.id.navigation_item_phone_call:
                        if (CommonUtils.isNetworkAvailable(DrawerBaseActivity.this)) {
                            SocialUtil.getContactList();
                            SocialUtil.dialPhoneCall(DrawerBaseActivity.this, SocialUtil.phoneNo);
                        } else {
                            Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.navigation_item_message:
                        if (CommonUtils.isNetworkAvailable(DrawerBaseActivity.this)) {
                            SocialUtil.getContactList();
                            SocialUtil.sendMail(DrawerBaseActivity.this, SocialUtil.email);
                        } else {
                            Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.navigation_item_whatsapp:
                        if (CommonUtils.isNetworkAvailable(DrawerBaseActivity.this)) {
                            SocialUtil.getContactList();
                            try {
                                SocialUtil.sendWhatsAppMsg(DrawerBaseActivity.this, SocialUtil.whatsAppNo);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.navigation_item_map:
                        Toast.makeText(DrawerBaseActivity.this, "Map service not avialable", Toast.LENGTH_SHORT).show();
                        break;


                }
                return false;
            }
        });

    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.END);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.END);
        }

        return super.onOptionsItemSelected(item);
    }


}

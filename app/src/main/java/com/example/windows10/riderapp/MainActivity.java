package com.example.windows10.riderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected FragmentManager fragmentManager;
    TextView navPhone;
    TextView txtFullName;
    String name, email, phone, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView= navigationView.getHeaderView(0);
        navPhone = (TextView)headerView.findViewById(R.id.navPhone);
        Intent myIntent = getIntent();
        phone = myIntent.getStringExtra("Phone");
        navPhone.setText(phone);

        txtFullName= (TextView)headerView.findViewById(R.id.navFullName);
        name = myIntent.getStringExtra("name");
        txtFullName.setText(name);

        email = myIntent.getStringExtra("Email");
        pwd = myIntent.getStringExtra("Password");



        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.content_main_FrameLayout)!=null)
        {

            if(savedInstanceState!=null)
            {
                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            btnOption homeFragment = new btnOption();
            Intent intent = new Intent(MainActivity.this, EditProfile.class);
            intent.putExtra("Phone1", phone);
            intent.putExtra("name", name);
            fragmentTransaction.add(R.id.content_main_FrameLayout,homeFragment, null);
            fragmentTransaction.commit();

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_viewDeliveredHistory) {
            Intent intent1 = new Intent(MainActivity.this, History.class);
            intent1.putExtra("Phone", phone);
            startActivity(intent1);
            return true;
        } else
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_signOut)
        {
            Intent intent = new Intent(MainActivity.this, LoginRider.class);
            startActivity(intent);
        } else {

            displaySelectedScreen(id);
        }

        return true;
    }

    private void displaySelectedScreen(int id)
    {
        Fragment fragment = null;

        if(fragment!=null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main_FrameLayout, fragment);
            ft.commit();
        }

        switch(id)
        {
            case R.id.nav_home:


                break;
            case R.id.nav_editProfile:


                break;
            case R.id.nav_order:

                break;
            case R.id.nav_help:


                break;
            case R.id.nav_about:
                //fragment = new about();


                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
}

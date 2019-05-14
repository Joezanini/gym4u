package com.example.gym4u;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Announcement extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "yo";
    SharedPreferences sh;
    String post;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.navHeadName);

        /**
         * if user is brand new, post will be null so we
         * use the extra that is extracted from the
         * registration user detail activity to set
         * name text edit value. else, we use the database
         * value.
         */
        if(post == null) {
            String s = getIntent().getStringExtra("name");
            Log.d(TAG, "onCreate: " + s);
            name.setText(s);
        } else {
            //name = findViewById(R.id.navHeadName);
            name.setText(post);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Announcement.this, Client_Home.class);
            //puts extra string for value to be available in other activities
            String s = name.getText().toString();
            intent.putExtra("name", s);
            startActivity(intent);
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

        if (id == R.id.nav_annoucements) {
            Intent intent = new Intent(Announcement.this, Announcement.class);
            //puts extra string for value to be available in other activities
            String s = name.getText().toString();
            intent.putExtra("name", s);
            startActivity(intent);
        } else if (id == R.id.nav_wall) {
            Intent intent = new Intent(Announcement.this, Wall.class);
            //puts extra string for value to be available in other activities
            String s = name.getText().toString();
            intent.putExtra("name", s);
            startActivity(intent);
        } else if (id == R.id.nav_gym) {
            Intent intent = new Intent(Announcement.this, Urgym.class);
            //puts extra string for value to be available in other activities
            String s = name.getText().toString();
            intent.putExtra("name", s);
            startActivity(intent);
        } else if (id == R.id.nav_heart) {
            Intent intent = new Intent(Announcement.this, Urheart.class);
            //puts extra string for value to be available in other activities
            String s = name.getText().toString();
            intent.putExtra("name", s);
            startActivity(intent);
        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(Announcement.this, Client_Home.class);
            //name = findViewById(R.id.navHeadName);
            String s = name.getText().toString();
            intent.putExtra("name", s);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

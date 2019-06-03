package com.example.gym4u;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Urheart extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {
    private static final String TAG = "urHeart yo";
    TextView name;
    String post;
    FirebaseUser user;

    //added for simple heart rate monitor
    Button show;
    TextView showHeartRate;
    SensorManager sensorMgr;
    Sensor heartRate;
    String heartRateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urheart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        user = FirebaseAuth.getInstance().getCurrentUser();
        post = user.getDisplayName();


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


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        show = findViewById(R.id.urHeart_start);
        show.setOnClickListener(displayHeartRate);
        showHeartRate = findViewById(R.id.number);
        sensorMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        heartRate = sensorMgr.getDefaultSensor(Sensor.TYPE_HEART_RATE);


    }

    View.OnClickListener displayHeartRate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setHeartrate(heartRateValue);
        }
    };

    private void setHeartrate(String rate) {
        showHeartRate.setText(rate);
    }

    @Override
    protected void onResume(){
        super.onResume();

        sensorMgr.registerListener(this , heartRate,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorMgr.unregisterListener((SensorEventListener) this);
    }


    public void onSensorChanged(SensorEvent sensorEvent) {
        heartRateValue = Integer.toString((int) (sensorEvent.values.length > 0 ? sensorEvent.values[0] : 0.0f));
        setHeartrate(heartRateValue);
        Log.d(TAG, "onSensorChanged: reading hearRate");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Urheart.this, Client_Home.class);
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
        if (id == R.id.action_signout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(Urheart.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_annoucements) {
            Intent intent = new Intent(Urheart.this, Announcement.class);
            startActivity(intent);
        } else if (id == R.id.nav_wall) {
            Intent intent = new Intent(Urheart.this, Wall.class);
            startActivity(intent);
        } else if (id == R.id.nav_gym) {
            Intent intent = new Intent(Urheart.this, Urgym.class);
            startActivity(intent);
        } else if (id == R.id.nav_heart) {
            Intent intent = new Intent(Urheart.this, Urheart.class);
            startActivity(intent);
        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(Urheart.this, Client_Home.class);
            startActivity(intent);
        }else if(id == R.id.nav_profile){
            Intent intent = new Intent(Urheart.this, Your_Profile.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

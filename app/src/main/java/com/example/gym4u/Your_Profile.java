package com.example.gym4u;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;

import static android.media.CamcorderProfile.get;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

//import com.jjoe64.graphview.GraphView;

public class Your_Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LocationManager locationManager;
    LocationListener locationListener;
    public ImageView picturePost;
    public static final String MY_PREFS = "MyPrefs";
    String destination, id, name;
    private static final int galleryPick = 1;
    private Uri ImageUri;
    private FirebaseStorage mStoreRef;
    public RequestManager glide;

    public JobIntentService serviceintent;

    LocationService locationService;
    Context ctx;
    public Context getCtx(){
        return ctx;
    }

    //variable for service code
    Calendar now;
    FirebaseDatabase database;
    DatabaseReference ref;
    public static BarGraphSeries<DataPoint> series;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your__profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ctx = this;
        destination = "UserProfilePicture";
        id = FirebaseAuth.getInstance().getUid();
        picturePost = findViewById(R.id.imageView2);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        Log.d("TAG", "restoredTxt is not null");
        name = prefs.getString("name", "No name defined");
        Log.d("TAG", "from shared prefs = "+name);



        try{
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(destination).child(name+".jpg");
            if (storageReference != null) {
                Log.d("TAG", "picturePost = " + picturePost);
                try {
                    GlideApp.with(this).load(storageReference).placeholder(R.drawable.profile_pic).diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true).into(picturePost);
                    //glide.load(storageReference).into(picturePost);
                    Log.d("TAG", "Picture Exists");
                }
                catch (Exception e) {
                }
            } else {
                Log.d("TAG", "No Picture exists for this");
            }
        }
        catch (Exception e){
            Log.d("TAG", "No Picture for ");
        }




        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        // start of a whole bunch of graph stuff

        final GraphView graph = (GraphView) findViewById(R.id.graph);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Days of the Week");
        gridLabel.setVerticalAxisTitle("Minutes spent @ gym");
        graph.getGridLabelRenderer().setNumHorizontalLabels(7);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(130);
        series = new BarGraphSeries<>();


        now = Calendar.getInstance();
        //calendar stuff
        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                now.set(Calendar.YEAR, year);
                now.set(Calendar.MONTH, month);
                now.set(Calendar.DATE, dayOfMonth);
                now.get(Calendar.WEEK_OF_YEAR);


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        child("GymTimeInfo").child(String.valueOf((now.get(Calendar.WEEK_OF_YEAR))));
                reference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {


                            graph.removeAllSeries();
                            int a, b, c, d, e, f, g;
                            a = dataSnapshot.getValue(gymTime.class).getMon();
                            b = dataSnapshot.getValue(gymTime.class).getTue();
                            c = dataSnapshot.getValue(gymTime.class).getWed();
                            d = dataSnapshot.getValue(gymTime.class).getThu();
                            e = dataSnapshot.getValue(gymTime.class).getFri();
                            f = dataSnapshot.getValue(gymTime.class).getSat();
                            g = dataSnapshot.getValue(gymTime.class).getSun();


                            DataPoint[] values = new DataPoint[7];
                            DataPoint m = new DataPoint(0, a);
                            DataPoint t = new DataPoint(1, b);
                            DataPoint w = new DataPoint(2, c);
                            DataPoint th = new DataPoint(3, d);
                            DataPoint fr = new DataPoint(4, e);
                            DataPoint sat = new DataPoint(5, f);
                            DataPoint sun = new DataPoint(6, g);
                            values[0] = m;
                            values[1] = t;
                            values[2] = w;
                            values[3] = th;
                            values[4] = fr;
                            values[5] = sat;
                            values[6] = sun;

                            series.resetData(values);

                            graph.addSeries(series);

                            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                                @Override
                                public int get(DataPoint data) {
                                    return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                                }


                            });
                            series.setSpacing(50);
                            series.setDrawValuesOnTop(true);
                            series.setValuesOnTopColor(Color.RED);
                            //series.resetData(values);

                        }else{

                            series.resetData(emptyValues(graph));


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


    }



    public DataPoint[] emptyValues(GraphView graphView) {
        DataPoint[] values = new DataPoint[7];
        for (int i = 0; i < 7; i++) {
            DataPoint m = new DataPoint(i, 0);
            values[i]=m;

        }
        return values;

    }

/*
    @Override
    protected void onDestroy() {
        stopService(serviceintent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();
    }

*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            Intent intent = new Intent(Your_Profile.this, MainActivity.class);
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
            Intent intent = new Intent(Your_Profile.this, Announcement.class);
            startActivity(intent);
        } else if (id == R.id.nav_wall) {
            Intent intent = new Intent(Your_Profile.this, Wall.class);
            startActivity(intent);
        } else if (id == R.id.nav_gym) {
            Intent intent = new Intent(Your_Profile.this, Urgym.class);
            startActivity(intent);
        } else if (id == R.id.nav_heart) {
            Intent intent = new Intent(Your_Profile.this, Urheart.class);
            startActivity(intent);
        }else if(id == R.id.nav_profile){
            Intent intent = new Intent(Your_Profile.this, Your_Profile.class);
            startActivity(intent);
        }else if (id == R.id.nav_home){
            Intent intent = new Intent(Your_Profile.this, Client_Home.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void OpenGallery(View view) {
        Intent galleryImage = new Intent();
        galleryImage.setAction(Intent.ACTION_GET_CONTENT);
        galleryImage.setType("image/*");
        startActivityForResult(galleryImage, galleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            picturePost.setImageURI(ImageUri);
            picturePost = findViewById(R.id.imageView2);
            picturePost.setDrawingCacheEnabled(true);
            picturePost.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) picturePost.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte [] data2 = baos.toByteArray();
            mStoreRef = FirebaseStorage.getInstance();
            StorageReference filePath = mStoreRef.getReference();
            StorageReference imageRef = filePath.child(destination);
            String fileName = name + ".jpg";
            StorageReference spaceRef = imageRef.child(fileName);
            UploadTask uploadTask = spaceRef.putBytes(data2);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TAG","Photo failure");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("TAG", "Photo Sucess");
                }
            });
        }
    }
}

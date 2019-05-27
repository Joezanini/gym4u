package com.example.gym4u;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
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
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client_Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "yo";
    //TextView name;
    //String s;
    FirebaseAuth mAuth;
    private static final int PICK_VIDEO = 1;
    private Uri videoUri;
    StorageReference videoRef, storageRef;
    String type, gym;
    VideoView videoView;




    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        locationManager =(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);



        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String longit = Location.convert(location.getLongitude(), location.FORMAT_DEGREES);
                String lat = Location.convert(location.getLatitude(), location.FORMAT_DEGREES);
                Double gymLat = 37.711780;
                Double gymLong = -121.000594;
                Integer hourStart, minStart;

                Location gym = new Location(location);
                gym.setLatitude(gymLat);
                gym.setLongitude(gymLong);

                Location current = new Location(location);
                minStart = getMin();
                hourStart = getHour();
                while(current.distanceTo(gym) < 100 ) {
                    Log.i("FOUND LOCATION", "AT THE GYM");

                  //  Log.i("start timer", "gym start time is " + hourStart + ":" + minStart);
                    if (current.distanceTo(gym) > 50) {
                        Integer minEnd = getMin();
                        Integer hourEnd = getHour();
                        Integer hours = hourEnd - hourStart;
                        Integer mins = minStart - minEnd;
                        Log.i("Gym TIME", "at the gym for " + hours+ "." +mins);
                        //Log.i("Location", "long: " + longit + "lat: " + lat);
                        return;

                    }

                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
            }
        };
        //go outside of location listener to ask for permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }

        final Button videoChangeButton = findViewById(R.id.post_button_for_video);
        String id1 = FirebaseAuth.getInstance().getUid();
        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference refT = database1.getReference("Users/" + id1 + "/type");
        refT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                type = (String) dataSnapshot.getValue();
                if (type.matches("Client")) {
                    videoChangeButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


        videoView = findViewById(R.id.VideoView);

        // Everything to get video running

/*        VideoView videoView = findViewById(R.id.VideoView);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.kurtosiander);
        videoView.start();

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
*/
        //end video stuff

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        // Get a reference to our posts
        String id = FirebaseAuth.getInstance().getUid();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users/"+id+"/name");
        DatabaseReference refG = database.getReference("Users/"+id+"/gym");
        FirebaseUser cUser = mAuth.getCurrentUser();

        storageRef = FirebaseStorage.getInstance().getReference();



        refG.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gym = (String) dataSnapshot.getValue();
                Log.d("Video: gym", gym);
                videoRef = storageRef.child("/video/" + gym);
                videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("Video", "The video was successful");
                        Toast.makeText(Client_Home.this, uri.toString(), Toast.LENGTH_SHORT).show();
                        MediaController mc = new MediaController(Client_Home.this);
                        videoView.setMediaController(mc);
                        videoView.setVideoURI(uri);
                        Log.d("Video", "we are halfway");
                        videoView.requestFocus();
                        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mediaPlayer.setLooping(true);
                                videoView.start();
                            }
                        });
                        Log.d("Video", "Video should be started");
                        Toast.makeText(Client_Home.this, "The video should be playing", Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post = (String) dataSnapshot.getValue();
                Log.d("Error:", post);
                //TextView name = findViewById(R.id.navHeadName);
                //name.setText(post);
               // s = name.getText().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




    }

    public void OpenGallery(View view) {
        Intent galleryVideo = new Intent();
        galleryVideo.setAction(Intent.ACTION_GET_CONTENT);
        galleryVideo.setType("video/*");
        startActivityForResult(galleryVideo, PICK_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == PICK_VIDEO && data != null) {
                    Uri selectedVideoUri = data.getData();
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                    videoRef = storageRef.child("/video/" + gym);
                    //TODO: save the video in the db
                    uploadData(selectedVideoUri);
            }
        }


    private void uploadData(Uri videoUri) {
        if(videoUri != null){
            UploadTask uploadTask = videoRef.putFile(videoUri);

            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful())
                        Toast.makeText(Client_Home.this, "Upload Complete", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                }
            });
            videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d("Video", "The video was successful");
                    Toast.makeText(Client_Home.this, uri.toString(), Toast.LENGTH_SHORT).show();
                    MediaController mc = new MediaController(Client_Home.this);
                    videoView.setMediaController(mc);
                    videoView.setVideoURI(uri);
                    Log.d("Video", "we are halfway");
                    videoView.requestFocus();
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.setLooping(true);
                            videoView.start();
                        }
                    });
                    Log.d("Video", "Video should be started");
                    Toast.makeText(Client_Home.this, "The video should be playing", Toast.LENGTH_LONG).show();
                }
            });

        }else {
            Toast.makeText(this, "Nothing to upload", Toast.LENGTH_SHORT).show();
        }

    }



    public static Integer getMin() {
        Date time = new Date();
        //lower case h = 12 hr time, a = use AM/PM
        String strTimeFormat = "mm";
        DateFormat timeFormat = new SimpleDateFormat(strTimeFormat);
        Integer min = Integer.parseInt(timeFormat.format(time));
        return min;
    }
    public static Integer getHour(){
        Date time = new Date();
        //lower case h = 12 hr time, a = use AM/PM
        String strTimeFormat = "hh";
        DateFormat timeFormat = new SimpleDateFormat(strTimeFormat);
        Integer hour = Integer.parseInt(timeFormat.format(time));
        return hour;
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_annoucements) {
            Intent intent = new Intent(Client_Home.this, Announcement.class);
            startActivity(intent);
        } else if (id == R.id.nav_wall) {
            Intent intent = new Intent(Client_Home.this, Wall.class);
            startActivity(intent);
        } else if (id == R.id.nav_gym) {
            Intent intent = new Intent(Client_Home.this, Urgym.class);
            startActivity(intent);
        } else if (id == R.id.nav_heart) {
            Intent intent = new Intent(Client_Home.this, Urheart.class);
            startActivity(intent);
        }else if(id == R.id.nav_profile){
            Intent intent = new Intent(Client_Home.this, Your_Profile.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Item is null", Toast.LENGTH_LONG).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

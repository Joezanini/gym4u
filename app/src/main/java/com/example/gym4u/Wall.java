package com.example.gym4u;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Wall extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Button NewPostButton;
    public ImageView picturePost;
    public EditText newPost;
    private static final int galleryPick = 1;
    private Uri ImageUri;
    SharedPreferences sh;
    String post;

    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.navHeadName);
        sh = getSharedPreferences("myPrefs", 0);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.navHeadName);

        NewPostButton = (Button) findViewById(R.id.postButton);
        picturePost = (ImageView) findViewById(R.id.postImage);
        picturePost.setVisibility(View.INVISIBLE);
        newPost = findViewById(R.id.Post);

        String id = FirebaseAuth.getInstance().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users/"+id+"/name");
        DatabaseReference refG = database.getReference("Users/"+id+"/gym");
        refG.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String gym = (String) dataSnapshot.getValue();
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
                post = (String) dataSnapshot.getValue();
                Log.d("Error:", post);
                name.setText(post);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", post);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey("name")) {
            this.name.setText(savedInstanceState.get("name").toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor edit = this.sh.edit();
        edit.putString("name", this.name.getText().toString());
        edit.apply();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Wall.this, Client_Home.class);
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
            Intent intent = new Intent(Wall.this, Announcement.class);
            String s = name.getText().toString();
            intent.putExtra("name", s);
            startActivity(intent);
        } else if (id == R.id.nav_wall) {
            Intent intent = new Intent(Wall.this, Wall.class);
            String s = name.getText().toString();
            intent.putExtra("name", s);
            startActivity(intent);
        } else if (id == R.id.nav_gym) {
            Intent intent = new Intent(Wall.this, Urgym.class);
            String s = name.getText().toString();
            intent.putExtra("name", s);
            startActivity(intent);
        } else if (id == R.id.nav_heart) {
            Intent intent = new Intent(Wall.this, Urheart.class);
            String s = name.getText().toString();
            intent.putExtra("name", s);
            startActivity(intent);
        } else if (id == R.id.nav_home) {
            Intent intent = new Intent(Wall.this, Client_Home.class);
            String s = name.getText().toString();
            intent.putExtra("name", s);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void OpenGallery(View view) {
        Intent galleryImage = new Intent();
        galleryImage.setAction(Intent.ACTION_GET_CONTENT);
        galleryImage.setType("image/*");
        startActivityForResult(galleryImage,galleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null){
            ImageUri = data.getData();
            picturePost.setVisibility(View.VISIBLE);
            picturePost.setImageURI(ImageUri);
        }
    }

    public void UpdatePost(View view) {
        if(newPost.getText().toString().isEmpty()){
            Toast.makeText(this, "no post content", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            String Description = newPost.getText().toString();

        }
    }
}

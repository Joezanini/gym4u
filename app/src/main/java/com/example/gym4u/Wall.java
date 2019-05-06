package com.example.gym4u;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
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

public class Wall extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button NewPostButton;
    private EditText newPost;
    private static final int galleryPick = 1;
    private Uri ImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NewPostButton = (Button) findViewById(R.id.postButton);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Wall.this, Client_Home.class);
            startActivity(intent);
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wall, menu);
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
            startActivity(intent);
        } else if (id == R.id.nav_wall) {
            Intent intent = new Intent(Wall.this, Wall.class);
            startActivity(intent);
        } else if (id == R.id.nav_gym) {
            Intent intent = new Intent(Wall.this, Urgym.class);
            startActivity(intent);
        } else if (id == R.id.nav_heart) {
            Intent intent = new Intent(Wall.this, Urheart.class);
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

    //@Override
    //protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      //  super.onActivityResult(requestCode, resultCode, data);

        //if (requestCode == galleryPick && resultCode == RESULT_OK && data != null){
          //  ImageUri = data.getData();
            //SelectPostImage.setImageURI(ImageUri);
        //}
    //}

    //public void UpdatePost(View view) {
        //String Description = PostDescription.getText().toString();
        //if(TextUtils.isEmpty(Description)){
            
      //  }
    //}
}

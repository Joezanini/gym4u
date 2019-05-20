package com.example.gym4u;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Wall extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //MyDB db;
    public Button NewPostButton;
    public ImageView picturePost;
    public EditText newPost;
    private static final int galleryPick = 1;
    private Uri ImageUri;
    public String name;
    ArrayList<Postdata> arrayList = new ArrayList<>();
    ArrayAdapter<Postdata> arrayAdapter;
    private DatabaseReference mDataRef;
    private FirebaseStorage mStoreRef;
    private RecyclerView mRecycleView;
    public  String saveDate, saveTime, saveName, downloadUrl, photoStringLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        // showPosts();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NewPostButton = (Button) findViewById(R.id.post_button);
        picturePost = (ImageView) findViewById(R.id.postImage1);
        picturePost.setVisibility(View.INVISIBLE);
        newPost = findViewById(R.id.postEditText);

        String id = FirebaseAuth.getInstance().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users/" + id + "/name");
        DatabaseReference refG = database.getReference("Users/" + id + "/gym");
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
                name = (String) dataSnapshot.getValue();
                Log.d("Error:", name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        mDataRef = FirebaseDatabase.getInstance().getReference("Gyms").child("Dynamic").child("posts");
        //mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        //List<Postdata> list = new ArrayList<>();
        //Adapter adapter = new Adapter(list);
        //mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        //mRecycleView.setAdapter(adapter);


        mDataRef.addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                               mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
                                               List<Postdata> list = new ArrayList<>();
                                               Adapter adapter = new Adapter(list, GlideApp.with(Wall.this));
                                               mRecycleView.setLayoutManager(new LinearLayoutManager(Wall.this));
                                               mRecycleView.setAdapter(adapter);
                                               List<Postdata> sampleList = new ArrayList<>();
                                               for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                   if (ds.child("name").getValue() == null) {
                                                       Log.d("TAG", "Empty name");
                                                   } else {
                                                       Postdata obj = new Postdata();
                                                       String name = ds.child("name").getValue(String.class);
                                                       String post = ds.child("post").getValue(String.class);
                                                       String date = ds.child("date").getValue(String.class);
                                                       String time = ds.child("time").getValue(String.class);
                                                       obj.setName(name);
                                                       obj.setPost(post);
                                                       obj.setDate(date);
                                                       obj.setTime(time);
                                                       sampleList.add(obj);
                                                       //String safeName = date + time;
                                                       //Log.d("TAG", "SafeName :" + safeName);
                                                       /*try{
                                                       StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("PostImages").child(safeName+".jpg");
                                                       ImageView imageView = findViewById(R.id.PostImg);
                                                       GlideApp.with(Wall.this )
                                                               .load(storageReference)
                                                               .into(imageView);}
                                                       catch(Exception exception){
                                                            Log.d("TAG", "No pictures");
                                                       }*/
                                                       Log.d("TAG", name + " / " + post + " / " + date + " / " + time);
                                                   }
                                                   //list.add(obj);
                                                   //adapter.notifyDataSetChanged();
                                               }
                                               list.addAll(sampleList);
                                               adapter.notifyDataSetChanged();
                                               Collections.reverse(list);
                                           }


                                           @Override
                                           public void onCancelled(@NonNull DatabaseError databaseError) {
                                               Log.d("TAG", "canceled");
                                           }
                                       }
        );

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
        startActivityForResult(galleryImage, galleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            picturePost.setVisibility(View.VISIBLE);
            picturePost.setImageURI(ImageUri);
            picturePost.setDrawingCacheEnabled(true);
            picturePost.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) picturePost.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte [] data2 = baos.toByteArray();
            saveDate = getDate();
            saveTime= getTime();
            saveName = saveDate + saveTime;
            mStoreRef = FirebaseStorage.getInstance();
            StorageReference filePath = mStoreRef.getReference();
            StorageReference imageRef = filePath.child("PostImages");
            String fileName = saveName + ".jpg";
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


    public static String getDate() {
        Date date = new Date();
        //lower case h = 12 hr time, a = use AM/PM
        String strDateFormat = "MM/dd/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public static String getTime() {
        Date time = new Date();
        //lower case h = 12 hr time, a = use AM/PM
        String strTimeFormat = "h:mm a";
        DateFormat timeFormat = new SimpleDateFormat(strTimeFormat);
        String formattedTime = timeFormat.format(time);
        return formattedTime;

    }

    public void UpdatePost(View view) {
        if (newPost.getText().toString().isEmpty()) {
            Toast.makeText(this, "no post content", Toast.LENGTH_LONG).show();
            return;
        } else {
            String post = newPost.getText().toString();
            newPost.setText("");
            picturePost.setVisibility(View.INVISIBLE);
            String date = getDate();
            String time = getTime();

            Postdata postdata = new Postdata();
            postdata.setName(name);
            postdata.setDate(date);
            postdata.setPost(post);
            postdata.setTime(time);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Gyms").child("Dynamic").child("posts");
            String newRef = ref.push().getKey();
            ref.child(newRef).setValue(postdata);

                }



        }
    }







package com.example.gym4u;

import android.app.Activity;
import android.app.Fragment;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
/*
public class announcement_fragment extends Fragment {

    private static final int galleryPick = 1;
    public ImageView picturePost;
    private Uri ImageUri;
    public  String saveDate, saveTime, saveName;
    private FirebaseStorage mStoreRef;
    private FirebaseDatabase mDataRef;
    public String name;
    public EditText newPost;
    public View view;
    public Button addPost;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.announcement_fragment, container,false);
        picturePost = view.findViewById(R.id.postImageForAnnoucement);
        newPost = view.findViewById(R.id.postEditTextforAnnoucements);
        addPost = view.findViewById(R.id.post_button_for_annoucement);
        String id = FirebaseAuth.getInstance().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users/" + id + "/name");
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
        return view;
    }
    public void OpenGallery1(View view) {
        Intent galleryImage = new Intent();
        galleryImage.setAction(Intent.ACTION_GET_CONTENT);
        galleryImage.setType("image/*");
        startActivityForResult(galleryImage, galleryPick);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryPick && resultCode == Activity.RESULT_OK && data != null) {
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

    public static String getDate(){
        Date date = new Date();
        //lower case h = 12 hr time, a = use AM/PM
        String strDateFormat = "MM/dd/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public static String getTime(){
        Date time = new Date();
        //lower case h = 12 hr time, a = use AM/PM
        String strTimeFormat = "h:mm a";
        DateFormat timeFormat = new SimpleDateFormat(strTimeFormat);
        String formattedTime = timeFormat.format(time);
        return formattedTime;

    }

    public void UpdatePost1(View view) {
        if (newPost.getText().toString().isEmpty()) {
            Log.d("TAG", "No post activity");
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
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Gyms").child("Dynamic").child("announcements");
            String newRef = ref.push().getKey();
            ref.child(newRef).setValue(postdata);

        }



    }
}
*/
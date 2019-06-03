package com.example.gym4u;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableResource;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class userDetailsActivity extends AppCompatActivity {
EditText name;
Spinner CISpinner;
Spinner GymSpinner;
SharedPreferences sh;
FirebaseStorage mStoreRef;
String destination = "UserProfilePicture";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        name = findViewById(R.id.nameEditText);
        CISpinner = (Spinner) findViewById(R.id.spinner);
        GymSpinner = findViewById(R.id.gymSpinner);

    }

    public void ButtonClick(View view) {

        String personName = name.getText().toString();
        String userType = CISpinner.getSelectedItem().toString();
        String gymName = GymSpinner.getSelectedItem().toString();

        Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.profile_pic);
        //Bitmap bitmap = ((Bitmap) R.drawable.profile_pic).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte [] data2 = baos.toByteArray();
        mStoreRef = FirebaseStorage.getInstance();
        StorageReference filePath = mStoreRef.getReference();
        StorageReference imageRef = filePath.child(destination);
        String fileName = personName + ".jpg";
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


        User user = new User(personName, gymName, userType);

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

        Intent intent = new Intent(this, Client_Home.class);
        intent.putExtra("name", personName);
        startActivity(intent);


    }
}

package com.example.gym4u;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.api.Api;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class userDetailsActivity extends AppCompatActivity {
EditText name;
Spinner CISpinner;
Spinner GymSpinner;
SharedPreferences sh;
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

        User user = new User(personName, gymName, userType);

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

        Intent intent = new Intent(this, Client_Home.class);
        intent.putExtra("name", personName);
        startActivity(intent);

    }
}

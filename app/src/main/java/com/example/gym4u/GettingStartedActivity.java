package com.example.gym4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GettingStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);
    }


    public void instrLogin(View view) {
        Intent intent = new Intent(this, InstructorLogin.class);
        startActivity(intent);
    }

    public void clientLogin(View view) {
        Intent intent = new Intent(this, ClientLogin.class);
        startActivity(intent);
    }
}

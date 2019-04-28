package com.example.gym4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InstructorLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_login);
    }

    public void instrReg(View view) {
        Intent intent = new Intent(this, InstructorRegister.class);
        startActivity(intent);
    }
}

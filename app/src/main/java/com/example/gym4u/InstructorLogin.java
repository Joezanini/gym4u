package com.example.gym4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InstructorLogin extends AppCompatActivity implements View.OnClickListener {

    Button iLogin;
    EditText iUsername, iPassword, iGym, iName;
    TextView iRegister;
    InstructorLocalStore instructorLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_login);

        iUsername = findViewById(R.id.etUsername);
        iName = findViewById(R.id.eName);
        iGym = findViewById(R.id.etGym);
        iPassword = findViewById(R.id.etPassword);
        iLogin = findViewById(R.id.instrLoginBtn);
        iRegister = findViewById(R.id.instrRegisterLink);

        instructorLocalStore = new InstructorLocalStore(this);
        iLogin.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticateInstructor() == true) {
            displayInstructorDetails();
        }

    }

    private boolean authenticateInstructor() {
        return instructorLocalStore.getInstructorLoggedIn();
    }

    private void displayInstructorDetails() {
        Instructor instructor = instructorLocalStore.getLoggedInstructor();
        iUsername.setText(instructor.username);
        iName.setText(instructor.name);
        iGym.setText(instructor.gym);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.instrLoginBtn:
                Instructor instructor = new Instructor(null, null, null, null);
                instructorLocalStore.storeInstructorData(instructor);
                instructorLocalStore.setInstructorLoggedIn(true);
                break;

            case R.id.instrRegisterLink:
                Intent intent = new Intent(this, InstructorRegister.class);
                startActivity(intent);
                break;
        }
    }

}

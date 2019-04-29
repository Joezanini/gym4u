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
    EditText iUsername, iPassword;
    TextView iRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_login);

        iUsername = (EditText) findViewById(R.id.etUsername);
        iPassword = (EditText) findViewById(R.id.etPassword);
        iLogin = (Button) findViewById(R.id.instrLoginBtn);
        iRegister = (TextView) findViewById(R.id.instrRegisterLink);
        iLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.instrLoginBtn:

                break;

            case R.id.instrRegisterLink:
                Intent intent = new Intent(this, InstructorRegister.class);
                startActivity(intent);
                break;
        }
    }

}

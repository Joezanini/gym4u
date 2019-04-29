package com.example.gym4u;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InstructorRegister extends AppCompatActivity implements View.OnClickListener{

    Button iRegister;
    EditText name, gym, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intructor_register);
        iRegister = (Button) findViewById(R.id.instrRegisterBtn);
        name = (EditText) findViewById(R.id.eName);
        gym = (EditText) findViewById(R.id.etGym);
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);

        iRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.instrRegisterBtn:

                break;
        }
    }
}

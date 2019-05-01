package com.example.gym4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ClientLogin extends AppCompatActivity implements View.OnClickListener{

    Button cLogin;
    EditText cUsername, cPassword;
    TextView cRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        cUsername = findViewById(R.id.etClientName);
        cPassword = findViewById(R.id.etClientPassword);
        cLogin = findViewById(R.id.clientLoginBtn);
        cRegister = findViewById(R.id.instrRegisterLink);
        cLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.clientLoginBtn:

                break;

            case R.id.clientRegisterLink:
                Intent intent = new Intent(this, ClientRegister.class);
                startActivity(intent);
                break;
        }
    }

}

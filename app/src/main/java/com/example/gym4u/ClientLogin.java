package com.example.gym4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ClientLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);
    }

    public void clientReg(View view) {
        Intent intent = new Intent(this, ClientRegister.class);
        startActivity(intent);
    }
}

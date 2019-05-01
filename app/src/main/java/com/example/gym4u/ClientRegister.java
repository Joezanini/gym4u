package com.example.gym4u;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ClientRegister extends AppCompatActivity implements View.OnClickListener {


    Button cRegister;
    EditText name, gym, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);
        cRegister = findViewById(R.id.clientRegisterBtn);
        name = findViewById(R.id.etClientName);
        gym = findViewById(R.id.etClientGym);
        username = findViewById(R.id.etClientUsername);
        password = findViewById(R.id.etClientPassword);
        cRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clientRegisterBtn:
                String cName = name.getText().toString();
                String cUsername = username.getText().toString();
                String cGym = gym.getText().toString();
                String cPassword = password.getText().toString();

                Client regClientData = new Client(cName, cUsername, cGym, cPassword);

                //logout info @11:49Pt2

                break;
        }
    }
}

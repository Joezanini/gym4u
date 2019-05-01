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
    EditText cUsername, cPassword, cName, cGym;
    TextView cRegister;
    ClientLocalStore clientLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        cName = findViewById(R.id.etClientName);
        cUsername = findViewById(R.id.etClientUsername);
        cGym = findViewById(R.id.etClientGym);
        cPassword = findViewById(R.id.etClientPassword);
        cLogin = findViewById(R.id.clientLoginBtn);
        cRegister = findViewById(R.id.instrRegisterLink);
        clientLocalStore = new ClientLocalStore(this);

        cLogin.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticateClient() == true) {
            displayClientDetails();
        }

    }

    private boolean authenticateClient() {
        return clientLocalStore.getClientLoggedIn();
    }

    private void displayClientDetails() {
        Client client = clientLocalStore.getLoggedClient();
        cUsername.setText(client.username);
        cName.setText(client.name);
        cGym.setText(client.gym);


    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.clientLoginBtn:
                Client client = new Client(null, null, null, null);
                clientLocalStore.storeClientData(client);
                clientLocalStore.setClientLoggedIn(true);
                break;

            case R.id.clientRegisterLink:
                Intent intent = new Intent(this, ClientRegister.class);
                startActivity(intent);
                break;
        }
    }

}

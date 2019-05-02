package com.example.gym4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserLogin extends AppCompatActivity implements View.OnClickListener{

    Button login;
    EditText username, password, name, gym;
    TextView register;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        gym = findViewById(R.id.gym);
        password = findViewById(R.id.password);
        login = findViewById(R.id.userLoginBtn);
        register = findViewById(R.id.userRegisterLink);
        userLocalStore = new UserLocalStore(this);

        login.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticateUser() == true) {
            displayUserDetails();
        }

    }

    private boolean authenticateUser() {
        return userLocalStore.getUserLoggedIn();
    }

    private void displayUserDetails() {
        User user = userLocalStore.getLoggedUser();
        username.setText(user.username);
        name.setText(user.name);
        gym.setText(user.gym);


    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.userLoginBtn:
                User user = new User(null, null, null, null);
                userLocalStore.storeUserData(user);
                userLocalStore.setUserLoggedIn(true);
                break;

            case R.id.userRegisterLink:
                Intent intent = new Intent(this, UserRegister.class);
                startActivity(intent);
                break;
        }
    }

}

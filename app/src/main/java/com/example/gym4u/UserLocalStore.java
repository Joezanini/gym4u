package com.example.gym4u;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {

    public static final String SP_USER = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_USER, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.name);
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.putString("gym", user.gym);
        spEditor.commit();
    }

    public User getLoggedUser() {
        String name = userLocalDatabase.getString("name", "");
        String username = userLocalDatabase.getString("username", "");
        String gym = userLocalDatabase.getString("gym", "");
        String password = userLocalDatabase.getString("password", "");

        User storedUser = new User(name, username, gym, password, false, false);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == true) {
            return true;
        } else {
            return false;
        }
    }

    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }


}

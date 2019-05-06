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
        spEditor.putBoolean("instructor", user.instructor);
        spEditor.putBoolean("client", user.client);
        spEditor.commit();
    }

    public User getLoggedUser() {
        String name = userLocalDatabase.getString("name", "");
        String username = userLocalDatabase.getString("username", "");
        String gym = userLocalDatabase.getString("gym", "");
        String password = userLocalDatabase.getString("password", "");
        boolean instructor = userLocalDatabase.getBoolean("instructor", false);
        boolean client = userLocalDatabase.getBoolean("client", false);
        User storedUser = new User(name, username, gym, password, instructor, client);
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

    //this is for logging out
    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }


}

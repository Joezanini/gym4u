package com.example.gym4u;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    String id, name, gym, password, username;
    boolean instructor, client;

    public User (String username,String password, String name, String gym) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.gym = gym;
        this.password = password;
    }
/*
    public User(String username, String password, String gym) {
        this.username = username;
        this.password = password;
        this.gym = gym;
        this.name = "none";
    }
  */
    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGym() {
        return gym;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isInstructor() {
        return instructor;
    }

    public boolean isClient() {
        return client;
    }
}

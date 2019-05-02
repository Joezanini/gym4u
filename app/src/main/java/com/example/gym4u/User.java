package com.example.gym4u;

public class User {
    String name, gym, password, username;

    public User (String name, String username, String gym, String password) {
        this.name = name;
        this.username = username;
        this.gym = gym;
        this.password = password;
    }

    public User(String username, String password, String gym) {
        this.username = username;
        this.password = password;
        this.gym = gym;
        this.name = "none";
    }
}

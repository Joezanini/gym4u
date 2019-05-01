package com.example.gym4u;

public class Client {
    String name, gym, password, username;

    public Client (String name, String username, String gym, String password) {
        this.name = name;
        this.username = username;
        this.gym = gym;
        this.password = password;
    }

    public Client(String username, String password, String gym) {
        this.username = username;
        this.password = password;
        this.gym = gym;
        this.name = "none";
    }
}

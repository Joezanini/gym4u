package com.example.gym4u;

import android.content.Context;
import android.content.SharedPreferences;

public class ClientLocalStore {

    public static final String SP_CLIENT = "clientDetails";
    SharedPreferences clientLocalDatabase;

    public ClientLocalStore(Context context) {
        clientLocalDatabase = context.getSharedPreferences(SP_CLIENT, 0);
    }

    public void storeClientData(Client client) {
        SharedPreferences.Editor spEditor = clientLocalDatabase.edit();
        spEditor.putString("name", client.name);
        spEditor.putString("username", client.username);
        spEditor.putString("password", client.password);
        spEditor.putString("gym", client.gym);
        spEditor.commit();
    }

    public Client getLoggedClient() {
        String name = clientLocalDatabase.getString("name", "");
        String username = clientLocalDatabase.getString("username", "");
        String gym = clientLocalDatabase.getString("gym", "");
        String password = clientLocalDatabase.getString("password", "");

        Client storedClient = new Client(name, username, gym, password);
        return storedClient;
    }

    public void setClientLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = clientLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public void clearClientData() {
        SharedPreferences.Editor spEditor = clientLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }


}

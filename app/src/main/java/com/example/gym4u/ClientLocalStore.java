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
    }

}

package com.example.gym4u;

import android.content.Context;
import android.content.SharedPreferences;

public class InstructorLocalStore {

    public static final String SP_INSTRUCTOR = "insDetails";
    SharedPreferences instructorLocalDatabase;

    public InstructorLocalStore(Context context) {
        instructorLocalDatabase = context.getSharedPreferences(SP_INSTRUCTOR, 0);
    }

    public void storeInstructorData(Instructor instructor) {
        SharedPreferences.Editor spEditor = instructorLocalDatabase.edit();
        spEditor.putString("name", instructor.name);
        spEditor.putString("username", instructor.username);
        spEditor.putString("password", instructor.password);
        spEditor.putString("gym", instructor.gym);
        spEditor.commit();
    }

    public Instructor getLoggedInstructor() {
        String name = instructorLocalDatabase.getString("name", "");
        String username = instructorLocalDatabase.getString("username", "");
        String gym = instructorLocalDatabase.getString("gym", "");
        String password = instructorLocalDatabase.getString("password", "");

        Instructor storedinstructor = new Instructor(name, username, gym, password);
        return storedinstructor;
    }

    public void setInstructorLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = instructorLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getInstructorLoggedIn() {
        if (instructorLocalDatabase.getBoolean("loggedIn", false) == true) {
            return true;
        } else {
            return false;
        }
    }

    public void clearInstructorData() {
        SharedPreferences.Editor spEditor = instructorLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }


}

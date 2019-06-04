package com.example.gym4u;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.CalendarView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class LocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATE = "com.example.gym4u.UPDATE_LOCATION";
    public static boolean atGym = false;
    public static int startTime,endTime;
    private DatabaseReference dbRef_toDate, dbRef_toDur;
    enum Days{
        Mon,Tue,Wed,Thu,Fri,Sat,Sun;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null){

            final String action = intent.getAction();
            if(ACTION_PROCESS_UPDATE.equals(action)){
                LocationResult result = LocationResult.extractResult(intent);
                if(result != null){
                    Location location = result.getLastLocation();
                    String location_string = new StringBuilder(""+ location.getLatitude())
                            .append("/").append(location.getLongitude()).toString();
                    Log.i("gpsStuff","location is "+ location_string);

                    // this is Dynamic's coordinates
                    double lat = 37.711780;
                    double lon = -121.000549;
                    Location gym = new Location(location);
                    gym.setLatitude(lat);
                    gym.setLongitude(lon);

                    if(atGym == false && location.distanceTo(gym) < 50 ){
                        atTheGym(location,gym);
                    }
                    if(atGym == true && location.distanceTo(gym)>50){
                        endAtGym();
                    }

                }
            }
        }
    }


    public gymTime setUp(String date,int dur){
        gymTime timeGym = new gymTime();

        if (date == "Mon"){
                timeGym.setMon(dur);
        }else if (date == "Tue"){
            timeGym.setTue(dur);
        }else if (date == "Wed"){
            timeGym.setWed(dur);
        }else if (date == "Thu"){
            timeGym.setThu(dur);
        }else if (date == "Fri"){
            timeGym.setFri(dur);
        }else if (date == "Sat"){
            timeGym.setSat(dur);
        }else {
            timeGym.setSun(dur);
        }
        return  timeGym;
    }


    public void insert(final int duration){
        //insert week #
        Log.i("gpsStuff","inserting into Firebase");


        gymTime NEW = setUp(getDate(),duration);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child("GymTimeInfo").child(getWeek()).setValue(NEW);


    }


public String getWeek(){
    Log.i("gpsStuff","getting week");

    Date date = new Date();
    //lower case h = 12 hr time, a = use AM/PM
    String strDateFormat = "w";
    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
    String formattedDate = dateFormat.format(date);
    return formattedDate;
}
    public static String getDate() {
        Date date = new Date();
        Log.i("gpsStuff","getting day of week");

        //lower case h = 12 hr time, a = use AM/PM
        String strDateFormat = "E";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public void atTheGym(Location location, Location gym){
        Log.i("gpsStuff","in locator..");
        if(atGym == false && location.distanceTo(gym) < 50 ) {
            atGym = true;
                Log.i("gpsStuff", "AT THE GYM");
                atGym = true;
                startTime = (int) (System.currentTimeMillis()/1000);
        }
        /*else if(atGym == false && location.distanceTo(gym)>50 ) {
            endTime = (int) (System.currentTimeMillis()/1060);
            Log.i("gpsStuff","start time is: "+startTime);
            int diff = endTime - startTime;
            insert(diff);
            Log.i("gpsStuff","Left the gym ");

            Log.i("gpsStuff","gym Time: "+  diff);
            atGym = true;
        }*/
    }

    public void endAtGym(){
        endTime = (int) (System.currentTimeMillis()/1000);
        Log.i("gpsStuff","start time is: "+startTime);
        int diff = (endTime - startTime)/60;
        atGym = false;
        insert(diff);
        Log.i("gpsStuff","Left the gym ");

        Log.i("gpsStuff","gym Time: "+  diff);

    }



}
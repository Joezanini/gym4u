package com.example.gym4u;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class scheduler extends AppCompatActivity {

    EditText className;
    EditText start;
    EditText end;
    Button schedule;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        className = findViewById(R.id.classNameSchd);
        start = findViewById(R.id.startTimeschd);
        end = findViewById(R.id.endTimeschd);
        schedule = findViewById(R.id.schedBtn);
        day = getIntent().getStringExtra("day");

        schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveSched();
            }
        });
    }

    void saveSched() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("Gyms/Dynamic/dayList/" + day +"/classList").push().getKey();

        Schedule sched = new Schedule();
        sched.setName(className.getText().toString());
        sched.setStart(start.getText().toString());
        sched.setEnd(end.getText().toString());


        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put( key, sched.toFirebaseObject());


        database.getReference("Gyms/Dynamic/dayList/" + day + "/classList").updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    finish();
                }
            }
        });
    }
}

package com.example.gym4u;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class DayDetail extends AppCompatActivity {
    RecycleAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Schedule> schedList;
    String day;
    String uId;
    String uType;
    Button fab;
    Button clr;
    String flag = "Client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);
        fab = findViewById(R.id.dayAddBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(DayDetail.this, scheduler.class);
                newIntent.putExtra("day", day);
                Log.d("day", "onClick: " + day);
                DayDetail.this.startActivity(newIntent);
            }
        });

        clr = findViewById(R.id.clrDayBtn);
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference("Gyms/Dynamic/dayList/" + day + "/classList").removeValue();
                finish();
            }
        });

        schedList = new ArrayList<>();

        recyclerView = findViewById(R.id.dayRecycleView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        adapter = new RecycleAdapter();
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        day = getIntent().getStringExtra("day");
        uId = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = database.getReference("Users/" + uId + "/type");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uType = (String) dataSnapshot.getValue();
                Log.d("user", "onDataChange: " + uType);

                if (uType.equals(flag)) {
                    fab.setVisibility(INVISIBLE);
                    clr.setVisibility(INVISIBLE);
                    Log.d("btn", "onResume: invisible");
                } else {
                    fab.setVisibility(VISIBLE);
                    clr.setVisibility(VISIBLE);
                    Log.d("btn", "onResume: visible");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database.getReference("Gyms/Dynamic/dayList/" + day + "/classList").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        schedList.clear();

                        Log.w("schedule", "getUser:onCancelled " + dataSnapshot.toString());
                        Log.w("schedule", "count = " + String.valueOf(dataSnapshot.getChildrenCount()) + " values " + dataSnapshot.getKey());
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Schedule sc = data.getValue(Schedule.class);
                            schedList.add(sc);
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("schedule", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }


    private class RecycleAdapter extends RecyclerView.Adapter {




        @Override
        public int getItemCount() {
            return schedList.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_schedule_single_item, parent, false);
            SimpleItemViewHolder pvh = new SimpleItemViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SimpleItemViewHolder viewHolder = (SimpleItemViewHolder) holder;
            viewHolder.position = position;
            Schedule schedule = schedList.get(position);
            ((SimpleItemViewHolder) holder).title.setText(schedule.getName());
            ((SimpleItemViewHolder) holder).start.setText(schedule.getStart());
            ((SimpleItemViewHolder) holder).end.setText(schedule.getEnd());
        }

        public final  class SimpleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView title;
            TextView start;
            TextView end;
            public int position;
            public SimpleItemViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                title = itemView.findViewById(R.id.tvClassName);
                start = itemView.findViewById(R.id.tvStartTime);
                end = itemView.findViewById(R.id.tvEndTime);
            }


            @Override
            public void onClick(View view) {

            }
        }
    }

}

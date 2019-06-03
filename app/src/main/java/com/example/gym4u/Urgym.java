package com.example.gym4u;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Urgym extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    public static SharedPreferences sharedPreferences;
    public static String SEL_DAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgym);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Joe is working here
        setupUIViews();
        setupListView();
        //Joe is working here


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Joe is working between these comments vvvv
    //------------------------------------------
    private void setupUIViews(){
        listView = (ListView)findViewById(R.id.lvWeek);
        sharedPreferences = getSharedPreferences("MY_DAY", MODE_PRIVATE);
    }

    private void setupListView(){
        String[] week = getResources().getStringArray(R.array.week);

        WeekAdapter adapter = new WeekAdapter(this, R.layout.activity_urgym_single_item, week);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0: {
                        Intent intent = new Intent(Urgym.this, DayDetail.class);
                        intent.putExtra("day", "Monday");
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Monday").apply();
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(Urgym.this, DayDetail.class);
                        intent.putExtra("day", "Tuesday");
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Tuesday").apply();
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(Urgym.this, DayDetail.class);
                        intent.putExtra("day", "Wednesday");
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Wednesday").apply();
                        break;
                    }
                    case 3: {
                        Intent intent = new Intent(Urgym.this, DayDetail.class);
                        intent.putExtra("day", "Thursday");
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Thursday").apply();
                        break;
                    }
                    case 4: {
                        Intent intent = new Intent(Urgym.this, DayDetail.class);
                        intent.putExtra("day", "Friday");
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Friday").apply();
                        break;
                    }
                    case 5: {
                        Intent intent = new Intent(Urgym.this, DayDetail.class);
                        intent.putExtra("day", "Saturday");
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Saturday").apply();
                        break;
                    }
                    case 6: {
                        Intent intent = new Intent(Urgym.this, DayDetail.class);
                        intent.putExtra("day", "Sunday");
                        startActivity(intent);
                        sharedPreferences.edit().putString(SEL_DAY, "Sunday").apply();
                        break;
                    }
                    default:break;
                }

            }
        });

    }

    public class WeekAdapter extends ArrayAdapter {

        private int resource;
        private LayoutInflater layoutInflater;
        private String[] week;
        private String[] day;

        public WeekAdapter(Context context, int resource, String[] object) {
            super(context, resource, object);
            this.resource = resource;
            this.week = object;
            this.day = new String[7];
            this.day[0] = "M";
            this.day[1] = "T";
            this.day[2] = "W";
            this.day[3] = "T";
            this.day[4] = "F";
            this.day[5] = "S";
            this.day[6] = "S";
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(resource, null);
                holder.tvLogo = (TextView)convertView.findViewById(R.id.tvLetter);
                holder.tvWeek = (TextView)convertView.findViewById(R.id.tvWeek);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            holder.tvLogo.setText(day[position]);
            holder.tvWeek.setText(week[position]);

            return convertView;
        }

        class ViewHolder{
            private TextView tvLogo;
            private TextView tvWeek;
        }
    }

    //-------------------------------------------
    //Joe is working between these comments ^^^^

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Urgym.this, Client_Home.class);
            startActivity(intent);
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(Urgym.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_annoucements) {
            Intent intent = new Intent(Urgym.this, Announcement.class);
            startActivity(intent);
        } else if (id == R.id.nav_wall) {
            Intent intent = new Intent(Urgym.this, Wall.class);
            startActivity(intent);
        } else if (id == R.id.nav_gym) {
            Intent intent = new Intent(Urgym.this, Urgym.class);
            startActivity(intent);
        } else if (id == R.id.nav_heart) {
            Intent intent = new Intent(Urgym.this, Urheart.class);
            startActivity(intent);
        }else if(id == R.id.nav_profile){
            Intent intent = new Intent(Urgym.this, Your_Profile.class);
            startActivity(intent);
        }else if (id == R.id.nav_home){
            Intent intent = new Intent(Urgym.this, Client_Home.class);
            startActivity(intent);}


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

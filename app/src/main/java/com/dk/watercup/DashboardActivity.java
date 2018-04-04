package com.dk.watercup;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        db = new SQLiteHelper(this);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        }

        final FirebaseUser user = auth.getCurrentUser();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        final TextView rankView = findViewById(R.id.rankView);
        final TextView nameView = findViewById(R.id.nameView);
        final DatabaseReference refr = FirebaseDatabase.getInstance().getReference("village").child(user.getUid()).child("name");
        refr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameView.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("village");
        dbr.orderByChild("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int index = Integer.parseInt(dataSnapshot.getChildrenCount()+"");
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    index--;
                    if(ds.getKey().equals(user.getUid())){
                        rankView.setText(rankView.getText().toString() + (index+1));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final ProgressBar daysRem = findViewById(R.id.daysRem);
        final TextView noOfDays = findViewById(R.id.noOfDays);
        final TextView pointView = findViewById(R.id.pointsView);
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("village").child(user.getUid()).child("points");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String points = dataSnapshot.getValue(Integer.class) + "";
                pointView.setText(pointView.getText().toString() + " "+ points);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("final_date");

        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String finalDate = dataSnapshot.getValue().toString();
                try {
                    Date firstDate = new Date();
                    Date secondDate = sdf.parse(finalDate);
                    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
                    Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    daysRem.setProgress(diff.intValue());
                    ObjectAnimator progressAnimator = ObjectAnimator.ofInt(daysRem, "progress", 100, diff.intValue());
                    progressAnimator.setDuration(1500);
                    progressAnimator.setInterpolator(new LinearInterpolator());
                    progressAnimator.start();
                    noOfDays.setText(diff.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final CardView newProject = findViewById(R.id.newProject);
        newProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AddProjectActivity.class));
            }
        });

        final CardView stats = findViewById(R.id.stats);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, StatsActivity.class));
            }
        });

        final CardView guide = findViewById(R.id.help);
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, GuideActivity.class));
            }
        });

        final CardView helpCard = findViewById(R.id.helpCard);
        helpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, HelpActivity.class));
            }
        });

        final CardView leaderBoard = findViewById(R.id.leaderBoard);
        leaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,LeaderboardActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                startActivity(new Intent(DashboardActivity.this, FAQActivity.class));
                return true;
            case R.id.logout:
                auth.signOut();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                db.deleteVillages();
                db.close();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}

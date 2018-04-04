package com.dk.watercup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbref = database.getReference("village");

        final RecyclerView pplview = findViewById(R.id.leaderBoardView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        pplview.setLayoutManager(layoutManager);

        FirebaseRecyclerAdapter<VillageModel, VillageHolder> adapter =
                new FirebaseRecyclerAdapter<VillageModel, VillageHolder>(
                VillageModel.class,
                R.layout.village_item,
                VillageHolder.class,
                dbref.orderByChild("points")
        ) {
            @Override
            protected void populateViewHolder(VillageHolder viewHolder, VillageModel model, int i) {
                viewHolder.setTName(model.getName());
                viewHolder.setTextPhone(R.string.phoneShow+model.getPhone());
                viewHolder.setTextTaluka(R.string.talukaShow+model.getTaluka());
                viewHolder.setTextPoints(String.valueOf(model.getPoints())+" Points");
            }
        };
        pplview.setAdapter(adapter);
    }
}

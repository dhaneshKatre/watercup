package com.dk.watercup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        final DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference("projects");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final RecyclerView projectRecView = findViewById(R.id.projectRecView);
        projectRecView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter<NewProjectModel, ProjectHolder> adapter = new FirebaseRecyclerAdapter<NewProjectModel, ProjectHolder>(
                NewProjectModel.class,
                R.layout.project_row,
                ProjectHolder.class,
                projectRef.child(user.getUid())
        ) {
            @Override
            protected void populateViewHolder(ProjectHolder viewHolder, NewProjectModel model, int position) {
                viewHolder.setTextCost(model.getCost());
                viewHolder.setTextName(model.getName());
                viewHolder.setTextDesc(model.getDesc());
            }
        };
        projectRecView.setAdapter(adapter);

    }
}

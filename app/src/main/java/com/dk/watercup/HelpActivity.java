package com.dk.watercup;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.Help);
        this.setTitle(R.string.Help);

        final TextView coord = findViewById(R.id.tvCoordinator);
        final TextView phone = findViewById(R.id.tvContact);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone.getText().toString(), null));
                startActivity(intent);
            }
        });

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference cordRef = FirebaseDatabase.getInstance().getReference("village").child(user.getUid());
        cordRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                coord.setText(dataSnapshot.getValue(VillageModel.class).getcName());
                phone.setText(dataSnapshot.getValue(VillageModel.class).getcPhone());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

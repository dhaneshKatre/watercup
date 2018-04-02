package com.dk.watercup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AddProjectActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        auth = FirebaseAuth.getInstance();

        final ArrayList<String> projectTypes = new ArrayList<>();
        projectTypes.add("Choose a project");
        projectTypes.add("Well");
        projectTypes.add("Trench");
        projectTypes.add("Hapsa");

        final Spinner projectType = findViewById(R.id.projectType);
        ArrayAdapter<String> forSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, projectTypes);
        forSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectType.setAdapter(forSpinner);
        projectType.setSelection(0);

        final ImageView projectImage = findViewById(R.id.projectImage);
        projectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: camera or gallery intent
            }
        });

        final EditText cost = findViewById(R.id.cost);
        final EditText desc = findViewById(R.id.desc);


    }
}

package com.dk.watercup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FormActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        final SQLiteHelper db = new SQLiteHelper(this);

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(R.string.progress_diaload);

        final ArrayList<String> coord = new ArrayList<>();
        coord.add("Mr. Viraj Khedekar,+917894662606");
        coord.add("Mr. Dhanesh Katre,+917844662656");
        coord.add("Mr. Utkarsh Aher,+917894662659");
        coord.add("Mr. Shubham Relekar,+917894642652");

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){
            startActivity(new Intent(FormActivity.this, LoginActivity.class));
            finish();
        }

        final FirebaseUser user = auth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference villages = database.getReference("village");

        final TextInputEditText vname = findViewById(R.id.vname);
        final TextInputEditText taluka = findViewById(R.id.taluka);
        final TextInputEditText phone = findViewById(R.id.phone);
        final Button done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                Random rn = new Random();
                final VillageModel vm = new VillageModel(vname.getText().toString().trim(), taluka.getText().toString().trim(), phone.getText().toString().trim(), 0, coord.get(rn.nextInt(4)).split(",")[0], coord.get(rn.nextInt(4)).split(",")[1]);
                villages.child(user.getUid()).setValue(vm)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if(task.isSuccessful()){
                                    pd.dismiss();
                                    db.addInfo(vm);
                                    startActivity(new Intent(FormActivity.this, DashboardActivity.class));
                                    finish();
                                }
                                else {
                                    pd.dismiss();
                                    Toast.makeText(FormActivity.this, R.string.check_Nw, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}

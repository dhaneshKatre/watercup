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

public class FormActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        final SQLiteHelper db = new SQLiteHelper(this);

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("On your marks ...");

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
                final VillageModel vm = new VillageModel(vname.getText().toString().trim(), taluka.getText().toString().trim(), phone.getText().toString().trim());
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
                                    Toast.makeText(FormActivity.this, "Check connection!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}

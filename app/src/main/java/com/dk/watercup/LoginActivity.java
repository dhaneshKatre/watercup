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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference villages = database.getReference("village");

        final TextInputEditText loginID = findViewById(R.id.loginId);
        final TextInputEditText loginPass = findViewById(R.id.loginPassword);
        final Button loginButton = findViewById(R.id.loginButton);

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle(    R.string.progress_diaload);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = loginID.getText().toString().trim();
                final String pass = loginPass.getText().toString().trim();
                pd.show();
                auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    villages.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            pd.dismiss();
                                            if(!dataSnapshot.exists()){
                                                startActivity(new Intent(LoginActivity.this, FormActivity.class));
                                                finish();
                                            } else {
                                                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                                finish();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(LoginActivity.this, R.string.check_Nw, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else {
                                    auth.createUserWithEmailAndPassword(email, pass)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(Task<AuthResult> task) {
                                                    if(task.isSuccessful()){
                                                        pd.dismiss();
                                                        startActivity(new Intent(LoginActivity.this, FormActivity.class));
                                                        finish();
                                                    }
                                                    else{
                                                        pd.dismiss();
                                                        Toast.makeText(LoginActivity.this, R.string.check_Nw, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(Exception e) {
                                                    Toast.makeText(LoginActivity.this, R.string.check_Nw, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {

                            }
                        });
            }
        });
    }
}

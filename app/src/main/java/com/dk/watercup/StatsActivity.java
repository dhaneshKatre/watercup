package com.dk.watercup;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class StatsActivity extends AppCompatActivity {

    private FirebaseRecyclerAdapter<NewProjectModel, ProjectHolder> adapter;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        final DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference("projects");
        user = FirebaseAuth.getInstance().getCurrentUser();

        final RecyclerView projectRecView = findViewById(R.id.projectRecView);
        projectRecView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("final_date");

        final ProgressBar daysRem = (ProgressBar) findViewById(R.id.daysRem);
        final TextView noOfDays = findViewById(R.id.noOfDays);
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

        adapter = new FirebaseRecyclerAdapter<NewProjectModel, ProjectHolder>(
                NewProjectModel.class,
                R.layout.project_row,
                ProjectHolder.class,
                projectRef.child(user.getUid())
        ) {
            @Override
            protected void populateViewHolder(ProjectHolder viewHolder, final NewProjectModel model, final int position) {
                viewHolder.setTextCost(model.getCost());
                viewHolder.setTextName(model.getName());
                viewHolder.setTextDesc(model.getDesc());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            showImage(position, model.getName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        projectRecView.setAdapter(adapter);
    }

    public void showImage(final int pos, final String name) throws IOException {
        final ProgressDialog pd = new ProgressDialog(StatsActivity.this);
        pd.setTitle("Please wait ...");
        final Dialog dialog = new Dialog(StatsActivity.this);
        dialog.setContentView(R.layout.image_dialog);
        dialog.setCancelable(true);
        final TextView projectName = dialog.findViewById(R.id.projectName);
        final File localFile = File.createTempFile("images", "jpg");
        pd.show();
        final StorageReference imageRef = FirebaseStorage.getInstance().getReference(user.getUid()).child(adapter.getRef(pos).getKey());
        imageRef.getFile(localFile)
                .addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(Task<FileDownloadTask.TaskSnapshot> task) {
                        pd.dismiss();
                        if(task.isSuccessful()){
                            projectName.setText(name);
                            Bitmap image = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            projectName.setCompoundDrawables(null, null, null, new BitmapDrawable(getResources(), image));
                            dialog.show();
                        } else {
                            Toast.makeText(StatsActivity.this, "Network!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

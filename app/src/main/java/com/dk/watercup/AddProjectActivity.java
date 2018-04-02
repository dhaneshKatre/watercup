package com.dk.watercup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddProjectActivity extends AppCompatActivity {

    private ImageView iv;
    private Uri finalImage;
    private TextView msg;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    Toast.makeText(this, "DAta null!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bitmap image = (Bitmap) data.getExtras().get("data");
                iv.setImageBitmap(image);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), image, "Title", null);
                finalImage = Uri.parse(path);
                msg.setVisibility(View.GONE);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "CANCELED ", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference projects = database.getReference("projects");
        final FirebaseUser user = auth.getCurrentUser();
        final StorageReference villageRef = FirebaseStorage.getInstance().getReference();

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

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("On your marks ...");

        msg = findViewById(R.id.msg);
        iv = findViewById(R.id.projectImage);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
        });

        final EditText cost = findViewById(R.id.cost);
        final EditText desc = findViewById(R.id.desc);


        final AppCompatButton doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                if(projectType.getSelectedItemPosition() == 0){
                    Toast.makeText(AddProjectActivity.this, "Specify project type!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String projectCost = cost.getText().toString().trim();
                String description = desc.getText().toString().trim();
                if(TextUtils.isEmpty(projectCost) || TextUtils.isEmpty(description)){
                    Toast.makeText(AddProjectActivity.this, "Enter proper values!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(iv.getDrawable() == null){
                    Toast.makeText(AddProjectActivity.this, "Choose image!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String project = projectType.getSelectedItem().toString();
                final NewProjectModel npm = new NewProjectModel(project, projectCost, description);
                final String newID = projects.child(user.getUid()).push().getKey();
                villageRef.child(user.getUid()).child(newID).putFile(finalImage)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                pd.dismiss();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                projects.child(user.getUid()).child(newID).setValue(npm)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(AddProjectActivity.this, "Text Uploaded!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AddProjectActivity.this, "Check Network!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddProjectActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}

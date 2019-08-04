package com.example.mycredibleinfo;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class updateEducationalActivity extends AppCompatActivity {
    DatabaseReference rootRef, demoRef;
    EditText organisation, degree, location;
    Button update;
    ImageView certificate;
    ProgressBar mProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_educational);

        organisation = findViewById(R.id.organisation);
        degree = findViewById(R.id.degree);
        location = findViewById(R.id.location);
        update = findViewById(R.id.educationUpdate);
        mProgressBar = findViewById(R.id.progress_bar);
        certificate = findViewById(R.id.certificate);

        certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("profile").child(login.newEmail).child("educationalDetail");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOrganisation = organisation.getText().toString();
                String strDegree = degree.getText().toString();
                String strLocation = location.getText().toString();
                demoRef.child("organisation").setValue(strOrganisation);
                demoRef.child("degree").setValue(strDegree);
                demoRef.child("location").setValue(strLocation);
                mStorageRef = FirebaseStorage.getInstance().getReference("certificate/").child(login.newEmail);
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(updateEducationalActivity.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }
            }
        });
    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(certificate);
        }
    }

    private String getFilExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(){
        if(certificate != null){
            StorageReference fileReference = mStorageRef.child(login.newEmail + ".jpg");
            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    },500);

                    Toast.makeText(updateEducationalActivity.this, "Upload Successfull",Toast.LENGTH_LONG).show();
                    openUpdateActivity();;

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(updateEducationalActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            });
        }
        else {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }
    public void openUpdateActivity(){
        Intent intent = new Intent(this, details.class);
        startActivity(intent);
    }
}

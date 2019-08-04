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

public class personalDetail extends AppCompatActivity {
    DatabaseReference rootRef, demoRef;
    EditText name, email, mobile, location, link, addSkill;
    Button save;
    ImageView imageView;
    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        location = findViewById(R.id.location);
        link = findViewById(R.id.links);
        addSkill = findViewById(R.id.skill);
        save = findViewById(R.id.save);
        imageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    name.setHint("");
                else{
                    name.setHint("Name");
                }
            }
        });
        /*email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    email.setHint("");
                else{
                    email.setHint("Email");

                }
            }
        });*/
        email.setText(signup.strEmail);
        email.setEnabled(false);

        mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    mobile.setHint("");
                else{
                    mobile.setHint("Mobile");
                }
            }
        });
        location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    location.setHint("");
                else{
                    location.setHint("Location");

                }
            }
        });
        link.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    link.setHint("");
                else{
                    link.setHint("Links");

                }
            }
        });
        addSkill.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    addSkill.setHint("");
                else{
                    addSkill.setHint("Add Skill");

                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef =rootRef.child("profile").child(signup.strNewEmail).child("personalDetail");
        if(imageView != null) {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strName = name.getText().toString();
                    //String strEmail = email.getText().toString();
                    String strMobile = mobile.getText().toString();
                    String strLocation = location.getText().toString();
                    String strLink = link.getText().toString();
                    String strAddSkill = addSkill.getText().toString();
                    demoRef.child("name").setValue(strName);
                    //demoRef.child("email").setValue(strEmail);
                    demoRef.child("mobile").setValue(strMobile);
                    demoRef.child("location").setValue(strLocation);
                    demoRef.child("Link").setValue(strLink);
                    demoRef.child("addSkill").setValue(strAddSkill);
                    mStorageRef = FirebaseStorage.getInstance().getReference("profileImage/").child(signup.strNewEmail);
                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(personalDetail.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile();
                    }

                }
            });
        }
        else{
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();

        }

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

            Picasso.with(this).load(mImageUri).into(imageView);
        }
    }

    private String getFilExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(){
        if(imageView != null){
            StorageReference fileReference = mStorageRef.child(signup.strNewEmail + ".jpg");
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

                    Toast.makeText(personalDetail.this, "Upload Successfull",Toast.LENGTH_LONG).show();
                    openSaveActivity();;

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(personalDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
    //--------------------------------------------------------------------------------------------------

    public void openSaveActivity(){
        Intent intent = new Intent(this, professionalDetail.class);
        startActivity(intent);
    }
}

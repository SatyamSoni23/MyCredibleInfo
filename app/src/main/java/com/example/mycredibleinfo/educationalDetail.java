package com.example.mycredibleinfo;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class educationalDetail extends AppCompatActivity {
    DatabaseReference rootRef, demoRef;
    Spinner spinnerStartYear, spinnerEndYear;
    EditText organisation, degree, location;
    ImageView certificate;
    Button save;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    String strStartYear, strEndYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_detail);

        organisation = findViewById(R.id.organisation);
        degree = findViewById(R.id.degree);
        location = findViewById(R.id.location);
        certificate = findViewById(R.id.certificate);
        save = findViewById(R.id.save);
        spinnerStartYear = findViewById(R.id.spinnerStartYear);
        spinnerEndYear = findViewById(R.id.spinnerEndYear);
        mProgressBar = findViewById(R.id.progress_bar);


        organisation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    organisation.setHint("");
                else{
                    organisation.setHint("Organisation");

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
        degree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    degree.setHint("");
                else{
                    degree.setHint("Degree");

                }
            }
        });

        final List<String> list = new ArrayList<String>();
        final List<String> list1 = new ArrayList<String>();

        list.add("Select Year");
        for(int i=2010; i<=2019; i++){
            list.add(Integer.toString(i));
        }

        list1.add("Select Year");
        for(int i=2010; i<=2025; i++){
            list1.add(Integer.toString(i));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStartYear.setAdapter(arrayAdapter);
        spinnerStartYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                spinnerStartYear.setSelection(i);
                strStartYear = list.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEndYear.setAdapter(arrayAdapter1);
        spinnerEndYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                spinnerEndYear.setSelection(i);
                strEndYear = list1.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef =rootRef.child("profile").child(signup.strNewEmail).child("educationalDetail");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOrganisation = organisation.getText().toString();
                String strDegree = degree.getText().toString();
                String strLocation = location.getText().toString();
                demoRef.child("organisation").setValue(strOrganisation);
                demoRef.child("degree").setValue(strDegree);
                demoRef.child("location").setValue(strLocation);
                demoRef.child("startYear").setValue(strStartYear);
                demoRef.child("endYear").setValue(strEndYear);
                mStorageRef = FirebaseStorage.getInstance().getReference("certificate/").child(signup.strNewEmail);
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(educationalDetail.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(educationalDetail.this, "Upload Successfull",Toast.LENGTH_LONG).show();
                    openSaveActivity();;

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(educationalDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, successSignup.class);
        startActivity(intent);
    }
}

package com.example.mycredibleinfo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class certificate extends AppCompatActivity {
    StorageReference storageRef, dataRef;
    ImageView certificate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);

        certificate = findViewById(R.id.certificate);

        storageRef = FirebaseStorage.getInstance().getReference().child("certificate").child(login.newEmail);
        dataRef = storageRef.child(login.newEmail + ".jpg");
        dataRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(certificate.this).load(uri).into(certificate);
            }
        });
    }
}

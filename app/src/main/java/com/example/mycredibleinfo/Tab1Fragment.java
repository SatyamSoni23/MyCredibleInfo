package com.example.mycredibleinfo;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Tab1Fragment extends Fragment {
    EditText organisation, degree, location;
    ImageView certificate;
    DatabaseReference rootRef, demoRef;
    StorageReference storageRef, dataRef;
    Button educatioanlUpdate;

    public static String strOrganisation, strDegree, strLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_one, container, false);
        organisation = view.findViewById(R.id.organisation);
        degree = view.findViewById(R.id.degree);
        location = view.findViewById(R.id.location);
        certificate = view.findViewById(R.id.certificate);
        educatioanlUpdate = view.findViewById(R.id.educationUpdate);

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("profile").child(login.newEmail).child("educationalDetail");
        demoRef.child("organisation").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strOrganisation = dataSnapshot.getValue().toString();
                organisation.setText(strOrganisation);
                organisation.setEnabled(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        demoRef.child("degree").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strDegree = dataSnapshot.getValue().toString();
                degree.setText(strDegree);
                degree.setEnabled(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        demoRef.child("location").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strLocation = dataSnapshot.getValue().toString();
                location.setText(strLocation);
                location.setEnabled(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        storageRef = FirebaseStorage.getInstance().getReference().child("certificate").child(login.newEmail);
        dataRef = storageRef.child(login.newEmail + ".jpg");
        dataRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso pic = Picasso.with(getActivity().getApplicationContext());
                pic.load(uri).into(certificate);
            }
        });
        certificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCertificateActivity();
            }
        });
        educatioanlUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEducatioanlUpdateActivity();
            }
        });

        return view;
    }
    public void openEducatioanlUpdateActivity(){
        Intent intent = new Intent(getActivity(), updateEducationalActivity.class);
        startActivity(intent);
    }
    public void openCertificateActivity(){
        Intent intent = new Intent(getActivity(), certificate.class);
        startActivity(intent);
    }
}

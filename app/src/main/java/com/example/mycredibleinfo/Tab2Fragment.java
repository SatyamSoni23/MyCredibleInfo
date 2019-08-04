package com.example.mycredibleinfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tab2Fragment extends Fragment {
    public static String strOrganisation, strDesignation, strStartMonth, strStartYear, strEndMonth, strEndYear;
    EditText organisation, designation, startDate, endDate;
    DatabaseReference rootRef, demoRef;
    Button updateProfessionalDetail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        organisation = view.findViewById(R.id.organisation);
        designation = view.findViewById(R.id.designation);
        startDate = view.findViewById(R.id.startDate);
        endDate = view.findViewById(R.id.endDate);
        updateProfessionalDetail = view.findViewById(R.id.professionalUpdate);

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("profile").child(login.newEmail).child("professionalDetail");
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
        demoRef.child("designation").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strDesignation = dataSnapshot.getValue().toString();
                designation.setText(strDesignation);
                designation.setEnabled(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        demoRef.child("startMonth").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strStartMonth = dataSnapshot.getValue().toString();
                startDate.setText(strStartMonth);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        demoRef.child("startYear").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strStartYear = dataSnapshot.getValue().toString();
                startDate.append(" - " + strStartYear);
                startDate.setEnabled(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        demoRef.child("endMonth").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strEndMonth = dataSnapshot.getValue().toString();
                endDate.setText(strEndMonth);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        demoRef.child("endYear").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strEndYear = dataSnapshot.getValue().toString();
                endDate.append(" - " + strEndYear);
                endDate.setEnabled(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateProfessionalDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfessionalActivity();
            }
        });
        return view;
    }
    public void openProfessionalActivity(){
        Intent intent = new Intent(getActivity(), updateProfessionalDetail.class);
        startActivity(intent);
    }
}

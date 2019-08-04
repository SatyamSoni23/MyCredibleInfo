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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tab3Fragment extends Fragment {
    EditText name, email, mobile, location, links, skills;
    Button updatepersonalDetail;
    DatabaseReference rootRef, demoRef;
    public static String strName, strEmail, strMpbile, strLinks, strSkills, strLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        mobile = view.findViewById(R.id.mobile);
        location = view.findViewById(R.id.location);
        links = view.findViewById(R.id.links);
        skills = view.findViewById(R.id.skills);
        updatepersonalDetail = view.findViewById(R.id.personalUpdate);

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("profile").child(login.newEmail).child("personalDetail");
        demoRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strName = dataSnapshot.getValue().toString();
                name.setText(strName);
                name.setEnabled(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        email.setText(login.strEmail);
        email.setEnabled(false);
        /*rootRef.child("profile").child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                strEmail = dataSnapshot.getValue().toString();
                email.setText(strEmail);
                email.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        demoRef.child("mobile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strMpbile = dataSnapshot.getValue().toString();
                mobile.setText(strMpbile);
                mobile.setEnabled(false);
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
        demoRef.child("Link").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strLinks = dataSnapshot.getValue().toString();
                links.setText(strLinks);
                links.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        demoRef.child("addSkill").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                strSkills = dataSnapshot.getValue().toString();
                skills.setText(strSkills);
                skills.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        updatepersonalDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPersonalActivity();
            }
        });
        return view;
    }
    public void openPersonalActivity(){
        Intent intent = new Intent(getActivity(), updatePersonalDetail.class);
        startActivity(intent);
    }
}

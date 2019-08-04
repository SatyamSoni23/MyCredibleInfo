package com.example.mycredibleinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updatePersonalDetail extends AppCompatActivity {
    EditText name, email, mobile, location, links, skills;
    Button update;
    DatabaseReference rootRef, demoRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_personal_detail);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        location = findViewById(R.id.location);
        links = findViewById(R.id.links);
        skills = findViewById(R.id.skills);
        update = findViewById(R.id.personalUpdate);

        email.setText(login.strEmail);
        email.setEnabled(false);
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("profile").child(login.newEmail).child("personalDetail");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = name.getText().toString();
                String strMobile = mobile.getText().toString();
                String strLocation = location.getText().toString();
                String strLink = links.getText().toString();
                String strAddSkill = skills.getText().toString();
                demoRef.child("name").setValue(strName);
                demoRef.child("mobile").setValue(strMobile);
                demoRef.child("location").setValue(strLocation);
                demoRef.child("Link").setValue(strLink);
                demoRef.child("addSkill").setValue(strAddSkill);
                openUpdateActivity();
            }
        });
    }
    public void openUpdateActivity(){
        Intent intent = new Intent(this, details.class);
        startActivity(intent);
    }
}

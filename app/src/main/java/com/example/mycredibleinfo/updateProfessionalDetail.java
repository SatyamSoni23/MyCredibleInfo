package com.example.mycredibleinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class updateProfessionalDetail extends AppCompatActivity {
    DatabaseReference rootRef, demoRef;
    String strOrganisation, strDesignation, strStartDate, strEndDate;
    EditText organisation, designation, startDate, endDate;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_professional_detail);

        organisation = findViewById(R.id.organisation);
        designation = findViewById(R.id.designation);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        update = findViewById(R.id.professionalUpdate);

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("profile").child(login.newEmail).child("professionalDetail");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strOrganisation = organisation.getText().toString();
                strDesignation = designation.getText().toString();
                strStartDate = startDate.getText().toString();
                strEndDate = endDate.getText().toString();
                demoRef.child("organisation").setValue(strOrganisation);
                demoRef.child("designation").setValue(strDesignation);
                demoRef.child("startYear").setValue(strStartDate);
                demoRef.child("endYear").setValue(strEndDate);
                openUpdateActivity();
            }
        });
    }
    public void openUpdateActivity(){
        Intent intent = new Intent(this, details.class);
        startActivity(intent);
    }
}

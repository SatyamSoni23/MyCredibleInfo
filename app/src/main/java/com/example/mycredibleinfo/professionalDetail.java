package com.example.mycredibleinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class professionalDetail extends AppCompatActivity {
    DatabaseReference rootRef, demoRef;

    EditText organisation, designation;
    CheckBox checkBox;
    Button save;
    LinearLayout endYear;
    Spinner spinnerStartMonth, spinnerEndMonth, spinnerStartYear, spinnerEndYear;
    String strStartMonth, strEndMonth, strStartYear, strEndYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_detail);
        organisation = findViewById(R.id.organisation);
        designation = findViewById(R.id.designation);
        checkBox = findViewById(R.id.checkbox);
        save = findViewById(R.id.save);
        spinnerStartMonth = findViewById(R.id.spinnerStartMonth);
        spinnerEndMonth = findViewById(R.id.spinnerEndMonth);
        spinnerStartYear = findViewById(R.id.spinnerStartYear);
        spinnerEndYear = findViewById(R.id.spinnerEndYear);
        endYear = findViewById(R.id.endyear);

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
        designation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    designation.setHint("");
                else{
                    designation.setHint("Designation");

                }
            }
        });

        final List<String> list = new ArrayList<String>();
        final List<String> list2 = new ArrayList<String>();
        final List<String> list1 = new ArrayList<String>();
        final List<String> list3 = new ArrayList<String>();

        list.add("Select month");
        list.add("Jan");
        list.add("Feb");
        list.add("Mar");
        list.add("Apr");
        list.add("May");
        list.add("Jun");
        list.add("Jul");
        list.add("Aug");
        list.add("Sep");
        list.add("Oct");
        list.add("Nov");
        list.add("Dec");

        list2.add("Select Year");
        for(int i=2010; i<=2019; i++){
            list2.add(Integer.toString(i));
        }

        list1.add("Select month");
        list1.add("Currently working");
        list1.add("Jan");
        list1.add("Feb");
        list1.add("Mar");
        list1.add("Apr");
        list1.add("May");
        list1.add("Jun");
        list1.add("Jul");
        list1.add("Aug");
        list1.add("Sep");
        list1.add("Oct");
        list1.add("Nov");
        list1.add("Dec");

        list3.add("Select Year");
        list3.add("Currently working");
        for(int i=2010; i<=2019; i++){
            list3.add(Integer.toString(i));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStartMonth.setAdapter(arrayAdapter);
        spinnerStartMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    spinnerStartMonth.setSelection(i);
                    strStartMonth = list.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list2);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStartYear.setAdapter(arrayAdapter2);
        spinnerStartYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                spinnerStartYear.setSelection(i);
                strStartYear = list2.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEndMonth.setAdapter(arrayAdapter1);
        spinnerEndMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                spinnerEndMonth.setSelection(i);
                strEndMonth = list1.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerEndMonth.setSelection(2);
            }
        });

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list3);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEndYear.setAdapter(arrayAdapter3);
        spinnerEndYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                spinnerEndYear.setSelection(i);
                strEndYear = list3.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerStartYear.setSelection(2);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked()) {
                    spinnerEndMonth.setSelection(1);
                    spinnerEndYear.setSelection(1);
                    spinnerEndMonth.setEnabled(false);
                    spinnerEndYear.setEnabled(false);

                }
                else {
                    spinnerEndMonth.setEnabled(true);
                    spinnerEndYear.setEnabled(true);
                }
            }
        });
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef =rootRef.child("profile").child(signup.strNewEmail).child("professionalDetail");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOrganisation = organisation.getText().toString();
                String strDesignation = designation.getText().toString();

                demoRef.child("organisation").setValue(strOrganisation);
                demoRef.child("designation").setValue(strDesignation);
                demoRef.child("startMonth").setValue(strStartMonth);
                demoRef.child("startYear").setValue(strStartYear);
                demoRef.child("endMonth").setValue(strEndMonth);
                demoRef.child("endYear").setValue(strEndYear);
                openSaveActivity();
            }
        });
    }
    public void openSaveActivity(){
        Intent intent = new Intent(this, educationalDetail.class);
        startActivity(intent);
    }
}

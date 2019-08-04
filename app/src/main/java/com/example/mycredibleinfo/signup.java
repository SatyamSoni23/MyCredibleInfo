package com.example.mycredibleinfo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class signup extends AppCompatActivity {
    DatabaseReference rootRef, demoRef,demoRef1,demoRef2;
    EditText email, password, repassword;
    Button continues;
    public static String strEmail, strPassword, strRePassword, strNewEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        continues = findViewById(R.id.continues);

        email.setTypeface(email.getTypeface(), Typeface.BOLD_ITALIC);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    email.setHint("");
                else{
                    email.setHint("Enter Email");

                }
            }
        });
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setTypeface(password.getTypeface(), Typeface.BOLD_ITALIC);
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    password.setHint("");
                else{
                    password.setHint("Enter Password");

                }
            }
        });
        repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        repassword.setTypeface(repassword.getTypeface(), Typeface.BOLD_ITALIC);
        repassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    repassword.setHint("");
                else{
                    repassword.setHint("Re-Enter Password");

                }
            }
        });

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("profile");
        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = email.getText().toString();
                strPassword = password.getText().toString();
                strRePassword = repassword.getText().toString();
                strNewEmail = strEmail.replaceAll("[@.]","");
                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(strNewEmail).exists()){
                            Toast.makeText(signup.this, "Email already registered",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            demoRef1 = demoRef.child(strNewEmail);
                            if(strPassword.equals(strRePassword)){
                                demoRef1 = demoRef.child(strNewEmail);
                                demoRef1.child("email").setValue(strEmail);
                                demoRef1.child("password").setValue(strPassword);
                                startContinueActivity();
                            }
                            else{
                                Toast.makeText(signup.this, "Password does not match",Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        openErrorActivity();
                    }
                });
            }
        });
    }
    public void startContinueActivity(){
        Intent intent = new Intent(this, personalDetail.class);
        startActivity(intent);
    }
    public void openErrorActivity(){
        Intent intent = new Intent(this, errorActivity.class);
        startActivity(intent);
    }
}

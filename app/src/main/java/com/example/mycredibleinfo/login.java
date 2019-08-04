package com.example.mycredibleinfo;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    EditText email,password;
    Button login;
    DatabaseReference rootRef, demoRef,demoRef1;
    public static String firePassword, strEmail,newEmail, strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        email.setTypeface(email.getTypeface(), Typeface.BOLD_ITALIC);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    email.setHint("");
                }
                else{
                    email.setHint("Email");
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
                    password.setHint("Password");

                }
            }
        });

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("profile");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = email.getText().toString();
                strPassword = password.getText().toString();
                newEmail = strEmail.replaceAll("[@.]","");
                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(newEmail).exists())
                        {
                            demoRef1 = demoRef.child(newEmail).child("password");
                            demoRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    firePassword =dataSnapshot.getValue().toString();
                                    if(firePassword.equals(strPassword)){
                                        openDetailActivity();
                                    }
                                    else {
                                        openWrongActivity();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    openErrorActivity();
                                }
                            });
                        }
                        else
                        {
                            openWrongActivity();
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
    public void openDetailActivity(){
        Intent intent = new Intent(this, details.class);
        startActivity(intent);
    }
    public void openWrongActivity(){
        Intent intent = new Intent(this, wrgActivity.class);
        startActivity(intent);
    }
    public void openErrorActivity(){
        Intent intent = new Intent(this, errorActivity.class);
        startActivity(intent);
    }
}

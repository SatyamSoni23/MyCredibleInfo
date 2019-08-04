package com.example.mycredibleinfo;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class updatePassword extends AppCompatActivity {
    DatabaseReference rootRef, demoRef, demoRef1;

    EditText password, rePassword;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);
        update = findViewById(R.id.passwordUpdate);

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
        rePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        rePassword.setTypeface(rePassword.getTypeface(), Typeface.BOLD_ITALIC);
        rePassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    rePassword.setHint("");
                else{
                    rePassword.setHint("Re-Enter Password");

                }
            }
        });

        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child("profile").child(login.newEmail);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPassword = password.getText().toString();
                String strRePassword = rePassword.getText().toString();
                if(strPassword.equals(strRePassword)){
                    demoRef.child("password").setValue(strPassword);
                    openUpdateActivity();;
                }
                else{
                    Toast.makeText(updatePassword.this, "Password does not match",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    public void openUpdateActivity(){
        Intent intent = new Intent(this, details.class);
        startActivity(intent);
    }
}

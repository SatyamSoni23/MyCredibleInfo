package com.example.mycredibleinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class wrgActivity extends AppCompatActivity {
    Button login,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrg);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openloginActivity();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensignupActivity();
            }
        });
    }
    public void openloginActivity(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
    public void opensignupActivity(){
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }
}

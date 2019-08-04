package com.example.mycredibleinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class tryAgainActivity extends AppCompatActivity {
    Button signup,home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_again);
        signup = findViewById(R.id.signup);
        home = findViewById(R.id.login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensignupActivity();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhomeActivity();
            }
        });
    }
    public void opensignupActivity(){
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }
    public void openhomeActivity(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}

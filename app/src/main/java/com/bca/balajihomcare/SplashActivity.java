package com.bca.balajihomcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity  extends AppCompatActivity {


    Button button_login;
    Button button_register;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        button_login=(Button)findViewById(R.id.blogin);
        button_register=(Button)findViewById(R.id.breg);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SplashActivity.this,loginActivity.class);
                startActivity(i);
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i=new Intent(SplashActivity.this,registerActivity.class);
                startActivity(i);
            }
        });

    }
}

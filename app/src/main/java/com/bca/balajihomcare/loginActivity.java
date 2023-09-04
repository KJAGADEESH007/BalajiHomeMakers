package com.bca.balajihomcare;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import  android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class loginActivity extends AppCompatActivity {
    TextView edit_email;
    TextView edit_password;
    EditText txtemail;
    EditText txtpass;
    Button button_reset;
    Button button_login;
    Button button_register;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        txtemail=(EditText)findViewById(R.id.txtemail);
        txtpass=(EditText)findViewById(R.id.txtpass);
        button_login=(Button)findViewById(R.id.button_login);
        button_register=(Button)findViewById(R.id.button_register);
        button_reset=(Button)findViewById(R.id.button_reset);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  email=txtemail.getText().toString();
                String password=txtpass.getText().toString();
                if(email.equals("") || password.equals(""))
                {
                    Toast.makeText(loginActivity.this,"Please Provide Email and Password to Login",Toast.LENGTH_LONG).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast t = Toast.makeText(loginActivity.this, " Valid User", Toast.LENGTH_LONG);
                                        t.show();
                                        Intent i = new Intent(loginActivity.this, homescreen.class);
                                        startActivity(i);
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(loginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(loginActivity.this,registerActivity.class);
                startActivity(i);
            }
        });
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(loginActivity.this,ForgetpasswordActivity.class);
                startActivity(i);
            }
        });

    }
}
package com.bca.balajihomcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetpasswordActivity extends AppCompatActivity {
    private static final String TAG = "FirebaseEmailPassword";
    EditText textmail;
    Button btnreset;
    FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        fbAuth = FirebaseAuth.getInstance();

        btnreset = (Button) findViewById(R.id.btnreset);
        textmail = (EditText) findViewById(R.id.textmail);
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textmail.getText().toString();
                if (email.equals("")) {
                    Toast t = Toast.makeText(ForgetpasswordActivity.this, "enter details", Toast.LENGTH_LONG);
                    t.show();

                } else {
                    fbAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast t = Toast.makeText(ForgetpasswordActivity.this, "Email sent with password rest link", Toast.LENGTH_LONG);
                                t.show();
                                Intent i = new Intent(ForgetpasswordActivity.this, SplashActivity.class);
                                startActivity(i);
                            } else {
                                Toast t = Toast.makeText(ForgetpasswordActivity.this, "invalid mail", Toast.LENGTH_LONG);
                                t.show();
                            }
                        }






                    });
                }


            }
        });
    }
}

package com.bca.balajihomcare;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.bca.balajihomcare.utils.API;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.app.ProgressDialog;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class registerActivity extends AppCompatActivity {
EditText txtname;
EditText txtenteremail;
EditText edit_password,txtaddress,txtcity,txtmob;
Button button_register;
    String valid_mob_number="",valid_email="",valid_fname="",valid_mname="",valid_lname="",valid_pwd="",valid_cpwd="";
    private FirebaseAuth mAuth;
    String pms="";
    public Map<String, String> params = new Hashtable<String, String>();
    public Map<String, String>  headers = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        txtname=(EditText)findViewById(R.id.txtname);
        txtenteremail=(EditText)findViewById(R.id.txtenteremail);
        edit_password=(EditText)findViewById(R.id.edit_password);
        button_register=(Button)findViewById(R.id.button_register);
        txtaddress=(EditText)findViewById(R.id.txtenteraddress);
        txtcity=(EditText)findViewById(R.id.txtentercity);
        txtmob=(EditText)findViewById(R.id.txtentermob);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtname.getText().toString();
                String uemail = txtenteremail.getText().toString();
                String password = edit_password.getText().toString();
                String city = txtcity.getText().toString();
                String address = txtaddress.getText().toString();
                String mob = txtmob.getText().toString();
                Is_Valid_Fname(txtname);
                Is_Valid_Email(txtenteremail);
                Is_Valid_Mob_Number(txtmob);
                Is_Valid_Pwd(edit_password);


                if(valid_mob_number!=null && valid_email!=null  && valid_fname!=null && valid_pwd!=null  )
                {

                    mAuth.createUserWithEmailAndPassword(uemail, password)
                            .addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        pms+="?customer_name="+name;
                                        pms+="&email="+uemail;
                                        pms+="&mob="+mob;
                                        pms+="&addr="+address;
                                        pms+="&city="+city;

                                        JSONObject obj=new JSONObject(params);
                                        //Log.e("params ", obj.toString());

                                        regUser(obj.toString());
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("reerror", task.getException());
                                        Toast.makeText(registerActivity.this, task.getException().getLocalizedMessage().toString(),
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }
            }
        });

    }

    public void Is_Valid_Pwd(EditText edt) {

        String pwd=edt.getText().toString();

        if(pwd.length()>=8)
        {
            Pattern pattern;
            Matcher matcher;
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(edt.getText());
            if(matcher.matches()){
                valid_pwd = edt.getText().toString();
            }
            else{
                edt.setError("Strong Password Should be there minimum of 8 characters");
                edt.requestFocus();
                valid_pwd = null;
            }



        }
        else
        {
            edt.setError("Password Should contain minimum of 8 characters");
            edt.requestFocus();
            valid_pwd = null;
        }



    } // END OF Edittext validation



    public void Is_Valid_Fname(EditText edt) {

        String fname=edt.getText().toString();
        String regexStr = "^[a-zA-Z]+$";
        if(fname.matches(regexStr))
        {
            valid_fname = edt.getText().toString();


        }
        else
        {
            edt.setError("Enter Letters Only");
            edt.requestFocus();
            valid_fname = null;
        }



    } // END OF Edittext validation



    public void Is_Valid_Mob_Number(EditText edt) {

        String mob=edt.getText().toString();
        String regexStr = "^[0-9]{10}$";
        if(mob.matches(regexStr))
        {
            valid_mob_number = edt.getText().toString();;


        }
        else
        {
            edt.setError("Enter 10 digit mobile number");
            edt.requestFocus();
            valid_mob_number = null;
        }



    } // END OF Edittext validation

    public void Is_Valid_Email(EditText edt) {
        if (edt.getText().toString() == null) {
            edt.requestFocus();
            edt.setError("Invalid Email Address");
            valid_email = null;
        } else if (isEmailValid(edt.getText().toString()) == false) {
            edt.setError("Invalid Email Address");
            edt.requestFocus();
            valid_email = null;
        } else {
            valid_email = edt.getText().toString();
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void regUser (String bodyParams) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Registering..");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;

        String url = API.REG_URL;

        //Log.e("URL", url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+pms, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                if (result != null) {
                    pd.dismiss();
                    try {
                        Log.e("Result", result);
                        JSONObject object = new JSONObject(result);
                        int status=object.getInt("success");
                        if(status==1){
                            Toast t= Toast.makeText(registerActivity.this,"Registration is Successfull",Toast.LENGTH_LONG);
                            t.show();
                            Intent i=new Intent(registerActivity.this, loginActivity.class);
                            startActivity(i);
                        }else{
                            Toast t= Toast.makeText(registerActivity.this,"Registration is UnSuccessfull",Toast.LENGTH_LONG);
                            t.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    pd.dismiss();
                    // Toast.makeText(getApplicationContext(), R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        pd.dismiss();
                        Log.e("Volley Error2", volleyError.toString());
                        NetworkResponse response = volleyError.networkResponse;
                        if (volleyError instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);

                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }
                        Toast.makeText(registerActivity.this, "Registration failed", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Accept", "application/json");

                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }


        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(registerActivity.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}

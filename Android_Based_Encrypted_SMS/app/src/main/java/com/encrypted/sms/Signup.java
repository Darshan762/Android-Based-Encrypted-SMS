package com.encrypted.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    EditText name,username,password,mobile;
    Button register;
    TextView goto_login;
    private static final String SIGNUP="http://wizzie.tech/EncryptedSMS/signup.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        goto_login=findViewById(R.id.backto_login);
        register=findViewById(R.id.register);

        name=findViewById(R.id.name);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        mobile=findViewById(R.id.mobile);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().isEmpty()) {
                    Snackbar.make(Signup.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter First Name", Snackbar.LENGTH_SHORT).show();
                }
                else if(username.getText().toString().trim().isEmpty()){
                    Snackbar.make(Signup.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter User Name", Snackbar.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().trim().isEmpty()){
                    Snackbar.make(Signup.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Password", Snackbar.LENGTH_SHORT).show();
                }
                else if(mobile.getText().toString().trim().isEmpty()){
                    Snackbar.make(Signup.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Mobile", Snackbar.LENGTH_SHORT).show();
                }
                else if(mobile.getText().toString().length()>10||mobile.getText().toString().length()<10||mobile.getText().toString().isEmpty()){
                    Snackbar.make(Signup.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter 10 Digits", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    function();
                }
            }
        });


        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

    }

    private void function() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, SIGNUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Signup.this, ""+response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject=new JSONObject(response);

                    if (jsonObject.getString("result").equals("success")){

                        Snackbar.make(Signup.this.getWindow().getDecorView().findViewById(android.R.id.content), "Register Successfully", Snackbar.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Signup.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<String, String>();
                params.put("name",name.getText().toString().trim());
                params.put("user",username.getText().toString().trim());
                params.put("pass",password.getText().toString().trim());
                params.put("mobile",mobile.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
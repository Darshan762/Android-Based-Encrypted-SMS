package com.encrypted.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView register;
    EditText username,password;
    TextView forgot_password;
    Button login;

    private static final String BASE_URL="http://wizzie.tech/EncryptedSMS/login.php";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Phone = "phoneKey";
    public static final String eml = "eml";
    static String mobile,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Phone)) {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            i.putExtra("mob",sharedpreferences.getString(Phone, ""));
            startActivity(i);
            finish();
        }

        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().isEmpty()) {
                    Snackbar.make(Login.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Name", Snackbar.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().trim().isEmpty()) {
                    Snackbar.make(Login.this.getWindow().getDecorView().findViewById(android.R.id.content), "Enter Password", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    function();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Signup.class));
                // finish();
            }
        });

    }

    private void function() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray=new JSONArray(response);
                    //Toast.makeText(Login.this, ""+response, Toast.LENGTH_SHORT).show();
                    if(jsonArray.length()==0){
                        Snackbar.make(Login.this.getWindow().getDecorView().findViewById(android.R.id.content), "Invalid Username Or Password ", Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            for (int j=0;j<jsonArray.length();j++){
                                JSONObject jsonObject=jsonArray.getJSONObject(j);

                                    mobile=jsonObject.getString("mobile");
                                    email=jsonObject.getString("username");
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Phone, mobile.trim());
                                    editor.putString(eml, email.trim());
                                    editor.commit();
                                    //Toast.makeText(Login.this, ""+cust_id, Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                    i.putExtra("mob",mobile);
                                    i.putExtra("em",email);
                                    startActivity(i);
                                    finish();

                            }

                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String, String>();
                params.put("u",username.getText().toString());
                params.put("p",password.getText().toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void init() {

        register=findViewById(R.id.login);
        username=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        forgot_password=findViewById(R.id.forgot_password);
        login=findViewById(R.id.cirLoginButton);

    }
}
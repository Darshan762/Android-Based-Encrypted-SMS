package com.encrypted.sms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Compose extends AppCompatActivity {

    Compose encryptionMain;
    Spinner from;
    EditText to,subject,compose;
    String date;
    private Button Switch;
    private String message;
    private String key;
    private EditText Textfield_Key;
    private TextView Answer;
    ArrayAdapter adapter;
    ArrayList mails =new ArrayList();
    private static final String URL="http://wizzie.tech/EncryptedSMS/sendmail.php";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        //Toast.makeText(this, ""+ MainActivity.email, Toast.LENGTH_SHORT).show();

        from=findViewById(R.id.from);
        to=findViewById(R.id.to);
        subject=findViewById(R.id.subject);
        compose=findViewById(R.id.data);
        Answer = findViewById(R.id.Answer);
        Switch = findViewById(R.id.Swtich);
        Textfield_Key = findViewById(R.id.key);

        mails.add(MainActivity.email);

        adapter =new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,mails);
        from.setAdapter(adapter);


    }

    public void encryptionButtonClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.Swtich:
                    encryptionMain.switchAlgho(view);
                    break;

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void switchAlgho(View view) {

        String SwitchValue = Switch.getText().toString();
        switch (SwitchValue) {
            case "Advanced Encryption Standard":
                Switch.setText("Triple Data Encryption Standard");
                break;

        }
    }

    public void send(View view) throws Exception {
        if (to.getText().toString().isEmpty()) {
            Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Enter To Address", Snackbar.LENGTH_SHORT).show();
        }else if(subject.getText().toString().isEmpty()){
            Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Subject Is missing", Snackbar.LENGTH_SHORT).show();
        }else if(compose.getText().toString().isEmpty()){
            Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Content Missing", Snackbar.LENGTH_SHORT).show();
        }
        else {
            fun();
        }
        message = String.valueOf(compose.getText());
        key = String.valueOf(Textfield_Key.getText());
        String Algorithm = String.valueOf(Switch.getText());
        switch (Algorithm) {
            case "Advanced Encryption Standard":
                AES aes = new AES();
                String enc = aes.AESencrypt(key.getBytes("UTF-16LE"), message.getBytes("UTF-16LE"));
                Answer.setText(enc);
                break;

        }

    }

    public void encrypt(View view) throws Exception {


        if (compose.length() == 0) {
            Toast.makeText(view.getContext(), "Enter a message to Encrypt", Toast.LENGTH_SHORT).show();
            return;
        }
        message = String.valueOf(compose.getText());
        key = String.valueOf(Textfield_Key.getText());
        String Algorithm = String.valueOf(Switch.getText());
        switch (Algorithm) {
            case "Advanced Encryption Standard":
                AES aes = new AES();
                String enc = aes.AESencrypt(key.getBytes("UTF-16LE"), message.getBytes("UTF-16LE"));
                Answer.setText(enc);
                break;
        }
    }


    private void fun() {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
        date = df.format(Calendar.getInstance().getTime());

        dialog = new ProgressDialog(Compose.this);
        dialog.setMessage("Loading, please wait.");
        dialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
               // Toast.makeText(Compose.this, ""+response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject=new JSONObject(response);


                    if (jsonObject.getString("result").equals("success")){

                        Snackbar.make(Compose.this.getWindow().getDecorView().findViewById(android.R.id.content), "Mail Send Successfully", Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Compose.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<String, String>();
                params.put("from",from.getSelectedItem().toString().trim());
                params.put("mobile",MainActivity.m.trim());
                params.put("to",to.getText().toString().trim());
                params.put("sub",subject.getText().toString().trim());
                params.put("mail_data",compose.getText().toString().trim());
                params.put("dt",date.toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
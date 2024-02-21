package com.encrypted.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Send extends AppCompatActivity {

    RecyclerView recyclerView;
    SendAdapter sendAdapter;
    private static final String URL="http://wizzie.tech/EncryptedSMS/get_sendmails.php";
    ProgressDialog dialog;
    ArrayList send_to=new ArrayList();
    ArrayList send_subject=new ArrayList();
    ArrayList send_data=new ArrayList();
    ArrayList send_time=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        recyclerView=findViewById(R.id.send);

        getData(MainActivity.email);

    }

    private void getData(final String email) {
        dialog = new ProgressDialog(Send.this);
        dialog.setMessage("Loading, please wait.");
        dialog.show();
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            // Toast.makeText(Inboxx.this, ""+response, Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                send_to.add(jsonObject.getString("to_address"));
                                send_subject.add(jsonObject.getString("sub"));
                                send_data.add(jsonObject.getString("mail_data"));
                                send_time.add(jsonObject.getString("dtime"));

                            }

                            sendAdapter = new SendAdapter(Send.this,send_to,send_subject,send_data,send_time);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(sendAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("m", email);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }
}
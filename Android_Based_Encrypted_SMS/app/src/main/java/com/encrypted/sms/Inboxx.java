package com.encrypted.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Inboxx extends AppCompatActivity {

    RecyclerView recyclerView;

    ComposeMain encryptionMain;
   static ArrayList to=new ArrayList();
   static ArrayList subject=new ArrayList();
    ArrayList data =new ArrayList();
    ArrayList datetime=new ArrayList();
    ArrayList id=new ArrayList();
    ArrayList status=new ArrayList();


    private static final String URL="http://wizzie.tech/EncryptedSMS/getmails.php";
    DataAdapter dataAdapter;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inboxx);

        recyclerView=findViewById(R.id.recycler);
        getData(MainActivity.email);


    }

    private void getData(final String m) {
        dialog = new ProgressDialog(Inboxx.this);
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
                                to.add(jsonObject.getString("to_address"));
                                subject.add(jsonObject.getString("sub"));
                                data.add(jsonObject.getString("mail_data"));
                                datetime.add(jsonObject.getString("dtime"));
                                id.add(jsonObject.getString("id"));
                                status.add(jsonObject.getString("status"));


                            }

                            dataAdapter = new DataAdapter(Inboxx.this,to,subject,data,datetime,id,status);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(dataAdapter);

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
                params.put("m", m);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void compose(View view) {
        startActivity(new Intent(getApplicationContext(),ComposeActivity.class));
    }
    public void decompose(View view){
        startActivity(new Intent(getApplicationContext(),ComposeActivity.class));
    }

    public void goToEncryption(View view) {
        encryptionMain = new ComposeMain();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.container, encryptionMain);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
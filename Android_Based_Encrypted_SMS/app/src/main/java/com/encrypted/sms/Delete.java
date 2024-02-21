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

public class Delete extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressDialog dialog;
    private static final String URL="http://wizzie.tech/EncryptedSMS/get_deletes.php";
    DeleteAdapter deleteAdapter;

    ArrayList to=new ArrayList();
    ArrayList subject=new ArrayList();
    ArrayList data =new ArrayList();
    ArrayList datetime=new ArrayList();
    ArrayList id=new ArrayList();
    ArrayList status=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        recyclerView=findViewById(R.id.deleted);
        getData(MainActivity.email);
    }

    private void getData( final String email) {
        dialog = new ProgressDialog(Delete.this);
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

                            deleteAdapter = new DeleteAdapter(Delete.this,to,subject,data,datetime,id,status);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(deleteAdapter);

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
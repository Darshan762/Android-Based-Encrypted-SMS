package com.encrypted.sms;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private String message;
    private String key;

    Context context;
    ArrayList to=new ArrayList();
    ArrayList subject=new ArrayList();
    ArrayList data =new ArrayList();
    ArrayList datetime=new ArrayList();
    ArrayList id=new ArrayList();
    ArrayList status=new ArrayList();
    static String to_addrs,subj;
    ComposeMain encryptionMain;

    private static final String URL="http://wizzie.tech/EncryptedSMS/add_favourite.php";
    private static final String URLD="http://wizzie.tech/EncryptedSMS/add_delete.php";

    public DataAdapter(Inboxx inboxx, ArrayList to, ArrayList subject, ArrayList data, ArrayList datetime, ArrayList id, ArrayList status) {

    this.context=inboxx;
    this.to=to;
    this.subject=subject;
    this.data=data;
    this.datetime=datetime;
    this.id=id;
    this.status=status;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.request_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.from_address.setText(to.get(position).toString().trim());
        holder.dtime.setText(datetime.get(position).toString().trim());
        holder.subject.setText(subject.get(position).toString().trim());
        holder.data.setText(data.get(position).toString().trim());

        if(status.get(position).toString().equalsIgnoreCase("favourite")){

            holder.star.setImageResource(R.drawable.ic_baseline_star_24);
        }

        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starfun(id.get(position).toString().trim());

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                delete(id.get(position).toString().trim());

            }
        });

        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_addrs=Inboxx.to.get(position).toString().trim();
                subj=Inboxx.subject.get(position).toString().trim();
                context.startActivity(new Intent(context,Compose.class));
            }
        });

//        holder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (holder.subject.length() == 0) {
//                    Toast.makeText(view.getContext(), "Enter a message to Decrypt", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                message = String.valueOf(holder.subject.getText());
//                key = String.valueOf(holder.Textfield_Key.getText());
//                String SwitchValue = holder.Switch.getText().toString();
//                switch (SwitchValue) {
//                    case "Advanced Encryption Standard":
//                        AES aes = new AES();
//                        try {
//                            String decData = aes.AESdecrypt(key, Base64.decode(message.getBytes("UTF-16LE"), Base64.DEFAULT));
//                            holder.Answer.setText(decData);
//                        } catch (Exception e) {
//                            Toast.makeText(view.getContext(), "Your key is wrong", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case "Triple Data Encryption Standard":
//                        DES des = new DES();
//                        try {
//                            String decData = des.decrypt(key, Base64.decode(message.getBytes("UTF-16LE"), Base64.DEFAULT));
//                            holder.Answer.setText(decData);
//                        } catch (Exception e) {
//                            Toast.makeText(view.getContext(), "Your key is wrong", Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case "Caesar Cipher":
//                        if (holder.Textfield_Key.length() == 0) {
//                            Toast.makeText(view.getContext(), "Enter a key", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        if (Integer.parseInt(key) >= 26) {
//                            Toast.makeText(view.getContext(), "The Key must be less than 26 characters", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        Caesarcipher c = new Caesarcipher();
//                        holder.Answer.setText(c.caesarcipherDec(message, Integer.parseInt(key)));
//                        break;
//                    case "Vigenere Cipher":
//                        if (holder.Textfield_Key.length() == 0) {
//                            Toast.makeText(view.getContext(), "Enter a key to Decrypt", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        for (char i : message.toUpperCase().toCharArray()) {
//                            if (i < 'A' || i > 'Z') {
//                                Toast.makeText(view.getContext(), "Only Letters are allowed here", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        }
//                        for (char i : key.toUpperCase().toCharArray()) {
//                            if (i < 'A' || i > 'Z') {
//                                Toast.makeText(view.getContext(), "Only Letters are allowed here", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        }
//                        Vigenere v = new Vigenere();
//                        holder.Answer.setText(v.Vigeneredecrypt(message, key));
//                        break;
//
//                }
//            }
//        });


    }

    private void delete(final String trim) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,URLD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject=new JSONObject(response);

                    if (jsonObject.getString("result").equals("success")){
                        Toast.makeText(context, "Mail Deleted", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<String, String>();
                params.put("id",trim);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    private void starfun(final String trim) {

        StringRequest stringRequest=new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject=new JSONObject(response);

                    if (jsonObject.getString("result").equals("success")){
                        Toast.makeText(context, "Added To Favourite", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<String, String>();
                params.put("id",trim);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    @Override
    public int getItemCount() {
        return id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout,layout,layout2;
        TextView from_address,dtime,subject;
        private TextView Answer;
        EditText data, Textfield_Key;
        Button button;
        private Button Switch;
        ImageView star,delete,reply,decrypt;
        int z = 0;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            from_address=itemView.findViewById(R.id.frm);
            dtime=itemView.findViewById(R.id.dt);
            subject=itemView.findViewById(R.id.sub);
            Answer = itemView.findViewById(R.id.Answer);
            Switch = itemView.findViewById(R.id.Swtich);
            data=itemView.findViewById(R.id.mail);
            linearLayout=itemView.findViewById(R.id.parent);
            Textfield_Key = itemView.findViewById(R.id.Key);
            layout=itemView.findViewById(R.id.dta);
            star=itemView.findViewById(R.id.str);
            delete=itemView.findViewById(R.id.dlt);
            reply=itemView.findViewById(R.id.reply);
            decrypt = itemView.findViewById(R.id.decrypt);

            decrypt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,ComposeActivity.class);
                    context.startActivity(intent);
                }
            });

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (z == 0) {
                        layout.setVisibility(View.VISIBLE);
                        z++;
                    } else {
                        layout.setVisibility(View.GONE);
                        z = 0;
                    }
                }
            });
        }
    }
}

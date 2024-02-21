package com.encrypted.sms;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Phone = "phoneKey";
    public static final String eml = "eml";
    static  String m,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Phone)) {

            m=sharedpreferences.getString(Phone, "");
            email=sharedpreferences.getString(eml, "");
            //Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();

        }else {
            Intent i = getIntent();
            m = i.getStringExtra("mob");
            email = i.getStringExtra("em");
        }


    }

    public void inbox(View view) {
        startActivity(new Intent(getApplicationContext(),Inboxx.class));
    }

    public void send(View view) {
        startActivity(new Intent(getApplicationContext(),Send.class));
    }

    public void favourite(View view) {
        startActivity(new Intent(getApplicationContext(),Favourites.class));

    }

    public void delete(View view) {
        startActivity(new Intent(getApplicationContext(),Delete.class));
    }

    public void logout(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirm Logout..!!!");
        alertDialogBuilder.setIcon(R.drawable.ic_baseline_power_settings_new_24);
        alertDialogBuilder.setMessage("Are you sure,You want to Logout");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                Toast.makeText(getApplicationContext(), "Logout Success!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
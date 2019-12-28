package com.example.design14;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnSigOut;
    private ProgressDialog dialog;
    private int time = 3000;

    SharedPreferencesClass sharedPreferencesClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferencesClass = new SharedPreferencesClass(this);
        btnSigOut = findViewById(R.id.btnSigOut);
        btnSigOut.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        Toast.makeText(this, "Welcome user :D", Toast.LENGTH_SHORT).show();
    }



    public void UpdatePreferences(){
        sharedPreferencesClass.UpdatePreferences();

    }

    @Override
    public void onClick(View v) {
        dialog.setMessage("disconnecting...");
        dialog.setCancelable(false);

        switch(v.getId()) {
            case R.id.btnSigOut:
                dialog.show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        UpdatePreferences();
                        finish();
                    }
                }, time);
                break;
        }
    }
}

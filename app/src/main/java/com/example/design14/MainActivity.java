package com.example.design14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText txtPhoneNumber;
    Button btnValidate;
    String number;
    Boolean status=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        btnValidate = findViewById(R.id.btnValidate);
        chargePreferences();

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = txtPhoneNumber.getText().toString().trim();
                if(number.isEmpty()){
                    txtPhoneNumber.setError("Number is Required");
                    txtPhoneNumber.requestFocus();
                    return;
                }else{
                    String phoneNumber = "+51"+number;
                    Intent intent = new Intent(MainActivity.this,CodeVerification.class);
                    intent.putExtra("phoneNumber",phoneNumber);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void chargePreferences(){
        SharedPreferences statusPreferences = getSharedPreferences("status",MODE_PRIVATE);

        status = statusPreferences.getBoolean("checked",false);

        if(status ==true){
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(intent);
            finish();
        }

    }
}

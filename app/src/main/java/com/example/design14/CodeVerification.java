package com.example.design14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class CodeVerification extends AppCompatActivity {

    private String verificationId;
    private Button btnSigIn;
    EditText txtCode;
    private FirebaseAuth mAuth;

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Waiting for code");
        dialog.setCancelable(false);
        dialog.show();
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        sendVerificationCode(phoneNumber);

        txtCode = findViewById(R.id.txtCode);
        btnSigIn = findViewById(R.id.btnSigIn);



        btnSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = txtCode.getText().toString().trim();

                if(code.isEmpty()|| code.length()<6){
                    txtCode.setError("Enter Code...");
                    txtCode.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
        sigInWithCredential(credential);
    }

    private void sigInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(CodeVerification.this,ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    SavePreferences();
                    dialog.dismiss();
                    startActivity(intent);
                }else{
                    Toast.makeText(CodeVerification.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId= s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if(code != null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(CodeVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    //Shared Preferences

    public void SavePreferences(){
            SharedPreferences statusPreferences = getSharedPreferences("status",MODE_PRIVATE);
            SharedPreferences.Editor editor = statusPreferences.edit();
            Boolean valor = true;
            editor.putBoolean("checked",valor);
            editor.commit();

    }


}

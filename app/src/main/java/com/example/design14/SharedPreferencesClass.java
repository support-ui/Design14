package com.example.design14;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;
public class SharedPreferencesClass {
    Boolean status=false;
    Context mContex;
    public SharedPreferencesClass(Context mContex) {
        this.mContex = mContex;
    }

    public void savePreferences(){
        SharedPreferences statusPreferences = mContex.getSharedPreferences("status",MODE_PRIVATE);
        SharedPreferences.Editor editor = statusPreferences.edit();
        Boolean valor = true;
        editor.putBoolean("checked",valor);
        editor.commit();
    }

    public void chargePreferences(){
        android.content.SharedPreferences statusPreferences = mContex.getSharedPreferences("status",MODE_PRIVATE);

        status = statusPreferences.getBoolean("checked",false);

        if(status ==true){
            Intent intent = new Intent(mContex,ProfileActivity.class);
            mContex.startActivity(intent);
            ((Activity) mContex).finish();
        }

    }

    public void updatePreferences(){
        SharedPreferences statusPreferences = mContex.getSharedPreferences("status",MODE_PRIVATE);
        SharedPreferences.Editor editor = statusPreferences.edit();
        Boolean valor = false;
        editor.putBoolean("checked",valor);
        editor.commit();
    }
}

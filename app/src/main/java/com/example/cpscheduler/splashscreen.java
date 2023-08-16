package com.example.cpscheduler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class splashscreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                Boolean check = pref.getBoolean("flag", true);
                Intent next;
                if(check){//Already Logged IN

                    next = new Intent(splashscreen.this,LoginActivity.class);
                }
                else {
                    next = new Intent(splashscreen.this,MainActivity.class);
                }
                startActivity(next);
                finish();
            }
        },2000);
    }
}

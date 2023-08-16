package com.example.cpscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView forgettv;
    private EditText em_EdTxt;
    private EditText pw_EdTxt;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(LoginActivity.this);
        TextView btn = findViewById(R.id.button);
        Button lbtn = findViewById(R.id.login_btn);
        pw_EdTxt = findViewById(R.id.login_password);
        em_EdTxt = findViewById(R.id.login_email);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(LoginActivity.this,Register.class);
                startActivity(reg);
            }
        });
        lbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("flag",false);
                editor.apply();
                String email = em_EdTxt.getText().toString();
                String password = pw_EdTxt.getText().toString();
                progressDialog.show();
                if (email.length() == 0 || password.length() ==0)
                    Toast.makeText(LoginActivity.this, "Enter attributes", Toast.LENGTH_SHORT).show();
                else {

                    firebaseAuth.signInWithEmailAndPassword(email,password)
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            progressDialog.cancel();
                                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                            finish();
                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.cancel();
                                                    Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                }
            }
        });
        forgettv = findViewById(R.id.forget);
        forgettv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=em_EdTxt.getText().toString();
                if (email.length() == 0 )
                    Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                else {
                    progressDialog.setTitle("Sending Mail");
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
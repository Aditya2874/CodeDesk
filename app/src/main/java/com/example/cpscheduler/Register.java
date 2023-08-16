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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

//        String greg = "^[a-zA-Z0-9_+&*-]+(?:\\."+
//                "[a-zA-Z0-9_+&*-]+){5,}@g(oogle)?mail\.com$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        firebaseFirestore=FirebaseFirestore.getInstance();
        Button R_btn = findViewById(R.id.register_btn);
        R_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText remail = findViewById(R.id.register_email);
                EditText rpass = findViewById(R.id.register_password);
                EditText rcon = findViewById(R.id.register_confirm);
                EditText rname= findViewById(R.id.name);
                EditText rleet=findViewById(R.id.leet);
                EditText rcodef=findViewById(R.id.codef);
                String email = remail.getText().toString();
                String pass = rpass.getText().toString();
                String confirm = rcon.getText().toString();
                String name= rname.getText().toString();
                String leet=rleet.getText().toString();
                SharedPreferences pref = getSharedPreferences("leet_name",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("lkey",leet);
                editor.apply();
                String cod=rcodef.getText().toString();
                SharedPreferences cfpref = getSharedPreferences("cf_name",MODE_PRIVATE);
                SharedPreferences.Editor ceditor = cfpref.edit();
                ceditor.putString("cfkey",cod);
                ceditor.apply();
                boolean check = isValid(email);
                if (email.length() == 0 || pass.length() ==0)
                    Toast.makeText(Register.this, "Enter Attributes", Toast.LENGTH_SHORT).show();
                else if (!pass.equals(confirm))
                    Toast.makeText(Register.this, "Password should match Confirm Password", Toast.LENGTH_SHORT).show();
                else if(!check)
                    Toast.makeText(Register.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                else if(!email.contains("."))
                    Toast.makeText(Register.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
                else if (pass.length()<7)
                    Toast.makeText(Register.this, "Password length should less than 7", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(Register.this, " Registration Successful", Toast.LENGTH_SHORT).show();
                    firebaseAuth = FirebaseAuth.getInstance();
                    progressDialog = new ProgressDialog(Register.this);
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email,pass)
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                         firebaseFirestore.collection("User")
                                                         .document(firebaseAuth.getInstance().getUid())
                                                                 .set(new usermode(name,leet,cod,email));
                                         startActivity(new Intent(Register.this,LoginActivity.class));
                                         progressDialog.cancel();
                                         finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Register.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    progressDialog.cancel();
                                                }
                                            });
                }
            }
        });
    }
}
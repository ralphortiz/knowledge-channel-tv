package com.example.kch_androiddev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextView tvLogin, tvError1;
    EditText etRegFullname, etRegEmail, etRegPassword;
    Button btnRegisterNow;
    FirebaseFirestore db;
    FirebaseAuth auth;
    MaterialCheckBox cbTerms;
    Map<String, Object> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        tvLogin = findViewById(R.id.tvLogin);
        tvError1 = findViewById(R.id.tvError1);
        etRegFullname = findViewById(R.id.etRegFullname);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegisterNow = findViewById(R.id.btnRegisterNow);
        cbTerms = findViewById(R.id.cbTerms);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = new HashMap<>();

        btnRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etRegFullname.getText().toString().trim().isEmpty() || etRegEmail.getText().toString().trim().isEmpty() || etRegPassword.getText().toString().trim().isEmpty()) {
                    tvError1.setText("Please fill up the required fields");
                } else if (etRegPassword.getText().length() < 8 && etRegPassword.getText().length() > 16) {
                    tvError1.setText("Password must be 8-16 characters");
                } else if (cbTerms.isChecked() == false) {
                    tvError1.setText("You must accept the terms and conditions to register an account");
                } else {
                    tvError1.setText("");
                    registerNow();
                }
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginNow();
            }
        });
    }

    private void registerNow() {
        String fullname = etRegFullname.getText().toString().trim();    
        String email = etRegEmail.getText().toString().trim();
        String password = etRegPassword.getText().toString().trim();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.put("fullname", etRegFullname.getText().toString().trim());
                            user.put("email", etRegEmail.getText().toString().trim());
                            user.put("password", etRegPassword.getText().toString().trim());
                            user.put("avatar", "https://firebasestorage.googleapis.com/v0/b/kch-mobile-app.appspot.com/o/Avatars%2Favatar_default.png?alt=media&token=2f21c6a0-bd8a-4fcd-b0f3-4762d6e313f3");
                            db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("Tag", "Data inserted successfully");
                                            Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Tag", "Failed");
                                }
                            });
                        } else {
                            Log.d("Tag", "Failed processing registration");
                        }
                    }
                });

    }
    private void loginNow() {
        finish();
    }
}
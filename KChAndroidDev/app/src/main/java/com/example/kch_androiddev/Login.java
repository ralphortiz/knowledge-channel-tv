package com.example.kch_androiddev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    TextView tvRegister, tvError;
    Button btnLogin;
    EditText etEmail, etPassword;
    FirebaseAuth auth;
    FirebaseFirestore db;
    String fullname;
    String defaultAvatar;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        tvRegister = findViewById(R.id.tvRegister);
        tvError = findViewById(R.id.tvError);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        etEmail.setText("ralph@gmail.com");
        etPassword.setText("ralph123");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().trim().isEmpty() || etPassword.getText().toString().trim().isEmpty()) {
                    tvError.setText("Email and Password is required");
                } else {
                    tvError.setText("");
                    login();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNow();
            }
        });
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final Intent intent = new Intent(Login.this, Home.class);
                    userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    db.collection("Users").document(userID)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        fullname = document.getString("fullname");
                                        defaultAvatar = document.getString("avatar");
                                        Log.d("TAG", "My name: " + fullname);
                                        intent.putExtra("fullname", fullname);
                                        intent.putExtra("avatar", defaultAvatar);
                                        intent.putExtra("userID", userID);
                                        tvError.setText("");
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Login.this, "Error", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    tvError.setText("Incorrect Email and Password");
                    Toast.makeText(Login.this, "Login unsuccessful", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registerNow() {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}
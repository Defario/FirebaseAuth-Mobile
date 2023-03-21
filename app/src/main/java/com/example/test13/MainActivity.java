package com.example.test13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailField, passwordField;
    Button loginBtn;
    TextView registerLink;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailField = findViewById(R.id.et_email);
        passwordField = findViewById(R.id.et_pass);
        loginBtn = findViewById(R.id.btn_login);
        registerLink = findViewById(R.id.tv_register);

        mAuth = FirebaseAuth.getInstance();

        registerLink.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });

        loginBtn.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String pass = passwordField.getText().toString();
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, task -> {
                if(!task.isSuccessful()){
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }
            });
        });

    }



}
package com.example.test13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText emailField, usernameField, passwordField, confPassField;
    Button registBtn;
    TextView loginLink;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userReference; //buat ngambil data atau nyimpen data baru

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailField = findViewById(R.id.et_email);
        usernameField = findViewById(R.id.et_user);
        passwordField = findViewById(R.id.et_pass);
        confPassField = findViewById(R.id.et_conf);
        registBtn = findViewById(R.id.btn_regist);
        loginLink = findViewById(R.id.tv_login);

        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        });


        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance("https://trial-1f1ff-default-rtdb.firebaseio.com/");

        registBtn.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String user = usernameField.getText().toString();
            String pass = passwordField.getText().toString();
            String conf = confPassField.getText().toString();

            if(!email.contains("@") || !email.endsWith(".com")){
                Toast.makeText(this, "Email must contains '@' and ends with '.com'", Toast.LENGTH_SHORT).show();
            }
            else if(user.isEmpty() || pass.isEmpty() || conf.isEmpty()){
                Toast.makeText(this, "All field must be filled", Toast.LENGTH_SHORT).show();
            }
            else if(!pass.equals(conf)){
                Toast.makeText(this, "Confirmation password must match with password", Toast.LENGTH_SHORT).show();
            }else{
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(RegisterActivity.this, task -> {
                    if(!task.isSuccessful()){
                        Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        userReference = firebaseDatabase.getReference().child("users").child(mAuth.getCurrentUser().getUid());
                        userReference.setValue(new User(user, email));
                    }
                });
            }

        });

    }
}
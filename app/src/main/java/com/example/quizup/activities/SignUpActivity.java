package com.example.quizup.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth fb;
    EditText em, pass1, cnfpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fb = FirebaseAuth.getInstance();
        Button BtnSignUp = (Button) findViewById(R.id.btnSignup);
        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });
        TextView login = findViewById(R.id.BtnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void signUpUser(){
        em = (EditText) findViewById(R.id.etEmailAddress1);
        pass1 = (EditText) findViewById(R.id.etPassword1);
        cnfpass = (EditText) findViewById(R.id.etCnfPassword);

        String email = em.getText().toString();
        String password = pass1.getText().toString();
        String cnfPassword = cnfpass.getText().toString();

        if(email.isBlank() || password.isBlank() || cnfPassword.isBlank()){
            Toast.makeText(this, "Email/Password can not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!(password.equals(cnfPassword))){
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        fb.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
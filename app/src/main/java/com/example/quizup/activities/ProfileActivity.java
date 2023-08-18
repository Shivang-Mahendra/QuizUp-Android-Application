package com.example.quizup.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.quizup.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView TextEmail;
    Button BtnLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        TextEmail = findViewById(R.id.textEmail);
        if(firebaseAuth.getCurrentUser() != null){
            TextEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        }

        BtnLogOut = findViewById(R.id.btnLogOut);
        BtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, IntroActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}
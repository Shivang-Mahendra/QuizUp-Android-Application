package com.example.quizup.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.quizup.R;
import com.google.firebase.auth.FirebaseAuth;
public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        FirebaseAuth fb = FirebaseAuth.getInstance();

        if(fb.getCurrentUser() != null){
            try {
                Toast.makeText(IntroActivity.this, "User already logged in", Toast.LENGTH_SHORT).show();
                redirect("MAIN");
            }
            catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        Button btn = (Button) findViewById(R.id.btnGo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    redirect("LOGIN");
                }
                catch (Exception e) {
                    Toast.makeText(IntroActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void redirect(String str) throws Exception{
        if(str.equals("LOGIN")){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if (str.equals("MAIN")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            throw new Exception("No path exists");
        }
    }

}
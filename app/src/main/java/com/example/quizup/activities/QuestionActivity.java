package com.example.quizup.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizup.R;
import com.example.quizup.adapters.OptAdapter;
import com.example.quizup.models.Questions;
import com.example.quizup.models.Quiz;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
public class QuestionActivity extends AppCompatActivity {
    OptAdapter optionAdapter;
    TextView des;
    Questions question;
    RecyclerView opt_list;
    FirebaseFirestore fbfs;
    List<Quiz> quizzes;
    Map<String, Questions> questions;
    int index;
    Button BtnPrevious,BtnNext,BtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        index = 1;
        BtnPrevious = (Button) findViewById(R.id.btnPrevious);
        BtnNext = (Button) findViewById(R.id.btnNext);
        BtnSubmit = (Button) findViewById(R.id.btnSubmit);
        setUpFirestore();
        setUpEventListener();
    }
    private void setUpFirestore() {
        //firestore query to get that date's quiz document
        fbfs = FirebaseFirestore.getInstance();
        String date = getIntent().getStringExtra("DATE");
        if(date != null){
            fbfs.collection("quizzes").whereEqualTo("title", date)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot it) {
                            if(it != null && !it.isEmpty()){
                                quizzes = it.toObjects(Quiz.class);
                                questions = quizzes.get(0).ques;
                                bindViews();
                            }
                        }
                    });
        }
    }

    private void setUpEventListener() {
        BtnPrevious.setOnClickListener(view -> {
            index--;
            bindViews();
        });
        BtnNext.setOnClickListener(view -> {
            index++;
            bindViews();
        });
        BtnSubmit.setOnClickListener(view -> {
//            Log.d("FINAL", questions.toString());
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            String json = new Gson().toJson(quizzes.get(0)); //Serialize objects in string format
            intent.putExtra("QUIZ", json);
            startActivity(intent);
            finish();

        });
    }
    private void bindViews() {
        BtnPrevious.setVisibility(View.GONE);
        BtnNext.setVisibility(View.GONE);
        BtnSubmit.setVisibility(View.GONE);

        if(index == 1){
            BtnNext.setVisibility(View.VISIBLE);
        }
        else if(index == questions.size()){
            BtnPrevious.setVisibility(View.VISIBLE);
            BtnSubmit.setVisibility(View.VISIBLE);
        }
        else{
            BtnPrevious.setVisibility(View.VISIBLE);
            BtnNext.setVisibility(View.VISIBLE);
        }
        String str = "question";
        str += index;
        question = questions.get(str);
        des = (TextView) findViewById(R.id.description);
        des.setText(question.description);
        optionAdapter = new OptAdapter(this, question);
        opt_list = (RecyclerView) findViewById(R.id.optionsList);
        opt_list.setLayoutManager(new LinearLayoutManager(this));
        opt_list.setAdapter(optionAdapter);
        opt_list.setHasFixedSize(true); // options are always 4 so it enhances recycler view performance
    }
}
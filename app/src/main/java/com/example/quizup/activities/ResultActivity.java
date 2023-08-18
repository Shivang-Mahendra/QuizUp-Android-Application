package com.example.quizup.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.quizup.R;
import com.example.quizup.models.Questions;
import com.example.quizup.models.Quiz;
import com.google.gson.Gson;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    Quiz quiz;
    int score;
    TextView TxtScore,TxtAnswer;
    StringBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setUpViews();
    }

    private void setUpViews() {
        String quizData = getIntent().getStringExtra("QUIZ");
        quiz = new Gson().fromJson(quizData, Quiz.class); //Deserialize string data passed from QuestionActivity back to object of Quiz
        calculateScore();
        setAnswerView();
    }

    private void setAnswerView() {
        builder = new StringBuilder("");
        for(String i : quiz.ques.keySet()){
            Questions questions = quiz.ques.get(i);
            if(questions!=null){
                builder.append("<font color '#18206F'><b>Question : ").append(questions.description).append("</b></font><br/><br/>");
                builder.append("<font color'#009688'>Answer : ").append(questions.ans).append("</font><br/><br/>");
            }
        }
        TxtAnswer = findViewById(R.id.txtAnswer);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            TxtAnswer.setText(Html.fromHtml(builder.toString(),Html.FROM_HTML_MODE_COMPACT));
        }
        else{
            TxtAnswer.setText(Html.fromHtml(builder.toString()));
        }
    }

    private void calculateScore() {
        score = 0;
        for(String i: quiz.ques.keySet()){
            Questions questions = quiz.ques.get(i);
            if(questions != null && questions.user_ans.equals(questions.ans)){
                score+=10;
            }
        }
        TxtScore = findViewById(R.id.txtScore);
        TxtScore.setText("Your Score : " + score);
    }
}
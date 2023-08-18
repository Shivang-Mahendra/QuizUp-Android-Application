package com.example.quizup.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizup.R;
import com.example.quizup.activities.QuestionActivity;
import com.example.quizup.models.Quiz;
import com.example.quizup.util.PickColors;
import com.example.quizup.util.PickIcon;

import java.util.Calendar;
import java.util.List;

public class QuizAdapters extends RecyclerView.Adapter<QuizAdapters.QuizViewHolder>{
    Context context;
    List<Quiz> quizzes;
    PickColors pc;
    PickIcon pi;
    public QuizAdapters(Context cnt, List<Quiz> quiz)  {
        context = cnt;
        quizzes = quiz;
        pc = new PickColors();
        pi = new PickIcon();
    }
    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quiz_item, parent, false);
        return new QuizViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textViewTitle.setText(quizzes.get(position).title);
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(pc.getColor()));
        holder.iconView.setImageResource(pi.getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(context, quizzes.get(position).title, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.putExtra("DATE", quizzes.get(position).title);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return quizzes.size();
    }


    class QuizViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView textViewTitle;
        ImageView iconView;
        CardView cardContainer;

        QuizViewHolder(View iv) {
            super(iv);
            itemView = iv;
            textViewTitle = (TextView) iv.findViewById(R.id.quizTitle);
            iconView = (ImageView) iv.findViewById(R.id.quizIcon);
            cardContainer = (CardView) iv.findViewById(R.id.cardContainer);
        }
    }
}

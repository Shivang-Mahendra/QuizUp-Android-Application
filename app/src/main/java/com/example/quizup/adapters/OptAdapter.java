package com.example.quizup.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizup.R;
import com.example.quizup.models.Questions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OptAdapter extends RecyclerView.Adapter<OptAdapter.OptionViewHolder>{
    Context context;
    Questions question;
    List<String> options;
    View view;
    public OptAdapter(Context cnt, Questions ques){
        question = new Questions();
        context = cnt;
        question = ques;
        options = new ArrayList<>(List.of(question.opt1,question.opt2,question.opt3,question.opt4));
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.opt_item, parent, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.optionView.setText(options.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                question.user_ans = options.get(position);
                notifyDataSetChanged();
            }
        });
        if(Objects.equals(question.user_ans, options.get(position))){
            holder.itemView.setBackgroundResource(R.drawable.opt_selected_background);
        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.opt_background);
        }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }


    class OptionViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        TextView optionView;

        OptionViewHolder(View iv){
            super(iv);
            itemView = iv;
            optionView = (TextView) iv.findViewById(R.id.quiz_options);
        }
    }
}

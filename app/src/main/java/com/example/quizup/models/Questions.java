package com.example.quizup.models;

public class Questions {
    public String description;
    public String opt1;
    public String opt2;
    public String opt3;
    public String opt4;
    public String ans;
    public String user_ans;


    public Questions(String description, String opt1, String opt2, String opt3, String opt4, String ans, String user_ans) {//parameterized constructor
        this.description = description;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.opt4 = opt4;
        this.ans = ans;
        this.user_ans = user_ans;
    }
    public Questions(){} // empty non - parameterized constructor
    public Questions(String description, String opt1, String opt2, String opt3, String opt4, String ans) { //parameterized constructor
        this.description = description;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.opt4 = opt4;
        this.ans = ans;
    }
}

package com.example.quizup.models;

import java.util.Map;

public class Quiz {
    String id = "";
    public String title = "";
    public Map<String, Questions> ques;

    public Quiz(String id, String title) { // parameterized constructor
        this.id = id;
        this.title = title;
    }
    public Quiz(){} // empty non-parameterized constructor
}

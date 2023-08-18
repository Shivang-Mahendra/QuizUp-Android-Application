package com.example.quizup.util;

import androidx.annotation.NonNull;

public class PickColors {
    String[] col;
    int curColorIndex;

    public PickColors() {
        col = new String[]{"#D36280", "#3EB9DF", "#E3AD17", "#E44F55", "#3685BC", "#FA8056", "#7D659F", "#818BCA", "#51BAB3", "#4FB66C", "#B5BFB6", "#EF8EAD", "#627991"};
        curColorIndex = 0;
    }

    public String getColor() {
        curColorIndex = (curColorIndex + 1) % col.length;
        return col[curColorIndex];
    }
}
package com.example.quizup.util;

import com.example.quizup.R;

public class PickIcon {
    int[] icons;
    int curIcon;

    public PickIcon() {
        icons = new int[]{R.drawable.quiz_icon, R.drawable.calendar_icon, R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4, R.drawable.icon_5, R.drawable.icon_6, R.drawable.icon_7};
        curIcon = 0;
    }

    public int getIcon() {
        curIcon = (curIcon + 1) % icons.length;
        return icons[curIcon];
    }
}

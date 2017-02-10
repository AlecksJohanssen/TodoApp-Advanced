package com.example.alecksjohanssen.todoapp.DataModel;

import java.util.ArrayList;

/**
 * Created by AlecksJohanssen on 2/9/17.
 */

public class Todo {
    private String mContent;
    private boolean selected;

    public Todo(String content) {
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

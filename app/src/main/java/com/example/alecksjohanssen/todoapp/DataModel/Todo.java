package com.example.alecksjohanssen.todoapp.DataModel;

import java.util.ArrayList;

/**
 * Created by AlecksJohanssen on 2/9/17.
 */

public class Todo {
    private String mContent;
    private int mID;
    private boolean selected;

    public Todo() {}


    public Todo(String content) {
        mContent = content;
    }

    public int getID() {
        return mID;
    }

    public void setID(int id) {
        this.mID = id;
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

}

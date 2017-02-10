package com.example.alecksjohanssen.todoapp.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.alecksjohanssen.todoapp.DataModel.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlecksJohanssen on 2/10/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ToDoList.db";
    public static final String TABLE_NAME = "ToDoList_Table";
    public static final String DATABASE_TABLE = "ToDo_DBTable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TODO";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TODO TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String TODO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, TODO);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<Todo> getAllTodos() {
        List<Todo> todoList = new ArrayList<Todo>();
        String selectQuery = "SELECT TODO FROM ToDoList_Table";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo("", 0);
                todo.setContent(cursor.getString(0));
                Log.d("ID", String.valueOf(cursor.getString(0)));
                todoList.add(todo);
            } while (cursor.moveToNext());
        }

        return todoList;
    }
}

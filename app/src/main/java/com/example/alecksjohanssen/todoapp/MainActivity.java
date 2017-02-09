package com.example.alecksjohanssen.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alecksjohanssen.todoapp.DataAdapter.TodosAdapter;
import com.example.alecksjohanssen.todoapp.DataModel.Todo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Todo> mTodos = new ArrayList<Todo>();
    private Button btnCreateContent;
    private String mContent;
    private EditText contentValue;
    private TodosAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvContents);
        adapter = new TodosAdapter(this, mTodos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentValue = (EditText) findViewById(R.id.tdEditText);
        btnCreateContent = (Button) findViewById(R.id.tdAddItems);
        AddNewTodo();

    }
    private void AddNewTodo() {
        btnCreateContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContent = (contentValue.getText().toString());
                mTodos.add(new Todo(mContent));
                Log.d("Array size", String.valueOf(mTodos.size()));
                adapter.notifyDataSetChanged();
            }
        });
    }
}

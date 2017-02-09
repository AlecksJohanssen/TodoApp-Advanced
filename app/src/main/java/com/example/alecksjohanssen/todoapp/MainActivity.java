package com.example.alecksjohanssen.todoapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alecksjohanssen.todoapp.DataAdapter.CustomRCAdapter;
import com.example.alecksjohanssen.todoapp.DataAdapter.TodosAdapter;
import com.example.alecksjohanssen.todoapp.DataModel.Todo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static ArrayList<Todo> mTodos = new ArrayList<Todo>();
    private Button btnCreateContent;
    private String mContent;
    private EditText contentValue;
    private static TodosAdapter adapter;
    private CheckBox checkBox;
    private TextView textView;
    private RecyclerView recyclerView;
    private static Activity thisActivity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thisActivity = this;
        recyclerView = (RecyclerView) findViewById(R.id.rvContents);
        adapter = new TodosAdapter(this, mTodos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentValue = (EditText) findViewById(R.id.tdEditText);
        btnCreateContent = (Button) findViewById(R.id.tdAddItems);
        AddNewTodo();

    }



    public void onCheckboxClicked(View v) {
        checkBox = (CheckBox) v.findViewById(R.id.todo_checkbox);
        if(checkBox.isChecked()) {
            int pos = (Integer) v.getTag();
            mTodos.remove(pos);
            adapter.notifyItemRemoved(pos);
            Toast.makeText(getApplicationContext(), "Congratulation Good work!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void onItemLongClicked(int position) {
        mTodos.remove(position);
        adapter.notifyItemRemoved(position);
        Toast.makeText(thisActivity, "To-do removed.", Toast.LENGTH_SHORT).show();
    }

    private void AddNewTodo() {
        btnCreateContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContent = (contentValue.getText().toString());
                if(mContent.isEmpty() || mContent.contains(" ")) {
                    Toast.makeText(getApplicationContext(), "Please type something in!", Toast.LENGTH_SHORT).show();
                } else {
                    mTodos.add(new Todo(mContent));
                    adapter.notifyDataSetChanged();
                    contentValue.setText(null);
                }
            }
        });
    }
}

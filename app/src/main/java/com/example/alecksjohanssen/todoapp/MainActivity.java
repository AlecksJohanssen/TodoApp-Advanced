package com.example.alecksjohanssen.todoapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
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
import com.example.alecksjohanssen.todoapp.DatabaseHelper.DatabaseHelper;
import com.example.alecksjohanssen.todoapp.Support.ItemClickSupport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Todo> mTodos = new ArrayList<Todo>();
    private Button btnCreateContent;
    private String mContent;
    private EditText contentValue;
    private  TodosAdapter adapter;
    private  RecyclerView recyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private DatabaseHelper TDdb;
    boolean alreadyExecuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rvContents);
        adapter = new TodosAdapter(this, mTodos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentValue = (EditText) findViewById(R.id.tdEditText);
        btnCreateContent = (Button) findViewById(R.id.tdAddItems);
        TDdb = new DatabaseHelper(this);
        myRef = database.getReference("todo");
        checkifOnline();
        AddNewTodo();
        implementClickonRC();
    }

    private void pushToFireBase(String content, int id) {
        myRef.child("todo" + id).setValue(content);
    }

    private void checkifOnline() {
        if(alreadyExecuted = false) {
            alreadyExecuted = true;
            if(isOnline()) {
                Toast.makeText(getApplicationContext(),"PULL DATA", Toast.LENGTH_SHORT).show();
                mTodos.clear();
                pullDataFromFireBase();
            } else {
                mTodos.addAll(TDdb.getAllTodos());
            }
        }
    }

    private boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    private void implementClickonRC() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                EditTodo mEditTodo = new EditTodo();
                mEditTodo.show(getSupportFragmentManager(), "activity");
                Bundle args = new Bundle();
                args.putInt("item_position", position);
                SharedPreferences.Editor editor = getSharedPreferences("key", MODE_PRIVATE).edit();
                editor.putString("current_todo", mTodos.get(position).getContent());
                editor.commit();
                mEditTodo.setArguments(args);
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                mTodos.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "To-do removed.", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isOnline()) {
            mTodos.clear();
            pullDataFromFireBase();
        } else {
            mTodos.clear();
            mTodos.addAll(TDdb.getAllTodos());
            adapter.notifyDataSetChanged();
        }
    }

    public void updateSingleRow(String editContent, int index) {
        mTodos.set(index, new Todo(editContent));
        adapter.notifyItemChanged(index);
    }


    private void pullDataFromFireBase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String value = childDataSnapshot.getValue(String.class);
                    mTodos.add(new Todo(value));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });
    }

    private void AddNewTodo() {
        btnCreateContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContent = (contentValue.getText().toString());
                if(mContent.isEmpty() && mContent.contains(" ")) {
                    Toast.makeText(getApplicationContext(), "Please type something in!", Toast.LENGTH_SHORT).show();
                } else if(mContent.contains(" ")) {
                    Toast.makeText(getApplicationContext(), "Please type something in!", Toast.LENGTH_SHORT).show();
                }
                else {
                    mTodos.add(new Todo(mContent));
                    adapter.notifyDataSetChanged();
                    contentValue.setText(null);
                    pushToFireBase(mContent, mTodos.size() -1);
                    TDdb.insertData(mContent);
                }
            }
        });
    }


}

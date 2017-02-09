package com.example.alecksjohanssen.todoapp.DataAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alecksjohanssen.todoapp.DataModel.Todo;
import com.example.alecksjohanssen.todoapp.MainActivity;
import com.example.alecksjohanssen.todoapp.R;

import java.util.List;

/**
 * Created by AlecksJohanssen on 2/9/17.
 */

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ViewHolder> {

    private List<Todo> mTodos;
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater mLayoutInflate = LayoutInflater.from(context);
        View todoView = mLayoutInflate.inflate(R.layout.content_layout, parent, false);
        ViewHolder mViewHolder = new ViewHolder(todoView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo todo = mTodos.get(position);
        TextView textView = holder.tvContent;
        textView.setText(todo.getContent());
        holder.checkBox.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    public TodosAdapter(Context context, List<Todo> todos) {
        mTodos = todos;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText editContent;
        public Button btnCreateContent;
        public TextView tvContent;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            editContent = (EditText) itemView.findViewById(R.id.tdEditText);
            btnCreateContent = (Button) itemView.findViewById(R.id.tdAddItems);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            checkBox = (CheckBox) itemView.findViewById(R.id.todo_checkbox);
        }
    }

    private Context getContext() {
        return mContext;
    }
}

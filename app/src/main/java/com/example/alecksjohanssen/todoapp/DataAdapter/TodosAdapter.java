package com.example.alecksjohanssen.todoapp.DataAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alecksjohanssen.todoapp.DataModel.Todo;
import com.example.alecksjohanssen.todoapp.MainActivity;
import com.example.alecksjohanssen.todoapp.R;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by AlecksJohanssen on 2/9/17.
 */

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ViewHolder> {

    private List<Todo> mTodos;
    private CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater mLayoutInflate = LayoutInflater.from(context);
        View todoView = mLayoutInflate.inflate(R.layout.content_layout, parent, false);
        ViewHolder mViewHolder = new ViewHolder(todoView);
        return mViewHolder;
    }

    public TodosAdapter(Context context, List<Todo> todos) {
        mTodos = todos;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Todo checkBoxTodo = mTodos.get(position);
        Todo todo = mTodos.get(position);
        TextView textView = holder.tvContent;
        textView.setText(todo.getContent());
        holder.tvContent.setTag(position);
        holder.checkBox.setTag(position);
        holder.checkBox.setTag(position);
        holder.checkBox.setChecked(checkBoxTodo.isSelected());

        if(position == 0 && mTodos.get(0).isSelected() && holder.checkBox.isChecked()) {
            lastChecked = holder.checkBox;
            lastCheckedPos = 0;
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckBox cb = (CheckBox) v;
                int clickedPos = ((Integer)cb.getTag()).intValue();
                if(cb.isChecked()) {
                    int pos = mTodos.get(clickedPos).getID();
                    ((MainActivity) getContext()).removeFromFirebase("todo" + pos);
                    mTodos.remove(clickedPos);
                    notifyItemRemoved(clickedPos);
                    notifyItemRangeChanged(clickedPos, mTodos.size());
                    Toast.makeText(getContext(), "Woohoo Well done!.", Toast.LENGTH_SHORT).show();
                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else {
                    lastChecked = null;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
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

package com.example.aaronbrecher.todos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aaronbrecher on 1/31/18.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoAdapterViewHolder> {

    @Override
    public TodoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TodoAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TodoAdapterViewHolder extends RecyclerView.ViewHolder{

        public TodoAdapterViewHolder(View itemView) {
            super(itemView);
        }
    }
}

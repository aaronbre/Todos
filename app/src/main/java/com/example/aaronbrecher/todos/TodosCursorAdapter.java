package com.example.aaronbrecher.todos;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.aaronbrecher.todos.data.TodoContract.TodosEntry;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by aaronbrecher on 2/1/18.
 */

public class TodosCursorAdapter extends CursorAdapter {

    public TodosCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //get all the view items
        TextView tvTitle = view.findViewById(R.id.list_item_title);
        TextView tvPriority = view.findViewById(R.id.list_item_priority);
        TextView tvDateCreated = view.findViewById(R.id.list_item_date_created);
        TextView tvCategory = view.findViewById(R.id.list_item_category);

        //get all the data from the cursors some will need additional formatting
        String title = cursor.getString(cursor.getColumnIndex(TodosEntry.COLUMN_TODO_TITLE));

        //gets a string for the category using custom function
        int categoryNum = cursor.getInt(cursor.getColumnIndex(TodosEntry.COLUMN_TODO_CATEGORY));
        String category = Utils.getCategoryString(categoryNum);
        int categoryColor = Utils.getColor(categoryNum, context);
        int priority = cursor.getInt(cursor.getColumnIndex(TodosEntry.COLUMN_TODO_PRIORITY));
        long dateD = cursor.getLong(cursor.getColumnIndex(TodosEntry.COLUMN_TODO_DATE_DUE));
        //format the dates to a string
        String dateDue = Utils.formatDate(dateD);

        //perform the actual binding of the views to the data
        tvTitle.setText(title);
        tvPriority.setText(String.valueOf(priority));
        tvDateCreated.setText("Due - " + dateDue);
        tvCategory.setText(category);
        tvCategory.setBackgroundColor(categoryColor);
    }

}

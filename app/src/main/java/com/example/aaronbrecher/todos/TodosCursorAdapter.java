package com.example.aaronbrecher.todos;

import android.content.Context;
import android.database.Cursor;
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
        TextView tvDateDue = view.findViewById(R.id.list_item_date_due);

        //get all the data from the cursors some will need additional formatting
        String title = cursor.getString(cursor.getColumnIndex(TodosEntry.COLUMN_TODO_TITLE));

        //gets a string for the category using custom function
        String category = getCategoryString(cursor.getInt(cursor.getColumnIndex(TodosEntry.COLUMN_TODO_CATEGORY)));
        int priority = cursor.getInt(cursor.getColumnIndex(TodosEntry.COLUMN_TODO_PRIORITY));
        long dateC = cursor.getLong(cursor.getColumnIndex(TodosEntry.COLUMN_TODO_DATE_CREATED));
        long dateD = cursor.getLong(cursor.getColumnIndex(TodosEntry.COLUMN_TODO_DATE_DUE));
        //format the dates to a string
        String dateCreated = formatDate(dateC);
        String dateDue = formatDate(dateD);

        //perform the actual binding of the views to the data
        tvTitle.setText(title);
        tvPriority.setText(String.valueOf(priority));
        tvDateCreated.setText(dateCreated);
        tvDateDue.setText(dateDue);
        tvCategory.setText(category);
    }

    /**
     * Simple function to determine the english word for the category, uses constants
     * defined in the TODOS Contract
     * @param cat the number representation of the category
     * @return The english word associated to that number
     */
    private String getCategoryString(int cat){
        String category;
        switch (cat){
            case TodosEntry.CATEGORY_FINANCES:
                category = "Finance";
                break;
            case TodosEntry.CATEGORY_FIXING:
                category = "Fixing";
                break;
            case TodosEntry.CATEGORY_HOME:
                category = "Home";
                break;
            case TodosEntry.CATEGORY_SHOPPING:
                category = "Shopping";
                break;
            case TodosEntry.CATEGORY_WORK:
                category = "Work";
                break;
            case TodosEntry.CATEGORY_OTHER:
            default:
                category = "Other";
        }
        return category;
    }

    /**
     * Format the date from a unixDate to a String readable date
     * @param unixDate the number representing seconds etc
     * @return a formatted date string
     */
    String formatDate(long unixDate){
        String dateString;

        Date date = new Date(unixDate * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
        dateString = sdf.format(date);
        return dateString;
    }
}

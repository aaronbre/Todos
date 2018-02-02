package com.example.aaronbrecher.todos.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aaronbrecher on 1/31/18.
 */

public class TodoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todosDB";
    private static final int DATABASE_VERSION = 1;

    //Constant with the intial database statements
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TodoContract.TodosEntry.TABLE_NAME + " (" +
            TodoContract.TodosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TodoContract.TodosEntry.COLUMN_TODO_TITLE + " TEXT NOT NULL, " +
            TodoContract.TodosEntry.COLUMN_TODO_DESCRIPTION + " TEXT, " +
            TodoContract.TodosEntry.COLUMN_TODO_CATEGORY + " INTEGER NOT NULL DEFAULT 0, " +
            TodoContract.TodosEntry.COLUMN_TODO_COMPLETED + " BOOLEAN DEFAULT FALSE, " +
            TodoContract.TodosEntry.COLUMN_TODO_DATE_CREATED + " INTEGER, " +
            TodoContract.TodosEntry.COLUMN_TODO_PRIORITY + " INTEGER DEFAULT 0, " +
            TodoContract.TodosEntry.COLUMN_TODO_DATE_DUE + " INTEGER)";

    public TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

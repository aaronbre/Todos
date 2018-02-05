package com.example.aaronbrecher.todos;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aaronbrecher.todos.data.TodoContract;
import com.example.aaronbrecher.todos.data.TodoDbHelper;

public class TodoDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private TextView mTvTitle;
    private TextView mTvDescription;
    private TextView mTvCategory;
    private TextView mTvDateDue;
    private TextView mTvDateCreated;
    private TextView mTvPriority;

    private Button mBtnEdit;
    private Button mBtnMarkComplete;
    private Button mBtnDelete;

    private Uri mTodoUri;

    private final int DETAILS_LOADER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        mTvTitle = (TextView)findViewById(R.id.details_title);
        mTvDescription = (TextView)findViewById(R.id.details_description);
        mTvCategory = (TextView)findViewById(R.id.details_category);
        mTvDateDue = (TextView)findViewById(R.id.details_date_due);
        mTvDateCreated = (TextView)findViewById(R.id.details_date_created);
        mTvPriority = (TextView)findViewById(R.id.details_priority);

        mBtnDelete = (Button)findViewById(R.id.details_button_delete);
        mBtnEdit = (Button)findViewById(R.id.details_button_edit);
        mBtnMarkComplete = (Button)findViewById(R.id.details_button_mark_complete);

        mTodoUri = getIntent().getData();
        getLoaderManager().initLoader(DETAILS_LOADER, null, this);




        mBtnMarkComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(TodoContract.TodosEntry.COLUMN_TODO_COMPLETED, true);
                getContentResolver().update(mTodoUri, values, null, null);
            }
        });

        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodoDetailActivity.this, AddTodoActivity.class);
                intent.setData(mTodoUri);
                startActivity(intent);
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().delete(mTodoUri, null, null);
                finish();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case DETAILS_LOADER:
                return new CursorLoader(
                        TodoDetailActivity.this,
                        mTodoUri,
                        null,
                        null,
                        null,
                        null
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst()){
            int titleIndex = data.getColumnIndex(TodoContract.TodosEntry.COLUMN_TODO_TITLE);
            int categoryIndex = data.getColumnIndex(TodoContract.TodosEntry.COLUMN_TODO_CATEGORY);
            int descriptionIndex = data.getColumnIndex(TodoContract.TodosEntry.COLUMN_TODO_DESCRIPTION);
            int priorityIndex = data.getColumnIndex(TodoContract.TodosEntry.COLUMN_TODO_PRIORITY);
            int dateDueIndex = data.getColumnIndex(TodoContract.TodosEntry.COLUMN_TODO_DATE_DUE);
            int dateCreatedIndex = data.getColumnIndex(TodoContract.TodosEntry.COLUMN_TODO_DATE_CREATED);

            setText(mTvTitle, data.getString(titleIndex));
            setText(mTvDescription, data.getString(descriptionIndex));
            setText(mTvPriority, String.valueOf(data.getInt(priorityIndex)));
            setText(mTvDateDue, Utils.formatDate(data.getLong(dateDueIndex)));
            setText(mTvDateCreated, Utils.formatDate(data.getLong(dateCreatedIndex)));
            setText(mTvCategory, data.getString(categoryIndex));
        }
    }

    private void setText(TextView view, String text){
        view.setText(text);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

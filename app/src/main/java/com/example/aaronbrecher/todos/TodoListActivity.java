package com.example.aaronbrecher.todos;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aaronbrecher.todos.data.TodoContract.TodosEntry;

public class TodoListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView mListView;
    private TextView mEmptyView;
    private TodosCursorAdapter mCursorAdapter;

    private final int TODOS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TodoListActivity.this, AddTodoActivity.class);
                startActivity(intent);
            }
        });

        mEmptyView = (TextView)findViewById(R.id.empty_view);
        mListView = (ListView)findViewById(R.id.lv_todo_list);
        mListView.setEmptyView(mEmptyView);

        mCursorAdapter = new TodosCursorAdapter(this, null);
        mListView.setAdapter(mCursorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TodoListActivity.this, TodoDetailActivity.class);
                intent.setData(ContentUris.withAppendedId(TodosEntry.CONTENT_URI, id));
                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(TODOS_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // If the user selected the new todos will open the editor activity
            case R.id.delete_all:
            deletePets();
            return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void deletePets(){
        getContentResolver().delete(TodosEntry.CONTENT_URI, null, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case TODOS_LOADER:
                return new CursorLoader(
                        TodoListActivity.this,
                        TodosEntry.CONTENT_URI,
                        getProjection(),
                        null,
                        null,
                        null);
                default:
                    return null;
        }
    }

    private String[] getProjection(){
        return new String[]{
                TodosEntry._ID,
                TodosEntry.COLUMN_TODO_TITLE,
                TodosEntry.COLUMN_TODO_PRIORITY,
                TodosEntry.COLUMN_TODO_DATE_CREATED,
                TodosEntry.COLUMN_TODO_DATE_DUE,
                TodosEntry.COLUMN_TODO_CATEGORY,
                TodosEntry.COLUMN_TODO_COMPLETED
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}

package com.example.aaronbrecher.todos.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by aaronbrecher on 1/31/18.
 */

public class TodoProvider extends ContentProvider {

    private static final String LOG = TodoProvider.class.getSimpleName();

    //Final for the Todos database - to be used by the UriMatcher
    private static final int TODOS = 100;

    //Final for the Todos single row - to be used by the UriMatcher
    private static final int TODOS_ID = 101;

    //Member variable for the uri matcher
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    TodoDbHelper mDbHelper;

    static {
        //Uri for the whole todos table
        mUriMatcher.addURI(TodoContract.CONTENT_AUTHORITY, TodoContract.PATH_TODOS, TODOS);
        //Uri for a specific todos
        mUriMatcher.addURI(TodoContract.CONTENT_AUTHORITY, TodoContract.PATH_TODOS + "/#", TODOS_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new TodoDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //get a readable instance of the DB
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;
        int match = mUriMatcher.match(uri);

        switch (match){

            //For quering the entire database.
            case TODOS:
                cursor = database.query(TodoContract.TodosEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            //For querying a specific id
            case TODOS_ID:
                //if we are quering a single id we set the selction to ID=?
                selection = TodoContract.TodosEntry._ID + "=?";
                //and set selectionArgs to the appended integer on the uri
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(TodoContract.TodosEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot Query unkown URI" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = mUriMatcher.match(uri);
        switch (match){
            case TODOS:
                return TodoContract.TodosEntry.CONTENT_LIST_TYPE;
            case TODOS_ID:
                return TodoContract.TodosEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unkown Uri " + uri + "with match" + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = mUriMatcher.match(uri);
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(TodoContract.TodosEntry.TABLE_NAME, null, contentValues);

        if(id == -1){
            Log.e(TAG, "insert into database failed for" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = mUriMatcher.match(uri);
        switch (match){
            case TODOS:
                //for deleting the whole list just notify and delete
                getContext().getContentResolver().notifyChange(uri, null);
                return database.delete(TodoContract.TodosEntry.TABLE_NAME, selection, selectionArgs);
            case TODOS_ID:
                //for deleting a single id set the selection to id and the args to the uri's id
                getContext().getContentResolver().notifyChange(uri, null);
                selection = TodoContract.TodosEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(TodoContract.TodosEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Delete not allowed for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = mUriMatcher.match(uri);
        switch (match){
            case TODOS:
                return updateTodo(uri, contentValues, selection, selectionArgs);
            case TODOS_ID:
                // we are only updating one row so set selection to the id field
                selection = TodoContract.TodosEntry._ID + "=?";
                // get the id from the uri
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                //update the pet using the updated selection and selectionArgs
                return updateTodo(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update not allowed for " + uri);
        }
    }

    private int updateTodo(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        getContext().getContentResolver().notifyChange(uri, null);
        return  database.update(TodoContract.TodosEntry.TABLE_NAME, values, selection, selectionArgs);
    }
}

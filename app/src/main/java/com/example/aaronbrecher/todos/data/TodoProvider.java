package com.example.aaronbrecher.todos.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    @Override
    public boolean onCreate() {
        mDbHelper = new TodoDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

package com.example.aaronbrecher.todos.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by aaronbrecher on 1/31/18.
 */

public final class TodoContract {

    //content authority string - uses the package of our app
    public static final String CONTENT_AUTHORITY = "com.example.aaronbrecher.todos";

    //base Uri built from the CONTENT_AUTHORITY -  basically just preppend the content schema to the content authority
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //specific table paths to append to the base Uri
    public static final String PATH_TODOS = "todos";

    public TodoContract(){/**empty Constructer all will be static */}

    public final static class TodosEntry implements BaseColumns{

        public static final String TABLE_NAME = "todos";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TODOS);

        //MIME types for the table and single row
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TODOS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TODOS;


        //table columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TODO_TITLE = "title";
        public static final String COLUMN_TODO_CATEGORY  = "category";
        public static final String COLUMN_TODO_DESCRIPTION = "description";
        public static final String COLUMN_TODO_DATE_DUE = "dateDue";
        public static final String COLUMN_TODO_DATE_CREATED = "dateCreated";
        public static final String COLUMN_TODO_COMPLETED = "completed";
        public static final String COLUMN_TODO_PRIORITY = "priority";

        //values for categories
        public static final int CATEGORY_OTHER = 0;
        public static final int CATEGORY_WORK = 1;
        public static final int CATEGORY_HOME = 2;
        public static final int CATEGORY_SHOPPING = 3;
        public static final int CATEGORY_FIXING = 4;
        public static final int CATEGORY_FINANCES = 5;

    }
}

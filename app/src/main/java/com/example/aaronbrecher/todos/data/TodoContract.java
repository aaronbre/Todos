package com.example.aaronbrecher.todos.data;

import android.provider.BaseColumns;

/**
 * Created by aaronbrecher on 1/31/18.
 */

public final class TodoContract {

    public TodoContract(){/**empty Constructer all will be static */}

    public final class TodosEntry implements BaseColumns{

        public static final String TABLE_NAME = "todos";

        //table columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TODO_TITLE = "title";
        public static final String COLUMN_TODO_CATEGORY  = "category";
        public static final String COLUMN_TODO_DESCRIPTION = "description";
        public static final String COLUMN_TODO_DATE_DUE = "dateDue";
        public static final String COLUMN_TODO_DATE_CREATED = "dateCreated";
        public static final String COLUMN_TODO_COMPLETED = "completed";

        //values for categories
        public static final int CATEGORY_OTHER = 0;
        public static final int CATEGORY_WORK = 1;
        public static final int CATEGORY_HOME = 2;
        public static final int CATEGORY_SHOPPING = 3;
        public static final int CATEGORY_FIXING = 4;
        public static final int CATEGORY_FINANCES = 5;

    }
}

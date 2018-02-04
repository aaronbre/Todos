package com.example.aaronbrecher.todos;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aaronbrecher.todos.data.TodoContract;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

public class AddTodoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, LoaderManager.LoaderCallbacks<Cursor>{

    private final int TODO_EDITOR_LOADER = 2;

    private EditText mTitleText;
    private EditText mDescriptionText;
    private EditText mDueDateText;
    private EditText mPriorityText;

    private long mUnixDueDate;

    private Spinner mCategorySpinner;
    private int mCategory;
    private Uri mCurrentTodoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        //set up the spinner
        mCategorySpinner = (Spinner)findViewById(R.id.spinner_category);
        setUpSpinner();

        //set up member variables to point to the UI components
        mTitleText = (EditText)findViewById(R.id.editor_title);
        mDueDateText = (EditText)findViewById(R.id.editor_due_date);
        mDescriptionText = (EditText)findViewById(R.id.editor_description);
        mPriorityText = (EditText)findViewById(R.id.editor_priority);

        // Set a listener to check if the date field has come into focus
        // if so will open the choose date dialog which we will use to set
        // the date
        mDueDateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDatePickerDialog();
                    //after the date has been picked remove the focus so cannot edit it text mode
                    v.clearFocus();
                }
            }
        });

        //Get the Uri if we are editting the todo
        mCurrentTodoUri = getIntent().getData();

        if(mCurrentTodoUri == null){
            setTitle("Create new Todo");
        }
        else {
           setTitle("Edit Todo");
           getLoaderManager().initLoader(TODO_EDITOR_LOADER, null, this);
        }
    }

    private void setUpSpinner(){
        // Create an ArrayAdapter to set up the spinner using the array from the resource files uses the android
        // layout for the item layout
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_category_options, android.R.layout.simple_spinner_item);

        // Set the dropdown layout using native android layout
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //set the adapter to the spinner
        mCategorySpinner.setAdapter(categorySpinnerAdapter);

        // Set the spinner to update the category on selecting an item
        // Uses the text value to determine the corresponding number
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String)parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection)){
                    switch (selection){
                        case "Work":
                            mCategory = TodoContract.TodosEntry.CATEGORY_WORK;
                            break;
                        case "Home":
                            mCategory = TodoContract.TodosEntry.CATEGORY_HOME;
                            break;
                        case "Other":
                            mCategory = TodoContract.TodosEntry.CATEGORY_OTHER;
                            break;
                        case "Repairs":
                            mCategory = TodoContract.TodosEntry.CATEGORY_FIXING;
                            break;
                        case "Finances":
                            mCategory = TodoContract.TodosEntry.CATEGORY_FINANCES;
                            break;
                        case "Shopping":
                            mCategory = TodoContract.TodosEntry.CATEGORY_SHOPPING;
                            break;

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    /**
     * Funciton to add the todos to the database
     *
     */
    private void insertTodo(){
        String todoTitle = mTitleText.getText().toString().trim();
        String todoDescription = mDescriptionText.getText().toString().trim();
        long todoDateStart = System.currentTimeMillis()/1000;
        boolean todoCompleted = false;
        //parse the priority to get an intger number if the field is empty will be null
        //in that case will set to default of 0
        Integer todoPriority = parsePriority(mPriorityText.getText().toString().trim());
        if(todoPriority == null) todoPriority = 0;
        ContentValues values = createContentValues(todoTitle, todoDescription, todoPriority, todoDateStart, mUnixDueDate);
        Uri uri = getContentResolver().insert(TodoContract.TodosEntry.CONTENT_URI, values);
        if (uri != null) Toast.makeText(this, getString(R.string.successfull_database_add_toast) , Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, getString(R.string.unsuccessfull_database_add_toast), Toast.LENGTH_SHORT).show();

    }

    /**
     * Parses the priority and returns the integer value - will return null if an empty space
     * @param s EditText text value
     * @return Integer value of the field
     */
    private Integer parsePriority(String s){
        Integer priority;
        try{
            priority = Integer.parseInt(s);
            return priority;
        }catch (NumberFormatException e){
            return null;
        }
    }

    /**
     * Function to create the Content Value object to be used in insert command
     * @param title
     * @param description
     * @param priority
     * @param startDate
     * @param dueDate
     * @return
     */
    ContentValues createContentValues(String title, String description, int priority, long startDate, long dueDate){
        ContentValues values = new ContentValues();
        values.put(TodoContract.TodosEntry.COLUMN_TODO_TITLE, title);
        values.put(TodoContract.TodosEntry.COLUMN_TODO_DESCRIPTION, description);
        values.put(TodoContract.TodosEntry.COLUMN_TODO_PRIORITY, priority);
        values.put(TodoContract.TodosEntry.COLUMN_TODO_DATE_CREATED, startDate);
        values.put(TodoContract.TodosEntry.COLUMN_TODO_DATE_DUE, dueDate);
        values.put(TodoContract.TodosEntry.COLUMN_TODO_CATEGORY, mCategory);
        values.put(TodoContract.TodosEntry.COLUMN_TODO_COMPLETED, false);
        return values;
    }

    /**
     * Function to show the dateDialog (using the dateDialogFragment)
     */
    private void showDatePickerDialog(){
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_todo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_todo_save:
                insertTodo();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * capture the date from the dateDialogFragment to use it in this activity
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dateText = dayOfMonth + "/" + month + "/" + year;
        mDueDateText.setText(dateText);
        Calendar calendar = new GregorianCalendar(year,month,dayOfMonth);
        mUnixDueDate = calendar.getTimeInMillis()/1000;
        Log.d("DATE", "onDateSet: " + mUnixDueDate);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case TODO_EDITOR_LOADER:
                return new CursorLoader(
                        AddTodoActivity.this,
                        mCurrentTodoUri,
                        getProjection(),
                        null,
                        null,
                        null
                );
            default:
                return null;
        }
    }

    private String[] getProjection(){
        return new String[]{
                TodoContract.TodosEntry.COLUMN_TODO_TITLE,
                TodoContract.TodosEntry.COLUMN_TODO_DESCRIPTION,
                TodoContract.TodosEntry.COLUMN_TODO_CATEGORY,
                TodoContract.TodosEntry.COLUMN_TODO_PRIORITY,
                TodoContract.TodosEntry.COLUMN_TODO_DATE_DUE
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst()){
            int titleIndex = data.getColumnIndex(TodoContract.TodosEntry.COLUMN_TODO_TITLE);
            mTitleText.setText(data.getString(titleIndex));

            int descriptionIndex = data.getColumnIndex(TodoContract.TodosEntry.COLUMN_TODO_DESCRIPTION);
            mDescriptionText.setText(data.getString(descriptionIndex));

            int priorityIndex = data.getColumnIndex(TodoContract.TodosEntry.COLUMN_TODO_PRIORITY);
            mPriorityText.setText(String.valueOf(data.getInt(priorityIndex)));

            int dateDueIndex = data.getColumnIndex(TodoContract.TodosEntry.COLUMN_TODO_DATE_DUE);
            mDueDateText.setText(Utils.formatDate(data.getLong(dateDueIndex)));

            int categoryIndex = data.getColumnIndex(TodoContract.TodosEntry.COLUMN_TODO_CATEGORY);
            setCategorySpinnerToIndex(data.getInt(categoryIndex));
        }
    }

    private void setCategorySpinnerToIndex(int index){
        mCategorySpinner.setSelection(index);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}

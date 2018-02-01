package com.example.aaronbrecher.todos;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
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

import com.example.aaronbrecher.todos.data.TodoContract;

import java.util.Calendar;
import java.util.zip.Inflater;

public class AddTodoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText mTitleText;
    private EditText mDescriptionText;
    private EditText mDueDateText;
    private EditText mPriorityText;

    private Spinner mCategorySpinner;
    private int mCategory;

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
                }
            }
        });
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
            public void onNothingSelected(AdapterView<?> parent) { mCategory = TodoContract.TodosEntry.CATEGORY_OTHER;}
        });
    }

    /**
     * Funciton to add the todos to the database
     * TODO implement this function
     */
    private void insertTodo(){
        String todoTitle = mTitleText.getText().toString().trim();
        String todoDescription = mDescriptionText.getText().toString().trim();
        Integer todoPriority = parsePriority(mDescriptionText.getText().toString());
    }

    /**
     * Parses the priority and returns the integer value - will return null if not a number
     * @param s
     * @return
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
                //save the todos
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * capture the date from the dateDialogFragment to use it in this activity
     * TODO get the date in a textual format to set the textField, and get the date UNIX code to be used in the database
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dateText = dayOfMonth + "/" + month + "/" + year;
        mDueDateText.setText(dateText);
    }
}

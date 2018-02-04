package com.example.aaronbrecher.todos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TodoDetailActivity extends AppCompatActivity {
    private TextView mTvTitle;
    private TextView mTvDescription;
    private TextView mTvCategory;
    private TextView mTvDateDue;
    private TextView mTvDateCreated;
    private TextView mTvPriority;

    private Button mBtnEdit;
    private Button mBtnMarkComplete;
    private Button mBtnDelete;

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
    }
}

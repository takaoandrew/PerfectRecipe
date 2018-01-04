package com.github.wrdlbrnft.searchablerecyclerviewdemo.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.wrdlbrnft.searchablerecyclerviewdemo.R;

public class DetailActivity extends AppCompatActivity {

    private TextView mTaskNameDisplay;
    private String mTaskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mTaskNameDisplay = (TextView) findViewById(R.id.tv_task_name);

        Intent intent = getIntent();
        mTaskName = intent.getStringExtra(Intent.EXTRA_TEXT);
        mTaskNameDisplay.setText(mTaskName);

    }
}
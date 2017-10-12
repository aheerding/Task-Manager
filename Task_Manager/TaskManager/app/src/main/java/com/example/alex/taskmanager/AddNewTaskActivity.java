package com.example.alex.taskmanager;

import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNewTaskActivity extends AppCompatActivity implements View.OnClickListener{

    EditText txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        txtDate = (EditText)findViewById(R.id.in_date);

        txtDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        //get current date
        final Calendar c = Calendar.getInstance();

    }
}

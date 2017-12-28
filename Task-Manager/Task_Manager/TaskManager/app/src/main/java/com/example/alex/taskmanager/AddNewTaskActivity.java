package com.example.alex.taskmanager;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddNewTaskActivity extends AppCompatActivity{

    static EditText txtDate;
    static EditText txtTask;

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_new_task_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTask = (EditText) findViewById(R.id.in_task);
        txtDate.setShowSoftInputOnFocus(false);

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitionDatePickerDialog(v);
            }
        });
    }

    //fragment to display the DatePicker
    @TargetApi(24)
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //get current date and set as default when the datepicker opens
            final Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);

            //create datepickerdialog and return it
            return new DatePickerDialog(getActivity(), this, y, m, d);
        }

        public void onDateSet(DatePicker datePicker, int year, int month, int day){
            //set the textview to the date selected
            txtDate.setText(month + "/" + day +"/" + year);
        }
    }


    public void showTruitionDatePickerDialog(View v){
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    //write task object to a file

    //Save the new task and return to the main activity
    public void saveNewTask(View view){
        //get the task title and the date as strings to be saved
        String taskName = txtTask.getText().toString();
        String taskDate = txtDate.getText().toString();

        //create new task object
        Task newTask = new Task(taskName, taskDate, false);

        //return to the main activity
        finish();
    }
}

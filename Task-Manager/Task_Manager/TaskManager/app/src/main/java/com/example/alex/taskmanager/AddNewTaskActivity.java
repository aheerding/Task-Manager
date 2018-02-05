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

import java.io.EOFException;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import android.util.Log;

public class AddNewTaskActivity extends AppCompatActivity{

    static EditText txtDate;
    static EditText txtTask;
    final String TASKFILENAME = "Tasks.dat";
    final String TAG = "AddNewTaskActivity";


    protected ArrayList<Task> tasks;

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

        //create new vector of tasks
        tasks = new ArrayList<Task>();

        //TODO: Check to see if there is already a saved Vector and assign to tasks

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
    public void addNewTask(View view){
        //get the task title and the date as strings to be saved
        String taskName = txtTask.getText().toString();
        String taskDate = txtDate.getText().toString();

        //create new task object
        Task newTask = new Task(taskName, taskDate,false);
        String path = getFilesDir() +"/"+ TASKFILENAME;
        File saveFile = new File(path);

        //check to see if the task file exists
        try{
            if(saveFile.createNewFile()){
                //write object to file since file was just created and is empty
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
                tasks.add(newTask);
                out.writeObject(tasks);
                out.flush();
                out.close();
            } else {
                //if the file already exists open a stream and read from it
                InputStream is = new FileInputStream(saveFile);
                ObjectInputStream oin = new ObjectInputStream(is);
                try{
                    tasks = (ArrayList<Task>)oin.readObject();
                } catch (EOFException e){
                    e.printStackTrace();
                }

                oin.close();

                tasks.add(newTask);

                ObjectOutputStream out =  new ObjectOutputStream(new FileOutputStream(path));
                out.writeObject(tasks);
                out.flush();
                out.close();

                //System.out.println(tasks.size());
            }
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        for(Task t : tasks){
            System.out.println(t.toString());
        }

        //return to the main activity
        finish();
    }
}

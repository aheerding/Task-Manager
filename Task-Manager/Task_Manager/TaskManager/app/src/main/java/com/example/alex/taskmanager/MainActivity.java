package com.example.alex.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final String TASKFILENAME = "Tasks.dat";
    private ListView lvTasks;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize the listView
        lvTasks = (ListView) findViewById(R.id.task_list_view);
        final ArrayList<Task> tasks = readList();
        String[] listItems = new String[tasks.size()];

        for(int i = 0; i < tasks.size(); i++){
            Task t = tasks.get(i);
            listItems[i] = t.getTaskName();
        }

        ArrayAdapter arrAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        lvTasks.setAdapter(arrAdapt);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                /* Open the add new task Activity*/
                Intent intent = new Intent(MainActivity.this, AddNewTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //retrieve the task list
    public ArrayList<Task> readList(){
        ArrayList<Task> taskList = new ArrayList<Task>();
        try{
            String path = getFilesDir() + "/" + TASKFILENAME;
            File saveFile = new File(path);
            InputStream is = new FileInputStream(saveFile);
            ObjectInputStream oin = new ObjectInputStream(is);
            try{
                taskList = (ArrayList<Task>)oin.readObject();
            } catch (EOFException e){
                e.printStackTrace();
            }

            oin.close();
        } catch (IOException | ClassNotFoundException ex){
            ex.printStackTrace();;
            Log.e(TAG, ex.toString());
        }

        return taskList;
    }
}

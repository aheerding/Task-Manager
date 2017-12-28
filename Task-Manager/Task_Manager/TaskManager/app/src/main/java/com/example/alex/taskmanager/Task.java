package com.example.alex.taskmanager;

/**
 * Created by Alex on 12/24/2017.
 * com.example.alex.taskmanager.Task class to store data about created tasks
 */

class Task {
    //attributes
    private String taskName;
    private String taskDueDate;
    private boolean taskDone;

    //default constructor
    public Task(){
        taskName = "com.example.alex.taskmanager.Task";
        taskDueDate = "";
        taskDone = false;
    }

    //constructor takes in task name, due date, and whether or not it's done
    public Task(String _taskName, String _taskDueDate, boolean _taskDone){
        taskName = _taskName;
        taskDueDate = _taskDueDate;
        taskDone = _taskDone;
    }

    //write object to file
    public void writeTask(){

    }

    //getters
    public String getTaskName(){ return taskName; }

    public String getTaskDueDate() { return taskDueDate; }

    public boolean getTaskDone() { return taskDone; }

    //setters
    public void setTaskName(String _taskName){ taskName = _taskName; }

    public void setTaskDueDate(String _taskDueDate) { taskDueDate = _taskDueDate; }

    public void setTaskDone (boolean _taskDone) { taskDone = _taskDone; }
}

package com.title51.TaskAlert;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.title51.TaskAlert.R;
import com.title51.TaskAlert.R.id;
import com.title51.TaskAlert.R.layout;
import com.title51.TaskAlert.Alarm.AlarmList;
import com.title51.TaskAlert.Task.Task;
import com.title51.TaskAlert.Task.TaskIntentFields;
import com.title51.TaskAlert.Task.TaskRow;
import com.title51.TaskAlert.XML.XmlReaderWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TaskAlertActivity extends Activity implements TaskIntentFields  {
    /** Called when the activity is first created. */
	
	//TODO change taskrow
	private LinearLayout m_layout_container;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //TODO find out how to get Layout objects from other classes while view is being built
        m_layout_container=(LinearLayout)findViewById(R.id.llContainer);
        
        
        //TODO: Test/Debug only
        //testXML();
    }
    //TODO: On Configuration change
    
    /*
     * TODO 
     * Pass Object to create task
     	* null if creating new
     	* not null if editing
     */	
    public void onClickCreateTask(View v) {
    	Intent create_task = new Intent();
        create_task.setClass(TaskAlertActivity.this,CreateTask.class);
        startActivityForResult(create_task,CREATE_TASK);
    }
    
    //TODO: change how we get info from create task
    protected void onActivityResult(int request_code, int result_code, Intent data)
    {
    	switch(request_code) {
	    	case CREATE_TASK:
	    		//update task list as necessary
	    		
	    		break;
	    	default:
	    		break;
    	}
    
    }
    
    //TODO change to Next_Alarm info instead of num alarms
    public void addRowView(Task task) {
    	String name = task.getName();
        TaskRow task_row = new TaskRow(getApplicationContext());
        task_row.setName(name);
        View view = (View) task_row.getRowView();
        m_layout_container.addView(view);
    }
    
    /*
     * TODO
     * Parse object out of intent
     */
    private void parseTask(Intent data) {
    	Bundle extras = data.getExtras();
    	
    	String name = extras.getString(TASK_NAME);
    	int option= extras.getInt(UPDATE_FLAG);
    	
    	switch(option) {
    		case ADD_TASK:
    			//addRowView(name);
    			break;
    		case EDIT_TASK:
    			break;
    		case NO_TASK:
    			break;
    		default:
    			break;
    	}
    }
    
    /*
     * Test XML functions
     */
    private void testXML() {
    	XmlReaderWriter xml = new XmlReaderWriter();
    	
    	ArrayList<Task> task_list = null;//testList();
    	//printTasks(task_list);
    	
    	/*
    	 * Create XML
    	 */
    	//xml.writeFile(getApplicationContext(), task_list);
    	
    	task_list = null;
    	/*
    	 * Read XML
    	 */
    	task_list = xml.getTaskList(getApplicationContext());
    	
    	displayTaskList(task_list);
    	printTasks(task_list);
    }
    
    /*
     * Create fake test data to test XML
     */
    private ArrayList<Task> testList() {
    	ArrayList<Task> task_list = new ArrayList<Task>();
    	
    	for(int counter = 0; counter < 10; counter++) {
    		int task_id = counter;
    		String task_name = "Task_" + task_id;
    	   	Task new_task = new Task(task_name, task_id);
    	   	
    	   	AlarmList alarm_list = new AlarmList();
    	   	//every other has alarm list
    	   	if(task_id%2==0) {
    	   		for(int alarm_counter=0; alarm_counter<5; alarm_counter++) {
	    	   		int alarm_id = task_id*100 + alarm_counter; 
	    	   		alarm_list.addAlarm(alarm_id, null, null);
    	   		}
    	   	}
    	   	
    	   	new_task.setAlarmList(alarm_list);
    	   	
    	   	task_list.add(new_task);
    	}

    	return task_list;
    }
    
    private void displayTaskList(ArrayList<Task> task_list) {
    	int num_tasks = task_list.size();
    	
    	for(int i=0; i<num_tasks; i++) {
    		Task current_task = task_list.get(i);
    		addRowView(current_task);
    	}
    }
    
    private void printTasks(ArrayList<Task> task_list) {
    	int num_tasks = task_list.size();
    	toast("Num_tasks: " + num_tasks);
    	for(int i=0; i<num_tasks; i++) {
    		Task current_task = task_list.get(i);
    		String name = current_task.getName();
    		toast("Task: " + name);
    	}
    }
    
    private void toast(String message) {
    	Toast.	makeText (getApplicationContext(), message, Toast.LENGTH_SHORT).show ();
    }
}
package com.title51.TaskAlert;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateTask extends Activity {
	//TODO:
	private Task m_task = null;
	
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_form);
        
    }
    
    public void checkRecurring(View v)
    {
    	CheckBox  cb_recurring = (CheckBox) v;
    	
    	if(cb_recurring == null) {
    		//ERROR
    		toast("Error: checkbox is null");
    	}
    	
    	//make recurring
    	if(cb_recurring.isChecked()) {
    		toast("Recurring");
    	} else {
    		//not recurring
    		toast("NOT Recurring");
    	}
    }
    
    public void onClickCreateTask (View v){
    	//toast("Click Create Task");
    	
    	long current_time, no_get, alarm_time;
    	//TODO get name from ui
    	String task_name = "TEST";
    	/*
    	 * Create Intent and Pending Intent to start alarm service
    	 */
    	Intent myIntent = new Intent(CreateTask.this, AlarmService.class);
    	//TODO: Request code, need to keep changing this
    	PendingIntent pending_intent;
    	pending_intent = PendingIntent.getService(CreateTask.this, 1452145, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
	
    	/*
    	 * TODO: Get date/repeating info from input
    	 */
    	GregorianCalendar calendar = new GregorianCalendar();
    	calendar.add(Calendar.SECOND, 5);
    	alarm_time = calendar.getTimeInMillis();
    	
    	/*
    	 * Create List of Alarms for the Task
    	 */
    	AlarmInfo alarm = new AlarmInfo(task_name, calendar);
    	
    	AlarmInfoList alarm_list = new AlarmInfoList();
    	alarm_list.addAlarm(alarm);
    	/*
    	 * TODO Start alarms
    	 */
    	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_time, pending_intent);
    	   
    	/*
    	 * Create Task with inputted info, including alarm that 
    	 */
    	m_task = new Task(task_name, alarm_list, pending_intent);
    	
    	AlarmInfoList list = m_task.getAlarmList(); 
    	int list_size = list.getNumAlarms();
    	AlarmInfo task_alarm = list.getAlarm(list_size);
    	
    	String name = m_task.getName();
    	String message = "Alarms Created: " + String.valueOf(list_size-1) + " name of last alarm: " + String.valueOf(name);
    	toast(message);
    } 
    
    //TODO change how task alarm is turned off
    public void onClickCancelTask (View v){
    	if(m_task == null) {
    		toast("No current task!");
    		return;
    	}
    	
    	PendingIntent cancel_intent = m_task.getIntent();
    	
    	AlarmManager alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	alarm_manager.cancel(cancel_intent);
    	
    	toast("Task Alarm Cancelled");
    }
    
    private void toast(String message) {
    	Toast.makeText (getApplicationContext(), message, Toast.LENGTH_SHORT).show ();
    }
}

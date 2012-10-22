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
    	
    	Intent myIntent = new Intent(CreateTask.this, AlarmService.class);

    	//TODO: Request code, need to keep changing this
    	PendingIntent pending_intent;
    	//getService(context, requestCode, intent, flags)
    	pending_intent = PendingIntent.getService(CreateTask.this, 1452145, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
	
    	GregorianCalendar calendar = new GregorianCalendar();
    	calendar.add(Calendar.SECOND, 5);
    	alarm_time = calendar.getTimeInMillis();
    	
    	m_task = new Task("Tetst", "Test Task", alarm_time, pending_intent);
    	
    	//String str_alarm =  calendar.getTime().toString();
    	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_time, pending_intent);
    	       
    	toast("Alarm Created");
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

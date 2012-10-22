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
	
	//used for alarm service
	private PendingIntent pendingIntent;
	
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
    	
    	Intent myIntent = new Intent(this, AlarmService.class);

    	//getService(context, requestCode, intent, flags)
    	pendingIntent = PendingIntent.getService(CreateTask.this, 1452145, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);

    	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

    	
    	GregorianCalendar calendar = new GregorianCalendar();
    	String str_time = calendar.getTime().toString();
    	// calendar = Calendar.getInstance();
//TODO    	Calendar calendar2 = Calendar.getInstance();
    	
    	calendar.setTimeInMillis(System.currentTimeMillis());
    	current_time = calendar.getTimeInMillis();
    	
    	
//TODO    	no_get = calendar2.getTimeInMillis();
    	
    	calendar.add(Calendar.SECOND, 10);
    	alarm_time = calendar.getTimeInMillis();
    	String str_alarm =  calendar.getTime().toString();
    	alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_time, pendingIntent);

    	       
    	toast("Current Time " + current_time + "   Alarm Time: " + alarm_time);

    	
    	EditText et_one = (EditText) findViewById(R.id.name_edit);
    	//TextView et_two = (TextView) findViewById(R.id.description_header);
    	EditText et_three = (EditText) findViewById(R.id.description_edit);
    	
    	et_one.setText(str_time);//String.valueOf(current_time));
    	//et_two.setText(String.valueOf(no_get));
    	et_three.setText(str_alarm);//String.valueOf(alarm_time));
    } 
    
    
    private void toast(String message) {
    	Toast.makeText (getApplicationContext(), message, Toast.LENGTH_LONG).show ();
    }
}

package com.title51.TaskAlert;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.title51.TaskAlert.Alarm.AlarmInfo;
import com.title51.TaskAlert.Alarm.AlarmInfoList;
import com.title51.TaskAlert.Alarm.AlarmService;
import com.title51.TaskAlert.Task.Task;
import com.title51.TaskAlert.Task.TaskAlarm;
import com.title51.TaskAlert.Task.TaskIntentFields;
import com.title51.TaskAlert.Task.TaskRow;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateTask extends Activity implements TaskIntentFields {
	
	private Task m_task = null;
	private EditText m_name = null;
	private EditText m_description = null;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_form);
        m_name = (EditText) findViewById(R.id.name_edit);
        m_description = (EditText) findViewById(R.id.description_edit);
    }
    
    public void onAddAlarm(View v)
    {
    	/*
    	 * Create alarm and add view to alarm container
    	 */
    	Button  b_add = (Button) v;
    	
    	if(b_add == null) {
    		//ERROR
    		toast("Error: checkbox is null");
    	}
    	
    	addAlarm();
    }
    
    public void addAlarm() {
    	//TODO: open up dialogue to create alarm or cancel
    	boolean create_alarm = createAlarm();
    	
    	if(create_alarm == true) {
    		addAlarmView();
    	}
    }
    
    public boolean createAlarm() {
    	//TODO: pop up menu allows user to add an alarm, or cancel
    	//TODO: will need to trigger create alarm view or cancel via onCreateAlarm, onCancelAlarm methods 
    	boolean create_alarm = true;
    	
    	return create_alarm;
    }
    
    public void addAlarmView() {
    	//Add alarm info to screen
    	RelativeLayout alarm_container=(RelativeLayout)findViewById(R.id.alarm_list);
    	
    	//create alarm view object
    	TaskAlarm alarm_view = new TaskAlarm(getApplicationContext());
    	View view = (View) alarm_view.getView();
    	
    	//add as second to last so that "add alarm" button is always on the bottom
    	alarm_container.addView(view);
    }
    
    public void onClickCreateTask (View v){
    	//toast("Click Create Task");
    	
    	//get name from ui
    	String task_name = getName();

    	/*
    	 * Extract alarm rules from input
    	 */
    	AlarmInfoList alarm_list = getAlarmRules();
    	
    	
    	/*
    	 * Create Task with inputted info, including alarm that 
    	 */
    	m_task = new Task(task_name, alarm_list);
    	
    	AlarmInfoList list = m_task.getAlarmList(); 
    	int list_size = list.getNumAlarms();
    	AlarmInfo task_alarm = list.getAlarm(list_size);
    	
    	String name = m_task.getName();
    	String message = "Alarms Created: " + String.valueOf(list_size-1) + " name of last alarm: " + String.valueOf(name);
    	toast(message);
    	
    	finish(m_task);
    } 
    
    public AlarmInfoList getAlarmRules() {
    	/*
    	 *TODO  Return empty list if no alarms
    	 */
    	
    	
    	
    	/*
    	 * Get number of alarms from layout
    	 * Then for each alarm, start and record alarm info
    	 */
    	RelativeLayout rl_alarm_wrapper = (RelativeLayout)findViewById(R.id.alarm_list);
    	int num_alarms = rl_alarm_wrapper.getChildCount();
    	
    	//TODO: GregorianCalendar calendar = new GregorianCalendar();
    	GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
    	String old_time = calendar.getTime().toString();
    	
    	calendar.add(Calendar.SECOND, 5);
    	long alarm_time = calendar.getTimeInMillis();
    	
    	/*
    	 * Create Intent and Pending Intent to start alarm service
    	 */
    	Intent myIntent = new Intent(this, AlarmEventActivity.class);
    	//TODO: Request code, need to keep changing this
    	PendingIntent pending_intent;
    	pending_intent = PendingIntent.getActivity(this, 12341, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
	
    	/*
    	 * Start alarms
    	 */
    	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_time, pending_intent);
    	   
    	/*
    	 * 
    	 * Create List of Alarms for the Task
    	 */
    	//Create TaskRow gui object and assign to alarm
    	TaskRow row = new TaskRow(getApplicationContext());
    	AlarmInfo alarm = new AlarmInfo("Single", calendar, row);
    	
    	AlarmInfoList alarm_list = new AlarmInfoList();
    	alarm_list.addAlarm(alarm);
    	
    	return alarm_list;
    }
    
    public void finish(Task task) {
    	
    	Intent intent = this.getIntent();
    	
    	if(task!=null) {
	    	int size = m_task.getAlarmList().getNumAlarms() - 1;
	    	
			intent.putExtra(TASK_NAME, String.valueOf(task.getName()));
			intent.putExtra(NUMBER_ALARMS, String.valueOf(size));
			
			this.setResult(RESULT_OK, intent);
		} else {
			this.setResult(RESULT_CANCELED, intent);
		}
    	
		finish();
    }
    
    public String getName() {
    	String name = "";
    	
    	name = m_name.getText().toString();
    	return name;
    }
    
    private String getDescription() {
    	String description = "";
    	
    	description = m_description.getText().toString();
    	return description;
    }
    
    //TODO change how task alarm is turned off
    public void onClickCancelTask (View v){
    	if(m_task == null) {
    		toast("No current task!");
    		return;
    	}
    	/*
    	PendingIntent cancel_intent = m_task.getIntent();
    	
    	AlarmManager alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	alarm_manager.cancel(cancel_intent);
    	*/
    	toast("TODO: Task Alarm Cancelled");
    	
    }
    
    private void toast(String message) {
    	Toast.makeText (getApplicationContext(), message, Toast.LENGTH_SHORT).show ();
    }
}

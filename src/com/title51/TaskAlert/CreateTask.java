package com.title51.TaskAlert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.title51.TaskAlert.Alarm.Alarm;
import com.title51.TaskAlert.Alarm.AlarmList;
import com.title51.TaskAlert.Alarm.AlarmListAdapter;
import com.title51.TaskAlert.Alarm.AlarmService;
import com.title51.TaskAlert.Task.Task;
import com.title51.TaskAlert.Task.TaskIntentFields;
import com.title51.TaskAlert.Task.TaskRow;
import com.title51.TaskAlert.XML.XmlReaderWriter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateTask extends ListActivity implements TaskIntentFields {
	
	private Task m_task = null;
	
	private EditText m_name = null;
	private EditText m_description = null;
	
	private Dialog m_dialog = null;
	//private ListView m_alarm_container = null;
	
	private AlarmListAdapter m_list_adapter = null;
	private ArrayList<Alarm> m_alarm_list = null;
	
	private AlarmList m_alarm_info_list = null;
	
	private boolean m_edit_task = false;
	
	private XmlReaderWriter m_xml =null;
	
	private long m_last_alarm_id = -1;
	private long m_last_task_id = -1;
	private long m_current_task_id = -1;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_form);
        int task_id = -1;
        
        m_name = (EditText) findViewById(R.id.name_edit);
        m_description = (EditText) findViewById(R.id.description_edit);

       
    	boolean edit_task = false;
    	//Get last alarm id
    	readXML();
    	
    	/*
    	 * If:   Edit item, extract data from XML
    	 * else: Create new objects 
    	 */
    	if(edit_task) {
    		m_task = getTask(task_id);
    		m_last_task_id = m_task.getId();
    		m_alarm_list = m_task.getAlarmList().getList();
    		
    		displayTask();
    		
    	} else {
    		m_alarm_list = new ArrayList<Alarm>();
    	}
        
    	m_list_adapter = new AlarmListAdapter(this, R.layout.row, m_alarm_list);
    	
    	setListAdapter(m_list_adapter);
    }
    
    public void readXML() {
    	m_xml = new XmlReaderWriter();
    	m_last_task_id = m_xml.getLastTaskId(getApplicationContext());
    	m_last_alarm_id = m_xml.getLastAlarmId(getApplicationContext());
    }
    
    
    public Task getTask(int task_id) {
    	Task task = null;
    	
    	return task;
    }
    
    public void onAddAlarm(View v)
    {
    	/*
    	 * Create alarm and add view to alarm container
    	 */
    	Button  b_add = (Button) v;
    	//toast("onAddAlarm");
    	if(b_add == null) {
    		//ERROR
    		toast("Error: checkbox is null");
    	}
    	
    	createAlarm();
    }
    
    /*
     * Display all aspects of a task in edit task screen
     */
    public void displayTask() {
    	//m_task
    }
    
    //http://androidresearch.wordpress.com/2012/04/16/creating-and-displaying-a-custom-dialog-in-android/
    public void createAlarm() {
    	/*
    	 * Dialog menu allows user to add an alarm, or cancel
    	 */
    	
    	Context context=CreateTask.this;
    	
    	m_dialog=new Dialog(context);
    	m_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

    	m_dialog.setContentView(R.layout.alarm_dialog);
    	
    	
    	/*
    	 * set up buttons for creating/canceling
    	 */
        Button button_create = (Button) m_dialog.findViewById(R.id.create_alarm);
        button_create.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
        		//get time
        		TimePicker time_picker = (TimePicker)m_dialog.findViewById(R.id.time_picker);
        		int hour = time_picker.getCurrentHour();
        		int minute = time_picker.getCurrentMinute();
        		
        		onAddAlarmView(hour, minute);
            }
        });
        
        Button button_cancel = (Button) m_dialog.findViewById(R.id.cancel_alarm);
        button_cancel.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
        		onCancelAddAlarm(v);
            }
        });
        
        m_dialog.show();
    }
    
    
    public void onAddAlarmView(int hour, int minute) {
    	/*
    	 * Close dialog menu
    	 * add alarm view to layout
    	 */
    	m_dialog.dismiss();
    	m_dialog = null;
    	
    	addAlarmView(hour, minute);
    }
    
    public void onCancelAddAlarm(View v) {
    	/*
    	 * Close dialog menu
    	 */
    	m_dialog.dismiss();
    	m_dialog = null;
    	
    }
    
    
    public void addAlarmView(int hour, int minute) {
    	//Add alarm info to screen
    	
    	Calendar calendar = (Calendar)Calendar.getInstance();
    	
    	calendar.set(calendar.HOUR, hour);
    	calendar.set(calendar.MINUTE, minute);
    	calendar.set(calendar.SECOND, 0);
    	
    	//TODO: why do I need this???
    	int day = calendar.getTime().getDay();
    	calendar.set(calendar.DAY_OF_WEEK, day);
    	
    	//String message = "MS: " + calendar.getTimeInMillis() + "   Day: " + day + "   Date 1 : " + calendar.getTime().toString();
    	//toast(message);
    	
    	//GregorianCalendar cal = (GregorianCalendar) calendar;
    	m_last_alarm_id++;
    	Alarm alarm_info = new Alarm(m_last_alarm_id, calendar);
    	
            
    	m_alarm_list.add(alarm_info);
    	
        m_list_adapter.notifyDataSetChanged();
        

    }
    
    //TODO: need to find out how to copy elements from ListAdapter to ArrayAdapter
    //TODO: move this
    public ArrayAdapter<View> getArrayAdapater(ListAdapter adapter) {
    	ArrayAdapter<View> array_adapter = new ArrayAdapter<View>(this,523535);
    	
    	
    	int num_elements = 0;
    	
    	if(adapter==null) {
    		num_elements = 0;
    	} else {
    		adapter.getCount();
    	}
    	
    	for(int i = 0; i<num_elements; i++) {
    		View v = null;
    		array_adapter.add(adapter.getView(i, v, null));
    	}
    	return array_adapter;
    }
    
    public void onClickCreateTask (View v){
    	//toast("Click Create Task");
    	
    	//get name from ui
    	String task_name = getName();

    	/*
    	 * Get list of alarm rules and start them
    	 */
    	AlarmList alarm_list = getAlarmRules();
    	
    	/*
    	 * Create Task with inputed info, including alarm that
    	 * if NULL, then new task
    	 */
    	if(m_task == null) {
    		m_task = new Task(task_name, m_last_task_id+1);
    	}
    	
    	m_task.setAlarmList(alarm_list);
    	
    	finish(m_task);
    	//toast("Click Create Task, ID: " + m_task.getId() + "  last_task_id: " + m_last_task_id + " last alarm: " + m_last_alarm_id);
    } 
    
    public AlarmList getAlarmRules() {
    	if(m_alarm_list.size() != m_list_adapter.getCount()) {
    		//ERROR
    		toast("ERROR: alarm_list != list_adapter element count");
    	}
    	
    	/*
    	 * Get alarms from list, start their alarms
    	 */
    	
    	int num_alarms = m_alarm_list.size();
    	AlarmList alarm_list = new AlarmList();
    	
    	for(int i=0; i<num_alarms; i++) {
    		Alarm alarm = m_alarm_list.get(i);
    		alarm_list.addAlarm(alarm);
    		//TODO start alarms
	    	//Create Intent and Pending Intent to start alarm service
	    	startAlarm(alarm);
    		 
    	}
    	
    	return alarm_list;
    }
    
    /*
     * Pass intent to be run on alarm time
     * pass alarm time
     * starts alarm and will run the intent (will only start if alarm not yet already started)
     */
    public void startAlarm(Alarm alarm) {
    	//TODO: get next alarm time based on rule/time
    	long alarm_time = alarm.getTimeMs();
    	
    	Intent intent = new Intent(getApplicationContext(), AlarmEventActivity.class);
    	
    	//TODO: Request code, need to keep changing this
    	PendingIntent pending_intent;
    	pending_intent = PendingIntent.getActivity(getApplicationContext(), 12344, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	
    	/*
    	 * Start alarms
    	 */		
    	AlarmManager alarmManager = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
    	alarmManager.set(AlarmManager.RTC_WAKEUP, alarm_time, pending_intent);
    	
    	String message = "MS: " + alarm_time + "   Date : " + alarm.getDateStr();
    	//toast(message);
    }
    
    public void finish(Task task) {
    	
    	Intent intent = this.getIntent();
    	
    	if(task!=null) {
	    	insertIntoXML(task);
	    	
	    	//TODO change what to return
			//intent.putExtra(TASK_NAME, String.valueOf(task.getName()));
			
			this.setResult(RESULT_OK, intent);
		} else {
			this.setResult(RESULT_CANCELED, intent);
		}
    	
		finish();
    }
    
    /*
     * Append or edit XML file with task
     */
    public void insertIntoXML(Task task) {
    	
    	m_xml.addItem(getApplicationContext(), task, m_edit_task);
    }
    
    //TODO: test
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
    	Toast.makeText (getApplicationContext(), message, Toast.LENGTH_LONG).show ();
    }
}

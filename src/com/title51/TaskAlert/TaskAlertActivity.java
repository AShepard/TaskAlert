package com.title51.TaskAlert;

import com.title51.TaskAlert.R;
import com.title51.TaskAlert.R.id;
import com.title51.TaskAlert.R.layout;
import com.title51.TaskAlert.Task.TaskIntentFields;
import com.title51.TaskAlert.Task.TaskRow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class TaskAlertActivity extends Activity implements TaskIntentFields  {
    /** Called when the activity is first created. */
	
	private TaskRow m_task;
	private LinearLayout m_layout_container;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //TODO find out how to get Layout objects from other classes while view is being built
        m_layout_container=(LinearLayout)findViewById(R.id.llContainer);
        
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
    
    protected void onActivityResult(int request_code, int result_code, Intent data)
    {
    	switch(request_code) {
	    	case CREATE_TASK: 
	    		parseTask(data);
	    		break;
	    	default:
	    		break;
    	}
    
    }
    
    //TODO change to Next_Alarm info instead of num alarms
    public void addRowView(String name, String num_alarms) {
    	
        m_task = new TaskRow(getApplicationContext());
        View view = (View) m_task.getRowView();
        m_layout_container.addView(view);
        m_task.setName(name);
        m_task.setNumAlarms(num_alarms);
        
        
    }
    
    /*
     * TODO
     * Parse object out of intent
     */
    private void parseTask(Intent data) {
    	Bundle extras = data.getExtras();
    	
    	String name = extras.getString(TASK_NAME);
    	String alarms = extras.getString(NUMBER_ALARMS);
    	
    	addRowView(name, alarms);
    }
}
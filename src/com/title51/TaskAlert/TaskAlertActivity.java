package com.title51.TaskAlert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TaskAlertActivity extends Activity {
    /** Called when the activity is first created. */
	private final int CREATE_TASK = 455;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
    }
    
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
    
    /*
     * TODO
     * Parse object out of intent
     */
    private void parseTask(Intent data) {
    	
    }
}
package com.title51.TaskAlert.Task;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.title51.TaskAlert.R;
import com.title51.TaskAlert.Alarm.Alarm;

public class TaskListAdapter extends ArrayAdapter<Task> {
	private ArrayList<Task> items;
	private LayoutInflater m_inflater = null;
	
	public TaskListAdapter(Context context, int textViewResourceId, ArrayList<Task> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        m_inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
          
    		if(convertView == null) {
    			convertView = m_inflater.inflate(R.layout.task_info, null);
    		}
    		
    		Task task = items.get(position);
    		String name = task.getName();
    		
    		/*
    		 * Need to set data here
    		 */
    		((TextView) convertView.findViewById(R.id.tv_name)).setText(name);
    		convertView.findViewById(R.id.tv_time);
    		
    		return convertView;
    }
}

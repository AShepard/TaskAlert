package com.title51.TaskAlert.Alarm;

import java.util.ArrayList;

import com.title51.TaskAlert.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//http://www.softwarepassion.com/android-series-custom-listview-items-and-adapters/
public class AlarmListAdapter extends ArrayAdapter<Alarm> {
	private ArrayList<Alarm> items;

    public AlarmListAdapter(Context context, int textViewResourceId, ArrayList<Alarm> items) {
            super(context, textViewResourceId, items);
            this.items = items;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            
            if (v == null) {
            	Alarm alarm = items.get(position);
            	v = alarm.getRowView();
            }
            
            return v;
    }
}

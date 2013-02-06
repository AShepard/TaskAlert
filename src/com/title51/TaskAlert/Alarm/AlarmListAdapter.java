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
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row, null);
            }
            Alarm alarm = items.get(position);
            if (alarm != null) {
                    TextView tv = (TextView) v.findViewById(R.id.tv_info);
                    if (tv != null) {
                    	tv.setText("Name: "+alarm.getAlarmIdStr()); 

                    }
            }
            
            return v;
    }
}

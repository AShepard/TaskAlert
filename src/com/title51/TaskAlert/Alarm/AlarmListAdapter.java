package com.title51.TaskAlert.Alarm;

import java.util.ArrayList;

import com.title51.TaskAlert.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//  http://www.softwarepassion.com/android-series-custom-listview-items-and-adapters/
public class AlarmListAdapter extends ArrayAdapter<Alarm> {
	private ArrayList<Alarm> items;
	private LayoutInflater m_inflater = null;
	
    public AlarmListAdapter(Context context, int textViewResourceId, ArrayList<Alarm> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            m_inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    //http://www.google.com/events/io/2009/sessions/TurboChargeUiAndroidFast.html
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
          /*
    		View v = convertView;
            
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row, null);
            }
            Alarm alarm = items.get(position);
            if (alarm != null) {
                    v = alarm.getRowView();
            }
            
            return v;
            */
    		if(convertView == null) {
    			convertView = m_inflater.inflate(R.layout.row, null);
    		}
    		Alarm alarm = items.get(position);
    		
    		/*
    		 * Need to set data here
    		 */
    		String date = alarm.getDateStr();
    		((TextView) convertView.findViewById(R.id.tv_day)).setText(date);
    		//convertView.findViewById(R.id.tv_time);
    		
    		return convertView;
    }
}

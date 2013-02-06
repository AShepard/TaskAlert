package com.title51.TaskAlert.Task;

import com.title51.TaskAlert.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//TODO rename: something like TaskEvent?
public class TaskAlarmView  extends LinearLayout {
	private TextView tv_day;
	private TextView tv_time;
	
	private RelativeLayout m_row_view;
	
	private int m_hour = -1;
	private int m_minute = -1;
	private String m_day_display = "";
	private String m_time_display = "";
	
	public TaskAlarmView(Context context, int hour, int minute) {
		super(context);
		
		m_hour = hour;
		m_minute = minute;
		
		//inflateLayout(context);
		LayoutInflater layout_inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
        m_row_view =(RelativeLayout) layout_inflater.inflate(R.layout.row, null);
        
        
        this.tv_day = (TextView) m_row_view.findViewById(R.id.tv_day);
        this.tv_time = (TextView) m_row_view.findViewById(R.id.tv_time);
        
        m_time_display = m_hour + ":" + m_minute;
        m_day_display = "1/2/3";
        
        setDayText();
        setTimeText();
	}
	
	public TaskAlarmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        //inflateLayout(context);
    }
	
	public void setDayText () {
		tv_day.setText(m_day_display);
	}
	
	public void setTimeText() {
		tv_time.setText(m_time_display);
	}
	
	public RelativeLayout getView() {
		return m_row_view;
	}
	
	public int getHour() {
		return m_hour;
	}
	public int getMinute() {
		return m_minute;
	}
	
}

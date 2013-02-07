package com.title51.TaskAlert.Alarm;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.widget.RelativeLayout;

import com.title51.TaskAlert.Task.TaskAlarmView;
import com.title51.TaskAlert.Task.TaskRow;

public class Alarm {
	//private String m_alarm_info = "";
	
	private long m_alarm_id = -1;
	private Calendar m_date = null;
	private boolean m_repeat = false;
	private TaskAlarmView m_alarm_view = null;
	//TODO rename to reflect GUI item
	public Alarm(long alarm_id, Calendar date, TaskAlarmView alarm_view) {
		m_alarm_id = alarm_id;
		m_date = date;
		m_alarm_view = alarm_view;
	}
	
	public Calendar getDate() {
		return m_date;
	}
	
	public long getAlarmId() {
		return m_alarm_id;
	}
	
	public String getAlarmIdStr() {
		return Long.toString(m_alarm_id);
	}
	
	public boolean isRepeating(){
		return m_repeat;
	}
	
	public long getTimeMs() {
		return m_date.getTimeInMillis();
	}
	
	public String getDateStr() {
		return m_date.getTime().toString();
	}
	
	public RelativeLayout getRowView() {
		return m_alarm_view.getView();
	}
	
}

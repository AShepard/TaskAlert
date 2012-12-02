package com.title51.TaskAlert.Alarm;

import java.util.GregorianCalendar;

import com.title51.TaskAlert.Task.TaskRow;

public class AlarmInfo {
	private String m_alarm_info = "";
	private GregorianCalendar m_date = null;
	private boolean m_repeat = false;
	
	//TODO rename to reflect GUI item
	public AlarmInfo(String alarm_info, GregorianCalendar date) {
		m_alarm_info = alarm_info;
		m_date = date;
		
	}
	
	public GregorianCalendar getDate() {
		return m_date;
	}
	
	public String getAlarmInfo() {
		return m_alarm_info;
	}
	
	public boolean isRepeating(){
		return m_repeat;
	}
	
}

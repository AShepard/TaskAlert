package com.title51.TaskAlert;

import java.util.GregorianCalendar;

public class AlarmInfo {
	private String m_alarm_info = "";
	private GregorianCalendar m_date = null;
	private boolean m_repeat = false;
	
	public AlarmInfo(String alarm_info, GregorianCalendar date) {
		m_alarm_info = alarm_info;
		m_date = date;
				
		//TODO RepeatInfo
		//m_repeat == true if RepeatInfo!=null
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

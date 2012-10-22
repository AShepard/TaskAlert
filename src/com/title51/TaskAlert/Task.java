package com.title51.TaskAlert;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.PendingIntent;


/*
 * Task holds name, description and time of alarm if exists
 * will hold extra information if alarm is repeating (will hold list of repeating times)
 */
public class Task {
	private long m_alarm_time_ms = -1;
	private String m_name = "";
	private String m_description = "";
	private boolean m_has_alarm = false;
	private boolean m_is_repeating = false;
	
	private PendingIntent m_pending_intent = null;
	
	//TODO repeating times/conditions
	public Task(String name, String description, long time_ms, PendingIntent intent) {
		m_name = name;
		m_description = description;
		m_alarm_time_ms = time_ms;
		
		m_pending_intent = intent;
		
		if(m_alarm_time_ms > 0) {
			m_has_alarm = true;
		}
	}
	
	/*
	 * Convert MS time to string
	 * Useful for printing date of Task's Alarm Time
	 */
	public String getAlarmString() {
		if(!m_has_alarm) {
			return "";
		}
		
		String str_date = "";
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(m_alarm_time_ms);
		
		str_date = calendar.getTime().toString(); 
		return str_date;
	}
	
	public String getName() {
		return m_name;
	}
	
	public String getDescription() {
		return m_description;
	}
	
	public long getTimeMs() {
		return m_alarm_time_ms;
	}
	
	public boolean hasAlarm() {
		return m_has_alarm;
	}
	
	public boolean isRepeating() {
		return m_is_repeating;
	}
	
	public PendingIntent getIntent() {
		return m_pending_intent;
	}
}

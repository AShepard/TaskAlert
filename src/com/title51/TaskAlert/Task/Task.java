package com.title51.TaskAlert.Task;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.title51.TaskAlert.Alarm.AlarmInfoList;



/*
 * Task holds name, description and time of alarm if exists
 * will hold extra information if alarm is repeating (will hold list of repeating times)
 */
public class Task {
	//TODO clean up
	private String m_name = "";
	private String m_description = "";
	private boolean m_has_alarm = false;
	private boolean m_is_repeating = false;
	
	
	//TODO Create list of AlarmInfoList
	private AlarmInfoList m_alarm_list = null;
	
	//TODO repeating times/conditions
	public Task(String name, AlarmInfoList alarm_list) {
		m_name = name;
		
		m_alarm_list = alarm_list;
		
		//m_alarm_list.addAlarm(name, date)
		
		//TODO change rule
		if(m_alarm_list.getNumAlarms() > 0) {
			m_has_alarm = true;
		}
	}
	
	/*
	 * TODO remove?
	 * Convert MS time to string
	 * Useful for printing date of Task's Alarm Time
	 */
	public String getAlarmString() {
		if(!m_has_alarm) {
			return "";
		}
		
		String str_date = "";
		
		
		//str_date = calendar.getTime().toString(); 
		return str_date;
	}
	
	public String getName() {
		return m_name;
	}
	
	public String getDescription() {
		return m_description;
	}
	
	//TODO add logic to determine next alarm date?s
	public long getTimeMs() {
		return 0;
	}
	
	public boolean hasAlarm() {
		return m_has_alarm;
	}
	
	public boolean isRepeating() {
		return m_is_repeating;
	}
	
	public AlarmInfoList getAlarmList() {
		return m_alarm_list;
	}
	
}

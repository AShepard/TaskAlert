package com.title51.TaskAlert.Task;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.title51.TaskAlert.Alarm.Alarm;
import com.title51.TaskAlert.Alarm.AlarmList;



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
	
	private long m_task_id = 0;
	
	//TODO Create list of AlarmInfoList
	private AlarmList m_alarm_list = null;
	
	
	public Task(String name, long task_id) { 
		m_name = name;
		m_task_id = task_id;
		m_alarm_list = new AlarmList();
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
	
	public long getId() {
		return m_task_id;
	}
	
	public String getIdStr() {
		return Long.toString(m_task_id);
	}
	
	public String getName() {
		return m_name;
	}
	
	public String getDescription() {
		return m_description;
	}
	
	public void setId(long id) {
		m_task_id = id;
	}
	
	/*
	 * Task alarm methods
	 */
	public void addAlarm(Alarm alarm) {
		m_alarm_list.addAlarm(alarm);
	}
	
	public int getNumAlarms() {
		return m_alarm_list.getNumAlarms();
	}
	
	public Alarm getAlarm(int i) {
		return m_alarm_list.getAlarm(i);
	}
	
	public void removeAlarm(int i) {
		m_alarm_list.removeAlarm(i);
	}
	
	/*
	 * Overwrites alarm list
	 * TODO: should this only be called in another constructor?
	 */
	public void setAlarmList(AlarmList alarm_list) {
		m_alarm_list = alarm_list;
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
	
	public AlarmList getAlarmList() {
		return m_alarm_list;
	}
	
}

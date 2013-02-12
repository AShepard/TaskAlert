package com.title51.TaskAlert.Alarm;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.title51.TaskAlert.Task.TaskAlarmView;
import com.title51.TaskAlert.Task.TaskRow;

public class AlarmList {
	private ArrayList<Alarm> m_alarm_list = null;
	private final int EXPECTED_ELEMENTS = 100;
	
	public AlarmList() {
		m_alarm_list = new ArrayList<Alarm>();
	}
	
	public void addAlarm(Alarm alarm) {
		m_alarm_list.add(alarm);
	}
	
	public void addAlarm(long alarm_id, GregorianCalendar alarm_date) {
		Alarm alarm = new Alarm(alarm_id, alarm_date);
		
		addAlarm(alarm);
	}
	
	public int getNumAlarms() {
		return m_alarm_list.size();
	}
	
	public Alarm getAlarm(int i) {
		return m_alarm_list.get(i);
	}
	
	public void removeAlarm(int i) {
		m_alarm_list.remove(i);
	}
}
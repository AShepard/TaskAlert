package com.title51.TaskAlert.Alarm;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.title51.TaskAlert.Task.TaskRow;

public class AlarmList {
	private ArrayList m_alarm_list = null;
	private final int EXPECTED_ELEMENTS = 100;
	
	public AlarmList() {
		m_alarm_list = new ArrayList<Alarm>(EXPECTED_ELEMENTS );
	}
	
	public void addAlarm(Alarm alarm) {
		m_alarm_list.add(alarm);
	}
	
	public void addAlarm(String info, GregorianCalendar date) {
		String alarm_info = info;
		GregorianCalendar alarm_date = date;
		Alarm alarm = new Alarm(alarm_info, alarm_date);
		
		addAlarm(alarm);
	}
	
	public int getNumAlarms() {
		return m_alarm_list.size();
	}
	
	public Alarm getAlarm(int i) {
		Alarm alarm = null;
		
		return alarm;
	}
	
	public void removeAlarm(int i) {
		m_alarm_list.remove(i);
	}
}
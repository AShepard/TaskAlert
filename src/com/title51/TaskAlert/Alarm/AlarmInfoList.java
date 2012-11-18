package com.title51.TaskAlert.Alarm;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.title51.TaskAlert.Task.TaskRow;

public class AlarmInfoList {
	private ArrayList m_alarm_list = null;
	private final int EXPECTED_ELEMENTS = 100;
	
	public AlarmInfoList() {
		m_alarm_list = new ArrayList<AlarmInfo>(EXPECTED_ELEMENTS );
	}
	
	public void addAlarm(AlarmInfo alarm) {
		m_alarm_list.add(alarm);
	}
	
	public void addAlarm(String info, GregorianCalendar date, TaskRow row) {
		String alarm_info = info;
		GregorianCalendar alarm_date = date;
		AlarmInfo alarm = new AlarmInfo(alarm_info, alarm_date, row);
		
		addAlarm(alarm);
	}
	
	public int getNumAlarms() {
		return m_alarm_list.size();
	}
	
	public AlarmInfo getAlarm(int i) {
		AlarmInfo alarm = null;
		
		
		return alarm;
	}
}
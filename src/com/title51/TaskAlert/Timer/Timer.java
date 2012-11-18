package com.title51.TaskAlert.Timer;

import java.util.GregorianCalendar;

import android.app.AlarmManager;

/*
 * sample to schedule service via AlarmManager:
 * http://android-er.blogspot.com/2010/10/simple-example-of-alarm-service-using.html
 */
public class Timer {
	private long m_time_ms;
	
	private void setAlarm(GregorianCalendar calendar) {
		long alarm_time = calendar.getTimeInMillis();
	}
	
}

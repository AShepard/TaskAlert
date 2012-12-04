package com.title51.TaskAlert.Task;

public interface TaskIntentFields {
	
	/*
	 * DEBUG
	 */
	static final String TASK_NAME = "TASK_NAME";
	static final String NUMBER_ALARMS = "NUMBER_ALARMS";
	
	/*
	 * Fields for both
	 */
	//ID to get info from XML
	static final String TASK_ID = "TASK_ID";
	
	/*
	 * Fields from CreateTask to TaskAlertActivity
	 */
	//tells activity to edit or add task or do nothing
	static final String UPDATE_FLAG = "UPDATE_FLAG";
	
	public final int ADD_TASK = 0;
	public final int EDIT_TASK = 1;
	public final int NO_TASK = 2;
	/*
	 * Fields from TaskAlertActivity to CreateTask
	 */
	static final int CREATE_TASK = 455;
}

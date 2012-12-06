package com.title51.TaskAlert.XML;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.title51.TaskAlert.Alarm.Alarm;
import com.title51.TaskAlert.Alarm.AlarmList;
import com.title51.TaskAlert.Task.Task;

import android.content.Context;
import android.util.Xml;

public class XmlReaderWriter {
	private static String FILE_PATH = "";
	private static String FILE_NAME = "task.xml";
	
	/*
	 * read iput and return list of elements
	 * http://androidideasblog.blogspot.com/2010/01/read-write-and-parse-xml-file-in.html
	 */
	public ArrayList<Task> parseData(Context context, long target_task_id) throws XmlPullParserException, IOException {
		ArrayList<Task> task_list = new ArrayList<Task>();
		XmlPullParser parser = Xml.newPullParser();
		try {
		    FileInputStream input_stream = context.openFileInput(FILE_NAME); 
		    InputStreamReader stream_reader = new InputStreamReader(input_stream);
		    
		    // auto-detect the encoding from the stream
		    parser.setInput(stream_reader);
		    int eventType = parser.getEventType();
		    Task current_task= null;
		    boolean done = false;
		    
		    /*
		     * For each element read, parse into list
		     */
		    while (eventType != XmlPullParser.END_DOCUMENT && !done){
		        String name = null;
		        switch (eventType){
		            case XmlPullParser.START_DOCUMENT:
		                break;
		            case XmlPullParser.START_TAG:
		                name = parser.getName();
		                if (name.equalsIgnoreCase("TaskList")){
		                	task_list = new ArrayList<Task>();
		                	
		                } else if (name.equalsIgnoreCase("Task")){
		                	/*
		                	 * Create new Task and extract task info 
		                	 * Alarm list will follow or be blank
		                	 */
	                    	long task_id = Long.parseLong(parser.getAttributeValue(0));
	                    	String task_name = parser.getAttributeValue(1);
	                    	current_task = new Task(task_name, task_id); 
		                } else if(name.equalsIgnoreCase("Alarm")) {
		                	/*
		                	 * Create alarm and add it to current task's list
		                	 */
		                	Alarm alarm = new Alarm("sfs", null);
		                	current_task.addAlarm(alarm);
		                }
		                break;
		            case XmlPullParser.END_TAG:
		                name = parser.getName();
		                if (name.equalsIgnoreCase("Task") && 
		                		current_task != null){
		                	task_list.add(0, current_task);
		                	current_task = null;
		                } else if (name.equalsIgnoreCase("TaskList")){
		                    done = true;
		                } else if (name.equalsIgnoreCase("Alarm")) {
		                	//TODO
		                }
		                break;
		            }
		        eventType = parser.next();
		    }
		    
		} catch (FileNotFoundException e) {
		    // TODO
		} catch (IOException e) {
		    // TODO
		} catch (Exception e){
		    // TODO
		}
		
		return task_list;
	}
	
}

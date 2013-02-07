package com.title51.TaskAlert.XML;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import com.title51.TaskAlert.Alarm.Alarm;
import com.title51.TaskAlert.Alarm.AlarmList;
import com.title51.TaskAlert.Task.Task;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.util.Xml;

public class XmlReaderWriter {
	private static String FILE_PATH = "";
	private static String FILE_NAME = "task.xml";
	
	/*
	 * XML file identifiers
	 * TODO: move to interface
	 */
	private static String TASK_LIST_TAG = "TASKLIST";
	private static String TASK_TAG = "TASK";
	private static String ALARM_TAG = "ALARM";
	
	private static String TASK_ID_ATTR = "TASK_ID_ATTR";
	private static String TASK_NAME_ATTR = "TASK_NAME_ATTR";
	
	private static String ALARM_ID_ATTR = "ALARM_ID_ATTR";
	
	private int m_num_tasks = 0;
	
	public ArrayList<Task> getTaskList(Context context) {
		ArrayList<Task> task_list = null;
		try {
			task_list = parseXMLFile(context, null);
		} catch (Exception e) {
			task_list = null;
		}
		
		return task_list;
	}
	/*
	 * read input and return list of elements
	 * http://androidideasblog.blogspot.com/2010/01/read-write-and-parse-xml-file-in.html
	 */
	private ArrayList<Task> parseXMLFile(Context context, Task new_task) throws XmlPullParserException, IOException {
		//reset counter used to give new tasks ID
		m_num_tasks=0;
		
		ArrayList<Task> task_list = new ArrayList<Task>();
		XmlPullParser parser = Xml.newPullParser();
		long target_id = -1;
		if(new_task != null) {
			target_id = new_task.getId();
		}
		try {
			String file_name = FILE_NAME;//Environment.getExternalStorageDirectory()+FILE_NAME;
		    FileInputStream input_stream = context.openFileInput(file_name); 
		    InputStreamReader stream_reader = new InputStreamReader(input_stream);
		    
		    // auto-detect the encoding from the stream
		    parser.setInput(stream_reader);
		    int eventType = parser.getEventType();
		    Task current_task= null;
		    Alarm current_alarm=null;
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
		                if (name.equalsIgnoreCase(TASK_LIST_TAG)){
		                	//TODO instantiate list here?
		                } else if (name.equalsIgnoreCase(TASK_TAG)){
		                	/*
		                	 * Create new Task and extract task attributes
		                	 * Alarm list will follow or be blank
		                	 */
	                    	long task_id = Long.parseLong(parser.getAttributeValue("", TASK_ID_ATTR));
	                    	String task_name = parser.getAttributeValue("", TASK_NAME_ATTR);
	                    	
	                    	current_task = new Task(task_name, task_id); 
	                    	
		                } else if(name.equalsIgnoreCase(ALARM_TAG)) {
		                	/*
		                	 * Create alarm and add it to current task's list
		                	 * TODO: get date and repeat rules
		                	 */
		                	long alarm_id = Long.parseLong(parser.getAttributeValue("", ALARM_ID_ATTR));
		                	
		                	current_alarm = new Alarm(alarm_id, null, null);
		                	
		                }
		                break;
		                
		            case XmlPullParser.END_TAG:
		                name = parser.getName();
		                if (name.equalsIgnoreCase(TASK_TAG) && 
		                		current_task != null){
		                	
		                	/*
		                	 * If this is a task we are edditing, insert eddited one instead
		                	 * Discards old one
		                	 */
		                	if(current_task.getId() == target_id) {
		                		task_list.add(new_task);
		                	} else {
		                		task_list.add(current_task);
		                	}
		                	
		                	m_num_tasks++;
		                	current_task = null;
		                } else if (name.equalsIgnoreCase(TASK_LIST_TAG)){
		                    done = true;
		                } else if (name.equalsIgnoreCase(ALARM_TAG)) {
		                	//TODO: 
		                	current_task.addAlarm(current_alarm);
		                }
		                break;
		            }
		        eventType = parser.next();
		    }
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return task_list;
	}
	
	
	/*
	 * Add/Edit Item
	 * Read in entire file
	 * if edit = true, then just edit that item
	 * else add to end of list with new id
	 */
	
	public void addItem(Context context, Task task, boolean edit_item) {
		/*
		 * Read in file and get elements
		 */
		ArrayList<Task> task_list = null;
		try {
			
			/*
			 * Only read if file exists
			 */
			if(xmlFileExists(context)) { 
				task_list = parseXMLFile(context, null);
			} else {
				task_list = new ArrayList<Task>();
			}
			
			/* 
			 * if new: Read list and add to end of file
			 */
			if(!edit_item) {
				//give task new ID
				task.setId(m_num_tasks);
				task_list.add(task);
			} 
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * Write edited list back to file
		 */
		writeFile(context, task_list);
	}
	
	private boolean xmlFileExists(Context context) throws XmlPullParserException, IOException {
		boolean file_exists = false;
		String file_name = FILE_NAME;
		
		try {
			FileInputStream input_stream = context.openFileInput(file_name); 
			
			if(input_stream != null) {
				file_exists = true;
			}
			
		} catch(Exception e) {
			file_exists = false;
		}
		
		
		return file_exists;
	}
	
	//http://xjaphx.wordpress.com/2011/10/27/android-xml-adventure-create-write-xml-data/
	public void writeFile(Context context, ArrayList<Task> task_list) {
        final String TESTSTRING = new String("Hello Android");
        
        /*
         * Wrte data to serializer to create file
         */
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
	        xmlSerializer.setOutput(writer);
	        // start DOCUMENT
	        xmlSerializer.startDocument("UTF-8", true);
	        //Create list tag
	        xmlSerializer.startTag("", TASK_LIST_TAG);
	        
	        int num_tasks = task_list.size();
	        
	        /*
	         * Create XML entries for each task
	         */
	        
	        for(int task_counter=0; task_counter<num_tasks; task_counter++) {
	        	Task task = task_list.get(task_counter);
	        	
	        	//TODO add more attributes for task into XML
	        	String task_id = task.getIdStr();
	        	String task_name = task.getName();

	        	
	        	xmlSerializer.startTag("", TASK_TAG);
	        	
	        	xmlSerializer.attribute("", TASK_ID_ATTR, task_id);
	        	xmlSerializer.attribute("", TASK_NAME_ATTR, task_name);
	        	
	        	/*
	        	 * Add alarms
	        	 */
	        	
	        	AlarmList alarm_list = task.getAlarmList();
	        	
	        	if(alarm_list != null) {
	        		int num_alarms = alarm_list.getNumAlarms();
	        		for(int alarm_counter=0; alarm_counter<num_alarms; alarm_counter++) {
	        			Alarm alarm = alarm_list.getAlarm(alarm_counter);
	        			String alarm_id = alarm.getAlarmIdStr();
	        			
	        			xmlSerializer.startTag("", ALARM_TAG);
	        			xmlSerializer.attribute("", ALARM_ID_ATTR, alarm_id);
	        			
	        			/*
	        			 * TODO: Rules
	        			 */
	        			
	        			xmlSerializer.endTag("", ALARM_TAG);
	        		}
	        	} else {
	        		//TDOD: ERROR
	        	}
	        	
	        	xmlSerializer.endTag("", TASK_TAG);
	        	
	        }

	        //End list
	        xmlSerializer.endTag("", TASK_LIST_TAG);
	        
			// end DOCUMENT
	        xmlSerializer.endDocument();
	        
	        String file_content = writer.toString();
	        
	        writeToFile(context, file_content);
        } catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void writeToFile(Context context, String file_content) {
		/*
         * Write to file
         */
		try {
			String file_name = FILE_NAME;//Environment.getExternalStorageDirectory()+FILE_NAME;
	        FileOutputStream fOut = context.openFileOutput(file_name, Context.MODE_PRIVATE); 
	        OutputStreamWriter osw = new OutputStreamWriter(fOut); 
	        
	        osw.write(file_content); 
	        osw.flush(); 
	        osw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

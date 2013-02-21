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
	
	private static String LAST_TASK_ID_ATTR = "LAST_TASK_ID_ATTR";
	private static String LAST_ALARM_ID_ATTR = "LAST_ALARM_ID_ATTR";
	
	private static String TASK_TAG = "TASK";
	private static String ALARM_TAG = "ALARM";
	
	private static String TASK_ID_ATTR = "TASK_ID_ATTR";
	private static String TASK_NAME_ATTR = "TASK_NAME_ATTR";
	
	private static String ALARM_ID_ATTR = "ALARM_ID_ATTR";
	
	
	private long m_last_task_id = -1;
	private long m_last_alarm_id = -1;
	
	ArrayList<Task> m_task_list = null;
	
	
	public ArrayList<Task> getTaskList(Context context) {
		if(m_task_list == null) {
			try {
				if(xmlFileExists(context)) {
					parseXMLFile(context);
				} 
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return m_task_list;
	}
	
	
	public long getLastAlarmId(Context context) {
		if(m_last_alarm_id <= 0) {
			try {
			
				if(xmlFileExists(context)) {
					parseXMLFile(context);
				} else {
					m_last_alarm_id = 0;
				}
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return m_last_alarm_id;
	}
	
	public long getLastTaskId(Context context) {
		if(m_last_task_id <= 0) {
			try {
			
				if(xmlFileExists(context)) {
					parseXMLFile(context);
				} else {
					m_last_task_id = 0;
				}
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return m_last_task_id;
	}
	
	
	public Task getTask(Context context, int id) {
		Task task = null;
		
		if(m_task_list == null) {
			try {
				parseXMLFile(context);
			} catch (Exception e) {
				m_last_alarm_id = -1;
			}
		}
		
		task = m_task_list.get(id);
		
		return task; 
	}
	
	
	/*
	 * read input and return list of elements
	 * http://androidideasblog.blogspot.com/2010/01/read-write-and-parse-xml-file-in.html
	 */
	private void parseXMLFile(Context context) throws XmlPullParserException, IOException {
		//reset counter used to give new tasks ID
		
		m_task_list = new ArrayList<Task>();
		XmlPullParser parser = Xml.newPullParser();
		
		try {
			String file_name = FILE_NAME;//Environment.getExternalStorageDirectory()+FILE_NAME;
		    FileInputStream input_stream = context.openFileInput(file_name); 
		    InputStreamReader stream_reader = new InputStreamReader(input_stream);
		    
		    // auto-detect the encoding from the stream
		    parser.setInput(stream_reader);
		    
		    Task current_task= null;
		    Alarm current_alarm=null;
		    boolean done = false;
		    
		    /*
		     * For each element read, parse into list
		     */
		    int eventType = parser.getEventType();
		    while (eventType != XmlPullParser.END_DOCUMENT && !done){
		        String name = null;
		        switch (eventType){
		            case XmlPullParser.START_DOCUMENT:
		                break;
		                
		            case XmlPullParser.START_TAG:
		                name = parser.getName();
		                if (name.equalsIgnoreCase(TASK_LIST_TAG)){
		                	m_last_task_id = Long.parseLong(parser.getAttributeValue("", LAST_TASK_ID_ATTR));
		                	m_last_alarm_id = Long.parseLong(parser.getAttributeValue("", LAST_ALARM_ID_ATTR));
		                	
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
		                	
		                	current_alarm = new Alarm(alarm_id, null);
		                	
		                }
		                break;
		                
		            case XmlPullParser.END_TAG:
		                name = parser.getName();
		                if (name.equalsIgnoreCase(TASK_TAG) && 
		                		current_task != null){
		                	
		                	/*
		                	 * Add task to list
		                	 */
		                	m_task_list.add(current_task);
		                	
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
		
		
	}
	
	
	public void readFileInput(FileInputStream input_stream) {
		StringBuffer fileContent = new StringBuffer("");

		byte[] buffer = new byte[1024];
		int length;
		try {
			while ((length = input_stream.read(buffer)) != -1) {
			    fileContent.append(new String(buffer));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String file_content = fileContent.toString();
		file_content = file_content;
	}
	
	
	/*
	 * Add/Edit Item
	 * Read in entire file
	 * if edit = true, then just edit that item
	 * else add to end of list with new id
	 */
	
	public void addItem(Context context, Task task, boolean edit_item) {
		
		/* 
		 * if new: Read list and add to end of file
		 */
		if(!edit_item) {
			//Add alarm
			addTask(task);
		} 
		
		/*
		 * Write edited list back to file
		 */
		writeFile(context, m_task_list);
	}
	
	public void addTask(Task task) {
		int task_id = (int)task.getId();
		if(m_task_list == null) {
			m_task_list = new ArrayList<Task>();
		}
		
		m_task_list.add(task);
			
		if(task_id > m_last_task_id) {
			m_last_task_id = task_id;
		}
		
		for(int i=0; i<task.getNumAlarms(); i++) {
			Alarm alarm = task.getAlarm(i);
			long alarm_id = alarm.getAlarmId();
			
			if(alarm_id> m_last_alarm_id) {
				m_last_alarm_id = alarm_id;
			}
		}
		
		
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
         * Write data to serializer to create file
         */
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        try {
	        xmlSerializer.setOutput(writer);
	        // start DOCUMENT
	        xmlSerializer.startDocument("UTF-8", true);
	        //Create list tag
	        xmlSerializer.startTag("", TASK_LIST_TAG);
	        
	        String s_last_task_id = (String)String.valueOf(m_last_task_id);
	        String s_last_alarm_id = (String)String.valueOf(m_last_alarm_id);
	        
	        xmlSerializer.attribute("", LAST_TASK_ID_ATTR, s_last_task_id);
        	xmlSerializer.attribute("", LAST_ALARM_ID_ATTR, s_last_alarm_id);
	        
	        /*
	         * Create XML entries for each task
	         */
        	int num_tasks = task_list.size();
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

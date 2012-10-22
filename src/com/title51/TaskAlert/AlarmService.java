package com.title51.TaskAlert;



import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


/*
 * TODO: Change Name and function
 */
public class AlarmService extends Service {


	@Override
	public void onCreate() {
			
        
		Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
		
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
		Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
	}
	
	@Override	
	public boolean onUnbind(Intent intent) {
		Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
		
		return super.onUnbind(intent);
	}
}
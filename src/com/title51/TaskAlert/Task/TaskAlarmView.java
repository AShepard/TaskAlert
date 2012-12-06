package com.title51.TaskAlert.Task;

import com.title51.TaskAlert.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//TODO rename: something like TaskEvent?
public class TaskAlarmView  extends LinearLayout {
	private TextView tvText;
	private RelativeLayout m_row_view;
	
	public TaskAlarmView(Context context) {
		super(context);
		//inflateLayout(context);
		LayoutInflater layout_inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
        m_row_view =(RelativeLayout) layout_inflater.inflate(R.layout.row, null);
        
        
        this.tvText = (TextView) m_row_view.findViewById(R.id.textView1);
	}
	
	public TaskAlarmView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        //inflateLayout(context);
    }
	
	public void setText (String s) {
		tvText.setText(s);
	}
	
	public RelativeLayout getView() {
		return m_row_view;
	}
}

package com.title51.TaskAlert.Task;

import com.title51.TaskAlert.R;
import com.title51.TaskAlert.R.id;
import com.title51.TaskAlert.R.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
	
//http://www.incurlybraces.com/inflating-layout-xml-view-object-android.html
public class TaskRow extends LinearLayout {
	private ImageView ivImage;
	private TextView tvText;
	private RelativeLayout m_row_view;
	private String m_name;
	
	public TaskRow(Context context) {
		super(context);
		//inflateLayout(context);
		LayoutInflater layout_inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
        m_row_view =(RelativeLayout) layout_inflater.inflate(R.layout.row, null);
        
        
        this.tvText = (TextView) m_row_view.findViewById(R.id.textView1);
	}
	
	public TaskRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        //inflateLayout(context);
    }
	
	public RelativeLayout getRowView() {
		return m_row_view;
	}
	
	public void setName (String s) {
		tvText.setText(s);
	}
	
	public void setNumAlarms(String s) {
		
	}
	
}
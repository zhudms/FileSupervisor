package com.example.explorer.data;

import java.io.File;

import com.example.explorer.R;
import com.example.explorer.R.id;
import com.example.explorer.R.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 
* @ClassName: Item 
* @Description: ListView的单元类
* @author T
* @date 2013-11-8 下午9:32:11 
*
 */
public class Item extends LinearLayout {

	private Boolean flag=false;
	private String absPath;
	private LinearLayout linearLayout;

	public interface ClickInLisener {
		void ClickIn();
	}

	public interface LongClickLisener {
		void LongClick();
	}

	public interface ClickSelectLisener {
		void Selected();
	}

	public Item(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.item, this, true);
		this.linearLayout = (LinearLayout) findViewById(R.id.ViewLayout);
		
		
//		linearLayout.setOnLongClickListener(new LongClickLisener() {
//
//			@Override
//			public void LongClick() {
//				// TODO Auto-generated method stub
//
//			}
//		});
	}

	public void setBackGround(Boolean boolean1) {
		this.flag = boolean1;
	}

	public void setAbuPath(String string) {
		this.absPath = string;
	}

	public String getAbuPath() {
		if (absPath != null) {
			return absPath;
		} else {
			return Messages.rootMenu.getAbsolutePath();// 若没有则返回根目录
		}
	}

	public void setFlag(boolean b) {
		this.flag = b;
	}

	public Boolean getFlag() {
		return this.flag;
	}
//public void setBool(View v){
//	this.flag=true;
//}
}

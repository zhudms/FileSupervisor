package com.example.explorer;

import java.io.File;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class AdapterSecond extends ArrayAdapter<File> {

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return super.getCount();
	}

	@Override
	public File getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getView(position, convertView, parent);
	}

	public AdapterSecond(Context context, int resource, File[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	
}

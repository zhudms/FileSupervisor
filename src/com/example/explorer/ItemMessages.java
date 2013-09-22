package com.example.explorer;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.view.View;

public class ItemMessages {

	private File mFile;
	private String name;
	private String Data;
	private String SubMub;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private Boolean style;
	private Boolean isHidden;
	private Boolean selected;
	private int position;

	public ItemMessages(File file) {
	
		this.mFile = file;
		this.name = file.getName();
		this.Data = dateFormat.format(new Date(file.lastModified()));
		this.selected = false;
	}

	public String getSubMub() {

		if (this.mFile.listFiles() == null) {
			SubMub = "(" + "0" + ")";
		} else {
			SubMub = "(" + String.valueOf(this.mFile.listFiles().length) + ")";
		}

		return SubMub;
	}

	public String getData() {
		return Data;
	}

	public String getName() {
		return name;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected() {
		this.selected = !selected;
	}

	public Boolean getStyle() {
		if (mFile.isFile()) {
			return false;
		} else {
			return true;
		}
	}

	public Boolean getHid() {
		if (mFile.isHidden()) {
			return true;
		} else {
			return false;
		}
	}

	public File getAbusPath() {
		return mFile;
	}

	public void setPosion(int posion) {
		this.position = posion;
	}

	public int getPosion() {
		return position;
	}
}

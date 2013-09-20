package com.example.explorer;

import java.io.File;

public class MyReader {

	public interface AddItem {
		void add(File[] file);
	}

	private File mFile;
	private String pathName;
	private AddItem mAddItem;
	private File[] files;

	public MyReader(File mFile) {

		super();
		this.mFile = mFile;

	}

	public void setAdditem(AddItem addItem) {
		this.mAddItem = addItem;
	}

	public void read() {
		if (mFile.isFile()) {
			files = mFile.listFiles();
			if (mAddItem != null) {
				mAddItem.add(files);
			}
		}else{
			//如果是文件
		}
	}

	public void getChildCount() {

	}

}

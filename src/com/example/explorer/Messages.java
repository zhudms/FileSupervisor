package com.example.explorer;

import java.io.File;
import android.os.Environment;

public class Messages {

	static File rootMenu = Environment.getRootDirectory();
	static File outsideStorage = Environment.getExternalStorageDirectory();
	static File downStorage =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
	static File DicStorage=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	// static File outsideStorage = Environment.getExternalStorageDirectory();
//	private static String s;

	public Messages() {

	}

//	public static String getFileName(String k) {
//
//		String[] o = k.split("/");
//		k = o[o.length - 1];
//		return k;
//	}
}

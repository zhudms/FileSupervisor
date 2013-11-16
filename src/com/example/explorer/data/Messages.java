package com.example.explorer.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import android.os.Environment;

/**
 * 
 * @ClassName: Messages
 * @Description: 静态类,用于获取保存公共变量
 * @author T
 * @date 2013-11-8 下午9:22:12
 * 
 */
public class Messages {

	public static File rootMenu = Environment.getRootDirectory();
	public static File outsideStorage = Environment
			.getExternalStorageDirectory();
	public static File downStorage = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
	public static File DicStorage = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	// static File outsideStorage = Environment.getExternalStorageDirectory();
	// private static String s;
	public static String Hidden = "H";
	public static Stack<ItemMessages> fileToCopy = new Stack<ItemMessages>();
	public static Stack<File> fileFlow = new Stack<File>();

	public Messages() {

	}

	public static void setHid(String s) {
		Messages.Hidden = s;
	}

	// public static String getFileName(String k) {
	//
	// String[] o = k.split("/");
	// k = o[o.length - 1];
	// return k;
	// }

	public static String getHid() {
		// TODO Auto-generated method stub
		return Messages.Hidden;
	}
}

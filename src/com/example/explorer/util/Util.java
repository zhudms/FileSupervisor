package com.example.explorer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;

import com.example.explorer.data.FileInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Util {
	private static String ANDROID_SECURE = Environment
			.getExternalStorageDirectory() + "/.android_secure";

	private static final String LOG_TAG = "Util";

	public static boolean isSDCardReady() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	// if path1 contains path2
	public static boolean containsPath(String path1, String path2) {
		String path = path2;
		while (path != null) {
			if (path.equalsIgnoreCase(path1))
				return true;

			if (path.equals("/"))
				break;
			path = new File(path).getParent();
		}

		return false;
	}

	public static String makePath(String path1, String path2) {
		if (path1.endsWith(File.separator))
			return path1 + path2;

		return path1 + File.separator + path2;
	}

	public static String getSdDirectory() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	public static boolean isNormalFile(String fullName) {
		return !fullName.equals(ANDROID_SECURE);
	}

	public static FileInfo GetFileInfo(String filePath) {
		File lFile = new File(filePath);
		if (!lFile.exists())
			return null;

		FileInfo lFileInfo = new FileInfo();
		lFileInfo.canRead = lFile.canRead();
		lFileInfo.canWrite = lFile.canWrite();
		lFileInfo.isHidden = lFile.isHidden();
		lFileInfo.fileName = Util.getNameFromFilepath(filePath);
		lFileInfo.ModifiedDate = lFile.lastModified();
		lFileInfo.IsDir = lFile.isDirectory();
		lFileInfo.filePath = filePath;
		lFileInfo.fileSize = lFile.length();
		return lFileInfo;
	}

	public static FileInfo GetFileInfo(File f, FilenameFilter filter,
			boolean showHidden) {
		FileInfo lFileInfo = new FileInfo();
		String filePath = f.getPath();
		File lFile = new File(filePath);
		lFileInfo.canRead = lFile.canRead();
		lFileInfo.canWrite = lFile.canWrite();
		lFileInfo.isHidden = lFile.isHidden();
		lFileInfo.fileName = f.getName();
		lFileInfo.ModifiedDate = lFile.lastModified();
		lFileInfo.IsDir = lFile.isDirectory();
		lFileInfo.filePath = filePath;
		if (lFileInfo.IsDir) {
			int lCount = 0;
			File[] files = lFile.listFiles(filter);

			if (files == null) {
				return null;
			}

			for (File child : files) {
				if ((!child.isHidden() || showHidden)
						&& Util.isNormalFile(child.getAbsolutePath())) {
					lCount++;
				}
			}
			lFileInfo.Count = lCount;

		} else {

			lFileInfo.fileSize = lFile.length();

		}
		return lFileInfo;
	}

	/*
	 * 
	 * http://code.google.com/p/android/issues/detail?id=9151
	 */
	public static Drawable getApkIcon(Context context, String apkPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(apkPath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = apkPath;
			appInfo.publicSourceDir = apkPath;
			try {
				return appInfo.loadIcon(pm);
			} catch (OutOfMemoryError e) {
				Log.e(LOG_TAG, e.toString());
			}
		}
		return null;
	}

	public static String getExtFromFilename(String filename) {
		int dotPosition = filename.lastIndexOf('.');
		if (dotPosition != -1) {
			return filename.substring(dotPosition + 1, filename.length());
		}
		return "";
	}

	public static String getNameFromFilename(String filename) {
		int dotPosition = filename.lastIndexOf('.');
		if (dotPosition != -1) {
			return filename.substring(0, dotPosition);
		}
		return "";
	}

	public static String getPathFromFilepath(String filepath) {
		int pos = filepath.lastIndexOf('/');
		if (pos != -1) {
			return filepath.substring(0, pos);
		}
		return "";
	}

	public static String getNameFromFilepath(String filepath) {
		int pos = filepath.lastIndexOf('/');
		if (pos != -1) {
			return filepath.substring(pos + 1);
		}
		return "";
	}

	// return new file path if successful, or return null
	public static String copyFile(String src, String dest) {
		File file = new File(src);
		if (!file.exists() || file.isDirectory()) {
			Log.v(LOG_TAG, "copyFile: file not exist or is directory, " + src);
			return null;
		}
		FileInputStream fi = null;
		FileOutputStream fo = null;
		try {
			fi = new FileInputStream(file);
			File destPlace = new File(dest);
			if (!destPlace.exists()) {
				if (!destPlace.mkdirs())
					return null;
			}

			String destPath = Util.makePath(dest, file.getName());
			File destFile = new File(destPath);
			int i = 1;
			while (destFile.exists()) {
				String destName = Util.getNameFromFilename(file.getName())
						+ " " + i++ + "."
						+ Util.getExtFromFilename(file.getName());
				destPath = Util.makePath(dest, destName);
				destFile = new File(destPath);
			}

			if (!destFile.createNewFile())
				return null;

			fo = new FileOutputStream(destFile);
			int count = 102400;
			byte[] buffer = new byte[count];
			int read = 0;
			while ((read = fi.read(buffer, 0, count)) != -1) {
				fo.write(buffer, 0, read);
			}

			// TODO: set access privilege

			return destPath;
		} catch (FileNotFoundException e) {
			Log.e(LOG_TAG, "copyFile: file not found, " + src);
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(LOG_TAG, "copyFile: " + e.toString());
		} finally {
			try {
				if (fi != null)
					fi.close();
				if (fo != null)
					fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static boolean setText(View view, int id, String text) {
		TextView textView = (TextView) view.findViewById(id);
		if (textView == null)
			return false;

		textView.setText(text);
		return true;
	}

	public static boolean setText(View view, int id, int text) {
		TextView textView = (TextView) view.findViewById(id);
		if (textView == null)
			return false;

		textView.setText(text);
		return true;
	}

	// comma separated number
	public static String convertNumber(long number) {
		return String.format("%,d", number);
	}

	// storage, G M K B
	public static String convertStorage(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	public static class SDCardInfo {
		public long total;

		public long free;
	}

	public static String formatDateString(Context context, long time) {
		DateFormat dateFormat = android.text.format.DateFormat
				.getDateFormat(context);
		DateFormat timeFormat = android.text.format.DateFormat
				.getTimeFormat(context);
		Date date = new Date(time);
		return dateFormat.format(date) + " " + timeFormat.format(date);
	}

	public static HashSet<String> sDocMimeTypesSet = new HashSet<String>() {
		{
			add("text/plain");
			add("text/plain");
			add("application/pdf");
			add("application/msword");
			add("application/vnd.ms-excel");
			add("application/vnd.ms-excel");
		}
	};

	public static String sZipFileMimeType = "application/zip";

	public static int CATEGORY_TAB_INDEX = 0;
	public static int SDCARD_TAB_INDEX = 1;
}

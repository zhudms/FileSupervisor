package com.example.explorer.data;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @ClassName: ItemMessages
 * @Description: 记录文件夹显示的内容
 * @author T
 * @date 2013-11-8 下午9:17:24
 * 
 */
public class ItemMessages {

	private File mFile;// 绝对路径
	private String name;// 文件夹名称
	private String Data;// 最后修改日期
	private String SubMub;// 子文件夹数
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private Boolean isHidden;// 是否是隐藏文件
	private Boolean selected;// 是否被选中
	private int position;//
	private String kind;// 文件类型
	private int imageId;
	public boolean canRead;
	public boolean canWrite;

	public ItemMessages(File file) {

		this.mFile = file;
		this.name = file.getName();
		this.Data = dateFormat.format(new Date(file.lastModified()));
		this.selected = false;
	}

	public ItemMessages() {

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

		/* String */

		// kind={};
		// switch (key) {
		// case value:
		//
		// break;
		//
		// default:
		// break;
		// }
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

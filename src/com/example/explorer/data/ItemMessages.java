package com.example.explorer.data;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @ClassName: ItemMessages
 * @Description: ��¼�ļ�����ʾ������
 * @author T
 * @date 2013-11-8 ����9:17:24
 * 
 */
public class ItemMessages {

	private File mFile;// ����·��
	private String name;// �ļ�������
	private String Data;// ����޸�����
	private String SubMub;// ���ļ�����
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private Boolean isHidden;// �Ƿ��������ļ�
	private Boolean selected;// �Ƿ�ѡ��
	private int position;//
	private String kind;// �ļ�����
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

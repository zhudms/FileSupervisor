package com.example.explorer;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.content.ClipData.Item;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	

	private File[] mFiles;
	private Context mContext;
	private View view;// �Ż�
	private TextView mTextName;
	private TextView mTextNumb;
	private TextView mTextTime;
	private DateFormat format1;
	private OnLongClickListener longClickListener;
	private OnClickListener clickListener;
	public LangClickLis mlangClickLis;
	private Boolean flag = false;
	private Item item;

	public interface LangClickLis {
		void langClicked();
	}

	public MyAdapter(Context folderAct, File[] files) {// Con���ױ�ʾʲô

		mFiles = files;
		mContext = folderAct;

	}

	public Boolean getFlag() {
		// TODO Auto-generated method stub
		return this.flag;
	}

	public void setFlag(Boolean boolean1) {
		this.flag = boolean1;
	}

	public void setLangClickLis(LangClickLis langClickLis) {
		this.mlangClickLis = langClickLis;
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		
		super.notifyDataSetChanged();
	
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFiles.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView == null) {

			convertView = LayoutInflater.from(mContext).inflate(R.layout.views,
					null);// ����list_item�ĸ�����(linear
			// _layout),from()ָʾ��˭��ʵ������,ָʾ����Ҫ�ӵ���,ָ��Ķ���(���ô˴��Ķ���?),�˴�ָ������Leaner
			// -layout

		}

		mTextName = (TextView) convertView.findViewById(R.id.textName);// View�а����Ĳ������ܱ��ҵ�(���򱨴�,����R���о���)(�˴�ָlist_item��)
		mTextNumb = (TextView) convertView.findViewById(R.id.textNumb);
		mTextTime = (TextView) convertView.findViewById(R.id.textTime);

		String s;
		mTextName.setText(mFiles[position].getName());
		if (mFiles[position].listFiles() == null) {
			s = "0";
		} else {
			s = String.valueOf((mFiles[position].listFiles()).length);
		}

		mTextNumb.setText("(" + s + ")");// ����listView��item������

		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		mTextTime.setText(format1.format(new Date(mFiles[position]
				.lastModified())));

		convertView.setOnLongClickListener(this.longClickListener);
		convertView.setOnClickListener(this.clickListener);

		return convertView;

	}
	public void setFiles(File[] files){
		this.mFiles=files;
	}

}

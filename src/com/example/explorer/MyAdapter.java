package com.example.explorer;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.content.Context;
import android.content.ClipData.Item;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private Context mContext;
	private View view;// �Ż�
	private TextView mTextName;
	private TextView mTextNumb;
	private TextView mTextTime;
	private ImageView mImageView;
	// private OnLongClickListener longClickListener;
	// private OnClickListener clickListener;
	public LangClickLis mlangClickLis;
	private Boolean flag = false;
	private ArrayList<ItemMessages> mArrayList;

	public interface LangClickLis {
		void langClicked();
	}

	public MyAdapter(Context folderAct, ArrayList<ItemMessages> mItemLists) {// Con���ױ�ʾʲô

		this.mArrayList = mItemLists;
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
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}

	@Override
	public ItemMessages getItem(int arg0) {
		// TODO Auto-generated method stub
		return mArrayList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (mArrayList.get(0).equals(new ItemMessages(Messages.DicStorage))) {

			// convertView = LayoutInflater.from(mContext).inflate(
			// R.layout.nochild, null);
		} else {
			if (convertView == null) {

				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.views, null);// ����list_item�ĸ�����(linear
				// _layout),from()ָʾ��˭��ʵ������,ָʾ����Ҫ�ӵ���,ָ��Ķ���(���ô˴��Ķ���?),�˴�ָ������Leaner
				// -layout

			} else {
				if (!mArrayList.get(position).getSelected()) {
					convertView.setBackgroundResource(R.drawable.view);
				} else {
					convertView.setBackgroundResource(R.drawable.l);
				}
			}

			mTextName = (TextView) convertView.findViewById(R.id.textName);// View�а����Ĳ������ܱ��ҵ�(���򱨴�,����R���о���)(�˴�ָlist_item��)
			mTextNumb = (TextView) convertView.findViewById(R.id.textNumb);
			mTextTime = (TextView) convertView.findViewById(R.id.textTime);
			mImageView = (ImageView) convertView.findViewById(R.id.imageView1);

			mTextName.setText(mArrayList.get(position).getName());
			mTextNumb.setText(mArrayList.get(position).getSubMub());
			mTextTime.setText(mArrayList.get(position).getData());
			if (!mArrayList.get(position).getStyle()) {
				mImageView.setImageResource(R.drawable.wenjian);
			}else{
				mImageView.setImageResource(R.drawable.folder);
			}
			// convertView.setOnLongClickListener(this.longClickListener);
			// convertView.setOnClickListener(this.clickListener);
		}
		return convertView;

	}

}

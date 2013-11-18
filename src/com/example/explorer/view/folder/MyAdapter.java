package com.example.explorer.view.folder;

import java.util.ArrayList;
import com.example.explorer.R;
import com.example.explorer.data.ItemMessages;
import com.example.explorer.data.Messages;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @ClassName: MyAdapter
 * @Description: ��ʾListView�ļ��е�Adapter
 * @author T
 * @date 2013-11-8 ����9:28:01
 * 
 */
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
	private String Hidden;

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

		// if (Hidden.equals("H")) {
		// Iterator<ItemMessages> iter = mArrayList.iterator();
		// if (iter.hasNext()) {
		// ItemMessages hid = iter.next();
		// if (hid.getName().startsWith(".")) {
		// iter.remove();
		// }
		// }
		//
		// }

		//
		// if (mArrayList.get(0).equals(new ItemMessages(Messages.DicStorage)))
		// {
		//
		// // convertView = LayoutInflater.from(mContext).inflate(
		// // R.layout.nochild, null);
		// } else {
		if (convertView == null) {

			convertView = LayoutInflater.from(mContext).inflate(R.layout.item,
					null);// ����list_item�ĸ�����(linear
			// _layout),from()ָʾ��˭��ʵ������,ָʾ����Ҫ�ӵ���,ָ��Ķ���(���ô˴��Ķ���?),�˴�ָ������Leaner
			// -layout

			mTextName = (TextView) convertView.findViewById(R.id.textName);// View�а����Ĳ������ܱ��ҵ�(���򱨴�,����R���о���)(�˴�ָlist_item��)
			mTextNumb = (TextView) convertView.findViewById(R.id.textNumb);
			mTextTime = (TextView) convertView.findViewById(R.id.textTime);
			this.mImageView = (ImageView) convertView
					.findViewById(R.id.imageView1);

			MyHodle hodle = new MyHodle();
			hodle.ImageView = this.mImageView;
			hodle.TextName = this.mTextName;
			hodle.TextTime = this.mTextTime;
			hodle.TextNumb = this.mTextNumb;

			convertView.setTag(hodle);
		}

		MyHodle hodle = (MyHodle) convertView.getTag();

		if (!mArrayList.get(position).getSelected()) {
			Log.d("123", "notSelected");
			// convertView.setBackgroundResource(R.drawable.normal_item);
			convertView.setBackgroundColor(0xFFCAFFFF);
		} else {
			Log.d("123", "Selected");
			// convertView.setBackgroundResource(R.drawable.selected_background);
			convertView.setBackgroundColor(0xFFFFFFCE);
		}

		hodle.TextName.setText(mArrayList.get(position).getName());
		hodle.TextNumb.setText(mArrayList.get(position).getSubMub());
		hodle.TextTime.setText(mArrayList.get(position).getData());
		/*����Itemͼ��*/
		if (!mArrayList.get(position).getStyle()) {
			hodle.ImageView.setImageResource(R.drawable.wenjian);
		} else {
			hodle.ImageView.setImageResource(R.drawable.folder);
		}
		// convertView.setOnLongClickListener(this.longClickListener);
		// convertView.setOnClickListener(this.clickListener);
		// }

		// convertView.set
		return convertView;

	}

	class MyHodle {

		TextView TextName;
		TextView TextNumb;
		TextView TextTime;
		ImageView ImageView;
	}
}

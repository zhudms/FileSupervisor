package com.example.explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FolderAct extends Activity {

	private File mFile;
	private MyReader mReader;
	private MyAdapter mAdapter;
	private ListView mListView;
	private String path;// 当前路径的绝对路径
	private TextView mPathTextView;
	private static Boolean flag;
	private ArrayList<ItemMessages> mItemLists;
	private ArrayList<ItemMessages> mFastLists;
	private ActionMode DelAndAdd;
	private Boolean back;
	private ActionMode mActionMode;
	private int numb;
	private ActionModeCallback mActionModeCallback;

	// private final DataSetObservable mDataSetObservable = new
	// DataSetObservable();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		back = false;
		flag = true;
		setContentView(R.layout.folder);
		Intent intent = this.getIntent();
		path = intent.getStringExtra("folderPath");
		mFile = new File(path);
		mListView = (ListView) findViewById(R.id.listView1);
		mPathTextView = (TextView) findViewById(R.id.textView1);
		mItemLists = new ArrayList<ItemMessages>();
		mItemLists = getArrayList();
		mAdapter = new MyAdapter(this, mItemLists);
		mFastLists = new ArrayList<ItemMessages>();
		mPathTextView.setText(path);// path是绝对路径
		mListView.setAdapter(mAdapter);
		mActionModeCallback = new ActionModeCallback(FolderAct.this);
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				// if (FolderAct.flag) {//此处设置控制没意义
				mAdapter.setFlag(true);

				// FolderAct.flag = false;
				/*
				 * mAdapter.getItem(arg2).setSelected();
				 * arg1.setBackgroundResource(R.drawable.l);
				 * mAdapter.getItem(arg2).setSelected();
				 * mFastLists.add(mAdapter.getItem(arg2));
				 */// 若此种长按默认有一个短按的动作那这些都可以省略了
					// }
				return false;
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("23", "1");
				if (mAdapter.getFlag()) {

					mAdapter.getItem(arg2).setSelected();

					if (mAdapter.getItem(arg2).getSelected()) {

						mActionMode = startActionMode(mActionModeCallback);
						// setActionBarTitle();
						
						arg1.setBackgroundResource(R.drawable.l);
						mFastLists.add(mAdapter.getItem(arg2));
						numb = mFastLists.size();
						mActionMode.setTitle(numb + "");
						mAdapter.getItem(arg2).setPosion(arg2);
					} else {
						// 在本地文件中删除

						Iterator<ItemMessages> iter = mFastLists.iterator();
						while (iter.hasNext()) {

							ItemMessages m = iter.next();

							if (m.equals(mAdapter.getItem(arg2))) {
								iter.remove();
								numb = mFastLists.size();

								mActionMode.setTitle(numb + "");
								mActionMode = startActionMode(new ActionModeCallback(
										FolderAct.this));
								setActionBarTitle();
								arg1.setBackgroundResource(R.drawable.b);

							}
						}
					}

					// arg1.各种操作
				} else {

					ItemMessages item = mAdapter.getItem(arg2);
					if (item.getStyle()) {

						mFile = item.getAbusPath();
						mPathTextView.setText(mFile.getAbsolutePath());
						/*
						 * if (back) {
						 * mListView.setBackgroundResource(R.drawable.large); }
						 */// 处理无子文件时的背景(未成功)

						mItemLists.clear();
						mItemLists = getArrayList();
						mAdapter.notifyDataSetChanged();
					} else {

						Toast.makeText(FolderAct.this, "can't open", 200)
								.show();

					}

				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	public ArrayList<ItemMessages> getArrayList() {

		File[] files = mFile.listFiles();
		if (files == null) {
			/*
			 * back = true; mListView.setBackgroundResource(R.drawable.large);
			 */// 处理无子文件时的背景(未成功)
			mItemLists.add(new ItemMessages(Messages.DicStorage));
		} else {
			back = false;
			for (int u = 0; u < files.length; u++) {
				mItemLists.add(new ItemMessages(files[u]));
			}
		}

		return mItemLists;

	}

	/**
	 * 设置ActionBar的标题显示
	 * 
	 * @author yanjun.wang
	 */
	private void setActionBarTitle() {

		mActionMode.setTitle(numb + "");

	}

	public class ActionModeCallback implements ActionMode.Callback {
		private Menu mMenu;
		private Context mContext;

		public ActionModeCallback(Context context) {
			mContext = context;
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = ((Activity) mContext).getMenuInflater();
			mMenu = menu;
			inflater.inflate(R.menu.operation_menu, mMenu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

			switch (item.getItemId()) {

			case R.id.action_delete:

				getItemNoml();
				Toast.makeText(FolderAct.this, "delete ok!", Toast.LENGTH_SHORT)
						.show();
				mActionMode.finish();
				break;

			case R.id.action_add_shortcut:

				getItemNoml();
				Toast.makeText(FolderAct.this, "add ok!", Toast.LENGTH_SHORT)
						.show();
				mActionMode.finish();
				break;

			}
			return false;
		}

		private void getItemNoml() {
			// TODO Auto-generated method stub

			Iterator<ItemMessages> m = mFastLists.iterator();

			if (m != null) {
				while (m.hasNext()) {
					ItemMessages item = m.next();
					item.setSelected();
					mListView.getChildAt(item.getPosion())
							.setBackgroundResource(R.drawable.b);
				}
			}

			mAdapter.setFlag(false);
			mFastLists.clear();

			numb = mFastLists.size();
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			//

		}

	}
}

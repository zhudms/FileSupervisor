package com.example.explorer.view.folder;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import com.example.explorer.R;
import com.example.explorer.R.drawable;
import com.example.explorer.R.id;
import com.example.explorer.R.layout;
import com.example.explorer.R.menu;
import com.example.explorer.data.Item;
import com.example.explorer.data.ItemMessages;
import com.example.explorer.data.Messages;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FolderAct extends Activity {
	private String FILE_NAME = "name";
	private File mFile;
	private MyAdapter mAdapter;
	private ListView mListView;
	private String path;// ��ǰ·���ľ���·��
	private TextView mPathTextView;
	private static Boolean flag;
	private ArrayList<ItemMessages> mItemLists;
	private ArrayList<ItemMessages> mFastLists;
	private ActionMode DelAndAdd;
	private Boolean back;
	private ActionMode mActionMode;
	private int numb;
	private ActionModeCallback mActionModeCallback;
	private SharedPreferences mPreferences;
	private SharedPreferences.Editor mEditor;
	private String Hidden;

	// private final DataSetObservable mDataSetObservable = new
	// DataSetObservable();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Hidden = Messages.getHid();
		mPreferences = this.getSharedPreferences("FastViews", MODE_PRIVATE);
		mEditor = mPreferences.edit();
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
		mPathTextView.setText(path);// path�Ǿ���·��
		mListView.setAdapter(mAdapter);
		mActionModeCallback = new ActionModeCallback(FolderAct.this);
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				if (FolderAct.flag) {// �˴����ÿ���û����
					mAdapter.setFlag(true);
					mActionMode = startActionMode(mActionModeCallback);
					flag = !flag;
				}
				// FolderAct.flag = false;
				/*
				 * mAdapter.getItem(arg2).setSelected();
				 * arg1.setBackgroundResource(R.drawable.l);
				 * mAdapter.getItem(arg2).setSelected();
				 * mFastLists.add(mAdapter.getItem(arg2));
				 */// �����ֳ���Ĭ����һ���̰��Ķ�������Щ������ʡ����
					// }
				return false;
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				if (mAdapter.getFlag()) {

					mAdapter.getItem(arg2).setSelected();

					if (mAdapter.getItem(arg2).getSelected()) {// ����ģʽ��,δ��ѡ���item

						// setActionBarTitle();

						// arg1.setBackgroundResource(R.drawable.selected_background);
						arg1.setBackgroundColor(0xFFFFFFCE);
						mFastLists.add(mAdapter.getItem(arg2));
						numb = mFastLists.size();
						// mActionMode.setTitle(numb + "");
						setActionBarTitle();
						mAdapter.getItem(arg2).setPosion(arg2);
					} else {// ����ģʽ���ѱ�ѡ���item

						Iterator<ItemMessages> iter = mFastLists.iterator();
						while (iter.hasNext()) {

							ItemMessages m = iter.next();

							if (m.equals(mAdapter.getItem(arg2))) {
								iter.remove();
								numb = mFastLists.size();

								setActionBarTitle();
								// arg1.setBackgroundResource(R.drawable.normal_item);
								arg1.setBackgroundColor(0xFFCAFFFF);
							}
						}
					}

					// arg1.���ֲ���
				} else {

					ItemMessages item = mAdapter.getItem(arg2);
					if (item.getStyle()) {

						mFile = item.getAbusPath();
						mPathTextView.setText(mFile.getAbsolutePath());
						/*
						 * if (back) {
						 * mListView.setBackgroundResource(R.drawable.large); }
						 */// ���������ļ�ʱ�ı���(δ�ɹ�)

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
		MenuItem item0 = menu.getItem(0);
		MenuItem item1 = menu.getItem(1);
		item0.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				onCancle();
				getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
				return false;
			}
		});

		item1.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				onCancle();
				getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
				return false;
			}
		});
		if (Messages.fileToCopy.isEmpty()) {

			MenuItem item2 = menu.getItem(1);
			MenuItem item3 = menu.getItem(0);
			item2.setVisible(false);
			item3.setVisible(false);
		}
		return true;
	}

	// /*
	// * Ӧ��������ѡ��ճ����ȡ��? (non-Javadoc)
	// *
	// * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	// */
	// @Override
	// public boolean onContextItemSelected(MenuItem item) {
	// // TODO Auto-generated method stub
	// switch (item.getItemId()) {
	// case 0:
	// // item.setVisible(false);
	// toPaste();
	// getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
	// break;
	//
	// case 1:
	// onCancle();
	// getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
	// // item.setVisible(false);
	// break;
	// //
	// // /*
	// // * ճ��
	// // */
	// // case R.id.action_paste:// �ƶ���menu.main����
	// // toPaste();
	// // getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
	// // break;
	// //
	// // /*
	// // * ȡ��
	// // */
	// // case R.id.action_cancle:// �ƶ���menu.main����
	// // onCancle();
	// // getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
	// // break;
	//
	// default:
	// break;
	// }
	// return super.onContextItemSelected(item);
	// }

	private void onCancle() {
		// TODO Auto-generated method stub
		while (!Messages.fileToCopy.empty()) {
			Messages.fileToCopy.pop();
		}

	}

	/*
	 * ճ��
	 */
	private void toPaste() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	public ArrayList<ItemMessages> getArrayList() {
		Hidden = Messages.getHid();
		File[] files = mFile.listFiles();
		if (files == null) {
			/*
			 * back = true; mListView.setBackgroundResource(R.drawable.large);
			 */// ���������ļ�ʱ�ı���(δ�ɹ�)
			mItemLists.add(new ItemMessages(Messages.DicStorage));
		} else {
			// back = false;
			if (Hidden.equals("H")) {

				for (int u = 0; u < files.length; u++) {
					if (files[u].getName().startsWith(".")) {
						continue;
					} else {
						mItemLists.add(new ItemMessages(files[u]));
					}
				}
			} else {

				for (int u = 0; u < files.length; u++) {

					mItemLists.add(new ItemMessages(files[u]));
				}
			}
		}

		return mItemLists;

	}

	/**
	 * ����ActionBar�ı�����ʾ
	 * 
	 * @author yanjun.wang
	 */
	private void setActionBarTitle() {

		mActionMode.setTitle(this.numb + "����ѡ��");

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
			/* ɾ�� */
			case R.id.action_delete:

				// getItemNoml();

				AlertDialog.Builder builder = new Builder(FolderAct.this);
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								removeDelList();
								mItemLists.clear();
								mItemLists = getArrayList();
								mAdapter.notifyDataSetChanged();
								mActionMode.finish();
								Toast.makeText(FolderAct.this, "delete ok!",
										Toast.LENGTH_SHORT).show();
							}
						});

				builder.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});

				builder.setTitle("ȷ��ɾ����?");
				builder.show();
				break;

			/* ��ݷ�ʽ */
			case R.id.action_add_shortcut:

				// getItemNoml();

				addAllFastViews();
				Toast.makeText(FolderAct.this, "add ok!", Toast.LENGTH_SHORT)
						.show();
				mActionMode.finish();

				break;
			/* �½� */
			case R.id.action_new:
				addNewItem();

				break;
			/* ���� */
			case R.id.action_move:
				toCut();
				Toast.makeText(FolderAct.this, "cut ok!", Toast.LENGTH_SHORT)
						.show();
				mActionMode.finish();
				break;

			}
			return false;
		}

		/*
		 * ����
		 */
		private void toCut() {
			// TODO Auto-generated method stub
			for (ItemMessages f : mFastLists) {
				Messages.fileToCopy.push(f);
			}
			getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);// ����menu��ʾ
		}

		/*
		 * �½�
		 */
		private void addNewItem() {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new Builder(FolderAct.this);

			builder.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							while (!Messages.fileToCopy.isEmpty()) {
								Messages.fileToCopy.pop();
							}
						}
					});
		}

		/*
		 * ɾ��SD�����ļ�
		 */
		private void removeDelList() {
			// TODO Auto-generated method stub
			for (int i = 0; i < mFastLists.size(); i++) {
				File f = mFastLists.get(i).getAbusPath();
				if (f.exists()) {
					f.delete();
					// if(){
					//
					// }
				}
			}

		}

		private void addAllFastViews() {
			// TODO Auto-generated method stub
			// Log.d("123", "4");
			// Log.d("123", mFastLists.size() + "");
			for (ItemMessages i : mFastLists) {
				Log.d("123", "456789");
				mEditor.putString(i.getName(), i.getAbusPath().toString());
			}
			mEditor.commit();
		}

		private void getItemNoml() {
			// TODO Auto-generated method stub

			Iterator<ItemMessages> m = mFastLists.iterator();

			if (m != null) {
				while (m.hasNext()) {
					ItemMessages item = m.next();
					item.setSelected();
					if (mListView.getChildAt(item.getPosion()) != null) {
						mListView.getChildAt(item.getPosion())
						// .setBackgroundResource(
						// R.drawable.selected_background);
								.setBackgroundColor(0xFFCAFFFF);
					}
				}
			}

			mAdapter.setFlag(false);
			mFastLists.clear();

			numb = mFastLists.size();
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			//
			getItemNoml();
			flag = !flag;
		}

	}
}

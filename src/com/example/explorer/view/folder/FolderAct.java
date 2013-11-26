package com.example.explorer.view.folder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.explorer.R;
import com.example.explorer.data.ItemMessages;
import com.example.explorer.data.Messages;
import com.example.explorer.util.IntentBuilder;

public class FolderAct extends Activity {
	private String FILE_NAME = "name";
	public File mFile;
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
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			// 1.
			switch (msg.what) {
			// 2.
			// switch (msg.arg1) {
			case 0:
				Toast.makeText(FolderAct.this, "�Ѱ������ļ�(��)", 0).show();

				mItemLists.clear();
				getArrayList();
				mAdapter.notifyDataSetChanged();
				getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
				break;
			case 1:
				Toast.makeText(FolderAct.this, "�����ļ�ʧ��", 0).show();

				mItemLists.clear();
				getArrayList();
				mAdapter.notifyDataSetChanged();
				getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
				break;
			case 2:
				mFastLists.clear();
				mItemLists.clear();
				getArrayList();
				mAdapter.notifyDataSetChanged();
				getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
				break;
			case 3:
				Toast.makeText(FolderAct.this, "�ļ��в��ɸ���", 0).show();
				mFastLists.clear();
				mItemLists.clear();
				getArrayList();
				mAdapter.notifyDataSetChanged();
				getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
				break;
			default:
				break;
			}

		};
	};

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
					mFastLists.clear();
					mActionMode = startActionMode(mActionModeCallback);
					flag = !flag;
				}

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

						// Toast.makeText(FolderAct.this, "can't open", 200)
						// .show();
						ItemMessages item2 = mAdapter.getItem(arg2);
						mFile = item2.getAbusPath();
						IntentBuilder.viewFile(FolderAct.this, mFile.getPath());

					}

				}
			}
		});

		ImageView view = new ImageView(this);
		view.setImageResource(R.drawable.ic_menu_new_folder);
		LayoutParams layoutParams = new LayoutParams(55, 55);
		layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(view, layoutParams);
		view.setClickable(true);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addNewItem();
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
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						int i = toPaste();
						Message message = Message.obtain();
						message.what = i;
						handler.sendEmptyMessage(message.what);

						// 1.
						// handler.sendEmptyMessage(i);
						// 2.
						// handler.sendMessage(message);

						// handler.post(new Runnable() {
						//
						// @Override
						// public void run() {//ִ��HandMessage�еķ���
						// // TODO Auto-generated method stub
						//
						// }
						// });

						// Intent intent = FolderAct.this.getIntent();
						// Intent intent = new Intent();
						// intent.setAction("Action");
						// FolderAct.this.sendBroadcast(intent);
					}
				}).start();

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

	/*
	 * ȡ��
	 */
	private void onCancle() {
		// TODO Auto-generated method stub
		while (!Messages.fileToCopy.empty()) {
			Messages.fileToCopy.pop();
		}

	}

	/*
	 * ճ��
	 */
	private int toPaste() {
		// TODO Auto-generated method stub

		int i = 0;
		while (Messages.fileToCopy.size() != 0) {
			boolean bo;
			ItemMessages itemMessages = Messages.fileToCopy.pop();
			File f = itemMessages.getAbusPath();

			String str = mFile.getAbsolutePath();
			String s = str + "/" + itemMessages.getName();
			File file = new File(s);
			Log.d("123", "file is exit=" + file.exists());
			if (file.exists()) {
				return 0;
			} else {
				try {
					// �����ļ�
					if (f.isDirectory()) {
						i++;
						Log.d("123", "is IDtectory");
						continue;
						// bo = file.mkdir();
						// �ݹ鸴���ļ���
					} else {
						bo = file.createNewFile();
					}

					// �ж��ļ��Ƿ񴴽��ɹ�
					if (!bo) {
						Messages.fileToCopy.push(itemMessages);
						Toast.makeText(FolderAct.this, "�����ļ�ʧ��", 0).show();
						return 1;
					}
					// д��
					InputStream inputStream = new FileInputStream(f);
					OutputStream outputStream = new FileOutputStream(file);
					byte[] b = new byte[1024];
					int len = 0;
					while (true) {
						len = inputStream.read(b);
						if (len != -1) {
							outputStream.write(b, 0, len);
						} else {
							break;
						}
					}
					outputStream.flush();
					outputStream.close();
					inputStream.close();
					//������ͼmap���в���
					f.delete();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					Log.d("123", "����������ʧ��");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.d("123", "�������");
					e.printStackTrace();
				}

			}

		}
		if (i > 0) {
			return 3;
		} else {
			return 2;

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Log.d("123", "outSrory" + Messages.outsideStorage.getAbsolutePath());
			Log.d("123", "rorot" + Messages.rootMenu.getAbsolutePath());
			// Log.d("123","sd"+Environment.get);
			mFile = mFile.getParentFile();
			/* �и�Ŀ¼,���ǵ��������ļ��п��Է���.....�����ص�sd���ļ��л����������ļ���ʱ��ֹ */
			if ((mFile != null || mFile.equals(Messages.clickIn))
					&& !mFile.equals(Messages.outsideStorage.getParentFile())
					&& !mFile.equals(Messages.clickIn.getParentFile())) {
				mPathTextView.setText(mFile.getAbsolutePath().toString());
				mItemLists.clear();
				getArrayList();
				mAdapter.notifyDataSetChanged();
			} else {
				finish();
			}

			break;

		default:
			break;
		}
		return true;
		// return super.onKeyDown(keyCode, event);
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

	/*
	 * �½�
	 */
	public void addNewItem() {
		// TODO Auto-generated method stub
		final View v;
		AlertDialog.Builder builder = new Builder(FolderAct.this);
		v = LayoutInflater.from(this).inflate(R.layout.new_file, null);

		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});

		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				EditText editText = (EditText) v.findViewById(R.id.editText1);
				String s = editText.getText().toString();
				s = FolderAct.this.mFile.getAbsolutePath() + "/" + s;
				File file = new File(s);

				if (!file.exists()) {
					boolean b;
					try {
						if (s.contains(".")) {
							b = file.createNewFile();
						} else {
							b = file.mkdirs();
						}

						if (b) {
							Toast.makeText(FolderAct.this, "�ļ��½��ɹ�", 0).show();
							ItemMessages itemMessages = new ItemMessages(file);
							FolderAct.this.mItemLists.add(itemMessages);
							FolderAct.this.mAdapter.notifyDataSetChanged();
						} else {
							Toast.makeText(FolderAct.this, "����֪����", 0).show();
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						Toast.makeText(FolderAct.this, "IOEXception", 0).show();
						e.printStackTrace();
					}
				} else {
					Toast.makeText(FolderAct.this, "�ļ��Ѵ���", 0).show();
				}
			}
		});

		builder.setView(v);
		builder.setTitle("�½��ļ���");
		builder.show();
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
				getItemNoml();
				mActionMode.finish();
				break;
			/* ���� */
			case R.id.action_move:
				toCut();

				if (!Messages.fileToCopy.isEmpty()) {
					Toast.makeText(FolderAct.this, "cut ok!",
							Toast.LENGTH_SHORT).show();
				}

				getItemNoml();
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
				if (f.getAbusPath().isDirectory()) {
					Toast.makeText(FolderAct.this, "�ļ��в��ɼ���", 0).show();
					continue;
				}
				if (-1 == Messages.fileToCopy.search(f)) {
					Messages.fileToCopy.push(f);
				}

				
			}
			getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);// ����menu��ʾ
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
					Toast.makeText(FolderAct.this, "ɾ���ɹ�", 0).show();
				}
			}

		}

		private void addAllFastViews() {
			// TODO Auto-generated method stub

			for (ItemMessages i : mFastLists) {

				if (!i.getAbusPath().isDirectory()) {
					Toast.makeText(FolderAct.this, "ֻ���ļ��в��ܴ�����ݷ�ʽŶ~", 0).show();
					continue;
				}
				mEditor.putString(i.getName(), i.getAbusPath().toString());
			}
			mEditor.commit();
		}

		private void getItemNoml() {
			// TODO Auto-generated method stub
			Log.d("123", "getItemNomal");
			ArrayList<ItemMessages> arrayList = new ArrayList<ItemMessages>();

			Iterator<ItemMessages> m = mFastLists.iterator();

			if (m != null) {
				// Log.d("123", "m!=null");
				while (m.hasNext()) {
					// Log.d("123", "m.hasNext");
					ItemMessages item = m.next();

					if (!arrayList.contains(item)) {
						arrayList.add(item);
						item.setSelected();
					} else {
						continue;
					}

					if (mListView.getChildAt(item.getPosion()) != null) {
						Log.d("123", "setBackground");
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

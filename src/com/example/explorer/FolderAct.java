package com.example.explorer;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.TextView;

public class FolderAct extends Activity {

	private File mFile;
	private MyReader mReader;
	private MyAdapter mAdapter;
	private ListView mListView;
	private String path;// 当前路径的绝对路径
	private TextView mPathTextView;
	private Boolean flag;
	private File[] files;
//	private final DataSetObservable mDataSetObservable = new DataSetObservable();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		flag = false;
		setContentView(R.layout.folder);
		Intent intent = this.getIntent();
		path = intent.getStringExtra("folderPath");
		mFile = new File(path);
		// mReader = new MyReader(mFile);
		mListView = (ListView) findViewById(R.id.listView1);
		mPathTextView = (TextView) findViewById(R.id.textView1);
		files = mFile.listFiles();
		mAdapter = new MyAdapter(this, files);
//		mAdapter.registerDataSetObserver(mDataSetObservable);
		mPathTextView.setText(path);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
//				((Item) arg1).setFlag(false);
				mAdapter.setFlag(true);

				arg1.setBackgroundColor(Color.BLUE);
				return false;
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (mAdapter.getFlag()) {
					arg1.setBackgroundColor(Color.BLUE);
					// arg1.各种操作
				} else {
					Log.d("123", "123124324");
					Item ite = (Item) mListView.getChildAt(arg2);
					mAdapter.notifyDataSetInvalidated();
					path = ite.getAbuPath();
					mFile = new File(path);
					files = mFile.listFiles();
					mAdapter.setFiles(files);
					mAdapter.notifyDataSetChanged();
					
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

}

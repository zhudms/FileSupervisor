package com.example.explorer.view.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.explorer.R;
import com.example.explorer.data.Messages;
import com.example.explorer.view.folder.FolderAct;
import com.example.explorer.view.main.MyWeight.ClickInListener;
import com.example.explorer.view.main.MyWeight.OnDelLisner;
import com.example.explorer.view.main.MyWeight.OnlangClickLisener;

public class MainActivity extends Activity {

	private LinearLayout mLinearLayout;
	private GridLayout mGridLayout;
	private MyWeight frountSd;
	private MyWeight frountPic;
	private MyWeight frountDown;
	private Animator mAnimSet;
	private Animator mAnimAbout;
	// private ImageView mToolImageView;
	private ImageView mSetImageView;
	private ImageView mAboutImageView;
	private AnimatorSet animSetStart;
	private ObjectAnimator animXSet;
	private ObjectAnimator animXAbout;
	private AnimatorSet animSetEnd;
	private Boolean flag;// 指示能否被移除
	private OnlangClickLisener onlangClickLisener;
	private Toast mToast;
	private OnDelLisner mDelLisner;
	private Boolean flagSet = false;
	private OnClickListener mClickListener;
	private Boolean flagReturn;// 用于处理返回键
	private ClickInListener clickInlistener;
	private SharedPreferences mPreferences;
	private SharedPreferences.Editor mEditor;
	private Map<String, String> map;
	private String s = Messages.DicStorage.toString();
	private ArrayList<MyWeight> fastList;
	private boolean fla = false;
	private CheckBox box;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		map = new HashMap<String, String>();
		fastList = new ArrayList<MyWeight>();
		Init();

		// int o = getWindow().getDecorView().getHeight();
		// int l = getWindow().getDecorView().getWidth();
		// int o=getWindowManager().getDefaultDisplay().getHeight();
		// int l=getWindowManager().getDefaultDisplay().getWidth();

		// Point p = new Point();
		// getWindowManager().getDefaultDisplay().getSize(p);
		// int o = p.x;
		// int l = p.y;
		// Log.d("123", "h=" + o + "  w=" + l);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.getItem(0);
		MenuItem item2 = menu.getItem(1);

		item.setVisible(false);
		item2.setVisible(false);

		return true;
	}

	/*
	 * 初始化,监听者,动画效果
	 */
	private void Init() {

		getAllLisener();

		mToast = new Toast(MainActivity.this);
		animSetStart = new AnimatorSet();
		animSetEnd = new AnimatorSet();
		mGridLayout = (GridLayout) findViewById(R.id.gridLayout);// 文件夹布局
		mLinearLayout = (LinearLayout) findViewById(R.id.BottomLayout);//

		mSetImageView = (ImageView) mLinearLayout.findViewById(R.id.imageSet);
		mAboutImageView = (ImageView) mLinearLayout
				.findViewById(R.id.imageAbout);
		mSetImageView.setVisibility(View.INVISIBLE);
		mAboutImageView.setVisibility(View.INVISIBLE);
		flag = true;

		frountSd = (MyWeight) mGridLayout.findViewById(R.id.WeightSd);
		frountDown = (MyWeight) mGridLayout.findViewById(R.id.WeightDown);
		frountPic = (MyWeight) mGridLayout.findViewById(R.id.WeightPic);

		frountSd.setPic(R.drawable.shortcutsd);
		frountPic.setPic(R.drawable.shortcut_pictures);
		frountDown.setPic(R.drawable.shortcut_downloads);

		inidView(frountSd, Messages.outsideStorage.getName(),
				Messages.outsideStorage);
		inidView(frountPic, Messages.DicStorage.getName(), Messages.DicStorage);// 暂时没有
		inidView(frountDown, Messages.downStorage.getName(),
				Messages.downStorage);

		// MyWeight m = new MyWeight(this, null);
		// MyWeight m1 = new MyWeight(this, null);
		// m.setLangClickLisener(onlangClickLisener);
		// m.setDelLisenter(mDelLisner);
		// mGridLayout.addView(m);
		// m1.setLangClickLisener(onlangClickLisener);
		// mGridLayout.addView(m1);
		// m1.setDelLisenter(mDelLisner);
		// MyWeight m2 = new MyWeight(this, null);
		// MyWeight m3 = new MyWeight(this, null);
		// MyWeight m4 = new MyWeight(this, null);
		//
		// m4.setLangClickLisener(onlangClickLisener);
		// mGridLayout.addView(m2);
		// m2.setDelLisenter(mDelLisner);
		// m2.setLangClickLisener(onlangClickLisener);
		// mGridLayout.addView(m3);
		// m3.setDelLisenter(mDelLisner);
		// m3.setLangClickLisener(onlangClickLisener);
		// mGridLayout.addView(m4);
		// m4.setDelLisenter(mDelLisner);

		mPreferences = this.getSharedPreferences("FastViews", MODE_PRIVATE);
		mEditor = mPreferences.edit();
		// mEditor.putString("Hidden", "T");
	}

	public void setShow(View v) {

		if (this.flag) {
			getShowAnim();
		} else {
			getCloseAnim();
		}

		this.flag = !flag;

	}

	public void getShowAnim() {

		mSetImageView.setVisibility(View.VISIBLE);
		mAboutImageView.setVisibility(View.VISIBLE);

		mAnimSet = ObjectAnimator.ofFloat(mSetImageView, "rotation", 0f, 360f);// 旋转
		animXSet = ObjectAnimator.ofFloat(mSetImageView, "x", 0f, 80f);

		mAnimAbout = ObjectAnimator.ofFloat(mAboutImageView, "rotation", 0f,
				360f);// 旋转
		animXAbout = ObjectAnimator.ofFloat(mAboutImageView, "x", 0f, 160f);

		animSetStart.setDuration(500);
		animSetStart.playTogether(mAnimSet, animXSet, mAnimAbout, animXAbout);
		animSetStart.start();

	}

	public void getCloseAnim() {

		mAnimSet = ObjectAnimator.ofFloat(mSetImageView, "rotation", 0f, 360f);// 旋转
		animXSet = ObjectAnimator.ofFloat(mSetImageView, "x", 80f, 0f);

		mAnimAbout = ObjectAnimator.ofFloat(mAboutImageView, "rotation", 0f,
				360f);// 旋转
		animXAbout = ObjectAnimator.ofFloat(mAboutImageView, "x", 160f, 0f);

		animSetEnd.setDuration(500);
		animSetEnd.playTogether(mAnimSet, animXSet, mAnimAbout, animXAbout);
		animSetEnd.start();

		animSetEnd.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				mSetImageView.setVisibility(View.INVISIBLE);
				mAboutImageView.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void setHide(View v) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("设置");

		LayoutInflater mInflater = LayoutInflater.from(this);
		View setIpView = mInflater.inflate(R.layout.show_hid, null);

		box = (CheckBox) setIpView.findViewById(R.id.checkBox1);
		box.setChecked(Messages.getHid().equals("H") ? false : true);
		// if (flagSet) {
		//
		// ((ImageView) v)
		// .setImageResource(R.drawable.btn_check_on_holo_light);
		//
		// } else {
		// ((ImageView) v)
		// .setImageResource(R.drawable.btn_check_off_holo_light);
		//
		// }

		builder.setView(setIpView);

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				flagSet = box.isChecked();
				if (flagSet) {

					Messages.setHid("S");
				} else {
					Messages.setHid("H");
				}

				getCloseAnim();
			}
		});
		Dialog d = builder.create();
		d.show();

	}

	public void about(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("关于");

		LayoutInflater mInflater = LayoutInflater.from(this);
		View setIpView = mInflater.inflate(R.layout.about, null);
		builder.setView(setIpView);

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				getCloseAnim();
			}
		});
		Dialog d = builder.create();
		d.show();

	}

	public void inidView(MyWeight myWeight, String text, File abuPath) {

		myWeight.setText(text);// 名字
		myWeight.setAbsPath(abuPath);// 绝对路径
		myWeight.setOnClickListener(mClickListener);
		myWeight.setLangClickLisener(onlangClickLisener);
		myWeight.setDelLisenter(mDelLisner);
		myWeight.setClickedInLisener(clickInlistener);
	}

	public void getAllLisener() {

		mDelLisner = new OnDelLisner() {

			@Override
			public void Del(View v) {
				// TODO Auto-generated method stub
				mGridLayout.removeView(v);
				if (fastList.contains(v)) {
					// Log.d("123", "qwertyyiuy");
					fastList.remove(v);
				}
			}
		};
		onlangClickLisener = new OnlangClickLisener() {

			@Override
			public void LangClick(View v, Boolean flag) {
				// TODO Auto-generated method stub
				if (!flag) {

					String s = "can't move";
					mToast = Toast.makeText(MainActivity.this, s, 200);
					mToast.show();

				} else {
					frountDown.setFlagClick();
					frountPic.setFlagClick();
					frountSd.setFlagClick();
					int i = mGridLayout.getChildCount();
					for (int j = 3; j < i; j++) {
						MyWeight myWeight = (MyWeight) mGridLayout
								.getChildAt(j);
						myWeight.getShow();
					}
				}
			}
		};

		mClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((MyWeight) v).getStyle()) {
					changeAct(((MyWeight) v).getAbsPath());
				} else {
					Toast.makeText(MainActivity.this, "can't open", 200).show();
				}

			}
		};

		clickInlistener = new ClickInListener() {

			@Override
			public void GetIn(View v) {
				// TODO Auto-generated method stub
				if (((MyWeight) v).getFlagClick()) {
					changeAct(((MyWeight) v).getAbsPath());
					Messages.clickIn = new File(((MyWeight) v).getAbsPath());
				}
			}
		};

	}

	public void changeAct(String value) {
		Intent intent = new Intent(MainActivity.this, FolderAct.class);
		intent.putExtra("folderPath", value);// 文件夹名,绝对路径
		startActivity(intent);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		mPreferences = this.getSharedPreferences("FastViews", MODE_PRIVATE);
		map = (Map<String, String>) mPreferences.getAll();
		Set<String> set = map.keySet();

		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
			String v = iter.next();
			Log.d("123", "v" + v);
			String path = mPreferences.getString(v, "123");
			if (!path.equals("123")) {

				Log.d("123", "yuiop" + path);
				MyWeight w = new MyWeight(this, null);

				inidView(w, v, new File(path));

				mGridLayout.addView(w);
				fastList.add(w);
				mEditor = mPreferences.edit();
				mEditor.putString(v, "123");

			} else {
				continue;
			}
		}

		mEditor.clear();
		mEditor.commit();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			int count = mGridLayout.getChildCount();

			flag = ((MyWeight) mGridLayout.getChildAt(count - 1)).getFlag();

			if (!flag) {

//				frountDown.setFlagClick();
//				frountPic.setFlagClick();
//				frountSd.setFlagClick();
				for (int i = 0; i < count; i++) {
					MyWeight v = (MyWeight) mGridLayout.getChildAt(i);
					v.setDelShow(flag);
					v.clearAnimation();
					v.stopAnim();
					if (flag == true) {
						flag = !flag;
					}
				}
				return true;
			} else if (flag) {
				Iterator<MyWeight> iter = fastList.iterator();
				while (iter.hasNext()) {
					MyWeight w = iter.next();
					mEditor.putString(w.getName(), w.getAbsPath());
				}
				mEditor.commit();
				AlertDialog.Builder builder = new Builder(this);
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
							}

						});

				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
				builder.setTitle("确定退出?");
				builder.show();
			}

		}

		return true;
		// return super.onKeyDown(keyCode, event);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

}

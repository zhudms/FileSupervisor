package com.example.explorer;

import java.io.File;

import com.example.explorer.MyWeight.ClickInListener;
import com.example.explorer.MyWeight.OnDelLisner;
import com.example.explorer.MyWeight.OnlangClickLisener;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
	private Boolean flagSet;
	private OnClickListener mClickListener;
	private Boolean flagReturn;// 用于处理返回键
	private ClickInListener clickInlistener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Init();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void Init() {

		getAllLisener();
		flagSet = true;
		mToast = new Toast(MainActivity.this);
		animSetStart = new AnimatorSet();
		animSetEnd = new AnimatorSet();
		mGridLayout = (GridLayout) findViewById(R.id.gridLayout);
		mLinearLayout = (LinearLayout) findViewById(R.id.BottomLayout);

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
		inidView(frountPic,Messages.DicStorage.getName(),
				Messages.DicStorage);// 暂时没有
		inidView(frountDown,Messages.downStorage.getName(),
				Messages.downStorage);

		MyWeight m = new MyWeight(this, null);
		MyWeight m1 = new MyWeight(this, null);
		m.setLangClickLisener(onlangClickLisener);
		m.setDelLisenter(mDelLisner);
		mGridLayout.addView(m);
		m1.setLangClickLisener(onlangClickLisener);
		mGridLayout.addView(m1);
		m1.setDelLisenter(mDelLisner);
		MyWeight m2 = new MyWeight(this, null);
		MyWeight m3 = new MyWeight(this, null);
		MyWeight m4 = new MyWeight(this, null);

		m4.setLangClickLisener(onlangClickLisener);
		mGridLayout.addView(m2);
		m2.setDelLisenter(mDelLisner);
		m2.setLangClickLisener(onlangClickLisener);
		mGridLayout.addView(m3);
		m3.setDelLisenter(mDelLisner);
		m3.setLangClickLisener(onlangClickLisener);
		mGridLayout.addView(m4);
		m4.setDelLisenter(mDelLisner);
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
		View setIpView = mInflater.inflate(R.layout.set, null);
		ImageView im = (ImageView) setIpView.findViewById(R.id.imageViewSelect);
		im.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (flagSet) {

					((ImageView) v)
							.setImageResource(R.drawable.btn_check_on_holo_light);
				} else {
					((ImageView) v)
							.setImageResource(R.drawable.btn_check_off_holo_light);
				}
				flagSet = !flagSet;
			}
		});
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
			
				changeAct(((MyWeight) v).getAbsPath());

			}
		};
		
		clickInlistener=new ClickInListener() {
			
			@Override
			public void GetIn(View v) {
				// TODO Auto-generated method stub
			
				changeAct(((MyWeight) v).getAbsPath());
			}
		};

	}

	public void changeAct(String value) {
		Intent intent = new Intent(MainActivity.this, FolderAct.class);
		intent.putExtra("folderPath", value);// 文件夹名,绝对路径
		startActivity(intent);
	}
}

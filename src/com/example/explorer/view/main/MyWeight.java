package com.example.explorer.view.main;

/*<LinearLayout
 android:layout_width="match_parent"
 android:layout_height="match_parent"
 android:layout_weight="1"
 android:background="#095655" >

 <com.example.buttonshake.MyWeight
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 android:layout_weight="1"
 android:background="#155589" >
 </com.example.buttonshake.MyWeight>




 </LinearLayout>*/
import java.io.File;

import com.example.explorer.R;
import com.example.explorer.R.id;
import com.example.explorer.R.layout;
import com.example.explorer.data.Messages;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @ClassName: MyWeight
 * @Description: 单元类,文件夹快捷方式
 * @author T
 * @date 2013-11-8 下午9:23:03
 * 
 */
public class MyWeight extends LinearLayout {

	private ImageView mImageViewPic;
	private ImageView mImageViewDel;
	private TextView mTextView;
	private Animation mAnimation;
	private boolean flag = true;// 是否显示删除符号
	private RelativeLayout mRelativeLayout;
	private Context context;
	private File mFile;
	private boolean flagClick = false;// 是否能点击?

	public interface OnlangClickLisener {
		void LangClick(View v, Boolean flag);
	}

	public interface OnDelLisner {
		void Del(View v);
	}

	public interface ClickInListener {
		void GetIn(View v);
	}

	private ClickInListener mClickInListener;
	private OnlangClickLisener mlangClickLisener;
	private OnDelLisner mDelLisner;

	public MyWeight(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		LayoutInflater.from(context).inflate(R.layout.floderbut_layout, this,
				true);
		mRelativeLayout = (RelativeLayout) findViewById(R.id.relateLayout);
		mImageViewDel = (ImageView) findViewById(R.id.imageViewdel);
		mImageViewPic = (ImageView) findViewById(R.id.imageViewFolder);
		mTextView = (TextView) findViewById(R.id.textViewName);

		setDelShow(false);

		mImageViewPic.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub

				if (flag) {

					if (mlangClickLisener != null) {

						if (mTextView.getText() == Messages.outsideStorage
								.getName()
								|| mTextView.getText().equals(
										Messages.DicStorage.getName())
								|| mTextView.getText().equals(
										Messages.downStorage.getName())) {
							mlangClickLisener.LangClick(MyWeight.this, false);

						} else {

							mlangClickLisener.LangClick(MyWeight.this, true);
						}

					} else {

					}

				} else {
					setDelShow(flag);
					stopAnim();
				}
				return false;
			}
		});

		mImageViewDel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				setDelShow(flag);
				stopAnim();
				if (mDelLisner != null) {
					mDelLisner.Del(MyWeight.this);// 只是调用这个函数
				}
			}
		});

		mImageViewPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mClickInListener != null) {
					mClickInListener.GetIn(MyWeight.this);
				}
			}
		});

	}

	public void getShow() {
		setDelShow(flag);
		shake();
		startAnimation();
	}

	public void setDelLisenter(OnDelLisner onDelLisner) {
		mDelLisner = onDelLisner;
	}

	public void setLangClickLisener(OnlangClickLisener onlangClickLisener) {
		mlangClickLisener = onlangClickLisener;
	}

	public Boolean getFlag() {
		return flag;
	}

	private void startAnimation() {
		// flagClick = !flagClick;
		mRelativeLayout.startAnimation(mAnimation);// 控制哪部分在晃
	}

	public void stopAnim() {
		// flagClick = !flagClick;
		mRelativeLayout.clearAnimation();
	}

	public void setDelShow(boolean flag) {
		flagClick = !flagClick;
		if (flag) {
			mImageViewDel.setVisibility(VISIBLE);
		} else {
			mImageViewDel.setVisibility(INVISIBLE);
		}
		this.flag = !flag;
	}

	public void shake() {
		mAnimation = new RotateAnimation(-15f, 15f, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mAnimation.setDuration(150);
		mAnimation.setRepeatMode(Animation.REVERSE);
		mAnimation.setRepeatCount(Animation.INFINITE);

	}

	public void setPic(int id) {
		mImageViewPic.setImageResource(id);
	}

	public void setText(String s) {
		mTextView.setText(s);
	}

	public String getKey() {// 此文件名
		return (String) mTextView.getText();
	}

	public void setAbsPath(File absPath) {
		mFile = absPath;
	}

	public String getAbsPath() {
		if (mFile.getAbsolutePath() != null) {
			return mFile.getAbsolutePath();
		} else {
			return Messages.outsideStorage.getPath();// 此处未处理(返回了外存根目录)
		}
	}

	public void setClickedInLisener(ClickInListener clickListener) {
		this.mClickInListener = clickListener;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return mFile.getName();
	}

	public boolean getFlagClick() {
		// TODO Auto-generated method stub
		return this.flagClick;
	}

	public void setFlagClick() {
		// TODO Auto-generated method stub
		this.flagClick = !flagClick;
	}

	public boolean getStyle() {
		// TODO Auto-generated method stub
		if (mFile.isFile()) {
			return false;
		} else {
			return true;
		}
	}
}

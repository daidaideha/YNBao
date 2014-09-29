package com.innouni.yinongbao.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.comFunction;
import com.innouni.yinongbao.widget.sPreferences;

/***
 * 欢迎界面
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-23
 */
public class WelcomeActivity extends Activity {
	/***
	 * 轮播图控件
	 */
	private ViewPager mViewPager;
	/***
	 * 轮播图底部圈圈布局控件
	 */
	private LinearLayout ll_vp_bottom;
	/***
	 * 存储数据的sp
	 */
	private sPreferences iSPreferences;

	/***
	 * 轮播图底部圈圈数据列表
	 */
	private List<ImageView> list_bottom;
	/***
	 * 轮播图内容数据列表
	 */
	private List<View> views;
	/***
	 * 轮播图当前选中项
	 */
	private int currPgae = 0;
	private int lastX = 0;
	
	/***
	 * 启动异步
	 */
	private LauncherTask launcherTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		iSPreferences = new sPreferences(this);

		initBodyer();
		initData();
	}

	/***
	 * 初始化数据
	 */
	private void initData() {
		views = new ArrayList<View>();
		list_bottom = new ArrayList<ImageView>();
		ImageView view_first = new ImageView(this);
		view_first.setScaleType(ScaleType.FIT_XY);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.bg_welcome_first);
		view_first.setImageBitmap(bmp);
		views.add(view_first);
		ImageView view_second = new ImageView(this);
		view_second.setScaleType(ScaleType.FIT_XY);
		Bitmap bmp2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.bg_welcome_second);
		view_second.setImageBitmap(bmp2);
		views.add(view_second);
		View view_third = getLayoutInflater().inflate(
				R.layout.view_welcome_third, null);
		Button btn_jump = (Button) view_third.findViewById(R.id.btn_jump);
		btn_jump.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				if (comFunction.isNullorSpace(iSPreferences.getSp().getString(
						"memberId", ""))) {
					new IntentToOther(WelcomeActivity.this,
							LoginActivity.class, null);
				} else {
					new IntentToOther(WelcomeActivity.this,
							MainPageActivity.class, null);
				}
			}
		});
		views.add(view_third);
		for (int i = 0; i < views.size(); i++) {
			ImageView imageView2 = new ImageView(this);
			imageView2.setScaleType(ScaleType.CENTER);
			if (i != currPgae) {
				imageView2
						.setBackgroundResource(R.drawable.icon_welcome_banner);
			} else {
				imageView2
						.setBackgroundResource(R.drawable.icon_welcome_banner_hover);
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(10, 10, 10, 10);
			imageView2.setLayoutParams(layoutParams);
			list_bottom.add(imageView2);
			ll_vp_bottom.addView(imageView2);
		}
		mViewPager.setAdapter(new MyAdapter());
	}

	/***
	 * 初始化布局控件
	 */
	private void initBodyer() {
		mViewPager = (ViewPager) findViewById(R.id.viewpager_welcome);
		ll_vp_bottom = (LinearLayout) findViewById(R.id.ll_vp_bottom);
		
		if (iSPreferences.getSp().getBoolean("first", true)) {
			iSPreferences.updateSp("first", false);
		} else {
			Launch();
		}

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				currPgae = position;
				for (int i = 0; i < list_bottom.size(); i++) {
					if (i == position) {
						list_bottom.get(i).setBackgroundResource(
								R.drawable.icon_welcome_banner_hover);
					} else {
						list_bottom.get(i).setBackgroundResource(
								R.drawable.icon_welcome_banner);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					lastX = (int) event.getX();
					break;
				case MotionEvent.ACTION_MOVE:
					if ((lastX - event.getX()) > 100
							&& (currPgae == views.size() - 1)) {
						finish();
						if (comFunction.isNullorSpace(iSPreferences.getSp()
								.getString("memberId", ""))) {
							new IntentToOther(WelcomeActivity.this,
									LoginActivity.class, null);
						} else {
							new IntentToOther(WelcomeActivity.this,
									MainPageActivity.class, null);
						}
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	/***
	 * viewpager适配器
	 * @author LinYuLing
	 *
	 */
	public class MyAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return super.getItemPosition(object);
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1));
			return views.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

	}
	
	/***
	 * 启动操作
	 */
	private void Launch() {
		if (launcherTask == null) {
			launcherTask = new LauncherTask();
			launcherTask.execute();
		}
	}
	
	/***
	 * 启动异步
	 * @author LinYuLing
	 *
	 */
	private class LauncherTask extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mViewPager.setVisibility(View.GONE);
			ll_vp_bottom.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			launcherTask = null;
			finish();
			if (comFunction.isNullorSpace(iSPreferences.getSp().getString(
					"memberId", ""))) {
				new IntentToOther(WelcomeActivity.this, LoginActivity.class,
						null);
			} else {
				new IntentToOther(WelcomeActivity.this, MainPageActivity.class,
						null);
			}
		}
	}
}

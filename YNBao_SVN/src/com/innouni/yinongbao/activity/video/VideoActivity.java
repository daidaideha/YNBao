package com.innouni.yinongbao.activity.video;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.AdDetailActivity;
import com.innouni.yinongbao.adapter.ExhibitionMainAdapter;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.unit.AdUnit;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.video.VideoMainUnit;
import com.innouni.yinongbao.unit.video.VideoTypeUnit;
import com.innouni.yinongbao.unit.video.VideoUnit;
import com.innouni.yinongbao.view.PopExhibitionCompany;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 视频库主界面
 * @author LinYuLing
 * @UpdateDate 2014-10-06
 */
public class VideoActivity extends Activity implements OnClickListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 菜单按钮
	 */
	private Button btn_menu;
	/***
	 * 搜索框
	 */
	private EditText edt_search;
	/***
	 * 视频展示控件
	 */
	private ListView listView;
	/***
	 * 广告控件
	 */
	private View headview;
	/***
	 * 轮播图外层布局控件
	 */
	private RelativeLayout rl_vp;
	/***
	 * 轮播图控件
	 */
	private ViewPager mViewPager;
	/***
	 * 轮播图底部圈圈布局控件
	 */
	private LinearLayout ll_vp_bottom;
	/***
	 * 分类弹出框
	 */
	private PopExhibitionCompany popType;
	
	/***
	 * 数据适配器
	 */
	private ExhibitionMainAdapter<VideoMainUnit> adapter;
	/***
	 * 图片加载工具类
	 */
	private ImageLoader mImageLoader;
	/***
	 * 用来获取屏幕大小
	 */
	private DisplayMetrics dm;
	/***
	 * 等待进度框
	 */
	private ProgressDialog pd;
	
	/***
	 * 初始化数据列表
	 */
	private List<VideoMainUnit> list_bak;
	/***
	 * 数据列表
	 */
	private List<VideoMainUnit> list_data;
	/***
	 * 分类数据列表
	 */
	private List<VideoTypeUnit> list_type;
	/***
	 * 分类按钮列表
	 */
	private List<Button> listBtn;
	/***
	 * 轮播图当前选中项
	 */
	private int currPgae = 0;
	/***
	 * 广告栏数据列表
	 */
	private List<AdUnit> list_ad;
	/***
	 * 轮播图底部圈圈数据列表
	 */
	private List<ImageView> list_bottom;
	private List<ImageView> pageViews;

	/***
	 * 获取数据异步
	 */
	private GetDataTask getDataTask;
	/***
	 * 获取分类数据异步
	 */
	private GetTypeTask getTypeTask;
	/***
	 * 获取广告数据异步
	 */
	private GetADTask getADTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exhibition_main);

		initData();
		initHeader();
		initBodyer();
		getType();
		getAD();
	}
	
	/***
	 * 数据初始化
	 */
	private void initData() {
		pd = new ProgressDialog(VideoActivity.this);
		pd.setMessage(getString(R.string.pd_data_link));
		pd.setIndeterminate(true);
		pd.setCancelable(true);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mImageLoader = new ImageLoader(this);
		list_bottom = new ArrayList<ImageView>();
		list_bak = new ArrayList<VideoMainUnit>();
		list_data = new ArrayList<VideoMainUnit>();
		list_type = new ArrayList<VideoTypeUnit>();
		list_ad = new ArrayList<AdUnit>();
		pageViews = new ArrayList<ImageView>();
		listBtn = new ArrayList<Button>();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(getString(R.string.tv_header_video));
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/***
	 * 初始化布局控件
	 */
	private void initBodyer() {
		btn_menu = (Button) findViewById(R.id.btn_menu);
		edt_search = (EditText) findViewById(R.id.edt_search);
		listView = (ListView) findViewById(R.id.listview);
		headview = getLayoutInflater().inflate(R.layout.view_viewpager, null);
		mViewPager = (ViewPager) headview.findViewById(R.id.viewpager_main);
		rl_vp = (RelativeLayout) headview.findViewById(R.id.rl_vp);
		rl_vp.setLayoutParams(new LayoutParams(dm.widthPixels, dm.widthPixels / 2));
		ll_vp_bottom = (LinearLayout) headview.findViewById(R.id.ll_vp_bottom);
		listView.addHeaderView(headview);
		adapter = new ExhibitionMainAdapter<VideoMainUnit>(this, 
				list_bak, dm.widthPixels / 21 * 8);
		listView.setAdapter(adapter);
		 
		edt_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		edt_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int actionId,
					KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					Bundle bundle = new Bundle();
					bundle.putInt("type", 1);
					bundle.putString("search", edt_search.getText().toString());
					new IntentToOther(VideoActivity.this, 
							VideoTypeActivity.class, bundle);
					edt_search.setText("");
				}
				return false;
			}
		});
		initViewPager();
		
		btn_menu.setOnClickListener(this);
	}

	/***
	 * 初始化轮播图控件
	 */
	private void initViewPager() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				currPgae = position;
				for (int i = 0; i < list_bottom.size(); i++) {
					if (i == currPgae) {
						list_bottom.get(i).setBackgroundResource(
								R.drawable.icon_homepage_banner_hover);
					} else {
						list_bottom.get(i).setBackgroundResource(
								R.drawable.icon_homepage_banner);
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

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (popType != null) {
			popType.showPopupWindow();
		}
	}

	/***
	 * 轮播图赋值
	 */
	private void initPVData() {
		for (int i = 0; i < list_ad.size(); i++) {
			ImageView imageView = new ImageView(this);
			imageView.setScaleType(ScaleType.FIT_XY);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.icon_app);
			imageView.setImageBitmap(bmp);
			ImageView imageView2 = new ImageView(this);
			imageView2.setScaleType(ScaleType.CENTER);
			if (i != currPgae) {
				imageView2.setBackgroundResource(R.drawable.icon_homepage_banner);
			} else {
				imageView2.setBackgroundResource(R.drawable.icon_homepage_banner_hover);
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(10, 10, 10, 10);
			imageView2.setLayoutParams(layoutParams);
			pageViews.add(imageView);
			ll_vp_bottom.addView(imageView2);
		}
		mViewPager.setAdapter(new MyAdapter());
		mViewPager.setCurrentItem(0);
		if (list_ad.size() == 0) {
			listView.removeHeaderView(headview);
		} 
	}

	/***
	 * 获取分类数据操作
	 */
	private void getType() {
		if (comFunction.isWiFi_3G(this)) {
			if (getTypeTask == null) {
				getTypeTask = new GetTypeTask();
				getTypeTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}

	/***
	 * 获取数据操作
	 */
	private void getData() {
		if (comFunction.isWiFi_3G(this)) {
			if (getDataTask == null) {
				getDataTask = new GetDataTask();
				getDataTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}

	/***
	 * 获取广告图片
	 */
	private void getAD() {
		if (comFunction.isWiFi_3G(this)) {
			if (getDataTask == null) {
				getADTask = new GetADTask();
				getADTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}
	
	/***
	 * 初始化分类弹出框
	 */
	private void initType() {
		for (int i = 0; i < list_type.size(); i++) {
			Button btn = new Button(this);
			btn.setText(list_type.get(i).getCatName());
			btn.setBackgroundResource(R.drawable.btn_exhibition_company_type_style);
			btn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dm.widthPixels / 8));
			btn.setGravity(Gravity.CENTER);
			final int pos = i;
			btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popType.showPopupWindow();
					Bundle bundle = new Bundle();
					bundle.putString("id", list_type.get(pos).getCatId());
					new IntentToOther(VideoActivity.this,
							VideoTypeActivity.class, bundle);
				}
			});
			listBtn.add(btn);
			
		}
		if (popType != null) {
			popType = null;
		}
		popType = new PopExhibitionCompany(this, listBtn, btn_menu, 
				dm.widthPixels / 3, dm.heightPixels / 3);
	}

	/***
	 * viewpager适配器
	 * 
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
			return list_ad.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			mImageLoader.DisplayImage(list_ad.get(arg1).getImageUrl(),
					pageViews.get(arg1), false);
			((ViewPager) arg0).addView(pageViews.get(arg1));
			final int pos = arg1;
			pageViews.get(arg1).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putString("title", list_ad.get(pos).getName());
					bundle.putString("linkurl", list_ad.get(pos).getLinkUrl());
					new IntentToOther(VideoActivity.this, AdDetailActivity.class, bundle);
				}
			});
			return pageViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

	}

	/***
	 * 获取数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, Void> {
		private JSONObject jobj;
		private JSONArray jArray_data;
		private List<NameValuePair> paramsList;
		/***
		 * 服务器返回类型值 200：成功
		 */
		private String code;
		/***
		 * 服务器返回提示内容值
		 */
		private String message = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			list_data.clear();
			paramsList = new ArrayList<NameValuePair>();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("get_video_index", 
					paramsList, VideoActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					jArray_data = new JSONArray(jobj.getString("data"));
					if (jArray_data == null) {
						return null;
					}
					VideoMainUnit unit = null;
					VideoUnit unit_p = null;
					List<VideoUnit> list = null;
					for (int i = 0; i < jArray_data.length(); i++) {
						unit = new VideoMainUnit();
						unit.setCatId(jArray_data.getJSONObject(i).getString("catid"));
						unit.setCatName(jArray_data.getJSONObject(i).getString("catname"));
						JSONArray jArray_p = new JSONArray(jArray_data.getJSONObject(i)
								.getString("picturelist"));
						list = new ArrayList<VideoUnit>();
						for (int j = 0; j < jArray_p.length(); j++) {
							unit_p = new VideoUnit();
							unit_p.setId(jArray_p.getJSONObject(j).getString("id"));
							unit_p.setVid(jArray_p.getJSONObject(j).getString("vid"));
							unit_p.setThumb(jArray_p.getJSONObject(j).getString("thumb"));
							unit_p.setTitle(jArray_p.getJSONObject(j).getString("title"));
							list.add(unit_p);
						}
						unit.setPicturelist(list);
						list_data.add(unit);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			getDataTask = null;
			if (message != null) {
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					adapter.clearList();
					adapter.setList(list_data);
					adapter.notifyDataSetChanged();
				} else {
					comFunction.toastMsg(message, VideoActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						VideoActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}

	}

	/***
	 * 获取筛选数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetTypeTask extends AsyncTask<Void, Void, Void> {
		private JSONObject jobj;
		private JSONArray jobj_data;
		private List<NameValuePair> paramsList;
		/***
		 * 服务器返回类型值 200：成功
		 */
		private String code;
		/***
		 * 服务器返回提示内容值
		 */
		private String message = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("get_video_menu", paramsList,
					VideoActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					jobj_data = new JSONArray(jobj.getString("data"));
					if (jobj_data == null) {
						return null;
					}
					VideoTypeUnit unit = null;
					for (int i = 0; i < jobj_data.length(); i++) {
						unit = new VideoTypeUnit();
						unit.setCatId(jobj_data.getJSONObject(i).getString("catid"));
						unit.setCatName(jobj_data.getJSONObject(i).getString("catname"));
						list_type.add(unit);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			getDataTask = null;
			if (message != null) {
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					initType();
				} else {
					comFunction.toastMsg(message, VideoActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						VideoActivity.this);
			}
			getAD();
			super.onPostExecute(result);
		}
	}

	/***
	 * 获取广告位信息
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetADTask extends AsyncTask<Void, Void, Void> {
		// /***
		// * 等待进度框
		// */
		// private ProgressDialog pd;
		private JSONObject jobj;
		private JSONArray jArray_data;
		private List<NameValuePair> paramsList;
		/***
		 * 服务器返回类型值 200：成功
		 */
		private String code;
		/***
		 * 服务器返回提示内容值
		 */
		private String message = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("spaceId", "103"));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("ynb_ad_index",
					paramsList, VideoActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					jArray_data = new JSONArray(jobj.getString("data"));
					if (jArray_data == null) {
						return null;
					}
					AdUnit unit = null;
					for (int i = 0; i < jArray_data.length(); i++) {
						unit = new AdUnit();
						unit.setName(jArray_data.getJSONObject(i).getString("name"));
						unit.setImageUrl(jArray_data.getJSONObject(i)
								.getString("imageurl"));
						unit.setLinkUrl(jArray_data.getJSONObject(i).getString(
								"linkurl"));
						list_ad.add(unit);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			getADTask = null;
			if (message != null) {
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					initPVData();
				} else {
					comFunction.toastMsg(message, VideoActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						VideoActivity.this);
			}
			getData();
		}
	}

}

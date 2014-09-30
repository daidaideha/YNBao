package com.innouni.yinongbao.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.AdDetailActivity;
import com.innouni.yinongbao.activity.ask.AskActivity;
import com.innouni.yinongbao.activity.counsel.PhoneCallActivity;
import com.innouni.yinongbao.activity.exhibition.ExhibitionActivity;
import com.innouni.yinongbao.activity.expert.ExpertMainActivity;
import com.innouni.yinongbao.activity.group.GroupOfListActivity;
import com.innouni.yinongbao.activity.knowledge.KnowledgeActivity;
import com.innouni.yinongbao.activity.pest.PestActivity;
import com.innouni.yinongbao.activity.video.VideoActivity;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.unit.AdUnit;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.comFunction;

@SuppressLint("NewApi")
public class HomePageFragment extends Fragment implements OnClickListener {
	/***
	 * 轮播图控件
	 */
	private ViewPager mViewPager;
	/***
	 * 轮播图外层布局
	 */
	private RelativeLayout rl_vp;
	/***
	 * 轮播图底部圈圈布局控件
	 */
	private LinearLayout ll_vp_bottom;
	/***
	 * btn_ask：我要问按钮 btn_exper：我要问按钮 btn_knowledge：知识库按钮 btn_exhibition：展厅按钮
	 * btn_pest：害虫按钮 btn_video：视频按钮 btn_group：群组按钮 btn_counsel：咨询按钮 btn_add：添加按钮
	 */
	private Button btn_ask, btn_exper, btn_knowledge, btn_exhibition, btn_pest,
			btn_video, btn_group, btn_counsel, btn_add;

	/***
	 * 轮播图底部圈圈数据列表
	 */
	private List<ImageView> list_bottom;
	/***
	 * 广告栏数据列表
	 */
	private List<AdUnit> list_ad;
	private List<ImageView> pageViews;
	/***
	 * 轮播图当前选中项
	 */
	private int currPgae = 0;

	private ImageLoader mImageLoader;

	private GetDataTask getDataTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initData();
		getData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View maintainView = inflater.inflate(R.layout.fragment_homepage,
				container, false);
		initBodyer(maintainView);
		return maintainView;
	}

	/***
	 * 初始化变量
	 */
	private void initData() {
		mImageLoader = new ImageLoader(getActivity());
		list_bottom = new ArrayList<ImageView>();
		list_ad = new ArrayList<AdUnit>();
		pageViews = new ArrayList<ImageView>();
	}

	/***
	 * 初始化布局控件
	 * 
	 * @param v
	 *            父视图
	 */
	private void initBodyer(View maintainView) {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) maintainView.findViewById(R.id.viewpager_main);
		rl_vp = (RelativeLayout) maintainView.findViewById(R.id.rl_vp);
		ll_vp_bottom = (LinearLayout) maintainView
				.findViewById(R.id.ll_vp_bottom);

		btn_ask = (Button) maintainView.findViewById(R.id.btn_main_firstfirst);
		btn_exper = (Button) maintainView.findViewById(R.id.btn_main_firsttwo);
		btn_knowledge = (Button) maintainView
				.findViewById(R.id.btn_main_firstthree);
		btn_exhibition = (Button) maintainView
				.findViewById(R.id.btn_main_secondone);
		btn_pest = (Button) maintainView.findViewById(R.id.btn_main_secondtwo);
		btn_video = (Button) maintainView
				.findViewById(R.id.btn_main_secondthree);
		btn_group = (Button) maintainView.findViewById(R.id.btn_main_thirdone);
		btn_counsel = (Button) maintainView
				.findViewById(R.id.btn_main_thirdtwo);
		btn_add = (Button) maintainView.findViewById(R.id.btn_main_thirdthree);

		btn_ask.setOnClickListener(this);
		btn_exper.setOnClickListener(this);
		btn_knowledge.setOnClickListener(this);
		btn_exhibition.setOnClickListener(this);
		btn_pest.setOnClickListener(this);
		btn_video.setOnClickListener(this);
		btn_group.setOnClickListener(this);
		btn_counsel.setOnClickListener(this);
		btn_add.setOnClickListener(this);
		initViewPager();
	}

	/***
	 * 轮播图赋值
	 */
	private void initPVData() {
		for (int i = 0; i < list_ad.size(); i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView.setScaleType(ScaleType.FIT_XY);
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.icon_app);
			imageView.setImageBitmap(bmp);
			ImageView imageView2 = new ImageView(getActivity());
			imageView2.setScaleType(ScaleType.CENTER);
			if (i != currPgae) {
				imageView2
						.setBackgroundResource(R.drawable.icon_homepage_banner);
			} else {
				imageView2
						.setBackgroundResource(R.drawable.icon_homepage_banner_hover);
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(10, 10, 10, 10);
			imageView2.setLayoutParams(layoutParams);
			pageViews.add(imageView);
			list_bottom.add(imageView2);
			ll_vp_bottom.addView(imageView2);
		}
		mViewPager.setAdapter(new MyAdapter());
		mViewPager.setCurrentItem(0);
		if (pageViews.size() < 1) {
			rl_vp.setVisibility(View.GONE);
		} else {
			rl_vp.setVisibility(View.VISIBLE);
		}
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
					new IntentToOther(getActivity(), AdDetailActivity.class, bundle);
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
		switch (v.getId()) {
		case R.id.btn_main_firstfirst:
			new IntentToOther(getActivity(), AskActivity.class, null);
			break;
		case R.id.btn_main_firsttwo:
			new IntentToOther(getActivity(), ExpertMainActivity.class, null);
			break;
		case R.id.btn_main_firstthree:
			new IntentToOther(getActivity(), KnowledgeActivity.class, null);
			break;
		case R.id.btn_main_secondone:
			new IntentToOther(getActivity(), ExhibitionActivity.class, null);
			break;
		case R.id.btn_main_secondtwo:
			new IntentToOther(getActivity(), PestActivity.class, null);
			break;
		case R.id.btn_main_secondthree:
			new IntentToOther(getActivity(), VideoActivity.class, null);
			break;
		case R.id.btn_main_thirdone:
			new IntentToOther(getActivity(), GroupOfListActivity.class, null);
			break;
		case R.id.btn_main_thirdtwo: {
			new IntentToOther(getActivity(), PhoneCallActivity.class, null);
			break;
		}
		case R.id.btn_main_thirdthree:

			break;
		default:
			break;
		}
	}

	/***
	 * 获取广告图片
	 */
	private void getData() {
		if (comFunction.isWiFi_3G(getActivity())) {
			if (getDataTask == null) {
				getDataTask = new GetDataTask();
				getDataTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link),
					getActivity());
		}
	}

	/***
	 * 获取广告位信息
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, Void> {
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
			paramsList.add(new BasicNameValuePair("spaceId", "100"));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("ynb_ad_index",
					paramsList, getActivity());
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
						unit.setName(jArray_data.getJSONObject(i).getString(
								"name"));
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
			if (message != null) {
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					initPVData();
				} else {
					comFunction.toastMsg(message, getActivity());
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						getActivity());
			}
		}
	}
}

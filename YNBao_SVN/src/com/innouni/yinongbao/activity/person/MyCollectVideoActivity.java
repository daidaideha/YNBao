package com.innouni.yinongbao.activity.person;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.MyCollectVideoAdapter;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.view.PullToRefreshView;
import com.innouni.yinongbao.view.PullToRefreshView.OnHeaderRefreshListener;
import com.innouni.yinongbao.widget.comFunction;
import com.innouni.yinongbao.widget.sPreferences;

/**
 * 收藏的视频
 * @author Hugj
 *
 */
public class MyCollectVideoActivity extends Activity implements
		OnHeaderRefreshListener, OnItemClickListener {

	private sPreferences sPreferences;

	/**
	 * TitleBar相关
	 */
	private RelativeLayout rl_back;
	private TextView tv_title;

	/**
	 * 刷新加载相关
	 */
	private PullToRefreshView pullview;
	private ListView listView;
	private View footView;
	private TextView tv_more;
	private MyCollectVideoAdapter adapter;
	private GetDataTask getDataTask;
	private int pageCount = 1;
	private ArrayList<CollectVideo> list_data;

	/***
	 * 等待进度框
	 */
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collect_video);
			sPreferences = new sPreferences(this);
			list_data = new ArrayList<CollectVideo>();
			initHeader();
			initView();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText("收藏的视频");
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void initView() {
		pd = new ProgressDialog(this);
		pd.setIndeterminate(true);
		pd.setCancelable(true);

		pullview = (PullToRefreshView) findViewById(R.id.pullview);
		listView = (ListView) findViewById(R.id.list_exper);
		footView = getLayoutInflater().inflate(R.layout.widget_listview_foot,
				null);
		tv_more = (TextView) footView.findViewById(R.id.tv_more);
		tv_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadMore();
			}
		});
		listView.addFooterView(footView);
		adapter = new MyCollectVideoAdapter(this);
		listView.setAdapter(adapter);
		pullview.setOnHeaderRefreshListener(this);
		listView.setOnItemClickListener(this);
		pullview.headerRefreshing();
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
	 * 获取数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		private JSONObject jobj;
		private JSONArray jArray_questions;
		private List<NameValuePair> paramsList;
		/***
		 * 服务器返回类型值 200：成功
		 */
		private String code;
		/***
		 * 服务器返回提示内容值
		 */
		private String message = null;

		/***
		 * 每页数据量
		 */
		// private String count;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (pageCount == 1) {
				list_data.clear();
				listView.setVisibility(View.GONE);
			} else {
				pd.show();
			}
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("userId", sPreferences
					.getStringValues("memberId")));
			paramsList.add(new BasicNameValuePair("page", pageCount + ""));
			System.out.println(sPreferences.getStringValues("memberId") + " "
					+ pageCount);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			String requery = comFunction.getDataFromServer("get_fav_my_video",
					paramsList, MyCollectVideoActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					jArray_questions = jobj.optJSONArray("data");
					for (int i = 0; i < jArray_questions.length(); i++) {
						CollectVideo question = new CollectVideo(
								jArray_questions.optJSONObject(i));
						list_data.add(question);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			getDataTask = null;
			if (message != null) {
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					adapter.clearList();
					adapter.setList(list_data);
					adapter.notifyDataSetChanged();
					// if (comFunction.isNullorSpace(count)) {
					// tv_more.setVisibility(View.GONE);
					// } else {
					// if (Integer.valueOf(count) < 14) {
					// tv_more.setVisibility(View.GONE);
					// } else {
					// tv_more.setVisibility(View.VISIBLE);
					// }
					// }
					listView.setVisibility(View.VISIBLE);
				} else {
					comFunction.toastMsg(message, MyCollectVideoActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						MyCollectVideoActivity.this);
			}
			pullview.onHeaderRefreshComplete();
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		pageCount = 1;
		getData();
	}

	/***
	 * 加载更多操作
	 */
	private void loadMore() {
		pageCount++;
		getData();
	}
}

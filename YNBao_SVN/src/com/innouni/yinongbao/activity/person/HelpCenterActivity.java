package com.innouni.yinongbao.activity.person;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.HelpCenterAdapter;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.view.PullToRefreshView;
import com.innouni.yinongbao.view.PullToRefreshView.OnHeaderRefreshListener;
import com.innouni.yinongbao.widget.comFunction;

/**
 * 帮助中心
 * 
 * @author Hugj
 * 
 */
public class HelpCenterActivity extends Activity implements
		OnHeaderRefreshListener, OnItemClickListener, TextWatcher {

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
	private HelpCenterAdapter adapter;
	private GetDataTask getDataTask;
	private ArrayList<HelpCenter> list_data;

	/**
	 * 搜索
	 */
	private EditText searchView;
	private Button searchButton;
	private String key = " ";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_c);
		list_data = new ArrayList<HelpCenter>();
		initHeader();
		initView();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		searchButton = (Button) findViewById(R.id.btn_menu);
		searchButton.setVisibility(View.GONE);
		searchView = (EditText) findViewById(R.id.edt_search);
		searchView.addTextChangedListener(this);

		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText("帮助中心");
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void initView() {
		pullview = (PullToRefreshView) findViewById(R.id.pullview);
		listView = (ListView) findViewById(R.id.list_exper);
		adapter = new HelpCenterAdapter(this);
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

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			list_data.clear();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("keyword ", key));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			String requery = comFunction.getDataFromServer("member_index_help",
					paramsList, HelpCenterActivity.this);
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
						HelpCenter question = new HelpCenter(
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
					listView.setVisibility(View.VISIBLE);
				} else {
					comFunction.toastMsg(message, HelpCenterActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						HelpCenterActivity.this);
			}
			pullview.onHeaderRefreshComplete();
			super.onPostExecute(result);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String itemid = adapter.getItem(position).id;
		Intent intent = new Intent(HelpCenterActivity.this,
				HelpCenterItemDetailActivity.class);
		intent.putExtra("itemid", itemid);
		startActivity(intent);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		getData();
	}

	@Override
	public void afterTextChanged(Editable s) {
		key = s.toString();
		getData();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

}

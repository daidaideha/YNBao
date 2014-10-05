package com.innouni.yinongbao.activity.person;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.widget.comFunction;

/**
 * 常见问题详情
 * 
 * @author Hugj
 * 
 */
public class HelpCenterItemDetailActivity extends Activity {

	/**
	 * TitleBar相关
	 */
	private RelativeLayout rl_back;
	private TextView tv_title;

	private TextView titleView, titleContent;

	private GetDataTask getDataTask;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hp_item_detial);
		initHeader();
		id = getIntent().getStringExtra("itemid");
		titleView = (TextView) findViewById(R.id.title);
		titleContent = (TextView) findViewById(R.id.content);
		getData();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText("问题详情");
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	/***
	 * 获取数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		private JSONObject jobj;
		private List<NameValuePair> paramsList;
		/***
		 * 服务器返回类型值 200：成功
		 */
		private String code;
		/***
		 * 服务器返回提示内容值
		 */
		private String message = null;
		String title;
		String content;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("Id", id));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			String requery = comFunction.getDataFromServer(
					"get_member_index_help_detail", paramsList,
					HelpCenterItemDetailActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					JSONObject object = jobj.optJSONObject("data");
					if (object != null) {
						if (!object.isNull("title")) {
							title = object.optString("title");
						}
						if (!object.isNull("content")) {
							content = object.optString("content");
						}
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
					titleView.setText(title);
					titleContent.setText(content);
				} else {
					comFunction.toastMsg(message,
							HelpCenterItemDetailActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						HelpCenterItemDetailActivity.this);
			}
			super.onPostExecute(result);
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
}

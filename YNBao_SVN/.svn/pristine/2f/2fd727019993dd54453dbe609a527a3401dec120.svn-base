package com.innouni.yinongbao.activity.person;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.widget.comFunction;
import com.innouni.yinongbao.widget.sPreferences;

/***
 * 用户反馈界面
 * @author LinYuLing
 * @UpdateDate 2014-09-24
 */
public class FeedBackActivity extends Activity {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 内容输入框
	 */
	private EditText mEditText;
	/***
	 * 提交按钮
	 */
	private Button mButton;

	/***
	 * 存储数据的sp
	 */
	private sPreferences iSPreferences;
	/***
	 * 等待进度框
	 */
	private ProgressDialog pd;
	/***
	 * 用户反馈异步
	 */
	private FeedBackTask feedBackTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_feedback);
		iSPreferences = new sPreferences(this);
		pd = new ProgressDialog(this);
		pd.setIndeterminate(true);
		pd.setCancelable(true);

		initHeader();
		initBodyer();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(getString(R.string.tv_person_feedback));
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
		mEditText = (EditText) findViewById(R.id.edt_feedback);
		mButton = (Button) findViewById(R.id.btn_submit);
		
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (check()) {
					feedBack();
				}
			}
		});
	}
	
	/***
	 * 检测输入框内容合法性
	 * @return true：合法，false：不合法
	 */
	private boolean check() {
		if (comFunction.isNullorSpace(mEditText.getText().toString())) {
			comFunction.toastMsg(getString(R.string.toast_person_feedback_null), this);
			return false;
		}
		return true;
	}
	
	/***
	 * 用户反馈操作
	 */
	private void feedBack() {
		if (comFunction.isWiFi_3G(this)) {
			if (feedBackTask == null) {
				feedBackTask = new FeedBackTask();
				feedBackTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}
	
	/***
	 * 登陆异步
	 * @author LinYuLing
	 *
	 */
	private class FeedBackTask extends AsyncTask<Void, Void, Void> {
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

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.setMessage(getString(R.string.pd_data_upload));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("userId", 
					iSPreferences.getSp().getString("memberId", "")));
			paramsList.add(new BasicNameValuePair("content", mEditText.getText().toString()));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("member_index_guestbook",
					paramsList, FeedBackActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("catch: " + e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			feedBackTask = null;
			if (message != null) {
				if (code.equals("200")) {
					finish();
					comFunction.toastMsg(getString(R.string.toast_person_feedback), 
							FeedBackActivity.this);
				} else {
					comFunction.toastMsg(message, FeedBackActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link), FeedBackActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
		}
	}

}

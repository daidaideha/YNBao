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
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.widget.MD5Util;
import com.innouni.yinongbao.widget.comFunction;
import com.innouni.yinongbao.widget.sPreferences;

public class UpdatePWDActivity extends Activity {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 用户名控件
	 */
	private TextView tv_name;
	/***
	 * 旧密码输入框
	 * 新密码输入框
	 * 确认新密码输入框
	 */
	private EditText edt_pwd_old, edt_pwd_new, edt_pwd_renew;
	/***
	 * 提交按钮
	 */
	private Button btn_submit;
	
	/***
	 * 等待进度框
	 */
	private ProgressDialog pd;
	/***
	 * 存储数据的sp
	 */
	private sPreferences iSPreferences;
	
	/***
	 * 修改密码异步
	 */
	private UpdateTask updateTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatepwd);
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

		tv_title.setText(getString(R.string.tv_person_updatepwd));
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/***
	 * 基本控件初始化
	 */
	private void initBodyer() {
		// TODO Auto-generated method stub
		tv_name = (TextView) findViewById(R.id.tv_username);
		edt_pwd_old = (EditText) findViewById(R.id.edt_password_old);
		edt_pwd_new = (EditText) findViewById(R.id.edt_password_new);
		edt_pwd_renew = (EditText) findViewById(R.id.edt_password_renew);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		
		tv_name.setText(Html.fromHtml(getString(R.string.tv_person_name).replace("$message$", 
				"<font color=\"#1fb7f6\">" + iSPreferences.getSp().getString("username", "")
				+ "</font>")));
		
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (check()) {
					update();
				}
			}
		});
	}
	
	/***
	 * 验证输入框内容合法性
	 * 
	 * @return true：合法，false：不合法
	 */
	private boolean check() {
		if (comFunction.isNullorSpace(edt_pwd_old.getText().toString())) {
			comFunction.toastMsg(getString(R.string.tv_person_update_password_old)
					.replace("：", "!"), this);
			return false;
		}
		if (comFunction.isNullorSpace(edt_pwd_new.getText().toString())) {
			comFunction.toastMsg(getString(R.string.tv_person_update_password_new)
					.replace("：", "!"), this);
			return false;
		}
		if (!edt_pwd_new.getText().toString().equals(edt_pwd_renew.getText().toString())) {
			comFunction.toastMsg(getString(R.string.toast_resgiter_password_again), this);
			return false;
		}
		return true;
	}
	
	/***
	 * 修改密码操作
	 */
	private void update() {
		if (comFunction.isWiFi_3G(this)) {
			if (updateTask == null) {
				updateTask = new UpdateTask();
				updateTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}
	
	/***
	 * 修改密码异步
	 * @author LinYuLing
	 *
	 */
	private class UpdateTask extends AsyncTask<Void, Void, Void> {
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
			String pwd_old = MD5Util.getMD5(edt_pwd_old.getText().toString());
			String pwd_new = MD5Util.getMD5(edt_pwd_new.getText().toString());
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("userId", 
					iSPreferences.getSp().getString("memberId", "")));
			paramsList.add(new BasicNameValuePair("old_password", pwd_old));
			paramsList.add(new BasicNameValuePair("new_password", pwd_new));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("member_update_password",
					paramsList, UpdatePWDActivity.this);
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
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			updateTask = null;
			if (message != null) {
				if (code.equals("200")) {
					finish();
				}
				comFunction.toastMsg(message, UpdatePWDActivity.this);
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link), UpdatePWDActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
		}
	}
}

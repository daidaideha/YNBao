package com.innouni.yinongbao.activity;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.MD5Util;
import com.innouni.yinongbao.widget.comFunction;
import com.innouni.yinongbao.widget.sPreferences;

public class LoginActivity extends Activity implements OnClickListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	private EditText edt_name;// 输入用户编辑框
	private EditText edt_pssword;// 输入用户密码编辑框
	private Button btn_login;// 登录按钮
	private TextView tv_register;// 注册按钮
	private TextView tv_forget;// 忘记密码按钮

	/***
	 * 用来记录两次点击返回键的时间
	 */
	private long time = 0;

	/***
	 * 等待进度框
	 */
	private ProgressDialog pd;
	/***
	 * 存储数据的sp
	 */
	private sPreferences iSPreferences;
	/***
	 * 登陆异步
	 */
	private LoginTask loginTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
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

		tv_title.setText(getString(R.string.tv_header_login));
		rl_back.setVisibility(View.GONE);
	}

	/***
	 * 实例化控件
	 */
	private void initBodyer() {
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_forget = (TextView) findViewById(R.id.txt_login_forget);
		tv_register = (TextView) findViewById(R.id.txt_login_resign);

		edt_pssword = (EditText) findViewById(R.id.edt_login_pass);
		edt_name = (EditText) findViewById(R.id.edt_login_user);

		btn_login.setOnClickListener(this);
		tv_forget.setClickable(true);
		tv_forget.setOnClickListener(this);
		tv_register.setClickable(true);
		tv_register.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login: {// 登录按钮点击事件
			if (check()) {
				login();
			}
			break;
		}
		case R.id.txt_login_resign: {// 注册页面点击事件
			finish();
			new IntentToOther(this, ResgisterActivity.class, null);
			break;
		}
		case R.id.txt_login_forget: {// 忘记密码点击事件
			new IntentToOther(this, FindBackActivity.class, null);
			break;
		}
		default:
			break;
		}

	}
	
	/***
	 * 检测输入框合法性
	 * @return true：合法，false：不合法
	 */
	private boolean check() {
		if (comFunction.isNullorSpace(edt_name.getText().toString())) {
			comFunction.toastMsg(getString(R.string.toast_login_name_null), this);
			return false;
		}
		if (comFunction.isNullorSpace(edt_pssword.getText().toString())) {
			comFunction.toastMsg(getString(R.string.toast_login_password_null), this);
			return false;
		}
		return true;
	}
	
	/***
	 * 登陆操作
	 */
	private void login() {
		if (comFunction.isWiFi_3G(this)) {
			if (loginTask == null) {
				loginTask = new LoginTask();
				loginTask.execute();
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
	private class LoginTask extends AsyncTask<Void, Void, Void> {
		private JSONObject jobj;
		private JSONObject jobj_data;
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
			pd.setMessage(getString(R.string.pd_data_login));
			pd.show();
			String password = MD5Util.getMD5(edt_pssword.getText().toString());
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("userName", edt_name
					.getText().toString()));
			paramsList.add(new BasicNameValuePair("password", password));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("member_login",
					paramsList, LoginActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					jobj_data = new JSONObject(jobj.getString("data"));
					if (jobj_data == null) {
						return null;
					}
					iSPreferences.updateSp("memberId", jobj_data.getString("uid"));
					iSPreferences.updateSp("username", jobj_data.getString("username"));
					iSPreferences.updateSp("avatar", jobj_data.getString("avatar"));
					iSPreferences.updateSp("grouptitle", jobj_data.getString("grouptitle"));
					iSPreferences.updateSp("credits", jobj_data.getString("credits"));
					iSPreferences.updateSp("newpm", jobj_data.getString("newpm"));
					iSPreferences.updateSp("follower", jobj_data.getString("follower"));
					iSPreferences.updateSp("realname", jobj_data.getString("realname"));
					iSPreferences.updateSp("identity", jobj_data.getString("identity"));
				}
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
			loginTask = null;
			if (message != null) {
				if (code.equals("200")) {
					finish();
					new IntentToOther(LoginActivity.this, MainPageActivity.class, null);
				} else {
					comFunction.toastMsg(message, LoginActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link), LoginActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			int LENG_EXIT = 1500;
			if (System.currentTimeMillis() - time > LENG_EXIT) {
				Toast.makeText(this,
						getResources().getString(R.string.toast_double_exit),
						LENG_EXIT).show();
				time = System.currentTimeMillis();
			} else {
				System.exit(0);
			}
		}
		return false;
	}
}

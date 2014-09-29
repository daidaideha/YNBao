package com.innouni.yinongbao.activity;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.unit.CodeUnit;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.MD5Util;
import com.innouni.yinongbao.widget.comFunction;
import com.innouni.yinongbao.widget.sPreferences;

/***
 * 该页面为注册页面 Data:2014-9-17 Author：LiuChao
 */
public class ResgisterActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	private EditText mEdtPhone; // 号码编辑框
	private EditText mEdtPassWord; // 密码输入框
	private EditText mResurePassEditText;// 确认密码输入框
	private EditText mGetIdentCodeEditText;// 输入验证码框
	private Button mGetIdentCodeButton;// 获取验证码按钮
	private TextView mRefereeNumTextView;// 推荐人选择
	private EditText mRefereeNumEditText;// 推荐人输入框
	private Button mSignButton;// 注册按钮
	private CheckBox mAgreeCheckBox;// 是否同意注册

	private int onpress = 0;// 判断是否有推荐人
//	private boolean agree = false;// 判断checkbox是否被点
	/***
	 * 验证码
	 */
	private String code = "";
	private int minute = 60;

	/***
	 * 等待进度框
	 */
	private ProgressDialog pd;
	/***
	 * 存储数据的sp
	 */
	private sPreferences iSPreferences;
	/***
	 * 计时器
	 */
	private Timer timer;
	/***
	 * 获取短信验证码
	 */
	private GetCodeTask getCodeTask;
	/***
	 * 注册异步
	 */
	private ResgiterTask resgiterTask;
	/***
	 * 手机号检测
	 */
	private CheckPhoneTask checkPhoneTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
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

		tv_title.setText(getString(R.string.tv_header_resign));
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				new IntentToOther(ResgisterActivity.this, LoginActivity.class, null);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
			new IntentToOther(ResgisterActivity.this, LoginActivity.class, null);
		}
		return super.onKeyDown(keyCode, event);
	}

	/***
	 * 基本控件初始化
	 */
	private void initBodyer() {
		// TODO Auto-generated method stub
		mEdtPassWord = (EditText) findViewById(R.id.edt_password);
		mEdtPhone = (EditText) findViewById(R.id.edt_phoneNum);
		mResurePassEditText = (EditText) findViewById(R.id.edt_resurepass);
		mGetIdentCodeEditText = (EditText) findViewById(R.id.edt_getcode);
		mRefereeNumEditText = (EditText) findViewById(R.id.edt_refereenum);
		mRefereeNumEditText.setVisibility(View.GONE);
		mGetIdentCodeButton = (Button) findViewById(R.id.btn_getcode);
		mSignButton = (Button) findViewById(R.id.btn_sign);
		mRefereeNumTextView = (TextView) findViewById(R.id.txt_refereenum);
		mAgreeCheckBox = (CheckBox) findViewById(R.id.chk_agree);

		mAgreeCheckBox.setChecked(true);

		mGetIdentCodeButton.setOnClickListener(this);
		mSignButton.setOnClickListener(this);
		mRefereeNumTextView.setClickable(true);
		mRefereeNumTextView.setOnClickListener(this);
		mAgreeCheckBox.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_refereenum: {// 点击弹出注册人输入页面
			onpress++;
			if (onpress % 2 == 0) {
				mRefereeNumEditText.setVisibility(View.GONE);
				mRefereeNumTextView.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.btn_more, 0, 0, 0);
			} else {
				mRefereeNumEditText.setVisibility(View.VISIBLE);
				mRefereeNumTextView.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.btn_more1, 0, 0, 0);
			}
			break;
		}
		case R.id.btn_sign: {// 注册按钮的事件监听
			if (check()) {
				resgiter();
			}
			break;
		}
		case R.id.btn_getcode: {
			if (checkPhone()) {
				doCheckPhone();
			}
			break;
		}
		default:
			break;
		}

	}

	/***
	 * 关于checkbox的监听事件
	 */
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean checked) {
		if (checked) {
			mSignButton.setEnabled(true);
		} else {
			mSignButton.setEnabled(false);
		}

	}

	/***
	 * 获取短信验证码操作
	 */
	private void getSMSCode() {
		if (comFunction.isWiFi_3G(this)) {
			if (getCodeTask == null) {
				getCodeTask = new GetCodeTask();
				getCodeTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}

	/***
	 * 验证手机号码合法性
	 */
	private boolean checkPhone() {
		if (!comFunction.isMobile(mEdtPhone.getText().toString())) {
			comFunction.toastMsg(getString(R.string.toast_resgiter_phone), this);
			return false;
		}
		return true;
	}

	/***
	 * 验证输入框内容合法性
	 * 
	 * @return true：合法，false：不合法
	 */
	private boolean check() {
		if (comFunction.isNullorSpace(mEdtPhone.getText().toString())) {
			comFunction.toastMsg(getString(R.string.toast_resgiter_phone_null), this);
			return false;
		}
		if (comFunction.isNullorSpace(mEdtPassWord.getText().toString())) {
			comFunction.toastMsg(getString(R.string.toast_login_password_null), this);
			return false;
		}
		if (mEdtPassWord.getText().toString().length() < 6) {
			comFunction.toastMsg(getString(R.string.toast_resgiter_password_length), this);
			return false;
		}
		if (!mEdtPassWord.getText().toString()
				.equals(mResurePassEditText.getText().toString())) {
			comFunction.toastMsg(getString(R.string.toast_resgiter_password_again), this);
			return false;
		}
		if (comFunction.isNullorSpace(mGetIdentCodeEditText.getText()
				.toString())) {
			comFunction.toastMsg(getString(R.string.toast_resgiter_code_null), this);
			return false;
		}
		if (!code.equals(mGetIdentCodeEditText.getText().toString())) {
			comFunction.toastMsg(getString(R.string.toast_resgiter_code_err), this);
			return false;
		}
		return true;
	}

	/***
	 * 计时并刷新获取验证码控件
	 */
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// handler处理消息
			if (msg.what == 1) {
				mGetIdentCodeButton.setText(minute + "");
			} else {
				// 在handler里可以更改UI组件
				mGetIdentCodeButton
						.setText(getString(R.string.tv_findback_getcode));
				mGetIdentCodeButton.setClickable(true);
				timer.cancel();
			}
		}
	};

	/***
	 * 验证码倒计时计时器启动
	 */
	private void initTimer() {
		timer = new Timer();
		// 定义计划任务，根据参数的不同可以完成以下种类的工作：在固定时间执行某任务，在固定时间开始重复执行某任务，重复时间间隔可控，在延迟多久后执行某任务，在延迟多久后重复执行某任务，重复时间间隔可控
		timer.schedule(new TimerTask() {
			// TimerTask 是个抽象类,实现的是Runable类
			@Override
			public void run() {
				// 定义一个消息传过去
				Message msg = new Message();
				if (minute > 1) {
					minute--;
					msg.what = 1;
				} else {
					msg.what = 0;
				}
				handler.sendMessage(msg);
			}

		}, 0, 1000);
	}

	/***
	 * 获取短信验证码
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetCodeTask extends AsyncTask<Void, Void, CodeUnit> {
		/***
		 * 短信服务器地址
		 */
		private String url;
		/***
		 * 短信用户名
		 */
		private String username;
		/***
		 * 短信密码
		 */
		private String password;
		/***
		 * 发送到短信的号码
		 */
		private String phone;
		/***
		 * 短信内容
		 */
		private String content;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			code = getCode();
			url = getString(R.string.app_sms_url);
			username = "114nzsms";
			password = "homehome";
			phone = mEdtPhone.getText().toString();
			content = getString(R.string.app_sms_context).replace("$number$",
					code);
		}

		@Override
		protected CodeUnit doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String reString = comFunction.getDataFromServerForGet(url
					+ "username=" + username + "&password=" + password
					+ "&receiver=" + phone + "&content=" + content);
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			CodeUnit unit = new CodeUnit();
			try {
				XMLReader xmlReader = saxParserFactory.newSAXParser()
						.getXMLReader();
				xmlReader.setContentHandler(new MyContentHandler(unit));
				xmlReader.parse(new InputSource(new StringReader(reString)));
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				unit = null;
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				unit = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				unit = null;
			}
			return unit;
		}

		@Override
		protected void onPostExecute(CodeUnit result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			getCodeTask = null;
			if (result != null) {
				if (Integer.valueOf(result.getResult()) > 0) {
					mGetIdentCodeButton.setClickable(false);
					minute = 60;
					initTimer();
				} else {
					comFunction.toastMsg(result.getMessage(),
							ResgisterActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ResgisterActivity.this);
			}
		}
	}

	/***
	 * 解析xml数据监听
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class MyContentHandler extends DefaultHandler {
		private String tagName = null;
		private CodeUnit unit = null;

		public MyContentHandler(CodeUnit unit) {
			// TODO Auto-generated constructor stub
			this.unit = unit;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			String temp = new String(ch, start, length);
			if (tagName.equals("result")) {
				unit.setResult(temp);
			} else if (tagName.equals("message")) {
				unit.setMessage(temp);
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			this.tagName = localName;
			if (tagName.equals("resource")) {
				unit = new CodeUnit();
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			tagName = "";
		}
	}

	/***
	 * 生成6位数字的验证码
	 * 
	 * @return 验证码
	 */
	private String getCode() {
		String code = "";
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < 6; i++)
			result = result * 10 + array[i];
		if (result < 100000) {
			code = "0" + result;
		} else {
			code = result + "";
		}
		return code;
	}

	/***
	 * 注册提交
	 */
	private void resgiter() {
		if (comFunction.isWiFi_3G(this)) {
			if (resgiterTask == null) {
				resgiterTask = new ResgiterTask();
				resgiterTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}
	
	/***
	 * 注册异步
	 * @author LinYuLing
	 *
	 */
	private class ResgiterTask extends AsyncTask<Void, Void, Void> {
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
			pd.setMessage(getString(R.string.pd_data_upload));
			pd.show();
			String password = MD5Util.getMD5(mEdtPassWord.getText().toString());
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("username", mEdtPhone
					.getText().toString()));
			paramsList.add(new BasicNameValuePair("password", password));
			paramsList.add(new BasicNameValuePair("referee",
					mRefereeNumEditText.getText().toString()));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("member_register",
					paramsList, ResgisterActivity.this);
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
					iSPreferences.updateSp("memberId", jobj_data.getString("id"));
					iSPreferences.updateSp("username", jobj_data.getString("username"));
					iSPreferences.updateSp("avatar", jobj_data.getString("avatar"));
					iSPreferences.updateSp("grouptitle", jobj_data.getString("grouptitle"));
					iSPreferences.updateSp("credits", jobj_data.getString("credits"));
					iSPreferences.updateSp("newpm", jobj_data.getString("newpm"));
					iSPreferences.updateSp("follower", jobj_data.getString("follower"));
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
			resgiterTask = null;
			if (message != null) {
				if (code.equals("200")) {
					finish();
					new IntentToOther(ResgisterActivity.this, MainPageActivity.class, null);
				} else {
					comFunction.toastMsg(message, ResgisterActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link), ResgisterActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
		}
	}
	
	/***
	 * 检查手机号是否注册操作
	 */
	private void doCheckPhone() {
		if (comFunction.isWiFi_3G(this)) {
			if (checkPhoneTask == null) {
				checkPhoneTask = new CheckPhoneTask();
				checkPhoneTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}
	
	/***
	 * 检测手机号是否已被注册
	 * @author LinYuLing
	 *
	 */
	private class CheckPhoneTask extends AsyncTask<Void, Void, Void> {
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
		/***
		 * 返回的值
		 */
		private String data;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.setMessage(getString(R.string.pd_data_phone_check));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("username", mEdtPhone
					.getText().toString()));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("member_check_username",
					paramsList, ResgisterActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				data = jobj.getString("data");
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
			checkPhoneTask = null;
			if (code != null) {
				if (data.equals("0")) {
					comFunction.toastMsg(message, ResgisterActivity.this);
				} else {
					getSMSCode();
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link), ResgisterActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
		}
		
	}
	
}

package com.innouni.yinongbao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;

/***
 * 该页为找回密码界面 Data:2014-9-18
 * 
 * @author LiuChao
 * 
 */
public class FindBackActivity extends Activity implements OnClickListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	private EditText edt_phone;// 用户输入手机号码框
	private EditText edt_code;// 用户输入验证码框
	private Button btn_getcode;// 短信获取验证码按钮
	private Button btn_submit;// 完成按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findback);

		initHeader();
		initBodyer();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(getString(R.string.tv_header_findback));
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
		btn_submit = (Button) findViewById(R.id.btn_findback_done);
		btn_getcode = (Button) findViewById(R.id.btn_findback_getmessage);
		edt_phone = (EditText) findViewById(R.id.edt_findback_phone);
		edt_code = (EditText) findViewById(R.id.edt_findback_getmessage);

		btn_submit.setOnClickListener(this);
		btn_getcode.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_findback_done: {// 完成按钮事件
			Intent intent = new Intent(this, ResetPassActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.btn_findback_getmessage: {// 获取短信验证事件

			break;
		}
		default:
			break;
		}
	}

}

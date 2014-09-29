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
 * 
 * @author Administrator
 *
 */
public class ResetPassActivity extends Activity implements OnClickListener{
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	EditText mResetPassNewPassEditText;//输入密码编辑框
	EditText mResetPassResureEditText;//确认密码编辑框
	Button mResetPassDoneButton;//完成更改密码按钮
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resetpassword);
		
		initHeader();
		initBodyer();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(getString(R.string.tv_header_ask));
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/***
	 * 实例化控件
	 */
	private void initBodyer(){
		mResetPassNewPassEditText = (EditText)findViewById(R.id.edt_resetpass_newpass);
		mResetPassResureEditText = (EditText)findViewById(R.id.edt_resetpass_resure);
		mResetPassDoneButton = (Button)findViewById(R.id.btn_resetpass_done);
		
		mResetPassDoneButton.setOnClickListener(this);
	}

	/***
	 * 检查密码是否符合更改信息
	 * @param a 第一个字符串
	 * @param b 第二个字符串
	 * @return true符合要求  false 不符合要求
	 */
	public boolean check(String a,String b){
		if (a.length() <= 16 && a.length() >= 8)
			if (a.equals(b)) 
				return true;
		return false;
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_resetpass_done:{//完成密码的修改
			String newPass;
			String resurePass;
			newPass = mResetPassNewPassEditText.getText().toString();
			resurePass = mResetPassResureEditText.getText().toString();
			
			if (check(newPass, resurePass)) {
				Intent intent=new Intent(this,LoginActivity.class);
				startActivity(intent);
			}
			
			break;
		}
		default:
			break;
		}
		
	}

}

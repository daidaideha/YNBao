package com.innouni.yinongbao.activity.counsel;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.R.layout;
import com.innouni.yinongbao.R.menu;
import android.os.Bundle;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PhoneCallActivity extends Activity implements OnClickListener{

	private RelativeLayout rl_back;//头部返回按钮
	private TextView tv_title;//头部控件内容
	private TextView mInProvinceTextView ;//在省内的电话
	private TextView mOutProvinceTextView;//不在省内的电话
	private PhoneCallDialog mInPhoneCallDialog;//拨打电话的弹出框
	private PhoneCallDialog mOutPhoneCallDialog;//拨打省外电话的弹出框
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainpage_96318);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone_call, menu);
		return true;
	}
	
	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(getString(R.string.tv_header_phonecall));
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	/*
	 * 实例化控件
	 */
	private void initBodyer(){
		mInProvinceTextView = (TextView)findViewById(R.id.txt_96318_inpro);
		mOutProvinceTextView = (TextView)findViewById(R.id.txt_96318_outpro);
		
		mInProvinceTextView.setClickable(true);
		mOutProvinceTextView.setClickable(true);
		mInProvinceTextView.setOnClickListener(this);
		mOutProvinceTextView.setOnClickListener(this);
		
	}
	
	private int inprovince = 0;
	private int outprovince = 0;
	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.txt_96318_inpro:{
			mInProvinceTextView.setTextColor(R.color.white);
			mInProvinceTextView.setBackgroundColor(R.color.blue);
			mInPhoneCallDialog.show();
			break;
		}
		case R.id.txt_96318_outpro:{
			mOutProvinceTextView.setTextColor(R.color.white);
			mInProvinceTextView.setBackgroundColor(R.color.blue);
			mOutPhoneCallDialog.show();
			break;
		}
		default:
			break;
		}
		
	}
	
	

}

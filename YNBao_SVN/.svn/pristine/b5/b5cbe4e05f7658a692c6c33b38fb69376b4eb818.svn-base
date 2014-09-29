package com.innouni.yinongbao.activity.group;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.R.layout;
import com.innouni.yinongbao.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GroupPersonInfo extends Activity implements OnClickListener{

	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	private ImageView mPersonHeadImageView;//个人头像
	private TextView mPersonSignDaTextView;//个人注册时间
	private TextView mPersonSexTextView;//个人性别
	private TextView mRealNameTextView;//真实姓名
	private TextView mPersonNoTextView;//自我介绍
	private TextView mHobbyTextView;//兴趣
	private TextView mLevelTextView;//等级
	private TextView mAddressTextView;//地区
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_personinfo);
	}

	/*
	 * 头部初始化
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(getString(R.string.tv_hearder_personinformation));
		rl_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	/*
	 * 实例化控件
	 * 
	 */
	private void initBodyer(){
		mPersonHeadImageView = (ImageView)findViewById(R.id.img_group_personhead);
		mPersonSignDaTextView = (TextView)findViewById(R.id.txt_group_personsigndate);
		mPersonSexTextView = (TextView)findViewById(R.id.txt_group_personsex);
		mRealNameTextView = (TextView)findViewById(R.id.txt_group_personrealname);
		mPersonNoTextView = (TextView)findViewById(R.id.txt_group_personintroduce);
		mHobbyTextView = (TextView)findViewById(R.id.txt_group_personhobby);
		mLevelTextView = (TextView)findViewById(R.id.txt_group_personlevel);
		mAddressTextView = (TextView)findViewById(R.id.txt_group_personaddress);
		
//		mPersonSignDaTextView.setClickable(true);
//		mPersonSexTextView.setClickable(true);
//		mRealNameTextView.setClickable(true);
//		mPersonNoTextView.setClickable(true);
//		mHobbyTextView.setClickable(true);
//		mLevelTextView.setClickable(true);
//		mAddressTextView.setClickable(true);
		
		mPersonHeadImageView.setOnClickListener(this);
//		mPersonSignDaTextView.setOnClickListener(this);
//		mPersonSexTextView.setOnClickListener(this);
//		mRealNameTextView.setOnClickListener(this);
//		mPersonNoTextView.setOnClickListener(this);
//		mHobbyTextView.setOnClickListener(this);
//		mLevelTextView.setOnClickListener(this);
//		mAddressTextView.setOnClickListener(this);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_person_info, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_group_personhead:{
			
			break;
		}
//		case R.id.txt_group_personsigndate:{
//			break;
//		}
//		case R.id.txt_group_personsex:{
//			break;
//		}
//		case R.id.txt_group_personrealname:{
//			break;
//		}
//		case R.id.txt_group_personintroduce:{
//			break;
//		}
//		case R.id.txt_group_personhobby:{
//			break;
//		}
//		case R.id.txt_group_personlevel:{
//			break;
//		}
//		case R.id.txt_group_personaddress:{
//			break;
//		}
		default:
			break;
		}
		
	}

}

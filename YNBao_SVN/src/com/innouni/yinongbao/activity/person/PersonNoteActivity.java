package com.innouni.yinongbao.activity.person;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.R.id;
import com.innouni.yinongbao.R.layout;
import com.innouni.yinongbao.R.menu;
import com.innouni.yinongbao.R.string;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonNoteActivity extends Activity implements OnClickListener{
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	private ImageButton ibtn_titleButton;//确认按钮
	private ImageView mHeadImageView;//头像
	private TextView mModifyHeadTextView;//修改头像
	private TextView mUserNameTextView;//修改用户名
	private TextView mRealNameTextView;//修改真实姓名
	private TextView mSexTextView;//修改性别
	private TextView mAddressTextView;//修改所在地
	private TextView mPersonNoTextView;//修改个人说明
	private TextView mCropsTextView;//作物
	private TextView mAcresTextView;//亩数
	private Button mOKButton;//ok按钮
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_infonote);
		initHeader();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person_note, menu);
		return true;
	}
	
	/*
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);
		ibtn_titleButton = (ImageButton)findViewById(R.id.ibnt_person_namedone);
		ibtn_titleButton.setOnClickListener(new OnClickListener() {//确认修改按钮
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

		tv_title.setText(getString(R.string.tv_hearder_personinformation));
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void initBodyer(){
		mHeadImageView = (ImageView)findViewById(R.id.img_person_headview);
		mModifyHeadTextView = (TextView)findViewById(R.id.txt_modify);
		mUserNameTextView = (TextView)findViewById(R.id.txt_person_usernameedit);
		mRealNameTextView = (TextView)findViewById(R.id.txt_person_realnameedit);
		mSexTextView = (TextView)findViewById(R.id.txt_person_sexedit);
		mAddressTextView = (TextView)findViewById(R.id.txt_person_addressedit);
		mPersonNoTextView = (TextView)findViewById(R.id.txt_person_noteedit);
		mOKButton = (Button)findViewById(R.id.btn_person_add);
		
		mHeadImageView.setClickable(true);
		mModifyHeadTextView.setClickable(true);
		mUserNameTextView.setClickable(true);
		mRealNameTextView.setClickable(true);
		mSexTextView.setClickable(true);
		mAddressTextView.setClickable(true);
		mPersonNoTextView.setClickable(true);
		
		mHeadImageView.setOnClickListener(this);
		mModifyHeadTextView.setOnClickListener(this);
		mUserNameTextView.setOnClickListener(this);
		mRealNameTextView.setOnClickListener(this);
		mSexTextView.setOnClickListener(this);
		mAddressTextView.setOnClickListener(this);
		mPersonNoTextView.setOnClickListener(this);
		mOKButton.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.txt_modify:{
			
			break;
		}
		case R.id.txt_person_usernameedit:{
			break;
		}
		case R.id.txt_person_realnameedit:{
			break;
		}
		case R.id.txt_person_addressedit:{
			break;
		}
		case R.id.txt_person_sexedit:{
			break;
		}
		case R.id.txt_person_noteedit:{
			break;
		}
		case R.id.btn_person_add:{
			break;
		}
		default:
			break;
		}
		
	}

}

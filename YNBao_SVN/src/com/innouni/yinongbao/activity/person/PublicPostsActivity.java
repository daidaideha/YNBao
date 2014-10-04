package com.innouni.yinongbao.activity.person;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.widget.IntentToOther;

/**
 * 发表帖子
 * 
 * @author Hugj
 * 
 */
public class PublicPostsActivity extends Activity implements OnClickListener {

	/**
	 * TitleBar相关
	 */
	private RelativeLayout rl_back;
	private TextView tv_title, tv_right;

	private EditText titleView, contentView;
	private Button submitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_public_post);
		initHeader();
		initView();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText("我要发帖");
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		tv_right = (TextView) findViewById(R.id.tv_r);
		tv_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new IntentToOther(PublicPostsActivity.this, MyTieziActivity.class, null);
			}
		});
	}

	private void initView() {
		titleView = (EditText) findViewById(R.id.ed_title);
		contentView = (EditText) findViewById(R.id.ed_content);
		submitButton = (Button) findViewById(R.id.btn_sub);
		submitButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sub:

			break;
		}
	}
}

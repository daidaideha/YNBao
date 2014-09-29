package com.innouni.yinongbao.activity.group;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.R.layout;
import com.innouni.yinongbao.R.menu;
import com.innouni.yinongbao.activity.expert.ExperListActivity;
import com.innouni.yinongbao.widget.IntentToOther;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchGroupActivity extends Activity implements OnClickListener{

	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	
	private Button mSearchButton;//查找按钮
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_search);
		initHeader();
		initBodyer();
	}

	/*
	 * 头部初始化
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(getString(R.string.tv_header_group));
		rl_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	private void initBodyer(){
		mSearchButton = (Button)findViewById(R.id.btn_group_search);
		
		mSearchButton.setOnClickListener(this);
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_group, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		new IntentToOther(SearchGroupActivity.this, ExperListActivity.class,bundle);
	}

}

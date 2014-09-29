package com.innouni.yinongbao.activity.group;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.R.layout;
import com.innouni.yinongbao.R.menu;
import com.innouni.yinongbao.widget.IntentToOther;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
/*
 * @auther : LiuChao
 * @date : 2014-9-28
 * @description : 群列表页面
 */
public class ListOfGroup extends Activity implements OnClickListener{

	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	private TextView mHotGroupTextView;//热门群组
	private TextView mRecommendTextView;//群组推荐
	private TextView mGroupNewsTextView;//群组动态
	/***
	 * 添加新的群组
	 */
	private ImageButton mAddGroupImageButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_list);
		initHeader();
		initBodyer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_of_group, menu);
		return true;
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
		mAddGroupImageButton = (ImageButton)findViewById(R.id.ibtn_group_add);
		mHotGroupTextView = (TextView)findViewById(R.id.txt_group_hotgroup);
		mRecommendTextView = (TextView)findViewById(R.id.txt_group_recommend);
		mGroupNewsTextView = (TextView)findViewById(R.id.txt_group_dynamic);
		
		mHotGroupTextView.setClickable(true);
		mRecommendTextView.setClickable(true);
		mGroupNewsTextView.setClickable(true);
		
		mAddGroupImageButton.setOnClickListener(this);
		mHotGroupTextView.setOnClickListener(this);
		mRecommendTextView.setOnClickListener(this);
		mGroupNewsTextView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ibtn_group_add:{
			new IntentToOther(ListOfGroup.this, SearchGroupActivity.class, null);
			break;
		}
		case R.id.txt_group_hotgroup:{
			break;
		}
		case R.id.txt_group_recommend:{
			break;
		}
		case R.id.txt_group_dynamic:{
			break;
		}

		default:
			break;
		}
		
	}

}

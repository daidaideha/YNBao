package com.innouni.yinongbao.activity.group;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.GroupListAdapter;
import com.innouni.yinongbao.fragment.CollectFragment;
import com.innouni.yinongbao.fragment.GroupHotFragment;
import com.innouni.yinongbao.fragment.GroupNewsFragment;
import com.innouni.yinongbao.fragment.GroupRecomFragment;
import com.innouni.yinongbao.fragment.HomePageFragment;
import com.innouni.yinongbao.fragment.PersonFragment;
import com.innouni.yinongbao.fragment.SettingFragment;
import com.innouni.yinongbao.widget.IntentToOther;
/*
 * @auther : LiuChao
 * @date : 2014-9-28
 * @description : 群列表页面
 */
public class GroupOfListActivity extends FragmentActivity implements OnClickListener{

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
	private ListView listView ;
	private LayoutInflater inflater;
	/***
	 * Fragment管理器
	 */
	private FragmentManager fragmentManager;
	/***
	 * 热门群组Fragment
	 */
	private GroupHotFragment groupHotFragment;
	/***
	 * 群组推荐Fragment
	 */
	private GroupRecomFragment groupRecomFragment;
	/***
	 * 群组动态Fragment
	 */
	private GroupNewsFragment groupNewsFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentManager = getSupportFragmentManager();
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
	/***
	 * 头部初始化
	 */
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
			new IntentToOther(GroupOfListActivity.this, SearchGroupActivity.class, null);
			break;
		}
		case R.id.txt_group_hotgroup:{
			setTabSelection(0);
			break;
		}
		case R.id.txt_group_recommend:{
			setTabSelection(1);
			break;
		}
		case R.id.txt_group_dynamic:{
			setTabSelection(2);
			break;
		}

		default:
			break;
		}
		
	}
	
	/***
	 * 顶部选项卡选择操作
	 * 
	 * @param index
	 *            选中的项 0：热门群组 1：群组推荐 2：群组动态 
	 */
	private void setTabSelection(int index) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 0:
			tv_title.setText(getString(R.string.txt_group_hotgroup));
			if (groupHotFragment == null) {
				groupHotFragment = new GroupHotFragment();
				transaction.add(R.id.ll_group_bodyer, groupHotFragment);
			} else {
				transaction.show(groupHotFragment);
			}
			break;
		case 1:
			tv_title.setText(getString(R.string.txt_group_groupsrecommend));
			if (groupRecomFragment == null) {
				groupRecomFragment = new GroupRecomFragment();
				transaction.add(R.id.ll_group_bodyer, groupRecomFragment);
			} else {
				transaction.show(groupRecomFragment);
			}
			break;
		case 2:
			tv_title.setText(getString(R.string.txt_group_dynamic));
			if (groupNewsFragment == null) {
				groupNewsFragment = new GroupNewsFragment();
				transaction.add(R.id.ll_group_bodyer, groupNewsFragment);
			} else {
				transaction.show(groupNewsFragment);
			}
			break;
		}
		transaction.commit();
	}
	
	/***
	 * 隐藏所有fragment
	 * 
	 * @param transaction
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (groupHotFragment != null) {
			transaction.hide(groupHotFragment);
		}
		if (groupRecomFragment != null) {
			transaction.hide(groupRecomFragment);
		}
		if (groupNewsFragment != null) {
			transaction.hide(groupNewsFragment);
		}
	}

}

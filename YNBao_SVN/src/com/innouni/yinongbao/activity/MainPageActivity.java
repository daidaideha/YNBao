package com.innouni.yinongbao.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.fragment.CollectFragment;
import com.innouni.yinongbao.fragment.HomePageFragment;
import com.innouni.yinongbao.fragment.PersonFragment;
import com.innouni.yinongbao.fragment.SettingFragment;

/***
 * 主界面
 * @author LinYuLing
 * @UpdateDate 2014-09-23
 */
public class MainPageActivity extends FragmentActivity implements
		OnClickListener {
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 底部tab按钮
	 */
	private TextView tv_homepage, tv_collect, tv_person, tv_setting;
	/***
	 * 首页界面
	 */
	private HomePageFragment homePageFragment;
	/***
	 * 我的收藏界面
	 */
	private CollectFragment collectFragment;
	/***
	 * 个人中心界面
	 */
	private PersonFragment personFragment;
	/***
	 * 设置界面
	 */
	private SettingFragment settingFragment;

	/***
	 * Fragment管理器
	 */
	private FragmentManager fragmentManager;
	/***
	 * 用来记录两次点击返回键的时间
	 */
	private long time = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initData();
		initBodyer();
	}

	/***
	 * 初始化数据
	 */
	private void initData() {
		// TODO Auto-generated method stub
		fragmentManager = getSupportFragmentManager();
	}

	/***
	 * 初始化布局控件
	 */
	private void initBodyer() {
		tv_title = (TextView) findViewById(R.id.tv_header_title);
		tv_homepage = (TextView) findViewById(R.id.txt_main_home);
		tv_collect = (TextView) findViewById(R.id.txt_main_collect);
		tv_person = (TextView) findViewById(R.id.txt_main_person);
		tv_setting = (TextView) findViewById(R.id.txt_main_set);

		tv_homepage.setOnClickListener(this);
		tv_collect.setOnClickListener(this);
		tv_person.setOnClickListener(this);
		tv_setting.setOnClickListener(this);
		tv_homepage.performClick();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_main_home:
			setTabSelection(0);
			break;
		case R.id.txt_main_collect:
			setTabSelection(1);
			break;
		case R.id.txt_main_person:
			setTabSelection(2);
			break;
		case R.id.txt_main_set:
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	/***
	 * 底部选项卡选择操作
	 * 
	 * @param index
	 *            选中的项 0：首恶界面 1：收藏界面 2：个人中心界面 3：设置界面
	 */
	private void setTabSelection(int index) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (index) {
		case 0:
			tv_title.setText(getString(R.string.txt_main_home));
			if (homePageFragment == null) {
				homePageFragment = new HomePageFragment();
				transaction.add(R.id.ll_bodyer, homePageFragment);
			} else {
				transaction.show(homePageFragment);
			}
			break;
		case 1:
			tv_title.setText(getString(R.string.txt_main_collect));
			if (collectFragment == null) {
				collectFragment = new CollectFragment();
				transaction.add(R.id.ll_bodyer, collectFragment);
			} else {
				transaction.show(collectFragment);
			}
			break;
		case 2:
			tv_title.setText(getString(R.string.txt_main_person));
			if (personFragment == null) {
				personFragment = new PersonFragment();
				transaction.add(R.id.ll_bodyer, personFragment);
			} else {
				transaction.show(personFragment);
			}
			break;
		case 3:
			tv_title.setText(getString(R.string.txt_main_set));
			if (settingFragment == null) {
				settingFragment = new SettingFragment();
				transaction.add(R.id.ll_bodyer, settingFragment);
			} else {
				transaction.show(settingFragment);
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
		if (homePageFragment != null) {
			transaction.hide(homePageFragment);
		}
		if (collectFragment != null) {
			transaction.hide(collectFragment);
		}
		if (personFragment != null) {
			transaction.hide(personFragment);
		}
		if (settingFragment != null) {
			transaction.hide(settingFragment);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			int LENG_EXIT = 1500;
			if (System.currentTimeMillis() - time > LENG_EXIT) {
				Toast.makeText(this, getResources().getString(R.string.toast_double_exit),
						LENG_EXIT).show();
				time = System.currentTimeMillis();
			} else {
				System.exit(0);
			}
		}
		return false;
	}
}

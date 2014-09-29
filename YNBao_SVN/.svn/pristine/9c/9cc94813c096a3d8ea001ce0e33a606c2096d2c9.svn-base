package com.innouni.yinongbao.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.LoginActivity;
import com.innouni.yinongbao.activity.person.FeedBackActivity;
import com.innouni.yinongbao.activity.person.MyCollectActivity;
import com.innouni.yinongbao.activity.person.UpdatePWDActivity;
import com.innouni.yinongbao.activity.person.UserInfoActivity;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.sPreferences;

/***
 * 个人中心界面
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-23
 */
@SuppressLint("NewApi")
public class PersonFragment extends Fragment implements OnClickListener {
	/***
	 * 用户头像
	 */
	private ImageView iv_photo;
	/***
	 * tv_name：用户名 tv_type：用泪类别 tv_focus：关注度 tv_number：积分 tv_level：等级
	 */
	private TextView tv_name, tv_type, tv_focus, tv_number, tv_level;
	/***
	 * 关注度星星框架
	 */
	private LinearLayout ll_focus;
	/***
	 * rl_information：个人资料 rl_video：收藏的视频 rl_group：我的群组
	 */
	private RelativeLayout rl_information, rl_video, rl_group;
	/***
	 * tv_myask：我要问 tv_myreplay：我答的 tv_post：我要发帖 tv_collect：我的收藏 tv_help：帮助中心
	 * tv_feedback：用户反馈 tv_upadatepwd：修改密码
	 */
	private TextView tv_myask, tv_myreplay, tv_post, tv_collect, tv_help,
			tv_feedback, tv_upadatepwd;
	/***
	 * 问我的
	 */
	private RelativeLayout rl_askme;
	/***
	 * 问我的新消息数量
	 */
	private TextView tv_askme_number;
	/***
	 * 退出
	 */
	private Button btn_exit;
	
	/***
	 * 存储数据的sp
	 */
	private sPreferences iSPreferences;
	/***
	 * 图片加载工具类
	 */
	private ImageLoader mImageLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		iSPreferences = new sPreferences(getActivity());
		mImageLoader = new ImageLoader(getActivity());
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View maintainView = inflater.inflate(R.layout.fragment_person,
				container, false);
		initBodyer(maintainView);
		initData();
		return maintainView;
	}

	/***
	 * 初始化布局控件
	 * 
	 * @param v
	 *            父视图
	 */
	private void initBodyer(View maintainView) {
		// 头部控件初始化
		iv_photo = (ImageView) maintainView.findViewById(R.id.iv_person_photo);
		tv_name = (TextView) maintainView.findViewById(R.id.tv_person_name);
		tv_type = (TextView) maintainView.findViewById(R.id.tv_person_type);
		tv_focus = (TextView) maintainView.findViewById(R.id.tv_person_focus);
		ll_focus = (LinearLayout) maintainView.findViewById(R.id.ll_person_focus);
		tv_number = (TextView) maintainView.findViewById(R.id.tv_person_number);
		tv_level = (TextView) maintainView.findViewById(R.id.tv_person_level);
		rl_information = (RelativeLayout) maintainView
				.findViewById(R.id.rl_person_information);
		rl_video = (RelativeLayout) maintainView
				.findViewById(R.id.rl_person_video);
		rl_group = (RelativeLayout) maintainView
				.findViewById(R.id.rl_person_group);
		// 中间各项控件初始化
		tv_myask = (TextView) maintainView.findViewById(R.id.tv_person_myask);
		tv_myreplay = (TextView) maintainView
				.findViewById(R.id.tv_person_myreplay);
		tv_post = (TextView) maintainView.findViewById(R.id.tv_person_post);
		tv_collect = (TextView) maintainView
				.findViewById(R.id.tv_person_collect);
		tv_help = (TextView) maintainView.findViewById(R.id.tv_person_help);
		tv_feedback = (TextView) maintainView
				.findViewById(R.id.tv_person_feedback);
		tv_upadatepwd = (TextView) maintainView
				.findViewById(R.id.tv_person_updatepwd);
		rl_askme = (RelativeLayout) maintainView
				.findViewById(R.id.rl_person_askme);
		tv_askme_number = (TextView) maintainView
				.findViewById(R.id.tv_person_askme_number);
		// 退出按钮初始化
		btn_exit = (Button) maintainView.findViewById(R.id.btn_person_exit);

		rl_information.setOnClickListener(new MyTabClickListener());
		rl_video.setOnClickListener(new MyTabClickListener());
		rl_group.setOnClickListener(new MyTabClickListener());

		tv_myask.setOnClickListener(new MyBodyerClickListener());
		tv_myreplay.setOnClickListener(new MyBodyerClickListener());
		tv_post.setOnClickListener(new MyBodyerClickListener());
		tv_collect.setOnClickListener(new MyBodyerClickListener());
		tv_help.setOnClickListener(new MyBodyerClickListener());
		tv_feedback.setOnClickListener(new MyBodyerClickListener());
		tv_upadatepwd.setOnClickListener(new MyBodyerClickListener());
		rl_askme.setOnClickListener(new MyBodyerClickListener());

		btn_exit.setOnClickListener(this);
	}
	
	/***
	 * 初始化数据
	 */
	private void initData() {
		if (iSPreferences.getSp().getString("identity", "").equals("0")) {
			tv_focus.setVisibility(View.GONE);
			ll_focus.setVisibility(View.GONE);
			tv_myreplay.setVisibility(View.GONE);
			tv_post.setVisibility(View.GONE);
			rl_askme.setVisibility(View.GONE);
			tv_type.setText(getString(R.string.tv_person_type_normal));
		} else {
			tv_type.setText(getString(R.string.tv_person_type_special));
			if (!iSPreferences.getSp().getString("follower", "").equals("null")
					|| !iSPreferences.getSp().getString("follower", "").equals("")) {
				int focus = Integer.valueOf(iSPreferences.getSp().getString("follower", ""));
				for (int i = 0; i < focus; i++) {
					ImageView iv = new ImageView(getActivity());
					iv.setBackgroundResource(R.drawable.icon_person_focus);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
					layoutParams.setMargins(10, 10, 10, 10);
					iv.setLayoutParams(layoutParams);
					ll_focus.addView(iv);
				}
			}
			if (iSPreferences.getSp().getString("newpm", "").equals("null")
					|| iSPreferences.getSp().getString("newpm", "").equals("")) {
				tv_askme_number.setVisibility(View.GONE);
			} else {
				tv_askme_number.setVisibility(View.VISIBLE);
				tv_askme_number.setText(iSPreferences.getSp().getString("newpm", ""));
			}
		}
		tv_name.setText(getString(R.string.tv_person_name).replace("$message$", 
				iSPreferences.getSp().getString("username", "")));
		if (iSPreferences.getSp().getString("credits", "").equals("") ||
				iSPreferences.getSp().getString("credits", "").equals("null")) {
			tv_number.setText(getString(R.string.tv_person_number).replace("$number$", "0"));
		} else {
			tv_number.setText(getString(R.string.tv_person_number).replace("$number$", 
					iSPreferences.getSp().getString("credits", "")));
		}
		if (iSPreferences.getSp().getString("credits", "").equals("") ||
				iSPreferences.getSp().getString("credits", "").equals("null")) {
			tv_number.setText(getString(R.string.tv_person_number).replace("$number$", "0"));
		} else {
			tv_number.setText(getString(R.string.tv_person_number).replace("$number$", 
					iSPreferences.getSp().getString("credits", "")));
		}
		tv_level.setVisibility(View.GONE);
		mImageLoader.DisplayImage(iSPreferences.getSp().getString("avatar", ""), iv_photo, false);
		
	}

	/***
	 * 个人信息，收藏的视频，我的群组点击事件
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class MyTabClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.rl_person_information:
				/**
				 * add by Hugj
				 */
				new IntentToOther(getActivity(), UserInfoActivity.class, null);
				break;
			case R.id.rl_person_video:

				break;
			case R.id.rl_person_group:

				break;
			default:
				break;
			}
		}

	}

	/***
	 * 我要问,问我的,我答的,我要发帖,我的收藏 ,帮助中心,用户反馈,修改密码 点击事件
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class MyBodyerClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_person_myask:
				
				break;
			case R.id.rl_person_askme:
				
				break;
			case R.id.tv_person_myreplay:
				
				break;
			case R.id.tv_person_post:
				
				break;
			case R.id.tv_person_collect:
				/**
				 * add by Hugj
				 */
				new IntentToOther(getActivity(), MyCollectActivity.class, null);
				break;
			case R.id.tv_person_help:
				
				break;
			case R.id.tv_person_feedback:
				new IntentToOther(getActivity(), FeedBackActivity.class, null);
				break;
			case R.id.tv_person_updatepwd:
				new IntentToOther(getActivity(), UpdatePWDActivity.class, null);
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		getActivity().finish();
		iSPreferences.clearUser();
		new IntentToOther(getActivity(), LoginActivity.class, null);
	}

}

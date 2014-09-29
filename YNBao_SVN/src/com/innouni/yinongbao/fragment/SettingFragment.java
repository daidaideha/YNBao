package com.innouni.yinongbao.fragment;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.R.id;
import com.innouni.yinongbao.R.layout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

/***
 * Date:2014-9-24 Author:LiuChao Description:设置界面
 */
@SuppressLint("NewApi")
public class SettingFragment extends Fragment implements OnClickListener {
	private ToggleButton mMessageToggleButton;//消息推送选择按钮
	private ToggleButton mOffLineToggleButton;//离线缓存选择按钮
	private TextView mClsMessageTextView;//清除消息列表
	private TextView mClsImageTextView;//清除缓存图片
	private TextView mClsVideoTextView;//清除缓存视频
	private TextView mClsShopsTextView;//清除缓存商铺
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View settingView = inflater.inflate(R.layout.activity_setting,
				container, false);
		return settingView;
	}
	/*
	 * 初始化选择按钮
	 */
	private void initToggleButton(View settingView) {
		mMessageToggleButton = (ToggleButton) settingView
				.findViewById(R.id.togbtn_messagepush);
		mOffLineToggleButton = (ToggleButton) settingView
				.findViewById(R.id.togbtn_offline);
		mMessageToggleButton
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if(isChecked){
							//选中
						}else{
							//未选中
						}
						
					}
				});
		mOffLineToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					//选中
				}else {
					//未选中
				}
				
			}
		});
	}

	/**
	 * 控件实例化
	 * @param settingView  父视图
	 */
	private void initBodyer(View settingView){
		mClsMessageTextView = (TextView) settingView.findViewById(R.id.txt_setting_clsmessagelist);
		mClsImageTextView = (TextView) settingView.findViewById(R.id.txt_setting_clsimage);
		mClsVideoTextView = (TextView) settingView.findViewById(R.id.txt_setting_clsvideo);
		mClsShopsTextView = (TextView) settingView.findViewById(R.id.txt_setting_clsshops);
		
		
		mClsMessageTextView.setClickable(true);
		mClsImageTextView.setClickable(true);
		mClsShopsTextView.setClickable(true);
		mClsVideoTextView.setClickable(true);
		
		mClsImageTextView.setOnClickListener(this);
		mClsMessageTextView.setOnClickListener(this);
		mClsShopsTextView.setOnClickListener(this);
		mClsVideoTextView.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_setting_clsimage:{//清除图片缓存
			break;
		}
		case R.id.txt_setting_clsmessagelist:{//清除消息列表缓存
			break;
		}
		case R.id.txt_setting_clsshops:{//清除商店缓存
			break;
		}
		case R.id.txt_setting_clsvideo:{//清除视频缓存
			break;
		}
		default:
			break;
		}

	}

}

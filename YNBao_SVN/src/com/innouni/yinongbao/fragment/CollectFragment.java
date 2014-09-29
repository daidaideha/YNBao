package com.innouni.yinongbao.fragment;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.R.id;
import com.innouni.yinongbao.R.layout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/*
 * Date : 2014-9-24
 * Author:LiuChao
 * Description:我的收藏页面
 * 
 */

@SuppressLint("NewApi")
public class CollectFragment extends Fragment implements OnClickListener{
	
	ImageButton mExpterImageButton;//专家按钮
	ImageButton mCropsImageButton;//作物按钮
	ImageButton mShopsImageButton;//商铺按钮
	ImageButton mProductsImageButton;//产品按钮
	ImageButton mKnowledgeImageButton;//知识按钮
	ImageButton mVideoImageButton;//视频按钮
	ImageButton mAddImageButton;//添加按钮
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View maintainView = inflater.inflate(R.layout.fragment_collect,
				container, false);
		return maintainView;
	}
	/**
	 * 收藏页面控件实例化
	 * @param collectView 父视图
	 */
	
	private void initBodyer(View collectView){
		mExpterImageButton = (ImageButton) collectView.findViewById(R.id.ibtn_collect_firstfirst);
		mCropsImageButton = (ImageButton) collectView.findViewById(R.id.ibtn_collect_firsttwo);
		mShopsImageButton = (ImageButton) collectView.findViewById(R.id.ibtn_collect_firstthree);
		mProductsImageButton = (ImageButton) collectView.findViewById(R.id.ibtn_collect_secondone);
		mKnowledgeImageButton = (ImageButton) collectView.findViewById(R.id.ibtn_collect_secondtwo);
		mVideoImageButton = (ImageButton) collectView.findViewById(R.id.ibtn_collect_secondthree);
		mAddImageButton = (ImageButton) collectView.findViewById(R.id.ibtn_collect_thirdone);
		
		mExpterImageButton.setOnClickListener(this);
		mCropsImageButton.setOnClickListener(this);
		mShopsImageButton.setOnClickListener(this);
		mProductsImageButton.setOnClickListener(this);
		mKnowledgeImageButton.setOnClickListener(this);
		mVideoImageButton.setOnClickListener(this);
		mAddImageButton.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ibtn_collect_firstfirst:{//专家按钮点击事件
			
			break;
		}
		case R.id.ibtn_collect_firsttwo:{//作物按钮点击事件
			break;
		}
		case R.id.ibtn_collect_firstthree:{//商铺按钮点击事件
			break;
		}
		case R.id.ibtn_collect_secondone:{//产品按钮点击事件
			break;
		}
		case R.id.ibtn_collect_secondtwo:{//知识按钮点击事件
			break;
		}
		case R.id.ibtn_collect_secondthree:{//视频按钮点击事件
			break;
		}
		case R.id.ibtn_collect_thirdone:{//添加按钮
			break;
		}
		default:
			break;
		}
		
	}

}
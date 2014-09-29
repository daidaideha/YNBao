package com.innouni.yinongbao.activity.group;

import org.w3c.dom.Text;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.R.layout;
import com.innouni.yinongbao.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/***
 * @author:LiuChao
 * @date:2014-9-28
 * @description:群介绍页面
 */
public class GroupInductionActivity extends Activity implements OnClickListener{

	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 头部管理员头像
	 */
	private ImageView mManagerImageView;
	/***
	 * 群管理员姓名
	 */
	private TextView mManagerNameTextView;//
	/***
	 * 群创建时间
	 */
	private TextView mCreateDaTextView;//
	/***
	 * 群号码
	 */
	private TextView mGroupNumberTextView;//
	/***
	 * 群成员数量
	 */
	private TextView mGroupMemberNumtexTextView;//
	/***
	 * 左边成员头像
	 */
	private ImageView mGroupLeftImageView;//
	/***
	 * 中间成员头像
	 */
	private ImageView mGroupMidImageView;//
	/***
	 * 右边成员头像
	 */
	private ImageView mGroupRightImageView;
	/***
	 * 更多的头像
	 */
	private ImageButton mGroupImageMoreImageView;
	/***
	 * 群相册
	 */
	private TextView mGroupImageTextView;
	/***
	 * 群组问题
	 */
	private TextView mGroupQuestionTextView;
	/***
	 * 群公告数量
	 */
	private TextView mGroupNoteNumTextView;
	/***
	 * 群等级
	 */
	private TextView mGroupLevelTextView;
	/***
	 * 群地点
	 */
	private TextView mGroupAddressTextView;
	/***
	 * 群分类
	 */
	private TextView mGroupCategoryTextView;
	/***
	 * 群介绍
	 */
	private TextView mGroupInfoTextView;
	/***
	 * 加入该群
	 */
	private TextView mGroupAddTextView;
	/***
	 * 头部标题
	 */
	private String mHeadNameString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_introduction);
		
		initHeader();
		initBodyer();
	}

	/***
	 * 头部初始化
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(mHeadNameString);
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	/***
	 * 控件的初始化
	 */
	private void initBodyer(){
		mManagerImageView = (ImageView)findViewById(R.id.img_group_manager);
		mManagerNameTextView = (TextView)findViewById(R.id.txt_group_mangername);
		mCreateDaTextView = (TextView)findViewById(R.id.txt_group_createdate);
		mGroupNumberTextView = (TextView)findViewById(R.id.txt_group_number);
		mGroupMemberNumtexTextView = (TextView)findViewById(R.id.txt_group_membernum);
		mGroupLeftImageView = (ImageView)findViewById(R.id.img_group_imgleft);
		mGroupMidImageView = (ImageView)findViewById(R.id.img_group_mid);
		mGroupRightImageView = (ImageView)findViewById(R.id.img_group_right);
		mGroupImageMoreImageView = (ImageButton)findViewById(R.id.img_group_imgmore);
		mGroupImageTextView = (TextView)findViewById(R.id.txt_group_image);
		mGroupQuestionTextView = (TextView)findViewById(R.id.txt_group_question);
		mGroupNoteNumTextView = (TextView)findViewById(R.id.txt_group_notenum);
		mGroupLevelTextView = (TextView)findViewById(R.id.txt_group_grouplevel);
		mGroupAddressTextView = (TextView)findViewById(R.id.txt_group_address);
		mGroupInfoTextView = (TextView)findViewById(R.id.txt_group_info);
		mGroupCategoryTextView = (TextView)findViewById(R.id.txt_group_category);
		mGroupAddTextView = (TextView)findViewById(R.id.txt_group_addgroup);
		
		mGroupImageTextView.setClickable(true);
		mGroupQuestionTextView.setClickable(true);
		mGroupNoteNumTextView.setClickable(true);
		mGroupInfoTextView.setClickable(true);
		mGroupAddTextView.setClickable(true);
		
		mGroupImageMoreImageView.setOnClickListener(this);
		mGroupImageTextView.setOnClickListener(this);
		mGroupQuestionTextView.setOnClickListener(this);
		mGroupNoteNumTextView.setOnClickListener(this);
		mGroupInfoTextView.setOnClickListener(this);
		mGroupAddTextView.setOnClickListener(this);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_induction, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_group_imgmore:{
			break;
		}
		case R.id.txt_group_image:{
			break;
		}
		case R.id.txt_group_question:{
			break;
		}
		case R.id.txt_group_notenum:{
			break;
		}
		case R.id.txt_group_info:{
			break;
		}
		case R.id.txt_group_addgroup:{
			break;
		}
		default:
			break;
		}
		// TODO Auto-generated method stub
		
	}

}

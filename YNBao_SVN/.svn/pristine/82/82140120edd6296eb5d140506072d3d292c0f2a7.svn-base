package com.innouni.yinongbao.activity.person;

import com.innouni.yinongbao.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 修改用户名
 * @author Hugj
 *
 */
public class ModifyUserNameActivity extends Activity implements OnClickListener {

	private RelativeLayout backLayout;
	private TextView titleView;
	private ImageButton submitButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_infoname);
		
		/**
		 * 初始化TitleBar
		 */
		backLayout = (RelativeLayout) findViewById(R.id.rl_header_back);
		backLayout.setOnClickListener(this);
		titleView = (TextView) findViewById(R.id.tv_header_title);
		titleView.setText("编辑姓名");
		submitButton = (ImageButton) findViewById(R.id.ibnt_person_namedone);
		submitButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_header_back:
			finish();
			break;
		case R.id.ibnt_person_namedone:
			break;
		}
	}
}

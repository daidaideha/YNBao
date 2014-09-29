package com.innouni.yinongbao.activity.person;

import com.innouni.yinongbao.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 修改用户名
 * @author Hugj
 *
 */
public class ModifyUserNameActivity extends Activity implements OnClickListener {
	
	public static final int RESULT_MODIFY_CODE = 100;

	private RelativeLayout backLayout;
	private TextView titleView;
	private ImageButton submitButton;
	
	private EditText nameView;
	private ImageView delView;
	
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
		
		/**
		 * 输入框和清除按钮
		 */
		nameView = (EditText) findViewById(R.id.edit_person_modifyname);
		delView = (ImageView) findViewById(R.id.img_person_del);
		delView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_header_back:
			finish();
			break;
		case R.id.ibnt_person_namedone:
			submitInfo();
			break;
		case R.id.img_person_del:
			nameView.setText("");
			break;
		}
	}

	private void submitInfo() {
		String username = nameView.getText().toString();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(ModifyUserNameActivity.this, "请先输入", Toast.LENGTH_LONG);
		} else {
			if (username.length() < 4 || username.length() > 30) {
				Toast.makeText(ModifyUserNameActivity.this, "长度不符合", Toast.LENGTH_LONG);
			} else {
				Intent intent = new Intent();
				intent.putExtra("new_username", username);
				setResult(RESULT_MODIFY_CODE, intent);
				finish();
			}
		}
	}
}

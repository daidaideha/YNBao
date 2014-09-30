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
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 修改个人说明
 * @author Hugj
 *
 */
public class ModifyDescActivity extends Activity implements OnClickListener {
	
	public static final int RESULT_MODIFY_CODE = 102;

	/**
	 * TitleBar
	 */
	private RelativeLayout backLayout;
	private TextView titleView;
	private ImageButton submitButton;
	
	private EditText decView;
	private String desc = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_desc);
		
		/**
		 * 初始化TitleBar
		 */
		backLayout = (RelativeLayout) findViewById(R.id.rl_header_back);
		backLayout.setOnClickListener(this);
		titleView = (TextView) findViewById(R.id.tv_header_title);
		titleView.setText("编辑个人说明");
		submitButton = (ImageButton) findViewById(R.id.ibnt_person_namedone);
		submitButton.setOnClickListener(this);
		
		decView = (EditText) findViewById(R.id.dec);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_header_back:
			finish();
			break;
		case R.id.ibnt_person_namedone:
			desc = decView.getText().toString();
			if (TextUtils.isEmpty(desc)) {
				finish();
			} else {
				Intent intent = new Intent();
				intent.putExtra("desc", desc);
				setResult(RESULT_MODIFY_CODE, intent);
				finish();
			}
			break;
		}
	}
}

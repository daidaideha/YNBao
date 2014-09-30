package com.innouni.yinongbao.activity.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;

/**
 * 修改性别
 * 
 * @author Hugj
 * 
 */
public class ModifySexActivity extends Activity implements OnClickListener {

	public static final int RESULT_MODIFY_CODE = 101;

	/**
	 * TitleBar
	 */
	private RelativeLayout backLayout;
	private TextView titleView;
	private ImageButton submitButton;

	private RadioGroup group;
	private String sex = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_sex);

		/**
		 * 初始化TitleBar
		 */
		backLayout = (RelativeLayout) findViewById(R.id.rl_header_back);
		backLayout.setOnClickListener(this);
		titleView = (TextView) findViewById(R.id.tv_header_title);
		titleView.setText("编辑性别");
		submitButton = (ImageButton) findViewById(R.id.ibnt_person_namedone);
		submitButton.setOnClickListener(this);

		group = (RadioGroup) findViewById(R.id.group);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio_nan) {
					sex = "男";
				} else if (checkedId == R.id.radio_nv) {
					sex = "女";
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_header_back:
			finish();
			break;
		case R.id.ibnt_person_namedone:
			if (TextUtils.isEmpty(sex)) {
				finish();
			} else {
				Intent intent = new Intent();
				intent.putExtra("sex", sex);
				setResult(RESULT_MODIFY_CODE, intent);
				finish();
			}
			break;
		}
	}
}

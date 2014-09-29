package com.innouni.yinongbao.activity.ask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 我要问界面
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-22
 */
public class AskActivity extends Activity implements OnClickListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 提问标题输入框
	 */
	private EditText edt_title;
	/***
	 * 提示内容输入框
	 */
	private EditText edt_context;
	/***
	 * 图片栏布局控件
	 */
	private LinearLayout ll_image;
	/***
	 * 提交按钮
	 */
	private Button btn_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask);

		initHeader();
		initBodyer();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(getString(R.string.tv_header_ask));
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/***
	 * 初始化布局控件
	 */
	private void initBodyer() {
		edt_title = (EditText) findViewById(R.id.edt_ask_title);
		edt_context = (EditText) findViewById(R.id.edt_ask_context);

		btn_submit = (Button) findViewById(R.id.btn_submit);

		btn_submit.setOnClickListener(this);
	}

	/***
	 * 检查输入框内容合法性
	 * 
	 * @return true：合法，false：不合法
	 */
	private boolean check() {
		if (comFunction.isNullorSpace(edt_title.getText().toString())) {
			comFunction.toastMsg(getString(R.string.toast_ask_title), this);
			return false;
		}
		if (comFunction.isNullorSpace(edt_context.getText().toString())) {
			comFunction.toastMsg(getString(R.string.toast_ask_context), this);
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (check()) {

		}
	}
	
//	private Map<String, String> getMap() {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("title", edt_title.getText().toString());
//		map.put("context", edt_context.getText().toString());
//		return map;
//	}
//	
//	/***
//	 * 提交操作
//	 */
//	private void post() {
//		if (comFunction.isWiFi_3G(this)) {
//			JSONObject jsonObject = comFunction.getDataFromService(this, "url", 
//					comFunction.map2JSONObject(getMap()));
//		}
//	}
}

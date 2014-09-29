package com.innouni.yinongbao.activity.expert;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.ExperDetailGVAdapter;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.exper.ExperCompanyUnit;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 专家库回答、技术列表界面
 * @author LinYuLing
 * @UpdateDate 2014-09-25
 */
public class ExperCompanyActivity extends Activity {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 展示列表
	 */
	private GridView mGridView;
	
	/***
	 * 数据列表适配器
	 */
	@SuppressWarnings("rawtypes")
	private ExperDetailGVAdapter adapter;
	
	/***
	 * 数据列表
	 */
	private List<ExperCompanyUnit> list_data;
	/***
	 * id
	 */
	private String id;

	/***
	 * 用来获取屏幕大小
	 */
	private DisplayMetrics dm;
	
	/***
	 * 获取数据异步
	 */
	private GetDataTask getDataTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exper_company);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		list_data = new ArrayList<ExperCompanyUnit>();
		
		try {
			id = getIntent().getStringExtra("id");
		} catch (Exception e) {
			// TODO: handle exception
		}

		initHeader();
		initBodyer();
		getData();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);
		
		tv_title.setText(getString(R.string.label_exper_detail_relate));
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/***
	 * 实例化控件
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initBodyer() {
		mGridView = (GridView) findViewById(R.id.gridview);
		adapter = new ExperDetailGVAdapter(this, list_data, dm.widthPixels / 8);
		mGridView.setAdapter(adapter);
	}
	
	/***
	 * 获取数据操作
	 */
	private void getData() {
		if (comFunction.isWiFi_3G(this)) {
			if (getDataTask == null) {
				getDataTask = new GetDataTask();
				getDataTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}

	/***
	 * 获取数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, Void> {
		/***
		 * 等待进度框
		 */
		private ProgressDialog pd;
		private JSONObject jobj;
		private JSONArray jArray_exper_company;
		private List<NameValuePair> paramsList;
		/***
		 * 服务器返回类型值 200：成功
		 */
		private String code;
		/***
		 * 服务器返回提示内容值
		 */
		private String message = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(ExperCompanyActivity.this);
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.setMessage(getString(R.string.pd_data_link));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("Id", id));
		}

		@Override
		protected Void doInBackground(Void... pragm) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("get_wen_expert_companylist", 
						paramsList, ExperCompanyActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					jArray_exper_company = new JSONArray(jobj.getString("data"));
					for (int j = 0; j < jArray_exper_company.length(); j++) {
						ExperCompanyUnit companyUnit = new ExperCompanyUnit();
						companyUnit.setUserid(jArray_exper_company
								.getJSONObject(j).getString("userid"));
						companyUnit.setLogo(jArray_exper_company.getJSONObject(
								j).getString("logo"));
						companyUnit.setCompanyname(jArray_exper_company
								.getJSONObject(j).getString("companyname"));
						list_data.add(companyUnit);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			getDataTask = null;
			if (message != null) {
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					adapter.notifyDataSetChanged();
				} else {
					comFunction.toastMsg(message, ExperCompanyActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ExperCompanyActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}
	}
}

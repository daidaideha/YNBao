package com.innouni.yinongbao.activity.exhibition;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.ExhibitionNewsAdapter;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.exhibition.ExhibitionNewsUnit;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 农资展厅企业新闻列表界面
 * @author LinYuLing
 * @UpdateDate 2014-09-25
 */
public class ExhibitionNewsListActivity extends Activity {
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
	private ListView mListView;
	
	/***
	 * 数据列表适配器
	 */
	private ExhibitionNewsAdapter adapter;
	
	/***
	 * 数据列表
	 */
	private List<ExhibitionNewsUnit> list_data;
	/***
	 * 企业id
	 */
	private String id;
	
	/***
	 * 获取数据异步
	 */
	private GetDataTask getDataTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exper_list);
		list_data = new ArrayList<ExhibitionNewsUnit>();
		
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
		
		tv_title.setText(getString(R.string.label_exhibition_company_new));
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
	private void initBodyer() {
		mListView = (ListView) findViewById(R.id.listview);
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
	 * 列表数据赋值
	 */
	private void initData() {
		adapter = new ExhibitionNewsAdapter(this, list_data);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("id", list_data.get(position).getId());
				bundle.putInt("type", 1);
				new IntentToOther(ExhibitionNewsListActivity.this, 
						ExhibitionWebDetailActivity.class, bundle);
			}
		});
		mListView.setAdapter(adapter);
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
		private JSONArray jArray;
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
			pd = new ProgressDialog(ExhibitionNewsListActivity.this);
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.setMessage(getString(R.string.pd_data_link));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("companyId", id));
		}

		@Override
		protected Void doInBackground(Void... pragm) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("get_shop_company_news", 
						paramsList, ExhibitionNewsListActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					jArray = new JSONArray(jobj.getString("data"));
					if (jArray == null) {
						return null;
					}
					ExhibitionNewsUnit unit = null;
					for (int i = 0; i < jArray.length(); i++) {
						unit = new ExhibitionNewsUnit();
						unit.setId(jArray.getJSONObject(i).getString("id"));
						unit.setTitle(jArray.getJSONObject(i).getString("title"));
						list_data.add(unit);
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
					initData();
				} else {
					comFunction.toastMsg(message, ExhibitionNewsListActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ExhibitionNewsListActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}
	}
}

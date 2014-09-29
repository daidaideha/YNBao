package com.innouni.yinongbao.activity.pest;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.ExperDetailGVAdapter;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.pest.PestUnit;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 害虫列表界面
 * @author LinYuLing
 * @UpdateDate 2014-09-30
 */
public class PestTypeActivity extends Activity {
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
	private List<PestUnit> list_data;
	/***
	 * id
	 */
	private String id;
	/***
	 * 用来判断是搜索还是普通
	 * 0：普通
	 * 1：搜索
	 */
	private int type = 0;
	/***
	 * 搜索关键词
	 */
	private String search = "";

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
		list_data = new ArrayList<PestUnit>();
		
		try {
			type = getIntent().getIntExtra("type", 0);
			if (type == 0) {
				id = getIntent().getStringExtra("id");
			} else {
				search = getIntent().getStringExtra("search");
			}
			System.out.println("id: " + id);
			System.out.println("search: " + search);
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
		
		tv_title.setText(getString(R.string.tv_header_pest_type));
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
		mGridView.setNumColumns(2);
		adapter = new ExperDetailGVAdapter(this, list_data, dm.widthPixels / 21 * 8);
		mGridView.setAdapter(adapter);
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("id", list_data.get(position).getId());
				new IntentToOther(PestTypeActivity.this,
						PestDetailActivity.class, bundle);
			}
		});
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
		private JSONArray jArray_data;
		private List<NameValuePair> paramsList;
		/***
		 * 服务器返回类型值 200：成功
		 */
		private String code;
		/***
		 * 服务器返回提示内容值
		 */
		private String message = null;
		/***
		 * 图片路径
		 */
		private String path;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(PestTypeActivity.this);
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.setMessage(getString(R.string.pd_data_link));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			if (type == 0) {
				paramsList.add(new BasicNameValuePair("catId", id));
			} else {
				paramsList.add(new BasicNameValuePair("keyword", search));
			}
		}

		@Override
		protected Void doInBackground(Void... pragm) {
			// TODO Auto-generated method stub
			String requery = "";
			if (type == 0) {
				requery = comFunction.getDataFromServer("get_gallery_list", 
						paramsList, PestTypeActivity.this);
			} else {
				requery = comFunction.getDataFromServer("get_gallery_search_list", 
						paramsList, PestTypeActivity.this);
			}
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					jArray_data = new JSONArray(jobj.getString("data"));
					if (jArray_data == null) {
						return null;
					}
					PestUnit unit = null;
					for (int i = 0; i < jArray_data.length(); i++) {
						unit = new PestUnit();
						unit.setId(jArray_data.getJSONObject(i).getString("id"));
//						if (type == 0) {
//							path = jArray_data.getJSONObject(i).getString("pic_url");
//						} else {
							path = jArray_data.getJSONObject(i).getString("thumb");
//						}
						unit.setThumb(path);
						unit.setTitle(jArray_data.getJSONObject(i).getString("title"));
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
					adapter.notifyDataSetChanged();
				} else {
					comFunction.toastMsg(message, PestTypeActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						PestTypeActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}
	}
}

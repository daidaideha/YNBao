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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.ExperAdapter;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.exper.ExperFiltrateUnit;
import com.innouni.yinongbao.unit.exper.ExperUnit;
import com.innouni.yinongbao.view.PopExperFiltrate;
import com.innouni.yinongbao.view.PullToRefreshView;
import com.innouni.yinongbao.view.PullToRefreshView.OnHeaderRefreshListener;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 专家库首页界面
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-23
 */
public class ExpertMainActivity extends Activity implements OnClickListener,
		OnItemClickListener, OnHeaderRefreshListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 搜索输入框
	 */
	private EditText edt_search;
	/***
	 * 筛选点击按钮
	 */
	private RelativeLayout rl_tab_left, rl_tab_middle, rl_tab_right;
	/***
	 * 筛选显示控件
	 */
	private TextView tv_exper_left, tv_exper_middle, tv_exper_right;
	/***
	 * 刷新控件
	 */
	private PullToRefreshView pullview;
	/***
	 * 数据展示列表控件
	 */
	private ListView listView;
	/***
	 * 列表尾部控件
	 */
	private View footView;
	/***
	 * 加载更多控件
	 */
	private TextView tv_more;
	/***
	 * 地区弹出框
	 */
	private PopExperFiltrate areaPop;
	/***
	 * 作物弹出框
	 */
	private PopExperFiltrate flagPop;
	/***
	 * 专家类型弹出框
	 */
	private PopExperFiltrate typePop;

	/***
	 * 初始展示列表数据列表
	 */
	private List<ExperUnit> list_bak;
	/***
	 * 展示数据列表
	 */
	private List<ExperUnit> list_data;
	/***
	 * 地区数据列表
	 */
	private List<ExperFiltrateUnit> list_area;
	/***
	 * 作物数据列表
	 */
	private List<ExperFiltrateUnit> list_flag;
	/***
	 * 专家类型数据列表
	 */
	private List<ExperFiltrateUnit> list_type;
	/***
	 * 地区 作物 专家类型
	 */
	private String area = "", flag = "", type = "";
	/***
	 * 页数
	 */
	private int pageCount = 1;
	/***
	 * 用来判断是筛选加载还是普通加载 0：表示普通 1：表示筛选 和 加载更多
	 */
	private int getType = 0;

	/***
	 * 用来获取屏幕大小
	 */
	private DisplayMetrics dm;
	/***
	 * 展示列表适配器
	 */
	private ExperAdapter adapter;
	/***
	 * 等待进度框
	 */
	private ProgressDialog pd;

	/***
	 * 获取数据异步
	 */
	private GetDataTask getDataTask;
	/***
	 * 获取筛选数据异步
	 */
	private GetFiltrateTask getFiltrateTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exper_main);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		pd = new ProgressDialog(this);
		pd.setIndeterminate(true);
		pd.setCancelable(true);
		list_area = new ArrayList<ExperFiltrateUnit>();
		list_flag = new ArrayList<ExperFiltrateUnit>();
		list_type = new ArrayList<ExperFiltrateUnit>();
		list_bak = new ArrayList<ExperUnit>();
		list_data = new ArrayList<ExperUnit>();

		initHeader();
		initBodyer();
		getFiltrate();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(getString(R.string.tv_header_exper));
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
		edt_search = (EditText) findViewById(R.id.edt_exper_search);
		rl_tab_left = (RelativeLayout) findViewById(R.id.rl_exper_left);
		rl_tab_middle = (RelativeLayout) findViewById(R.id.rl_exper_middle);
		rl_tab_right = (RelativeLayout) findViewById(R.id.rl_exper_right);
		tv_exper_left = (TextView) findViewById(R.id.tv_exper_left);
		tv_exper_middle = (TextView) findViewById(R.id.tv_exper_middle);
		tv_exper_right = (TextView) findViewById(R.id.tv_exper_right);
		pullview = (PullToRefreshView) findViewById(R.id.pullview);
		listView = (ListView) findViewById(R.id.list_exper);
		adapter = new ExperAdapter(this, list_bak);
		footView = getLayoutInflater().inflate(R.layout.widget_listview_foot,
				null);
		tv_more = (TextView) footView.findViewById(R.id.tv_more);
		tv_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadMore();
			}
		});
		listView.addFooterView(footView);
		listView.setAdapter(adapter);

		edt_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		edt_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int actionId,
					KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					getType = 1;
					pageCount = 1;
					getData();
				}
				return false;
			}
		});

		pullview.setOnHeaderRefreshListener(this);
		listView.setOnItemClickListener(this);
		rl_tab_left.setOnClickListener(this);
		rl_tab_middle.setOnClickListener(this);
		rl_tab_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_exper_left:
			if (areaPop != null) {
				areaPop.showPopupWindow();
			}
			break;
		case R.id.rl_exper_middle:
			if (flagPop != null) {
				flagPop.showPopupWindow();
			}
			break;
		case R.id.rl_exper_right:
			if (typePop != null) {
				typePop.showPopupWindow();
			}
			break;
		default:
			break;
		}
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
	 * 获取筛选数据操作
	 */
	private void getFiltrate() {
		if (comFunction.isWiFi_3G(this)) {
			if (getFiltrateTask == null) {
				getFiltrateTask = new GetFiltrateTask();
				getFiltrateTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}

	/***
	 * 获取筛选数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetFiltrateTask extends AsyncTask<Void, Void, Void> {
		private JSONObject jobj;
		private JSONObject jobj_data;
		private JSONArray jArray_area;
		private JSONArray jArray_flag;
		private JSONArray jArray_type;
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
			pd.setMessage(getString(R.string.pd_data_link));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer(
					"get_wen_expert_suoyin", paramsList,
					ExpertMainActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					jobj_data = new JSONObject(jobj.getString("data"));
					if (jobj_data == null) {
						return null;
					}
					jArray_area = new JSONArray(jobj_data.getString("area"));
					if (jArray_area == null) {
						return null;
					}
					ExperFiltrateUnit unit = null;
					for (int i = 0; i < jArray_area.length(); i++) {
						unit = new ExperFiltrateUnit();
						unit.setId(jArray_area.getJSONObject(i).getString("id"));
						unit.setName(jArray_area.getJSONObject(i).getString(
								"name"));
						list_area.add(unit);
					}
					jArray_flag = new JSONArray(jobj_data.getString("flag"));
					if (jArray_flag == null) {
						return null;
					}
					for (int i = 0; i < jArray_flag.length(); i++) {
						unit = new ExperFiltrateUnit();
						unit.setId(jArray_flag.getJSONObject(i)
								.getString("fid"));
						unit.setName(jArray_flag.getJSONObject(i).getString(
								"name"));
						list_flag.add(unit);
					}
					jArray_type = new JSONArray(jobj_data.getString("type"));
					if (jArray_type == null) {
						return null;
					}
					for (int i = 0; i < jArray_type.length(); i++) {
						unit = new ExperFiltrateUnit();
						unit.setId(jArray_type.getJSONObject(i).getString(
								"groupid"));
						unit.setName(jArray_type.getJSONObject(i).getString(
								"grouptitle"));
						list_type.add(unit);
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
					areaPop = new PopExperFiltrate(ExpertMainActivity.this,
							getArea(), rl_tab_left, dm.widthPixels / 3,
							dm.heightPixels / 2);
					flagPop = new PopExperFiltrate(ExpertMainActivity.this,
							getFlag(), rl_tab_middle, dm.widthPixels / 3,
							dm.heightPixels / 2);
					typePop = new PopExperFiltrate(ExpertMainActivity.this,
							getType(), rl_tab_right, dm.widthPixels / 3,
							dm.heightPixels / 2);
				} else {
					comFunction.toastMsg(message, ExpertMainActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ExpertMainActivity.this);
			}
			getData();
			// if (pd.isShowing()) {
			// pd.dismiss();
			// }
			super.onPostExecute(result);
		}
	}

	/***
	 * 获取地区按钮列表
	 * 
	 * @return 地区按钮列表
	 */
	private List<Button> getArea() {
		List<Button> list = new ArrayList<Button>();
		for (int i = 0; i < list_area.size(); i++) {
			Button btn = new Button(this);
			btn.setText(list_area.get(i).getName());
			btn.setBackgroundResource(0);
			btn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					dm.widthPixels / 8));
			// btn.setLayoutParams(new LayoutParams(dm.widthPixels / 3 - 20,
			// dm.widthPixels / 8));
			btn.setGravity(Gravity.CENTER);
			final int pos = i;
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getType = 1;
					area = list_area.get(pos).getId();
					tv_exper_left.setText(list_area.get(pos).getName());
					areaPop.showPopupWindow();
					pageCount = 1;
					getData();
				}
			});
			list.add(btn);
		}
		return list;
	}

	/***
	 * 获取作物按钮列表
	 * 
	 * @return 作物按钮列表
	 */
	private List<Button> getFlag() {
		List<Button> list = new ArrayList<Button>();
		for (int i = 0; i < list_flag.size(); i++) {
			Button btn = new Button(this);
			btn.setText(list_flag.get(i).getName());
			btn.setBackgroundResource(0);
			btn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					dm.widthPixels / 8));
			// btn.setLayoutParams(new LayoutParams(dm.widthPixels / 3 - 20,
			// dm.widthPixels / 8));
			btn.setGravity(Gravity.CENTER);
			final int pos = i;
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getType = 1;
					flag = list_flag.get(pos).getId();
					tv_exper_middle.setText(list_flag.get(pos).getName());
					flagPop.showPopupWindow();
					pageCount = 1;
					getData();
				}
			});
			list.add(btn);
		}
		return list;
	}

	/***
	 * 获取专家类型按钮列表
	 * 
	 * @return 地区按钮列表
	 */
	private List<Button> getType() {
		List<Button> list = new ArrayList<Button>();
		for (int i = 0; i < list_type.size(); i++) {
			Button btn = new Button(this);
			btn.setText(list_type.get(i).getName());
			btn.setBackgroundResource(0);
			btn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					dm.widthPixels / 8));
			// btn.setLayoutParams(new LayoutParams(dm.widthPixels / 3 - 20,
			// dm.widthPixels / 8));
			btn.setGravity(Gravity.CENTER);
			final int pos = i;
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getType = 1;
					type = list_type.get(pos).getName();
					tv_exper_right.setText(list_type.get(pos).getName());
					typePop.showPopupWindow();
					pageCount = 1;
					getData();
				}
			});
			list.add(btn);
		}
		return list;
	}

	/***
	 * 获取数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, Void> {
		private JSONObject jobj;
		private JSONObject jobj_data;
		private JSONArray jArray_exper;
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
		 * 每页数据量
		 */
		private String count;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (pageCount == 1) {
				list_data.clear();
				listView.setVisibility(View.GONE);
			}
			if (getType == 1) {
				pd.show();
			}
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("keyword", edt_search
					.getText().toString().trim()));
			paramsList.add(new BasicNameValuePair("page", pageCount + ""));
			paramsList.add(new BasicNameValuePair("area", area));
			paramsList.add(new BasicNameValuePair("flag", flag));
			paramsList.add(new BasicNameValuePair("type", type));
			System.out.println("keyword: "
					+ edt_search.getText().toString().trim() + " page: "
					+ pageCount + " area: " + area + " flag: " + flag
					+ " type: " + type);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer(
					"get_wen_expert_list", paramsList, ExpertMainActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					jobj_data = new JSONObject(jobj.getString("data"));
					if (jobj_data == null) {
						return null;
					}
					count = jobj_data.getString("count");
					jArray_exper = new JSONArray(jobj_data.getString("experts"));
					if (jArray_exper == null) {
						return null;
					}
					ExperUnit unit = null;
					for (int i = 0; i < jArray_exper.length(); i++) {
						unit = new ExperUnit();
						unit.setId(jArray_exper.getJSONObject(i)
								.getString("id"));
						unit.setUsername(jArray_exper.getJSONObject(i)
								.getString("username"));
						unit.setName(jArray_exper.getJSONObject(i).getString(
								"name"));
						unit.setPosition(jArray_exper.getJSONObject(i)
								.getString("position"));
						unit.setCompany(jArray_exper.getJSONObject(i)
								.getString("company"));
						unit.setAvatar(jArray_exper.getJSONObject(i).getString(
								"avatar"));
						unit.setMobile(jArray_exper.getJSONObject(i).getString(
								"mobile"));
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
					adapter.clearList();
					adapter.setList(list_data);
					adapter.notifyDataSetChanged();
					if (comFunction.isNullorSpace(count)) {
						tv_more.setVisibility(View.GONE);
					} else {
						if (Integer.valueOf(count) < 14) {
							tv_more.setVisibility(View.GONE);
						} else {
							tv_more.setVisibility(View.VISIBLE);
						}
					}
					listView.setVisibility(View.VISIBLE);
				} else {
					comFunction.toastMsg(message, ExpertMainActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ExpertMainActivity.this);
			}
			pullview.onHeaderRefreshComplete();
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putString("id", list_data.get(position).getId());
		new IntentToOther(ExpertMainActivity.this, ExperDetailActivity.class,
				bundle);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		pageCount = 1;
		getType = 0;
		getData();
	}

	/***
	 * 加载更多操作
	 */
	private void loadMore() {
		// TODO Auto-generated method stub
		getType = 1;
		pageCount++;
		getData();
	}
}

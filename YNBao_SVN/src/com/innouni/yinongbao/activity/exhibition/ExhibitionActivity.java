package com.innouni.yinongbao.activity.exhibition;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.ExhibitionMainAdapter;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.exhibition.ExhibitionMainUnit;
import com.innouni.yinongbao.unit.exhibition.ExhibitionTypeCUnit;
import com.innouni.yinongbao.unit.exhibition.ExhibitionTypeUnit;
import com.innouni.yinongbao.unit.exhibition.ExhibitionUnit;
import com.innouni.yinongbao.view.PopExhibitionType;
import com.innouni.yinongbao.view.PopExhibitionType.OnMyItemClickListener;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 农资展厅主界面
 * @author LinYuLing
 * @UpdateDate 2014-09-27
 */
public class ExhibitionActivity extends Activity implements OnClickListener,
		OnMyItemClickListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 菜单按钮
	 */
	private Button btn_menu;
	/***
	 * 搜索框
	 */
	private EditText edt_search;
	/***
	 * 产品展示控件
	 */
	private ListView listView;
	/***
	 * 广告控件
	 */
	private View headview;
	/***
	 * 轮播图控件
	 */
	private ViewPager mViewPager;
	/***
	 * 轮播图底部圈圈布局控件
	 */
	private LinearLayout ll_vp_bottom;
	/***
	 * 分类弹出框
	 */
	private PopExhibitionType popType;
	
	/***
	 * 数据适配器
	 */
	private ExhibitionMainAdapter adapter;
	/***
	 * 用来获取屏幕大小
	 */
	private DisplayMetrics dm;
	/***
	 * 等待进度框
	 */
	private ProgressDialog pd;
	
	/***
	 * 初始化数据列表
	 */
	private List<ExhibitionMainUnit> list_bak;
	/***
	 * 数据列表
	 */
	private List<ExhibitionMainUnit> list_data;
	/***
	 * 分类数据列表
	 */
	private List<ExhibitionTypeUnit> list_type;

	/***
	 * 获取数据异步
	 */
	private GetDataTask getDataTask;
	/***
	 * 获取分类数据异步
	 */
	private GetTypeTask getTypeTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exhibition_main);
		pd = new ProgressDialog(ExhibitionActivity.this);
		pd.setMessage(getString(R.string.pd_data_link));
		pd.setIndeterminate(true);
		pd.setCancelable(true);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		list_bak = new ArrayList<ExhibitionMainUnit>();
		list_data = new ArrayList<ExhibitionMainUnit>();
		list_type = new ArrayList<ExhibitionTypeUnit>();

		initHeader();
		initBodyer();
		getType();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(getString(R.string.tv_header_exhibition));
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
		btn_menu = (Button) findViewById(R.id.btn_menu);
		edt_search = (EditText) findViewById(R.id.edt_search);
		listView = (ListView) findViewById(R.id.listview);
		headview = getLayoutInflater().inflate(R.layout.view_viewpager, null);
		mViewPager = (ViewPager) headview.findViewById(R.id.viewpager_main);
		ll_vp_bottom = (LinearLayout) headview.findViewById(R.id.ll_vp_bottom);
		
//		ImageView iv = new ImageView(this);
//		iv.setBackgroundResource(R.drawable.default_exhibition);
//		iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//				LayoutParams.WRAP_CONTENT));
		listView.addHeaderView(headview);
		adapter = new ExhibitionMainAdapter(this, list_bak, dm.widthPixels / 21 * 8);
		listView.setAdapter(adapter);
		 
		edt_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		edt_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int actionId,
					KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					Bundle bundle = new Bundle();
					bundle.putInt("type", 1);
					bundle.putString("search", edt_search.getText().toString());
					new IntentToOther(ExhibitionActivity.this, 
							ExhibitionTypeActivity.class, bundle);
					edt_search.setText("");
				}
				return false;
			}
		});
		
		btn_menu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (popType != null) {
			popType.showPopupWindow();
		}
	}

	@Override
	public void onMyItemClick(int pPosition, int cPosition) {
		// TODO Auto-generated method stub
		popType.showPopupWindow();
		Bundle bundle = new Bundle();
		bundle.putString("id", list_type.get(pPosition).getC_types()
				.get(cPosition).getCatid());
		new IntentToOther(ExhibitionActivity.this, 
				ExhibitionTypeActivity.class, bundle);
	}

	/***
	 * 获取分类数据操作
	 */
	private void getType() {
		if (comFunction.isWiFi_3G(this)) {
			if (getTypeTask == null) {
				getTypeTask = new GetTypeTask();
				getTypeTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
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
	 * 获取数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, Void> {
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

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			list_data.clear();
			paramsList = new ArrayList<NameValuePair>();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("get_shop_index", 
					paramsList, ExhibitionActivity.this);
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
					ExhibitionMainUnit unit = null;
					ExhibitionUnit unit_p = null;
					List<ExhibitionUnit> list = null;
					for (int i = 0; i < jArray_data.length(); i++) {
						unit = new ExhibitionMainUnit();
						unit.setCatId(jArray_data.getJSONObject(i).getString("catId"));
						unit.setCatName(jArray_data.getJSONObject(i).getString("catName"));
						JSONArray jArray_p = new JSONArray(jArray_data.getJSONObject(i)
								.getString("picturelist"));
						list = new ArrayList<ExhibitionUnit>();
						for (int j = 0; j < jArray_p.length(); j++) {
							unit_p = new ExhibitionUnit();
							unit_p.setId(jArray_p.getJSONObject(j).getString("id"));
							unit_p.setThumb(jArray_p.getJSONObject(j).getString("thumb"));
							unit_p.setTitle(jArray_p.getJSONObject(j).getString("title"));
							list.add(unit_p);
						}
						unit.setPicturelist(list);
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
				} else {
					comFunction.toastMsg(message, ExhibitionActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ExhibitionActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}

	}

	/***
	 * 获取筛选数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetTypeTask extends AsyncTask<Void, Void, Void> {
		private JSONObject jobj;
		private JSONArray jobj_data;
		private JSONArray jArray_child;
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
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer("get_shop_menu", paramsList,
					ExhibitionActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				code = jobj.getString("code");
				message = jobj.getString("message");
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					jobj_data = new JSONArray(jobj.getString("data"));
					if (jobj_data == null) {
						return null;
					}
					ExhibitionTypeUnit unit = null;
					for (int i = 0; i < jobj_data.length(); i++) {
						unit = new ExhibitionTypeUnit();
						unit.setCatId(jobj_data.getJSONObject(i).getString("catId"));
						unit.setCatName(jobj_data.getJSONObject(i).getString("catName"));
						jArray_child = new JSONArray(jobj_data.getJSONObject(i).getString("c_types"));
						List<ExhibitionTypeCUnit> list = new ArrayList<ExhibitionTypeCUnit>();
						for (int j = 0; j < jArray_child.length(); j++) {
							ExhibitionTypeCUnit cUnit = new ExhibitionTypeCUnit();
							cUnit.setCatid(jArray_child.getJSONObject(j).getString("catid"));
							cUnit.setCatname(jArray_child.getJSONObject(j).getString("catname"));
							list.add(cUnit);
						}
						unit.setC_types(list);
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
					popType = new PopExhibitionType(ExhibitionActivity.this, list_type, 
							btn_menu, dm.widthPixels, dm.heightPixels / 5 * 4);
					popType.setOnMyItemClickListener(ExhibitionActivity.this);
				} else {
					comFunction.toastMsg(message, ExhibitionActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ExhibitionActivity.this);
			}
			getData();
			super.onPostExecute(result);
		}
	}

}

package com.innouni.yinongbao.fragment;

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
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.exhibition.ExhibitionDetailActivity;
import com.innouni.yinongbao.adapter.ExperDetailGVAdapter;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.exhibition.ExhibitionUnit;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 农资展厅产品列表界面
 * @author LinYuLing
 * @UpdateDate 2014-09-28
 */
public class ProductFragment extends Fragment {
	/***
	 * 展示控件
	 */
	private GridView mGridView;
	
	/***
	 * 数据列表适配器
	 */
	@SuppressWarnings("rawtypes")
	private ExperDetailGVAdapter adapter;
	/***
	 * 用来获取屏幕大小
	 */
	private DisplayMetrics dm;
	
	/***
	 * 数据列表
	 */
	private List<ExhibitionUnit> list_data;
	/***
	 * 公司id
	 */
	private String companyId;
	/***
	 * 分类id
	 */
	private String catId;
	
	/***
	 * 获取产品数据异步
	 */
	private GetDataTask getDataTask;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		list_data = new ArrayList<ExhibitionUnit>();
		dm = new DisplayMetrics();
		((Activity) getActivity()).getWindowManager().getDefaultDisplay().getMetrics(dm);
		try {
			companyId = getActivity().getIntent().getStringExtra("id");
			catId = getArguments().getString("catId");
		} catch (Exception e) {
			// TODO: handle exception
			catId = "";
		}
		System.out.println("catId: " + catId);
		System.out.println("companyId: " + companyId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View maintainView = inflater.inflate(R.layout.fragment_gridview,
				container, false);
		initBodyer(maintainView);
		getData();
		return maintainView;
	}

	/***
	 * 初始化布局控件
	 * 
	 * @param v
	 *            父视图
	 */
	private void initBodyer(View maintainView) {
		mGridView = (GridView) maintainView.findViewById(R.id.gridview);
		adapter = new ExperDetailGVAdapter<>(getActivity(), list_data, dm.widthPixels / 21 * 8);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("id", list_data.get(position).getId());
				new IntentToOther(getActivity(), ExhibitionDetailActivity.class, bundle);
			}
		});
	}
	
	/***
	 * 获取数据操作
	 */
	private void getData() {
		if (comFunction.isWiFi_3G(getActivity())) {
			if (getDataTask == null) {
				getDataTask = new GetDataTask();
				getDataTask.execute();
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), getActivity());
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
		private JSONArray jArray;
		private List<NameValuePair> paramsList;
		/***
		 * 等待进度框
		 */
		private ProgressDialog pd;
		/***
		 * 服务器返回类型值 200：成功
		 */
		private String code;
		/***
		 * 服务器返回提示内容值
		 */
		private String message = null;
//		/***
//		 * 每页数据量
//		 */
//		private String count;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(getActivity());
			pd.setMessage(getString(R.string.pd_data_link));
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.show();
			list_data.clear();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("companyId", companyId));
			paramsList.add(new BasicNameValuePair("catId", catId));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer(
					"get_shop_company_product", paramsList, getActivity());
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
					ExhibitionUnit unit = null;
					for (int i = 0; i < jArray.length(); i++) {
						unit = new ExhibitionUnit();
						unit.setId(jArray.getJSONObject(i).getString("id"));
						unit.setTitle(jArray.getJSONObject(i).getString("title"));
						unit.setThumb(jArray.getJSONObject(i).getString("pic_url"));
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
//					if (comFunction.isNullorSpace(count)) {
//						tv_more.setVisibility(View.GONE);
//					} else {
//						if (Integer.valueOf(count) < 14) {
//							tv_more.setVisibility(View.GONE);
//						} else {
//							tv_more.setVisibility(View.VISIBLE);
//						}
//					}
//					listView.setVisibility(View.VISIBLE);
				} else {
					comFunction.toastMsg(message, getActivity());
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						getActivity());
			}
//			pullview.onHeaderRefreshComplete();
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}
	}
}

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.exhibition.ExhibitionCompanyDetailActivity;
import com.innouni.yinongbao.activity.exhibition.ExhibitionNewsListActivity;
import com.innouni.yinongbao.activity.exhibition.ExhibitionWebDetailActivity;
import com.innouni.yinongbao.adapter.ExhibitionNewsAdapter;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.exhibition.ExhibitionCompanyUnit;
import com.innouni.yinongbao.unit.exhibition.ExhibitionNewsUnit;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.comFunction;
import com.innouni.yinongbao.widget.sPreferences;

public class ContextFragment extends Fragment implements OnClickListener {
	/***
	 * 企业新闻展示控件
	 */
	private ListView mListView;
	/***
	 * 公司名称
	 */
	private TextView tv_company;
	/***
	 * iv_logo：公司logo
	 * iv_logo_level：公司等级logo
	 */
	private ImageView iv_logo, iv_logo_level;
	/***
	 * tv_link：联系人 tv_bile：手机 tv_phone：电话 tv_fax：传真 tv_address：公司地址
	 */
	private TextView tv_link, tv_bile, tv_phone, tv_fax, tv_address;
	/***
	 * 企业新闻
	 */
	private TextView tv_news;
	/***
	 * tv_collect：收藏 
	 */
	private TextView tv_collect;


	/***
	 * 存储数据的sp
	 */
	private sPreferences iSPreferences;
	/***
	 * 数据列表适配器
	 */
	private ExhibitionNewsAdapter adapter;
	/***
	 * 用来获取屏幕大小
	 */
	private DisplayMetrics dm;
	/***
	 * 图片加载工具类
	 */
	private ImageLoader mImageLoader;
	
	/***
	 * 农资展厅企业实体类
	 */
	private ExhibitionCompanyUnit unit = null;
	/***
	 * 公司id
	 */
	private String companyId;
	/***
	 * 用来判断收藏状态
	 */
	private boolean IS_COLLECT;

	/***
	 * 获取产品数据异步
	 */
	private GetDataTask getDataTask;
	/***
	 * 收藏操作异步
	 */
	private FocusTask focusTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		iSPreferences = new sPreferences(getActivity());
		dm = new DisplayMetrics();
		((Activity) getActivity()).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		mImageLoader = new ImageLoader(getActivity());
		try {
			companyId = getActivity().getIntent().getStringExtra("id");
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("companyId: " + companyId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View maintainView = inflater.inflate(R.layout.fragment_exhibition_company_detail,
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
		tv_company = (TextView) maintainView.findViewById(R.id.tv_company);
		iv_logo = (ImageView) maintainView.findViewById(R.id.iv_logo);
		iv_logo_level = (ImageView) maintainView.findViewById(R.id.iv_logo_level);
		tv_link = (TextView) maintainView.findViewById(R.id.tv_linkman);
		tv_bile = (TextView) maintainView.findViewById(R.id.tv_bile);
		tv_phone = (TextView) maintainView.findViewById(R.id.tv_phone);
		tv_fax = (TextView) maintainView.findViewById(R.id.tv_fax);
		tv_address = (TextView) maintainView.findViewById(R.id.tv_address);
		tv_news = (TextView) maintainView.findViewById(R.id.tv_news);
		tv_collect = (TextView) maintainView.findViewById(R.id.tv_collect);
		mListView = (ListView) maintainView.findViewById(R.id.listview);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("id", unit.getNews().get(position).getId());
				bundle.putInt("type", 1);
				new IntentToOther(getActivity(), ExhibitionWebDetailActivity.class,
					bundle);
			}
		});

		tv_company.setOnClickListener(this);
		tv_news.setOnClickListener(this);
		tv_collect.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.tv_collect:
			IS_COLLECT = !IS_COLLECT;
			focus();
			break;
		case R.id.tv_company:
			bundle.putSerializable("unit", unit);
			new IntentToOther(getActivity(), ExhibitionCompanyDetailActivity.class, bundle);
			break;
		case R.id.tv_news:
			bundle.putString("id", companyId);
			new IntentToOther(getActivity(), ExhibitionNewsListActivity.class, bundle);
			break;
		default:
			break;
		}
	}
	
	/***
	 * 给控件赋值
	 */
	private void initData() {
		tv_company.setText(unit.getCompanyname());
		tv_bile.setText(getString(R.string.tv_exhibition_company_bile)
				.replace("$message$", unit.getBile()));
		tv_phone.setText(getString(R.string.tv_exhibition_company_phone)
				.replace("$message$", unit.getTelephone()));
		tv_fax.setText(getString(R.string.tv_exhibition_company_fax)
				.replace("$message$", unit.getFax()));
		tv_link.setText(getString(R.string.tv_exhibition_company_link)
				.replace("$message$", unit.getLinkman()));
		tv_address.setText(getString(R.string.tv_exhibition_company_address)
				.replace("$message$", unit.getAddress()));
		if (IS_COLLECT) {
			tv_collect.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.icon_exper_technology_collect_hover, 0, 0, 0);
			tv_collect.setTextColor(getResources().getColor(R.color.color_orange));
		} else {
			tv_collect.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.icon_exper_technology_collect, 0, 0, 0);
			tv_collect.setTextColor(getResources().getColor(R.color.gray));
		}
		adapter = new ExhibitionNewsAdapter(getActivity(), unit.getNews());
		mListView.setAdapter(adapter);
		mImageLoader.DisplayImage(unit.getLogo(), iv_logo, false);
		mImageLoader.DisplayImage(unit.getIcon(), iv_logo_level, false);
	}

	/***
	 * 收藏、取消收藏操作
	 */
	private void focus() {
		if (comFunction.isWiFi_3G(getActivity())) {
			if (focusTask == null) {
				focusTask = new FocusTask();
				if (IS_COLLECT) {
					focusTask.execute("add_attention_shop");
				} else {
					focusTask.execute("cancel_attention_shop");
				}
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), getActivity());
		}
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
			comFunction.toastMsg(getString(R.string.toast_net_link),
					getActivity());
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
		private JSONObject jobj_data;
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
		/***
		 * 用来判断收藏状态
		 */
		private String status;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(getActivity());
			pd.setMessage(getString(R.string.pd_data_link));
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("companyId", companyId));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer(
					"get_shop_company_contact", paramsList, getActivity());
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
					status = jobj_data.getString("status");
					ExhibitionNewsUnit newUnit = null;
					unit = new ExhibitionCompanyUnit();
					unit.setCompanyid(jobj_data.getString("companyid"));
					unit.setCompanyname(jobj_data.getString("companyname"));
					unit.setIntroduce(jobj_data.getString("introduce"));
					unit.setLinkman(jobj_data.getString("linkman"));
					unit.setBile(jobj_data.getString("bile"));
					unit.setTelephone(jobj_data.getString("telephone"));
					unit.setFax(jobj_data.getString("fax"));
					unit.setAddress(jobj_data.getString("address"));
					unit.setLogo(jobj_data.getString("logo"));
					unit.setIcon(jobj_data.getString("icon"));
					JSONArray jarry = new JSONArray(jobj_data.getString("news"));
					List<ExhibitionNewsUnit> list = new ArrayList<ExhibitionNewsUnit>();
					for (int j = 0; j < jarry.length(); j++) {
						newUnit = new ExhibitionNewsUnit();
						newUnit.setId(jarry.getJSONObject(j).getString("id"));
						newUnit.setTitle(jarry.getJSONObject(j).getString("title"));
						list.add(newUnit);
					}
					unit.setNews(list);
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
					if (status.equals("1")) {
						IS_COLLECT = true;
					} else {
						IS_COLLECT = false;
					}
					initData();
				} else {
					comFunction.toastMsg(message, getActivity());
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						getActivity());
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}
	}

	/***
	 * 获取数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class FocusTask extends AsyncTask<String, Void, Void> {
		/***
		 * 等待进度框
		 */
		private ProgressDialog pd;
		private JSONObject jobj;
		private List<NameValuePair> paramsList;
		// /***
		// * 服务器返回类型值 200：成功
		// */
		// private String code;
		/***
		 * 服务器返回提示内容值
		 */
		private String message = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(getActivity());
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.setMessage(getString(R.string.pd_data_upload));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("shopId", companyId));
			paramsList.add(new BasicNameValuePair("userId", iSPreferences
					.getSp().getString("memberId", "")));
		}

		@Override
		protected Void doInBackground(String... parms) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer(parms[0],
					paramsList, getActivity());
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				// code = jobj.getString("code");
				message = jobj.getString("message");
				String data = jobj.getString("data");
				if (!data.equals("1")) {
					IS_COLLECT = !IS_COLLECT;
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
			focusTask = null;
			if (pd.isShowing()) {
				pd.dismiss();
			}
			if (message != null) {
				if (IS_COLLECT) {
					tv_collect.setCompoundDrawablesWithIntrinsicBounds(
							R.drawable.icon_exper_technology_collect_hover, 0, 0, 0);
					tv_collect.setTextColor(getResources().getColor(
							R.color.color_orange));
				} else {
					tv_collect.setCompoundDrawablesWithIntrinsicBounds(
							R.drawable.icon_exper_technology_collect, 0, 0, 0);
					tv_collect.setTextColor(getResources().getColor(
							R.color.gray));
				}
				comFunction.toastMsg(message, getActivity());
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						getActivity());
			}
			super.onPostExecute(result);
		}

	}
}

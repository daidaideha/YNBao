package com.innouni.yinongbao.activity.exhibition;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.ExperDetailListAdapter;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.exhibition.ExhibitionUnit;
import com.innouni.yinongbao.view.MyListView;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.comFunction;
import com.innouni.yinongbao.widget.sPreferences;

/***
 * 农资展厅产品详情界面
 * 
 * @author LinYuLing
 * @Updatedate 2014-09-28
 */
@SuppressLint("SetJavaScriptEnabled")
@SuppressWarnings("deprecation")
public class ExhibitionDetailActivity extends Activity implements
		OnClickListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 主体布局
	 */
	private LinearLayout ll_bodyer;
	/***
	 * 产品图片展示控件
	 */
	private ImageView iv_photo;
	/***
	 * tv_name：产品名称 tv_company：产品所属公司
	 */
	private TextView tv_name, tv_company;
	/***
	 * tv_collect：收藏 tv_share：分享
	 */
	private TextView tv_collect, tv_share;
	/***
	 * tv_offer：供应商 
	 */
	private TextView tv_offer;
	/***
	 * 详情内容
	 */
	private WebView mWebView;
	/***
	 * 应用案例
	 */
	private MyListView listView;
	/***
	 * edt_phone：手机号输入框 edt_context：资讯内容输入框
	 */
	private EditText edt_phone, edt_context;
	/***
	 * 咨询按钮
	 */
	private Button btn_submit;

	/***
	 * 用来判断是否已收藏
	 */
	private boolean IS_COLLECT = false;
	/***
	 * id
	 */
	private String id;
	/***
	 * 农资展厅产品实体类
	 */
	private ExhibitionUnit unit = null;

	/***
	 * 存储数据的sp
	 */
	private sPreferences iSPreferences;
	/***
	 * 用来获取屏幕大小
	 */
	private DisplayMetrics dm;
	/***
	 * 图片加载工具类
	 */
	private ImageLoader mImageLoader;

	/***
	 * 获取数据异步
	 */
	private GetDataTask getDataTask;
	/***
	 * 收藏操作异步
	 */
	private FocusTask focusTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exhibition_detail);
		iSPreferences = new sPreferences(this);
		mImageLoader = new ImageLoader(this);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		try {
			id = getIntent().getStringExtra("id");
			System.out.println("id: " + id);
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

		tv_title.setText(getString(R.string.tv_header_exhibition_detail));
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
		ll_bodyer = (LinearLayout) findViewById(R.id.ll_bodyer);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_company = (TextView) findViewById(R.id.tv_company);
		tv_collect = (TextView) findViewById(R.id.tv_collect);
		tv_share = (TextView) findViewById(R.id.tv_share);
		tv_offer = (TextView) findViewById(R.id.tv_offer);
		mWebView = (WebView) findViewById(R.id.web_context);
		listView = (MyListView) findViewById(R.id.listview);
		edt_phone = (EditText) findViewById(R.id.edt_phone);
		edt_context = (EditText) findViewById(R.id.edt_context);
		btn_submit = (Button) findViewById(R.id.btn_submit);

		tv_collect.setOnClickListener(this);
		tv_offer.setOnClickListener(this);
		tv_share.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		initWeb();
	}

	/***
	 * 初始化内容控件
	 */
	private void initWeb() {
		// TODO Auto-generated method stub
		String release = android.os.Build.VERSION.RELEASE;
		release = release.substring(0, 3);
		if ("4.4".equals(release)) {
			mWebView.setWebViewClient(new MyWebViewClient());
		} else {
			mWebView.setVisibility(View.VISIBLE);
			WebSettings ws = mWebView.getSettings();
			ws.setJavaScriptEnabled(true);
			ws.setAllowFileAccess(true);
			ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			ws.setDefaultTextEncodingName("utf-8");
			ws.setTextSize(TextSize.NORMAL);
			ws.setAppCacheEnabled(false);
			ws.setDomStorageEnabled(true);
			if (android.os.Build.VERSION.SDK_INT >= 8) {
				ws.setPluginState(PluginState.ON);
			}
			ws.setRenderPriority(RenderPriority.HIGH);
			mWebView.setWebViewClient(new WebViewClientDemo());
			mWebView.setWebChromeClient(new WebViewChromeClientDemo());
		}
	}

	private class MyWebViewClient extends WebViewClient {

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
		}
	}

	private class WebViewClientDemo extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return false;
		}
	}

	private class WebViewChromeClientDemo extends WebChromeClient {
		public void onProgressChanged(WebView view, int newProgress) {
		}

		public void onReceivedTitle(WebView view, String title) {
		}

		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			return super.onJsAlert(view, url, message, result);
		}

		@Override
		public boolean onJsPrompt(WebView view, String url, String message,
				String defaultValue, JsPromptResult result) {
			return super.onJsPrompt(view, url, message, defaultValue, result);
		}

		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				JsResult result) {
			return super.onJsConfirm(view, url, message, result);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_collect:
			IS_COLLECT = !IS_COLLECT;
			focus();
			break;
		case R.id.tv_offer:
			Bundle bundle = new Bundle();
			bundle.putString("company", unit.getCompany());
			bundle.putString("id", unit.getCompanyId());
			new IntentToOther(ExhibitionDetailActivity.this, 
					ExhibitionCompanyActivity.class, bundle);
			break;
		case R.id.tv_share:

			break;
		case R.id.btn_submit:

			break;
		default:
			break;
		}
	}

	/***
	 * 收藏、取消收藏操作
	 */
	private void focus() {
		if (comFunction.isWiFi_3G(this)) {
			if (focusTask == null) {
				focusTask = new FocusTask();
				if (IS_COLLECT) {
					focusTask.execute("add_attention_product_my");
				} else {
					focusTask.execute("cancel_attention_product_my");
				}
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}

	/***
	 * 控件赋值
	 */
	@SuppressWarnings("rawtypes")
	private void initData() {
		// TODO Auto-generated method stub
		LayoutParams layoutParams = new LayoutParams(dm.widthPixels / 21 * 8,
				dm.widthPixels / 21 * 8);
		layoutParams.setMargins(10, 10, 0, 0);
		iv_photo.setLayoutParams(layoutParams);
		mImageLoader.DisplayImage(unit.getThumb(), iv_photo, false);
		tv_name.setText(unit.getTitle());
		tv_company.setText(unit.getCompany());
		tv_offer.setText(getString(R.string.tv_exhibition_offer).replace(
				"$message$", unit.getCompany()));
		mWebView.loadDataWithBaseURL("", unit.getIntroduce(),
				"text/html", "utf-8", null);
		ExperDetailListAdapter adapter = new ExperDetailListAdapter<String>(
				this, unit.getCaselist());
		listView.setAdapter(adapter);
		if (IS_COLLECT) {
			tv_collect.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.icon_exper_technology_collect_hover, 0, 0, 0);
			tv_collect.setTextColor(getResources().getColor(R.color.color_orange));
		} else {
			tv_collect.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.icon_exper_technology_collect, 0, 0, 0);
			tv_collect.setTextColor(getResources().getColor(R.color.gray));
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
		/***
		 * 等待进度框
		 */
		private ProgressDialog pd;
		private JSONObject jobj;
		private JSONObject jobj_data;
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
		 * 用来判断是否已经收藏
		 */
		private String state;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ll_bodyer.setVisibility(View.GONE);
			pd = new ProgressDialog(ExhibitionDetailActivity.this);
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.setMessage(getString(R.string.pd_data_link));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("Id", id));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer(
					"get_shop_product_detail", paramsList,
					ExhibitionDetailActivity.this);
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
					state = jobj_data.getString("status");
					unit = new ExhibitionUnit();
					unit.setId(jobj_data.getString("id"));
					unit.setTitle(jobj_data.getString("title"));
					unit.setCompany(jobj_data.getString("companyname"));
					unit.setIntroduce(jobj_data.getString("introduce"));
					unit.setThumb(jobj_data.getString("thumb"));
					unit.setCompanyId(jobj_data.getString("userid"));
					JSONArray jarry_pic = new JSONArray(
							jobj_data.getString("pic_urls"));
					List<String> pic_urls = new ArrayList<String>();
					for (int i = 0; i < jarry_pic.length(); i++) {
						pic_urls.add(jarry_pic.getJSONObject(i)
								.getString("url"));
					}
					unit.setPic_urls(pic_urls);
					JSONArray jarry_case = new JSONArray(
							jobj_data.getString("caselist"));
					List<String> caselist = new ArrayList<String>();
					for (int i = 0; i < jarry_case.length(); i++) {
						caselist.add(jarry_case.getJSONObject(i).getString(
								"title"));
					}
					unit.setCaselist(caselist);
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
					if (state.equals("1")) {
						IS_COLLECT = true;
					} else {
						IS_COLLECT = false;
					}
					initData();
					ll_bodyer.setVisibility(View.VISIBLE);
				} else {
					comFunction
							.toastMsg(message, ExhibitionDetailActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ExhibitionDetailActivity.this);
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
			pd = new ProgressDialog(ExhibitionDetailActivity.this);
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.setMessage(getString(R.string.pd_data_upload));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("productId", id));
			paramsList.add(new BasicNameValuePair("userId", iSPreferences
					.getSp().getString("memberId", "")));
		}

		@Override
		protected Void doInBackground(String... parms) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer(parms[0],
					paramsList, ExhibitionDetailActivity.this);
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
							R.drawable.icon_exper_technology_collect_hover, 0,
							0, 0);
					tv_collect.setTextColor(getResources().getColor(
							R.color.color_orange));
				} else {
					tv_collect.setCompoundDrawablesWithIntrinsicBounds(
							R.drawable.icon_exper_technology_collect, 0, 0, 0);
					tv_collect.setTextColor(getResources().getColor(
							R.color.gray));
				}
				comFunction.toastMsg(message, ExhibitionDetailActivity.this);
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ExhibitionDetailActivity.this);
			}
			super.onPostExecute(result);
		}

	}

}

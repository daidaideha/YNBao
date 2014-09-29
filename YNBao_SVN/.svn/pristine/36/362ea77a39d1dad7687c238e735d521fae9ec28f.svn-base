package com.innouni.yinongbao.activity.exhibition;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.exhibition.ExhibitionTechnologyUnit;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 农资展厅植保技术、企业新闻详情页面
 * @author LinYuLing
 * @UpdateDate 2014-09-29
 */
@SuppressLint("SetJavaScriptEnabled")
@SuppressWarnings("deprecation")
public class ExhibitionWebDetailActivity extends Activity {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * tv_detail_title：内容标题控件 tv_detail_date：内容时间控件
	 */
	private TextView tv_detail_title, tv_detail_date;
	/***
	 * 内容详情控件
	 */
	private WebView web_content;

	/***
	 * id
	 */
	private String id;
	/***
	 * 技术实体类
	 */
	private ExhibitionTechnologyUnit unit;
	/***
	 * 用来判断是新闻详情还是植保技术详情
	 * 0：植保技术详情
	 * 1：新闻详情
	 */
	private int type = 0;

	/***
	 * 获取数据异步
	 */
	private GetDataTask getDataTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exhibition_technology_detail);

		try {
			id = getIntent().getStringExtra("id");
			type = getIntent().getIntExtra("type", 0);
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

		if (type == 0) {
			tv_title.setText(getString(R.string.tv_header_exhibition_technology_detail));
		} else {
			tv_title.setText(getString(R.string.tv_header_exhibition_news_detail));
		}
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
		tv_detail_title = (TextView) findViewById(R.id.tv_title);
		tv_detail_date = (TextView) findViewById(R.id.tv_date);
		web_content = (WebView) findViewById(R.id.web_content);
		initWeb();
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

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(ExhibitionWebDetailActivity.this);
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
			String requery = "";
			if (type == 0) {
				requery = comFunction.getDataFromServer("get_shop_company_tech_detail", 
						paramsList, ExhibitionWebDetailActivity.this);
			} else {
				requery = comFunction.getDataFromServer("get_shop_company_news_detail", 
						paramsList, ExhibitionWebDetailActivity.this);
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
					jobj_data = new JSONObject(jobj.getString("data"));
					if (jobj_data == null) {
						return null;
					}
					unit = new ExhibitionTechnologyUnit();
					unit.setTitle(jobj_data.getString("title"));
					unit.setInputtime(jobj_data.getString("inputtime"));
					unit.setContent(jobj_data.getString("content"));
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
					tv_detail_title.setText(unit.getTitle());
					tv_detail_date.setText(unit.getInputtime());
					web_content.loadDataWithBaseURL("", unit.getContent(),
							"text/html", "utf-8", null);
				} else {
					comFunction.toastMsg(message,
							ExhibitionWebDetailActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ExhibitionWebDetailActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}
	}

	/***
	 * 初始化内容控件
	 */
	private void initWeb() {
		// TODO Auto-generated method stub
		String release = android.os.Build.VERSION.RELEASE;
		release = release.substring(0, 3);
		if ("4.4".equals(release)) {
			web_content.setWebViewClient(new MyWebViewClient());
		} else {
			web_content.setVisibility(View.VISIBLE);
			WebSettings ws = web_content.getSettings();
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
			web_content.setWebViewClient(new WebViewClientDemo());
			web_content.setWebChromeClient(new WebViewChromeClientDemo());
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
}

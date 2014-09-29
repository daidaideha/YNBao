package com.innouni.yinongbao.activity.exhibition;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.unit.exhibition.ExhibitionCompanyUnit;

/***
 * 农资展厅企业详情界面
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-29 15:34:42
 */
@SuppressLint("SetJavaScriptEnabled")
@SuppressWarnings("deprecation")
public class ExhibitionCompanyDetailActivity extends Activity {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
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
	 * 内容详情控件
	 */
	private WebView web_content;

	/***
	 * 图片加载工具类
	 */
	private ImageLoader mImageLoader;

	/***
	 * 技术实体类
	 */
	private ExhibitionCompanyUnit unit;
	/***
	 * 用来判断是新闻详情还是植保技术详情 0：植保技术详情 1：新闻详情
	 */
	private int type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exhibition_company_detail);
		mImageLoader = new ImageLoader(this);
		
		try {
			unit = (ExhibitionCompanyUnit) getIntent().getSerializableExtra("unit");
		} catch (Exception e) {
			// TODO: handle exception
		}


		initHeader();
		initBodyer();
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
		tv_company = (TextView) findViewById(R.id.tv_company);
		iv_logo = (ImageView) findViewById(R.id.iv_logo);
		iv_logo_level = (ImageView) findViewById(R.id.iv_logo_level);
		web_content = (WebView) findViewById(R.id.web_content);
		
		tv_company.setText(unit.getCompanyname());
		mImageLoader.DisplayImage(unit.getLogo(), iv_logo, false);
		mImageLoader.DisplayImage(unit.getIcon(), iv_logo_level, false);
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
			web_content.loadDataWithBaseURL("", unit.getIntroduce(),
					"text/html", "utf-8", null);
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

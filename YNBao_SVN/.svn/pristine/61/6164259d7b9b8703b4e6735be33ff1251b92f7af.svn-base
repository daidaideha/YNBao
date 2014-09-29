package com.innouni.yinongbao.activity;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;

/***
 * 广告详情界面
 * @author LinYuLing
 * @UpdateDate 2014-09-28
 */
@SuppressWarnings("deprecation")
@SuppressLint("SetJavaScriptEnabled")
public class AdDetailActivity extends Activity {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 内容详情展示控件
	 */
	private WebView mWebView;
	
	/***
	 * 公司名称
	 */
	private String title;
	/***
	 * 网页链接地址
	 */
	private String linkUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		try {
			title = getIntent().getStringExtra("title");
			linkUrl = getIntent().getStringExtra("linkurl");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		initHeader();
		initWeb();
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(title);
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/***
	 * 初始化内容控件
	 */
	private void initWeb() {
		// TODO Auto-generated method stub
		mWebView = (WebView) findViewById(R.id.webview);
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
		mWebView.loadUrl(linkUrl);
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

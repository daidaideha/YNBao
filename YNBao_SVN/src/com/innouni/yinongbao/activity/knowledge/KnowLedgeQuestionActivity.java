package com.innouni.yinongbao.activity.knowledge;

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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.KnowledgeQuestionAdapter;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.knowledge.KnowledgeAnswersUnit;
import com.innouni.yinongbao.view.PullToRefreshView;
import com.innouni.yinongbao.view.PullToRefreshView.OnHeaderRefreshListener;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 知识库回答问题列表
 * @author LinYuLing
 * @UpdateDate 2014-09-27
 */
public class KnowLedgeQuestionActivity extends Activity implements OnHeaderRefreshListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 刷新控件
	 */
	private PullToRefreshView pullview;
	/***
	 * 展示列表
	 */
	private ListView mListView;
	/***
	 * 列表尾部控件
	 */
	private View footView;
	/***
	 * 加载更多控件
	 */
	private TextView tv_more;

	/***
	 * 数据列表适配器
	 */
	private KnowledgeQuestionAdapter adapter;
	/***
	 * 等待进度框
	 */
	private ProgressDialog pd;
	/***
	 * 用来获取屏幕大小
	 */
	private DisplayMetrics dm;

	/***
	 * 初始化列表数据列表
	 */
	private List<KnowledgeAnswersUnit> list_bak;
	/***
	 * 专家解答列表数据列表
	 */
	private List<KnowledgeAnswersUnit> list_question;
	/***
	 * 网友评论列表数据列表
	 */
	private List<KnowledgeAnswersUnit> list_comment;
	/***
	 * 用来判断是专家解答列表还是网游评论列表
	 * 0：表示专家解答列表
	 * 1：表示网游评论列表
	 */
	private int type = 0;
	/***
	 * 页数
	 */
	private int pageCount = 1;
	/***
	 * 用来判断是筛选加载还是普通加载 0：表示普通 1：表示筛选 和 加载更多
	 */
	private int getType = 0;
	/***
	 * id
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
		setContentView(R.layout.activity_knowledge_list);
		pd = new ProgressDialog(KnowLedgeQuestionActivity.this);
		list_bak = new ArrayList<KnowledgeAnswersUnit>();
		list_question = new ArrayList<KnowledgeAnswersUnit>();
		list_comment = new ArrayList<KnowledgeAnswersUnit>();
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		try {
			type = getIntent().getIntExtra("type", 0);
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

		if (type == 0) {
			tv_title.setText(getString(R.string.label_exper_detail_question));
		} else {
			tv_title.setText(getString(R.string.label_exper_detail_technology));
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
	 * 实例化控件
	 */
	private void initBodyer() {
		pullview = (PullToRefreshView) findViewById(R.id.pullview);
		mListView = (ListView) findViewById(R.id.listview);
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
		adapter = new KnowledgeQuestionAdapter(this, list_bak, type, dm.widthPixels / 5);
		mListView.setAdapter(adapter);
		
		pullview.setOnHeaderRefreshListener(this);
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
	private void initData(String count) {
		adapter.clearList();
		if (type == 0) {
			adapter.setList(list_question);
		} else {
			adapter.setList(list_comment);
		}
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
		pullview.onHeaderRefreshComplete();
		mListView.setVisibility(View.VISIBLE);
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
		private JSONArray jArray_comment;
		private JSONArray jArray_question;
		private JSONArray jArray_url;
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
		 * 当前页数据量
		 */
		private String count;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.setMessage(getString(R.string.pd_data_link));
			if (pageCount == 1) {
				list_comment.clear();
				list_question.clear();
				mListView.setVisibility(View.GONE);
			}
			if (getType == 1) {
				pd.show();
			}
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("tId", id));
			paramsList.add(new BasicNameValuePair("page", pageCount + ""));
		}

		@Override
		protected Void doInBackground(Void... pragm) {
			// TODO Auto-generated method stub
			String requery = null;
			if (type == 0) {
				requery = comFunction.getDataFromServer("get_wen_question_answers", 
						paramsList, KnowLedgeQuestionActivity.this);
			} else {
				requery = comFunction.getDataFromServer("get_wen_question_comments", 
						paramsList, KnowLedgeQuestionActivity.this);
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
					if (jobj == null) {
						return null;
					}
					count = jobj_data.getString("count");
					if (type == 0) {
						jArray_question = new JSONArray(jobj_data.getString("list"));
						for (int i = 0; i < jArray_question.length(); i++) {
							KnowledgeAnswersUnit answer = new KnowledgeAnswersUnit();
							answer.setId(jArray_question.getJSONObject(i).getString("id"));
							answer.setAuthor(jArray_question.getJSONObject(i)
									.getString("author"));
							answer.setAddtime(jArray_question.getJSONObject(i)
									.getString("addtime"));
							answer.setContent(jArray_question.getJSONObject(i)
									.getString("content"));
//							if (type == 1) {
//								answer.setIsbest(jArray_question.getJSONObject(i)
//										.getString("isbest"));
//							}
							answer.setName(jArray_question.getJSONObject(i)
									.getString("name"));
							answer.setAvatar(jArray_question.getJSONObject(i)
									.getString("avatar"));
							answer.setGroupname(jArray_question.getJSONObject(i)
									.getString("groupname"));
							jArray_url = new JSONArray(jArray_question.getJSONObject(i)
									.getString("urls"));
							List<String> urls = new ArrayList<String>();
							for (int j = 0; j < jArray_url.length(); j++) {
								urls.add(jArray_url.getString(j));
							}
							answer.setUrls(urls);
							list_question.add(answer);
						}
					} else {
						jArray_comment = new JSONArray(jobj_data.getString("list"));
						for (int i = 0; i < jArray_comment.length(); i++) {
							KnowledgeAnswersUnit comment = new KnowledgeAnswersUnit();
							comment.setId(jArray_comment.getJSONObject(i)
									.getString("id"));
							comment.setAuthor(jArray_comment.getJSONObject(i)
									.getString("author"));
							comment.setAddtime(jArray_comment.getJSONObject(i)
									.getString("addtime"));
							comment.setContent(jArray_comment.getJSONObject(i)
									.getString("content"));
							comment.setIsbest(jArray_comment.getJSONObject(i)
									.getString("isbest"));
							comment.setName(jArray_comment.getJSONObject(i)
									.getString("name"));
							comment.setAvatar(jArray_comment.getJSONObject(i)
									.getString("avatar"));
							comment.setGroupname(jArray_comment.getJSONObject(i)
									.getString("groupname"));
//							jArray_url = new JSONArray(jArray_comment.getJSONObject(i)
//									.getString("urls"));
							List<String> urls = new ArrayList<String>();
//							for (int j = 0; j < jArray_url.length(); j++) {
//								urls.add(jArray_url.getString(j));
//							}
							comment.setUrls(urls);
							list_comment.add(comment);
						}
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
					initData(count);
				} else {
					comFunction.toastMsg(message, KnowLedgeQuestionActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						KnowLedgeQuestionActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}
	}
}

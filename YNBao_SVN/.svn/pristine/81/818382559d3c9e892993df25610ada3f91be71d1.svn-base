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
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.ExperDetailListAdapter;
import com.innouni.yinongbao.adapter.GVPhotoAdapter;
import com.innouni.yinongbao.adapter.KnowledgeQuestionAdapter;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.exper.ExperThreadUnit;
import com.innouni.yinongbao.unit.knowledge.KnowledgeAnswersUnit;
import com.innouni.yinongbao.unit.knowledge.KnowledgeUnit;
import com.innouni.yinongbao.view.MyGridView;
import com.innouni.yinongbao.view.MyListView;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.comFunction;
import com.innouni.yinongbao.widget.sPreferences;

/***
 * 知识库详情界面
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-26
 */
public class KnowledgeDetailActivity extends Activity implements
		OnClickListener, OnItemClickListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 内容总体布局控件
	 */
	private LinearLayout ll_bodyer;
	/***
	 * tv_detail_title：问题标题 tv_detail_tag：标签 tv_detail_name：用户名
	 * tv_detail_date：时间 tv_detail_content：问题内容
	 */
	private TextView tv_detail_title, tv_detail_tag, tv_detail_name,
			tv_detail_date, tv_detail_content;
	/***
	 * 问题图片展示控件
	 */
	private MyGridView mGridView;
	/***
	 * 浏览量
	 */
	private TextView tv_look;
	/***
	 * 评论数量
	 */
	private TextView tv_comment;
	/***
	 * 收藏
	 */
	private RelativeLayout rl_collect;
	/***
	 * 收藏显示控件
	 */
	private TextView tv_collect;
	/***
	 * 分享
	 */
	private RelativeLayout rl_share;
	/***
	 * rl_question：专家解答 rl_comment：网友评论
	 */
	private RelativeLayout rl_question, rl_comment;
	/***
	 * 相关问题
	 */
	private TextView tv_relate;
	/***
	 * tv_question_number：专家解答数量 tv_comment_number：网友评论数量
	 */
	private TextView tv_question_number, tv_comment_number;
	/***
	 * 专家解答列表控件 网友评论列表控件 相关问题列表控件
	 */
	private MyListView qListView, cListView, rListView;

	/***
	 * 问题id
	 */
	private String id;
	/***
	 * 知识库实体类
	 */
	private KnowledgeUnit unit;
	/***
	 * 用来判断是否已收藏
	 */
	private boolean IS_COLLECT = false;

	/***
	 * 图片展示适配器
	 */
	private GVPhotoAdapter adapter;
	/***
	 * 存储数据的sp
	 */
	private sPreferences iSPreferences;
	/***
	 * 用来获取屏幕大小
	 */
	private DisplayMetrics dm;
	
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
		setContentView(R.layout.activity_knowledge_detail);
		iSPreferences = new sPreferences(this);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		try {
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

		tv_title.setText(getString(R.string.tv_header_konwledge_detail));
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/***
	 * 基本控件初始化
	 */
	private void initBodyer() {
		// TODO Auto-generated method stub
		ll_bodyer = (LinearLayout) findViewById(R.id.ll_bodyer);
		tv_detail_title = (TextView) findViewById(R.id.tv_detail_title);
		tv_detail_tag = (TextView) findViewById(R.id.tv_detail_type);
		tv_detail_name = (TextView) findViewById(R.id.tv_detail_name);
		tv_detail_date = (TextView) findViewById(R.id.tv_detail_date);
		tv_detail_content = (TextView) findViewById(R.id.tv_detail_content);
		mGridView = (MyGridView) findViewById(R.id.gv_knowledge);
		
		tv_look = (TextView) findViewById(R.id.tv_look);
		tv_comment = (TextView) findViewById(R.id.tv_comment);
		tv_collect = (TextView) findViewById(R.id.tv_collect);
		rl_collect = (RelativeLayout) findViewById(R.id.rl_collect);
		rl_share = (RelativeLayout) findViewById(R.id.rl_share);
		
		rl_question = (RelativeLayout) findViewById(R.id.rl_question);
		rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
		tv_relate = (TextView) findViewById(R.id.tv_relate);
		tv_question_number = (TextView) findViewById(R.id.tv_question_number);
		tv_comment_number = (TextView) findViewById(R.id.tv_comment_number);
		
		qListView = (MyListView) findViewById(R.id.list_question);
		cListView = (MyListView) findViewById(R.id.list_comment);
		rListView = (MyListView) findViewById(R.id.list_relate);

		rl_collect.setOnClickListener(this);
		rl_share.setOnClickListener(this);
		rl_question.setOnClickListener(this);
		rl_comment.setOnClickListener(this);
		tv_relate.setOnClickListener(this);
		
		mGridView.setOnItemClickListener(this);
//		qListView.setOnItemClickListener(this);
//		cListView.setOnItemClickListener(this);
//		rListView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.rl_collect:
			IS_COLLECT = !IS_COLLECT;
			focus();
			break;
		case R.id.rl_share:
			
			break;
		case R.id.rl_question:
			bundle.putString("id", id);
			bundle.putInt("type", 0);
			new IntentToOther(this, KnowLedgeQuestionActivity.class, bundle);
			break;
		case R.id.rl_comment:
			bundle.putString("id", id);
			bundle.putInt("type", 1);
			new IntentToOther(this, KnowLedgeQuestionActivity.class, bundle);
			
			break;
		case R.id.tv_relate:
			
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.gv_knowledge:
//			
//			break;
//		case R.id.list_question:
//			
//			break;
//		case R.id.list_comment:
//			
//			break;
//		case R.id.list_relate:
//			
//			break;
//		default:
//			break;
//		}
	}

	/***
	 * 控件数据赋值
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initData() {
		tv_detail_title.setText(unit.getTitle());
		tv_detail_tag.setText(Html.fromHtml(getString(
				R.string.label_knowledge_tag).replace("$name$",
				"<font color=\"#1fb7f6\">" + unit.getName() + "</font>")
				.replace("$type$", unit.getType())));
		tv_detail_name.setText(unit.getAuthor());
		tv_detail_date.setText(unit.getAddtime());
		tv_detail_content.setText(unit.getContent());
		adapter = new GVPhotoAdapter(this, unit.getUrls(), dm.widthPixels / 6);
		mGridView.setAdapter(adapter);
		tv_look.setText(getString(R.string.tv_exper_teachnology_look).replace(
				"$number$", unit.getViews()));
		tv_comment.setText(getString(R.string.tv_exper_teachnology_comment)
					.replace("$number$", (Integer.valueOf(unit.getReplies())
							+ Integer.valueOf(unit.getComments_count()) + "")));
		tv_question_number.setText(unit.getReplies());
		tv_comment_number.setText(unit.getComments_count());
		if (IS_COLLECT) {
			tv_collect.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.icon_exper_technology_collect_hover, 0, 0, 0);
			tv_collect.setTextColor(getResources().getColor(R.color.color_orange));
		} else {
			tv_collect.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.icon_exper_technology_collect, 0, 0, 0);
			tv_collect.setTextColor(getResources().getColor(R.color.gray));
		}
		if (unit.getAnswers().size() == 0) {
			rl_question.setVisibility(View.GONE);
		} else {
			KnowledgeQuestionAdapter qAdatper = new KnowledgeQuestionAdapter(this,
					unit.getAnswers(), 0, dm.widthPixels / 5);
			qListView.setAdapter(qAdatper);
		}
		if (unit.getComments().size() == 0) {
			rl_comment.setVisibility(View.GONE);
		} else {
			KnowledgeQuestionAdapter tAdatper = new KnowledgeQuestionAdapter(this,
					unit.getComments(), 1, dm.widthPixels / 5);
			cListView.setAdapter(tAdatper);
		}
		if (unit.getRelations().size() == 0) {
			tv_relate.setVisibility(View.GONE);
		} else {
			ExperDetailListAdapter rAdatper = new ExperDetailListAdapter(this,
					unit.getRelations());
			rListView.setAdapter(rAdatper);
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
	 * 收藏、取消收藏操作
	 */
	private void focus() {
		if (comFunction.isWiFi_3G(this)) {
			if (focusTask == null) {
				focusTask = new FocusTask();
				if (IS_COLLECT) {
					focusTask.execute("add_attention_wen_question");
				} else {
					focusTask.execute("cancel_attention_wen_question");
				}
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
		private JSONArray jArray_url;
		private JSONArray jArray_answers;
		private JSONArray jArray_comment;
		private JSONArray jArray_relate;
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
			pd = new ProgressDialog(KnowledgeDetailActivity.this);
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.setMessage(getString(R.string.pd_data_link));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("tId", id));
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer(
					"get_wen_question_detail", paramsList,
					KnowledgeDetailActivity.this);
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
					unit = new KnowledgeUnit();
					unit.setTid(jobj_data.getString("id"));
					unit.setTitle(jobj_data.getString("title"));
					unit.setAuthor(jobj_data.getString("author"));
					unit.setFid(jobj_data.getString("fid"));
					unit.setType(jobj_data.getString("type"));
					unit.setAddtime(jobj_data.getString("addtime"));
					unit.setViews(jobj_data.getString("views"));
					unit.setReplies(jobj_data.getString("replies"));
					unit.setName(jobj_data.getString("name"));
					unit.setContent(jobj_data.getString("content"));
					unit.setComments_count(jobj_data
							.getString("comments_count"));
					jArray_url = new JSONArray(jobj_data.getString("urls"));
					List<String> urls = new ArrayList<String>();
					for (int i = 0; i < jArray_url.length(); i++) {
						urls.add(jArray_url.getString(i));
					}
					unit.setUrls(urls);
					jArray_answers = new JSONArray(
							jobj_data.getString("answers"));
					List<KnowledgeAnswersUnit> answers = new ArrayList<KnowledgeAnswersUnit>();
					for (int i = 0; i < jArray_answers.length(); i++) {
						KnowledgeAnswersUnit answer = new KnowledgeAnswersUnit();
						answer.setId(jArray_answers.getJSONObject(i).getString("id"));
						answer.setAuthor(jArray_answers.getJSONObject(i)
								.getString("author"));
						answer.setAddtime(jArray_answers.getJSONObject(i)
								.getString("addtime"));
						answer.setContent(jArray_answers.getJSONObject(i)
								.getString("content"));
//						answer.setIsbest(jArray_answers.getJSONObject(i)
//								.getString("isbest"));
						answer.setName(jArray_answers.getJSONObject(i)
								.getString("name"));
						answer.setAvatar(jArray_answers.getJSONObject(i)
								.getString("avatar"));
						answer.setGroupname(jArray_answers.getJSONObject(i)
								.getString("groupname"));
						JSONArray jArray_urls = new JSONArray(jArray_answers.getJSONObject(i)
								.getString("urls"));
						List<String> aurls = new ArrayList<String>();
						for (int j = 0; j < jArray_urls.length(); j++) {
							aurls.add(jArray_urls.getString(j));
						}
						answer.setUrls(aurls);
						answers.add(answer);
					}
					unit.setAnswers(answers);
					jArray_comment = new JSONArray(
							jobj_data.getString("comments"));
					List<KnowledgeAnswersUnit> list = new ArrayList<KnowledgeAnswersUnit>();
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
//						JSONArray jArray_urls = new JSONArray(jArray_comment.getJSONObject(i)
//								.getString("urls"));
						List<String> aurls = new ArrayList<String>();
//						for (int j = 0; j < jArray_urls.length(); j++) {
//							aurls.add(jArray_urls.getString(j));
//						}
						comment.setUrls(aurls);
						list.add(comment);
					}
					unit.setComments(list);
					jArray_relate = new JSONArray(jobj_data.getString("relations"));
					List<ExperThreadUnit> list_relate = new ArrayList<ExperThreadUnit>();
					for (int j = 0; j < jArray_relate.length(); j++) {
						ExperThreadUnit relateUnit = new ExperThreadUnit();
						relateUnit.setId(jArray_relate.getJSONObject(j).getString("tid"));
						relateUnit.setSubject(jArray_relate.getJSONObject(j).getString("subject"));
						relateUnit.setAddtime(jArray_relate.getJSONObject(j).getString("addtime"));
						list_relate.add(relateUnit);
					}
					unit.setRelations(list_relate);
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
					comFunction.toastMsg(message, KnowledgeDetailActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						KnowledgeDetailActivity.this);
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
//		/***
//		 * 服务器返回类型值 200：成功
//		 */
//		private String code;
		/***
		 * 服务器返回提示内容值
		 */
		private String message = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(KnowledgeDetailActivity.this);
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.setMessage(getString(R.string.pd_data_upload));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("tId", id));
			paramsList.add(new BasicNameValuePair("userId", iSPreferences.getSp()
					.getString("memberId", "")));
			paramsList.add(new BasicNameValuePair("userName", iSPreferences.getSp()
					.getString("username", "")));
		}

		@Override
		protected Void doInBackground(String... parms) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer(parms[0], paramsList,
					KnowledgeDetailActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
//				code = jobj.getString("code");
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
					tv_collect.setTextColor(getResources().getColor(R.color.color_orange));
				} else {
					tv_collect.setCompoundDrawablesWithIntrinsicBounds(
							R.drawable.icon_exper_technology_collect, 0, 0, 0);
					tv_collect.setTextColor(getResources().getColor(R.color.gray));
				}
				comFunction.toastMsg(message, KnowledgeDetailActivity.this);
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						KnowledgeDetailActivity.this);
			}
			super.onPostExecute(result);
		}
	}
}

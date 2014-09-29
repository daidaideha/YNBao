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
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.ExperTechnologyCommentAdapter;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.exper.ExperTechnologyCommentUnit;
import com.innouni.yinongbao.unit.exper.ExperTechnologyUnit;
import com.innouni.yinongbao.view.MyListView;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 个人文章界面
 * 
 * @author LinYuLing
 * @UpdateDate 2014-09-25
 */
public class ExperTechnologyDetailActivity extends Activity implements
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
	 * 内容总体布局控件
	 */
	private LinearLayout ll_bodyer;
	/***
	 * 文章标题
	 */
	private TextView tv_detail_title;
	/***
	 * 文章添加时间
	 */
	private TextView tv_time;
	/***
	 * 文章内容
	 */
	private TextView tv_message;
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
	 * 分享
	 */
	private RelativeLayout rl_share;
	/***
	 * 评论列表
	 */
	private MyListView listView;
	/***
	 * 评论输入框
	 */
	private EditText mEditText;
	/***
	 * 提交按钮
	 */
	private Button mButton;
	
	/***
	 * 评论列表适配器
	 */
	private ExperTechnologyCommentAdapter adapter;

	/***
	 * 用来获取屏幕大小
	 */
	private DisplayMetrics dm;
	
	/***
	 * 技术文章id
	 */
	private String id;
	/***
	 * 实体类
	 */
	private ExperTechnologyUnit unit;
	/***
	 * 初始化评论数据列表
	 */
	private List<ExperTechnologyCommentUnit> list_bak;

	/***
	 * 获取数据异步
	 */
	private GetDataTask getDataTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exper_technology_detail);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		list_bak = new ArrayList<ExperTechnologyCommentUnit>();

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

		tv_title.setText(getString(R.string.tv_header_exper_teachnology_detail));
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
		ll_bodyer = (LinearLayout) findViewById(R.id.ll_bodyer);
		tv_detail_title = (TextView) findViewById(R.id.tv_detail_title);
		tv_time = (TextView) findViewById(R.id.tv_detail_date);
		tv_message = (TextView) findViewById(R.id.tv_detail_message);
		tv_look = (TextView) findViewById(R.id.tv_look);
		tv_comment = (TextView) findViewById(R.id.tv_comment);
		rl_collect = (RelativeLayout) findViewById(R.id.rl_collect);
		rl_share = (RelativeLayout) findViewById(R.id.rl_share);
		listView = (MyListView) findViewById(R.id.list_comment);
		mEditText = (EditText) findViewById(R.id.edt_comment);
		mButton = (Button) findViewById(R.id.btn_submit);
		adapter = new ExperTechnologyCommentAdapter(this, list_bak, dm.widthPixels);
		listView.setAdapter(adapter);

		rl_collect.setOnClickListener(this);
		rl_share.setOnClickListener(this);
		mButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_collect:
			
			break;
		case R.id.rl_share:
			
			break;
		case R.id.btn_submit:
			
			break;
		default:
			break;
		}
	}
	
	/***
	 * 设置控件数据
	 */
	private void initData() {
		tv_detail_title.setText(unit.getTitle());
		tv_time.setText(unit.getAddtime());
		tv_message.setText(Html.fromHtml(unit.getMessage()));
		tv_look.setText(getString(R.string.tv_exper_teachnology_look)
				.replace("$number$", unit.getViewnum()));
		tv_comment.setText(getString(R.string.tv_exper_teachnology_comment)
				.replace("$number$", unit.getReplynum()));
		adapter.clearList();
		adapter.setList(unit.getComments());
		adapter.notifyDataSetChanged();
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
		private JSONArray jArray_comment;
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
			ll_bodyer.setVisibility(View.GONE);
			pd = new ProgressDialog(ExperTechnologyDetailActivity.this);
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
					"get_wen_expert_blog_detail", paramsList,
					ExperTechnologyDetailActivity.this);
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
					unit = new ExperTechnologyUnit();
					unit.setId(jobj_data.getString("id"));
					unit.setTitle(jobj_data.getString("title"));
					unit.setAddtime(jobj_data.getString("addtime"));
					unit.setMessage(jobj_data.getString("message"));
					unit.setViewnum(jobj_data.getString("viewnum"));
					unit.setReplynum(jobj_data.getString("replynum"));
					jArray_comment = new JSONArray(jobj_data.getString("comments"));
					List<ExperTechnologyCommentUnit> list = new ArrayList<ExperTechnologyCommentUnit>();
					for (int j = 0; j < jArray_comment.length(); j++) {
						ExperTechnologyCommentUnit commentUnit = new ExperTechnologyCommentUnit();
						commentUnit.setAuthorid(jArray_comment.getJSONObject(j)
								.getString("authorid"));
						commentUnit.setName(jArray_comment.getJSONObject(j)
								.getString("name"));
						commentUnit.setContent(jArray_comment.getJSONObject(j)
								.getString("content"));
						commentUnit.setPic_url(jArray_comment.getJSONObject(j)
								.getString("pic_url"));
						commentUnit.setAddtime(jArray_comment.getJSONObject(j)
								.getString("addtime"));
						list.add(commentUnit);
					}
					unit.setComments(list);;
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
					initData();
					ll_bodyer.setVisibility(View.VISIBLE);
				} else {
					comFunction.toastMsg(message, ExperTechnologyDetailActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ExperTechnologyDetailActivity.this);
			}
			if (pd.isShowing()) {
				pd.dismiss();
			}
			super.onPostExecute(result);
		}
	}
	
	

}

package com.innouni.yinongbao.activity.exhibition;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.fragment.ContextFragment;
import com.innouni.yinongbao.fragment.ExperFragment;
import com.innouni.yinongbao.fragment.ProductFragment;
import com.innouni.yinongbao.fragment.TechnologyFragment;
import com.innouni.yinongbao.unit.HttpCode;
import com.innouni.yinongbao.unit.exhibition.ExhibitionTypeCUnit;
import com.innouni.yinongbao.view.PopExhibitionCompany;
import com.innouni.yinongbao.widget.comFunction;

/***
 * 农资展厅企业主界面
 * @author LinYuLing
 * @UpdateDate 2014-09-28
 */
public class ExhibitionCompanyActivity extends FragmentActivity implements OnClickListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 搜索外层布局控件
	 */
	private RelativeLayout rl_search;
	/***
	 * 菜单按钮
	 */
	private Button btn_menu;
	/***
	 * 搜索框
	 */
	private EditText edt_search;
	/***
	 * 产品列表
	 * 植保技术
	 * 企业专家
	 * 企业动态
	 */
	private TextView tv_tab_product, tv_tab_technology, 
				tv_tab_exper, tv_tab_context;
	/***
	 * 首页界面
	 */
	private ProductFragment productFragment;
	/***
	 * 我的收藏界面
	 */
	private TechnologyFragment technologyFragment;
	/***
	 * 个人中心界面
	 */
	private ExperFragment experFragment;
	/***
	 * 设置界面
	 */
	private ContextFragment contextFragment;
	/***
	 * 分类弹出框
	 */
	private PopExhibitionCompany popType;
	
	/***
	 * Fragment管理器
	 */
	private FragmentManager fragmentManager;
	/***
	 * 用来获取屏幕大小
	 */
	private DisplayMetrics dm;

	/***
	 * 分类数据列表
	 */
	private List<ExhibitionTypeCUnit> list_type;
	/***
	 * 分类按钮列表
	 */
	private List<Button> listBtn;
	/***
	 * 企业名称
	 */
	private String title;
	/***
	 * 企业id
	 */
	private String id;
	/***
	 * 记录当前选中项
	 * 0：产品列表
	 * 1：保值技术
	 * 2：企业专家
	 * 3：企业动态
	 */
	private int curPosition;
	
	/***
	 * 获取分类数据异步
	 */
	private GetTypeTask getTypeTask;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_exhibition_company);
		fragmentManager = getSupportFragmentManager();
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		list_type = new ArrayList<ExhibitionTypeCUnit>();
		listBtn = new ArrayList<Button>();
		
		try {
			title = getIntent().getStringExtra("company");
			id = getIntent().getStringExtra("id");
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
	 * 初始化布局控件
	 */
	private void initBodyer() {
		rl_search = (RelativeLayout) findViewById(R.id.rl_search);
		btn_menu = (Button) findViewById(R.id.btn_menu);
		edt_search = (EditText) findViewById(R.id.edt_search);
		tv_tab_product = (TextView) findViewById(R.id.tv_product);
		tv_tab_technology = (TextView) findViewById(R.id.tv_technology);
		tv_tab_exper = (TextView) findViewById(R.id.tv_exper);
		tv_tab_context = (TextView) findViewById(R.id.tv_context);
		 
		edt_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		edt_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int actionId,
					KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					
				}
				return false;
			}
		});
		
		btn_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popType.showPopupWindow();
			}
		});
		tv_tab_product.setOnClickListener(this);
		tv_tab_technology.setOnClickListener(this);
		tv_tab_exper.setOnClickListener(this);
		tv_tab_context.setOnClickListener(this);
		tv_tab_product.performClick();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		selected(v.getId());
		switch (v.getId()) {
		case R.id.tv_product:
			curPosition = 0;
			getType();
			break;
		case R.id.tv_technology:
			curPosition = 1;
			getType();
			break;
		case R.id.tv_exper:
			curPosition = 2;
			break;
		case R.id.tv_context:
			curPosition = 3;
			break;
		default:
			break;
		}
		setTabSelection(v.getId());
	}

	/***
	 * 底部选项卡选择操作
	 * 
	 * @param id 选中项控件id
	 */
	private void setTabSelection(int id) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch (id) {
		case R.id.tv_product: //产品列表
			if (productFragment == null) {
				productFragment = new ProductFragment();
				transaction.add(R.id.ll_bodyer, productFragment);
			} else {
				transaction.show(productFragment);
			}
			break;
		case R.id.tv_technology: //植保技术
			if (technologyFragment == null) {
				technologyFragment = new TechnologyFragment();
				transaction.add(R.id.ll_bodyer, technologyFragment);
			} else {
				transaction.show(technologyFragment);
			}
			break;
		case R.id.tv_exper: //企业专家
			if (experFragment == null) {
				experFragment = new ExperFragment();
				transaction.add(R.id.ll_bodyer, experFragment);
			} else {
				transaction.show(experFragment);
			}
			break;
		case R.id.tv_context: //企业动态
			if (contextFragment == null) {
				contextFragment = new ContextFragment();
				transaction.add(R.id.ll_bodyer, contextFragment);
			} else {
				transaction.show(contextFragment);
			}
			break;
		}
		transaction.commit();
	}
	
	/***
	 * 设置选中先文字颜色
	 * @param id 选中项id
	 */
	private void selected(int id) {
		if (id == R.id.tv_product) {
			tv_tab_product.setTextColor(getResources().getColor(R.color.blue));
			tv_tab_product.setEnabled(false);
			rl_search.setVisibility(View.VISIBLE);
			btn_menu.setVisibility(View.VISIBLE);
		} else {
			tv_tab_product.setTextColor(Color.BLACK);
			tv_tab_product.setEnabled(true);
		}
		if (id == R.id.tv_technology) {
			tv_tab_technology.setTextColor(getResources().getColor(R.color.blue));
			tv_tab_technology.setEnabled(false);
			rl_search.setVisibility(View.VISIBLE);
			btn_menu.setVisibility(View.VISIBLE);
		} else {
			tv_tab_technology.setTextColor(Color.BLACK);
			tv_tab_technology.setEnabled(true);
		}
		if (id == R.id.tv_exper) {
			tv_tab_exper.setTextColor(getResources().getColor(R.color.blue));
			tv_tab_exper.setEnabled(false);
			rl_search.setVisibility(View.VISIBLE);
			btn_menu.setVisibility(View.GONE);
		} else {
			tv_tab_exper.setTextColor(Color.BLACK);
			tv_tab_exper.setEnabled(true);
		}
		if (id == R.id.tv_context) {
			tv_tab_context.setTextColor(getResources().getColor(R.color.blue));
			tv_tab_context.setEnabled(false);
			rl_search.setVisibility(View.GONE);
		} else {
			tv_tab_context.setTextColor(Color.BLACK);
			tv_tab_context.setEnabled(true);
		}
	}

	/***
	 * 隐藏所有fragment
	 * 
	 * @param transaction
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (productFragment != null) {
			transaction.hide(productFragment);
		}
		if (technologyFragment != null) {
			transaction.hide(technologyFragment);
		}
		if (experFragment != null) {
			transaction.hide(experFragment);
		}
		if (contextFragment != null) {
			transaction.hide(contextFragment);
		}
	}
	
	/***
	 * 初始化分类弹出框
	 */
	private void initType() {
		for (int i = 0; i < list_type.size(); i++) {
			Button btn = new Button(this);
			btn.setText(list_type.get(i).getCatname());
			btn.setBackgroundResource(R.drawable.btn_exhibition_company_type_style);
			btn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dm.widthPixels / 8));
			btn.setGravity(Gravity.CENTER);
			final int pos = i;
			btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popType.showPopupWindow();
					Bundle bundle = new Bundle();
					bundle.putString("catId", list_type.get(pos).getCatid());
					FragmentTransaction transaction = fragmentManager.beginTransaction();
					if (curPosition == 0) {
						transaction.remove(productFragment);
						productFragment = null;
						productFragment = new ProductFragment();
						productFragment.setArguments(bundle);
						transaction.add(R.id.ll_bodyer, productFragment);
					} else if (curPosition == 1) {
						transaction.remove(technologyFragment);
						technologyFragment = null;
						technologyFragment = new TechnologyFragment();
						technologyFragment.setArguments(bundle);
						transaction.add(R.id.ll_bodyer, technologyFragment);
					}
					transaction.commit();
				}
			});
			listBtn.add(btn);
			
		}
		if (popType != null) {
			popType = null;
		}
		if (curPosition == 0) {
			popType = new PopExhibitionCompany(this, listBtn, btn_menu, 
					dm.widthPixels / 3, dm.heightPixels / 3);
		} else if (curPosition == 1) {
			popType = new PopExhibitionCompany(this, listBtn, btn_menu, 
					dm.widthPixels / 5 * 2, dm.heightPixels / 4);
		}
	}
	
	/***
	 * 获取分类异步
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
	 * 获取分类数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class GetTypeTask extends AsyncTask<Void, Void, Void> {
		private JSONObject jobj;
		private JSONArray jArray;
		private List<NameValuePair> paramsList;
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
			list_type.clear();
			listBtn.clear();
			paramsList = new ArrayList<NameValuePair>();
			if (curPosition == 0) {
				paramsList.add(new BasicNameValuePair("companyId", id));
			}
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String requery = "";
			if (curPosition == 0) {
				requery = comFunction.getDataFromServer("get_shop_company_menu", 
						paramsList, ExhibitionCompanyActivity.this);
			} else if (curPosition == 1) {
				requery = comFunction.getDataFromServer("get_shop_company_tech_menu", 
						paramsList, ExhibitionCompanyActivity.this);
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
					jArray = new JSONArray(jobj.getString("data"));
					if (jArray == null) {
						return null;
					}
					ExhibitionTypeCUnit unit = null;
					for (int i = 0; i < jArray.length(); i++) {
						unit = new ExhibitionTypeCUnit();
						unit.setCatid(jArray.getJSONObject(i).getString("catid"));
						unit.setCatname(jArray.getJSONObject(i).getString("catname"));
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
			getTypeTask = null;
			if (message != null) {
				if (code.equals(HttpCode.SERVICE_SUCCESS)) {
					initType();
				} else {
					comFunction.toastMsg(message, ExhibitionCompanyActivity.this);
				}
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						ExhibitionCompanyActivity.this);
			}
//			pullview.onHeaderRefreshComplete();
//			if (pd.isShowing()) {
//				pd.dismiss();
//			}
			super.onPostExecute(result);
		}

	}
}

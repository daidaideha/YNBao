package com.innouni.yinongbao.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.KnowledgeTypeAdapter;
import com.innouni.yinongbao.unit.knowledge.KnowledgeTypeUnit;

@SuppressLint("NewApi")
public class PopKnowledgeType {
	/***
	 * 上下文变量
	 */
	private Context context;
	/***
	 * 显示的pop
	 */
	private PopupWindow popupWindow;
	/***
	 * pop显示所依托的view
	 */
	private View anchorView;
	/***
	 * 数据展示列表控件
	 */
	private ListView listView;
	/***
	 * 侧边字母栏控件
	 */
	private SideBar sideBar;
	/***
	 * 选中字母弹出显示控件
	 */
	private TextView mDialogText;

	private WindowManager mWindowManager;
	/***
	 * 分类列表适配器
	 */
	private KnowledgeTypeAdapter adapter;

	/***
	 * pop显示的宽度和高度
	 */
	private int popWidth = 100, popHeight = 200;
	/***
	 * 数据列表
	 */
	private List<KnowledgeTypeUnit> list;
	private OnMyItemClickListener onMyItemClickListener;

	public PopKnowledgeType(Context context, List<KnowledgeTypeUnit> list,
			View anchorView, int popWidth, int popHeight) {
		this.context = context;
		this.anchorView = anchorView;
		this.popHeight = popHeight;
		this.popWidth = popWidth;
		this.list = list;
		initPopWindow();
	}

	/***
	 * 初始化pop
	 */
	public void initPopWindow() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.view_pop_knowledge, null);
		listView = (ListView) v.findViewById(R.id.lv_knowledge_type);
		adapter = new KnowledgeTypeAdapter(context, list);
		listView.setAdapter(adapter);
		sideBar = (SideBar) v.findViewById(R.id.sideBar);
		sideBar.setListView(listView);
		mDialogText = (TextView) inflater.inflate(R.layout.view_list_position,
				null);
		mDialogText.setVisibility(View.INVISIBLE);
		mWindowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		mWindowManager.addView(mDialogText, lp);
		sideBar.setTextView(mDialogText);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (onMyItemClickListener != null) {
					onMyItemClickListener.onMyItemClick(arg0, arg1, arg2, arg3);
				}
			}
		});

		popupWindow = new PopupWindow(v, popWidth, popHeight, true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0xffffffff));
	}

	/***
	 * 显示、隐藏pop
	 */
	public void showPopupWindow() {
		if (popupWindow != null) {
			if (popupWindow.isShowing()) {
				popupWindow.dismiss();
			} else {
				popupWindow.showAsDropDown(anchorView, -(anchorView.getWidth() / 2), 20);
			}
		}
	}

	/***
	 * 列表单项点击事件
	 * 
	 * @author LinYuLing
	 */
	public interface OnMyItemClickListener {
		public void onMyItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3);
	}

	/***
	 * 设置列表单项点击事件
	 */
	public void setOnMyItemClickListener(
			OnMyItemClickListener onMyItemClickListener) {
		this.onMyItemClickListener = onMyItemClickListener;
	}
}

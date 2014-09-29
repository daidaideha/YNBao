package com.innouni.yinongbao.view;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;

import com.innouni.yinongbao.R;

/***
 * 农资展厅商铺分类弹出框
 * @author LinYuLing
 * @UpdateDate 2014-09-29
 */
public class PopExhibitionCompany {
	/***
	 * 上下文变量
	 */
	private Context context;
	/***
	 * pop显示的内容数据
	 */
	private List<Button> list;
	/***
	 * 显示的pop
	 */
	private PopupWindow popupWindow;
	/***
	 * pop显示所依托的view
	 */
	private View anchorView;
	/***
	 * pop显示的宽度和高度
	 */
	private int popWidth = 100, popHeight = 200;

	public PopExhibitionCompany(Context context, List<Button> list, View anchorView,
			int popWidth, int popHeight) {
		this.context = context;
		this.list = list;
		this.anchorView = anchorView;
		this.popHeight = popHeight;
		this.popWidth = popWidth;
		initPopWindow();
	}

	/***
	 * 初始化pop
	 */
	public void initPopWindow() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.view_pop_exhibition_company, null);
		LinearLayout ll_pop_merchat_more = (LinearLayout) v
				.findViewById(R.id.ll_body);
		ll_pop_merchat_more.removeAllViews();
		for (int i = 0; i < list.size(); i++) {
			ll_pop_merchat_more.addView(list.get(i));
			if (i != list.size() - 1) {
				ImageView iv_line = new ImageView(context);
				iv_line.setBackgroundResource(R.drawable.line_ask_top);
				iv_line.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
				ll_pop_merchat_more.addView(iv_line);
			}
		}
		popupWindow = new PopupWindow(v, popWidth, popHeight, true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(0xffffff));

	}

	/***
	 * 显示、隐藏pop
	 */
	public void showPopupWindow() {
		System.out.println("height: " + anchorView.getHeight());
		if (popupWindow != null) {
			if (popupWindow.isShowing()) {
				popupWindow.dismiss();
			} else {
				popupWindow.showAsDropDown(anchorView, -(anchorView.getWidth() / 2), 20);
			}
		}
	}
}

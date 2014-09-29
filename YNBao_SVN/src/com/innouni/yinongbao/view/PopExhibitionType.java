package com.innouni.yinongbao.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.TextAdapter;
import com.innouni.yinongbao.unit.exhibition.ExhibitionTypeUnit;

/***
 * 农资展厅分类弹出框
 * @author LinYuLing
 * @UpdateDate 2014-09-27
 */
public class PopExhibitionType {
	/***
	 * 显示的pop
	 */
	private PopupWindow popupWindow;
	/***
	 * 父级展示控件
	 */
	private ListView pListView;
	/***
	 * 子级展示控件
	 */
	private ListView cListView;
	/***
	 * pop显示所依托的view
	 */
	private View anchorView;
	
	/***
	 * 上下文变量
	 */
	private Context context;
	/***
	 * 父级列表适配器
	 */
	private TextAdapter pAdapter;
	/***
	 * 子级列表适配器
	 */
	private TextAdapter cAdapter;

	/***
	 * pop显示的内容数据
	 */
	private List<ExhibitionTypeUnit> list;
	/***
	 * 父级数据列表
	 */
	private List<HashMap<String, String>> pList;
	/***
	 * 子级数据列表
	 */
	private List<LinkedList<HashMap<String, String>>> cList;
	/***
	 * 当前自己数据列表
	 */
	private LinkedList<HashMap<String, String>> curList;
	/***
	 * curParent：父级当前选中项
	 * curChildren：子级当前选中项
	 */
	private int curParent = 0, curChildren;
	/***
	 * pop显示的宽度和高度
	 */
	private int popWidth = 100, popHeight = 200;
	
	/***
	 * 自己单项点击事件监听
	 */
	private OnMyItemClickListener onMyItemClickListener;

	public PopExhibitionType(Context context, List<ExhibitionTypeUnit> list, View anchorView,
			int popWidth, int popHeight) {
		this.context = context;
		this.list = list;
		this.anchorView = anchorView;
		this.popHeight = popHeight;
		this.popWidth = popWidth;
		initList();
		initPopWindow();
	}
	
	/***
	 * 初始化父级、子级数据
	 */
	private void initList() {
		curList = new LinkedList<HashMap<String, String>>();
		pList = new ArrayList<HashMap<String, String>>();
		cList = new ArrayList<LinkedList<HashMap<String, String>>>();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", list.get(i).getCatId());
			map.put("title", list.get(i).getCatName());
			LinkedList<HashMap<String, String>> children = new LinkedList<HashMap<String, String>>();
			for (int j = 0; j < list.get(i).getC_types().size(); j++) {
				HashMap<String, String> map2 = new HashMap<String, String>();
				map2.put("id", list.get(i).getC_types().get(j).getCatid());
				map2.put("title", list.get(i).getC_types().get(j).getCatname());
				children.add(map2);
			}
			pList.add(map);
			cList.add(children);
		}
	}

	/***
	 * 初始化pop
	 */
	public void initPopWindow() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.view_pop_exhibition_type, null);
		pListView = (ListView) v.findViewById(R.id.list_type_parent);
		cListView = (ListView) v.findViewById(R.id.list_type_children);
		pAdapter = new TextAdapter(context, pList, R.drawable.bg_exhibition_type_p_selected, 
				R.drawable.bg_exhibition_type_p, 0);
		pAdapter.setTextSize(14);
		pAdapter.setSelectedPositionNoNotify(0);
		pListView.setAdapter(pAdapter);
		pAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
			
			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				curParent = position;
				curList.clear();
				curList.addAll(cList.get(position));
				cAdapter.notifyDataSetChanged();
			}
		});
		if (cList.size() > 0) {
			curList.addAll(cList.get(0));
		}
		cAdapter = new TextAdapter(context, curList, R.drawable.bg_exhibition_type_c_selected, 
				R.drawable.bg_exhibition_type_c, 1);
		cAdapter.setTextSize(14);
//		cAdapter.setSelectedPositionNoNotify(curChildren);
		cListView.setAdapter(cAdapter);
		cAdapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {
			
			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
				curChildren = position;
				if (onMyItemClickListener != null) {
					onMyItemClickListener.onMyItemClick(curParent, curChildren);
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
		System.out.println("height: " + anchorView.getHeight());
		if (popupWindow != null) {
			if (popupWindow.isShowing()) {
				popupWindow.dismiss();
			} else {
				popupWindow.showAsDropDown(anchorView, -(anchorView.getWidth() / 2), 20);
			}
		}
	}
	
	/***
	 * 子级单项点击事件监听
	 * @author LinYuLing
	 * 
	 */
	public interface OnMyItemClickListener {
		/***
		 * 子级单项点击事件
		 * @param position 点击单元项
		 */
		public void onMyItemClick(int pPosition, int cPosition);
	}
	
	public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
		this.onMyItemClickListener = onMyItemClickListener;
	}
}

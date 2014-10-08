package com.innouni.yinongbao.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.innouni.yinongbao.R;

public class GroupRecoListAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	/***
	 * 上下文变量
	 */
	private Context context;
	List<Map<String, Object>> items;
	
	public GroupRecoListAdapter(Context context){
		this.inflater = LayoutInflater.from(context);
		this.context = context;
		items = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 9; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("imageItem", R.drawable.bg_group_fruit);// 添加图像资源的ID
			item.put("textItem", "icon" + i);// 按序号添加ItemText
			items.add(item);
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView= inflater.inflate(R.layout.adapter_group_recolist, null);
		GridView gridView = (GridView) convertView.findViewById(R.id.gv_group_foodcrp);
		
		SimpleAdapter adapter = new SimpleAdapter(context, items,
				R.layout.gridview_group_reco, new String[] { "imageItem",
						"textItem" }, new int[] { R.id.img_group_crop,
						R.id.txt_group_crop });

		gridView.setAdapter(adapter);
		return convertView;
	}

}

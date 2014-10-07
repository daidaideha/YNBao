package com.innouni.yinongbao.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.person.ZuoWu;

/**
 * 我收藏的作物列表适配器
 * 
 * @author Hugj
 * 
 */
public class MyCollectZuoWuAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<ZuoWu> list;

	public MyCollectZuoWuAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		list = new ArrayList<ZuoWu>();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public ZuoWu getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_my_collect_zuowu_list,
					null);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ZuoWu question = getItem(position);
		holder.tvTitle.setText(question.name);
		return convertView;
	}

	public void clearList() {
		list.clear();
	}

	public void setList(ArrayList<ZuoWu> sublist) {
		if (sublist != null) {
			list.addAll(sublist);
		}
	}

	class ViewHolder {
		TextView tvTitle;
	}

}

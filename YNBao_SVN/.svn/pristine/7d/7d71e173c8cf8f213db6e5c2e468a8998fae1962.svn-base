package com.innouni.yinongbao.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.person.HelpCenter;
import com.innouni.yinongbao.cache.ImageLoader;

/**
 * 帮助中心列表适配器
 * 
 * @author Hugj
 * 
 */
public class HelpCenterAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<HelpCenter> list;

	public HelpCenterAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		list = new ArrayList<HelpCenter>();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public HelpCenter getItem(int position) {
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
			convertView = inflater
					.inflate(R.layout.item_help_center_list, null);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tvid = (TextView) convertView.findViewById(R.id.tv_id);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HelpCenter question = getItem(position);
		holder.tvTitle.setText(question.content);
		holder.tvid.setText(question.id);
		return convertView;
	}

	public void clearList() {
		list.clear();
	}

	public void setList(ArrayList<HelpCenter> sublist) {
		if (sublist != null) {
			list.addAll(sublist);
		}
	}

	class ViewHolder {
		TextView tvid, tvTitle;
	}

}

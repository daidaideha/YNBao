package com.innouni.yinongbao.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.person.MyTiezi;

/**
 * 我的帖子列表适配器
 * 
 * @author Hugj
 * 
 */
public class MyTieziAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<MyTiezi> list;

	public MyTieziAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		list = new ArrayList<MyTiezi>();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public MyTiezi getItem(int position) {
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
			convertView = inflater.inflate(R.layout.item_my_tiezi_list, null);
			holder.tv1 = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv2 = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv3 = (TextView) convertView.findViewById(R.id.tv_view);
			holder.tv4 = (TextView) convertView.findViewById(R.id.tv_reply);
			holder.tv5 = (TextView) convertView.findViewById(R.id.tv_satau);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyTiezi question = getItem(position);
		holder.tv1.setText(question.title);
		holder.tv2.setText(question.addtime);
		holder.tv3.setText("浏览数: " + question.viewnum);
		holder.tv4.setText("回复数: " + question.replynum);
		holder.tv5.setText(question.catname);
		return convertView;
	}

	public void clearList() {
		list.clear();
	}

	public void setList(ArrayList<MyTiezi> sublist) {
		if (sublist != null) {
			list.addAll(sublist);
		}
	}

	class ViewHolder {
		TextView tv1, tv2, tv3, tv4, tv5;
	}

}

package com.innouni.yinongbao.adapter;

import java.util.ArrayList;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.person.AskQuestion;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 我问的...列表适配器
 * 
 * @author Hugj
 * 
 */
public class MyAskAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<AskQuestion> list;

	public MyAskAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		list = new ArrayList<AskQuestion>();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public AskQuestion getItem(int position) {
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
			convertView = inflater.inflate(R.layout.item_my_ask_question_list,
					null);
			holder.tv1 = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv2 = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv3 = (TextView) convertView.findViewById(R.id.tv_view);
			holder.tv4 = (TextView) convertView.findViewById(R.id.tv_reply);
			holder.tv5 = (TextView) convertView.findViewById(R.id.tv_satau);
			holder.tvusername = (TextView) convertView.findViewById(R.id.tv_username);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		AskQuestion question = getItem(position);
		holder.tv1.setText(question.title);
		holder.tv2.setText(question.addTime);
		holder.tv3.setText("浏览数: " + question.views);
		holder.tv4.setText("回复数: " + question.replies);
		holder.tv5.setText("解答状态: " + question.status);
		if (TextUtils.isEmpty(question.username)) {
			holder.tvusername.setVisibility(View.GONE);
		} else {
			holder.tvusername.setVisibility(View.VISIBLE);
			holder.tvusername.setText(question.username);
		}
		return convertView;
	}

	public void clearList() {
		list.clear();
	}

	public void setList(ArrayList<AskQuestion> sublist) {
		if (sublist != null) {
			list.addAll(sublist);
		}
	}

	class ViewHolder {
		TextView tv1, tv2, tv3, tv4, tv5;
		TextView tvusername;
	}

}

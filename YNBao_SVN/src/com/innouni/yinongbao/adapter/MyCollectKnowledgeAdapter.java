package com.innouni.yinongbao.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.unit.knowledge.KnowledgeUnit;

/**
 * 我收藏的知识列表适配器
 * 
 * @author Hugj
 * 
 */
public class MyCollectKnowledgeAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<KnowledgeUnit> list;

	public MyCollectKnowledgeAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		list = new ArrayList<KnowledgeUnit>();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public KnowledgeUnit getItem(int position) {
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
			convertView = inflater.inflate(
					R.layout.item_my_collect_knowledge_list, null);
			holder.tvtitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvtime = (TextView) convertView.findViewById(R.id.time);
			holder.tvview = (TextView) convertView.findViewById(R.id.views);
			holder.tvreply = (TextView) convertView.findViewById(R.id.replies);
			holder.tvtype = (TextView) convertView.findViewById(R.id.type);
			holder.tvname = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		KnowledgeUnit question = getItem(position);
		holder.tvtitle.setText(question.getTitle());
		holder.tvtime.setText(question.getAddtime());
		holder.tvview.setText("浏览:" + question.getViews());
		holder.tvreply.setText("回复:" + question.getReplies());
		holder.tvtype.setText("类型:" + question.getType());
		holder.tvname.setText("名字:" + question.getName());
		return convertView;
	}

	public void clearList() {
		list.clear();
	}

	public void setList(ArrayList<KnowledgeUnit> sublist) {
		if (sublist != null) {
			list.addAll(sublist);
		}
	}

	class ViewHolder {
		TextView tvtitle, tvtime, tvview, tvreply, tvtype, tvname;
	}

}

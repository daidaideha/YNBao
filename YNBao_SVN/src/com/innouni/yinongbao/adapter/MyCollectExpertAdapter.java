package com.innouni.yinongbao.adapter;

import java.util.ArrayList;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.R.layout;
import com.innouni.yinongbao.activity.person.AskQuestion;
import com.innouni.yinongbao.activity.person.CollectVideo;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.unit.exper.ExperUnit;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我收藏的专家列表适配器
 * 
 * @author Hugj
 * 
 */
public class MyCollectExpertAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<ExperUnit> list;
	private ImageLoader loader;

	public MyCollectExpertAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		list = new ArrayList<ExperUnit>();
		loader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public ExperUnit getItem(int position) {
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
					R.layout.item_my_collect_expert_list, null);
			holder.iconView = (ImageView) convertView.findViewById(R.id.image);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvcom = (TextView) convertView.findViewById(R.id.company);
			holder.tvpos = (TextView) convertView.findViewById(R.id.position);
			holder.tvgro = (TextView) convertView.findViewById(R.id.groupname);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ExperUnit question = getItem(position);
		holder.tvTitle.setText(question.getName());
		holder.tvcom.setText(question.getCompany());
		holder.tvpos.setText(question.getPosition());
		holder.tvgro.setText(question.getGroupname());
		loader.DisplayImage(question.getAvatar(), holder.iconView, false);
		return convertView;
	}

	public void clearList() {
		list.clear();
	}

	public void setList(ArrayList<ExperUnit> sublist) {
		if (sublist != null) {
			list.addAll(sublist);
		}
	}

	class ViewHolder {
		ImageView iconView;
		TextView tvTitle, tvcom, tvpos, tvgro;
	}

}

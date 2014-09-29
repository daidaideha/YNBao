package com.innouni.yinongbao.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.person.CollectVideo;
import com.innouni.yinongbao.activity.person.MyGroup;
import com.innouni.yinongbao.cache.ImageLoader;

/**
 * 我的群组列表适配器
 * 
 * @author Hugj
 * 
 */
public class MyGroupAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<MyGroup> list;
	private ImageLoader loader;

	public MyGroupAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		list = new ArrayList<MyGroup>();
		loader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public MyGroup getItem(int position) {
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
			convertView = inflater.inflate(R.layout.iitem_my_group_list, null);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvCount = (TextView) convertView.findViewById(R.id.count);
			holder.tvDecs = (TextView) convertView.findViewById(R.id.dec);
			holder.iconView = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyGroup question = getItem(position);
		holder.tvTitle.setText(question.name);
		holder.tvCount.setText(question.membernum + "人");
		holder.tvDecs.setText(question.description);
		loader.DisplayImage(question.icon, holder.iconView, false);
		return convertView;
	}

	public void clearList() {
		list.clear();
	}

	public void setList(ArrayList<MyGroup> sublist) {
		if (sublist != null) {
			list.addAll(sublist);
		}
	}

	class ViewHolder {
		ImageView iconView;
		TextView tvTitle, tvCount, tvDecs;
	}

}

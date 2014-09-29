package com.innouni.yinongbao.adapter;

import java.util.ArrayList;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.R.layout;
import com.innouni.yinongbao.activity.person.AskQuestion;
import com.innouni.yinongbao.activity.person.CollectVideo;
import com.innouni.yinongbao.cache.ImageLoader;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 收藏的视频列表适配器
 * 
 * @author Hugj
 * 
 */
public class MyCollectVideoAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<CollectVideo> list;
	private ImageLoader loader;

	public MyCollectVideoAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		list = new ArrayList<CollectVideo>();
		loader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public CollectVideo getItem(int position) {
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
			convertView = inflater.inflate(R.layout.item_my_collect_video_list,
					null);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.iconView = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CollectVideo question = getItem(position);
		holder.tvTitle.setText(question.title);
		loader.DisplayImage(question.image, holder.iconView, false);
		return convertView;
	}

	public void clearList() {
		list.clear();
	}

	public void setList(ArrayList<CollectVideo> sublist) {
		if (sublist != null) {
			list.addAll(sublist);
		}
	}

	class ViewHolder {
		ImageView iconView;
		TextView tvTitle;
	}

}

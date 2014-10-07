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
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.unit.exhibition.ExhibitionUnit;

/**
 * 我收藏的产品列表适配器
 * 
 * @author Hugj
 * 
 */
public class MyCollectProductAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<ExhibitionUnit> list;
	private ImageLoader loader;

	public MyCollectProductAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		list = new ArrayList<ExhibitionUnit>();
		loader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public ExhibitionUnit getItem(int position) {
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
					R.layout.item_my_collect_product_list, null);
			holder.iconView = (ImageView) convertView.findViewById(R.id.image);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvcom = (TextView) convertView.findViewById(R.id.company);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ExhibitionUnit question = getItem(position);
		holder.tvTitle.setText(question.getTitle());
		holder.tvcom.setText(question.getCompany());
		loader.DisplayImage(question.getThumb(), holder.iconView, false);
		return convertView;
	}

	public void clearList() {
		list.clear();
	}

	public void setList(ArrayList<ExhibitionUnit> sublist) {
		if (sublist != null) {
			list.addAll(sublist);
		}
	}

	class ViewHolder {
		ImageView iconView;
		TextView tvTitle, tvcom;
	}

}

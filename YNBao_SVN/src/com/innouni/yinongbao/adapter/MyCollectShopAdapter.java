package com.innouni.yinongbao.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.person.Shop;

/**
 * 我收藏的商铺列表适配器
 * 
 * @author Hugj
 * 
 */
public class MyCollectShopAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<Shop> list;

	public MyCollectShopAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		list = new ArrayList<Shop>();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Shop getItem(int position) {
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
			convertView = inflater.inflate(R.layout.item_my_collect_shop_list,
					null);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
			holder.tvcom = (TextView) convertView.findViewById(R.id.position);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Shop question = getItem(position);
		holder.tvTitle.setText(question.companyName);
		holder.tvcom.setText(question.groupName);
		return convertView;
	}

	public void clearList() {
		list.clear();
	}

	public void setList(ArrayList<Shop> sublist) {
		if (sublist != null) {
			list.addAll(sublist);
		}
	}

	class ViewHolder {
		TextView tvTitle, tvcom;
	}

}

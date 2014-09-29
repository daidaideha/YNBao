package com.innouni.yinongbao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.unit.exper.ExperTechnologyCommentUnit;

public class ExperTechnologyCommentAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	/***
	 * 图片加载工具类
	 */
	private ImageLoader mImageLoader;
	/***
	 * 数据列表
	 */
	private List<ExperTechnologyCommentUnit> list;
	/***
	 * 图片宽度
	 */
	private int width;

	public ExperTechnologyCommentAdapter(Context context,
			List<ExperTechnologyCommentUnit> list, int width) {
		this.inflater = LayoutInflater.from(context);
		this.mImageLoader = new ImageLoader(context);
		this.list = list;
		this.width = width;
	}
	
	public void setList(List<ExperTechnologyCommentUnit> list) {
		this.list.addAll(list);
	}
	
	public void clearList() {
		this.list.clear();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.adapter_exper_comment, null);
		ImageView iv_exper_photo = (ImageView) convertView
				.findViewById(R.id.iv_photo);
		TextView tv_exper_name = (TextView) convertView
				.findViewById(R.id.tv_name);
		TextView tv_exper_message = (TextView) convertView
				.findViewById(R.id.tv_message);
		TextView tv_exper_date = (TextView) convertView
				.findViewById(R.id.tv_date);

		iv_exper_photo.setLayoutParams(new LayoutParams(width, width));
		mImageLoader.DisplayImage(list.get(position).getPic_url(),
				iv_exper_photo, false);
		tv_exper_name.setText(list.get(position).getName());
		tv_exper_message.setText(list.get(position).getContent());
		tv_exper_date.setText(list.get(position).getAddtime());
		return convertView;
	}

}

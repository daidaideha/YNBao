package com.innouni.yinongbao.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.unit.exper.ExperUnit;
import com.innouni.yinongbao.widget.comFunction;

public class ExhibitionExperAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	/***
	 * 图片加载工具类
	 */
	private ImageLoader mImageLoader;
	/***
	 * 上下文变量
	 */
	private Context context;
	/***
	 * 数据列表
	 */
	private List<ExperUnit> list;
	/***
	 * 产品图片宽度
	 */
	private int width;

	public ExhibitionExperAdapter(Context context, List<ExperUnit> list,
				int width) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		mImageLoader = new ImageLoader(context);
		this.list = list;
		this.width = width;
	}

	public void setList(List<ExperUnit> list) {
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
		System.out.println("get " + position);
		convertView = inflater.inflate(R.layout.adapter_exhibition_exper, null);
		ImageView iv_exper_photo = (ImageView) convertView
				.findViewById(R.id.iv_photo);
		TextView tv_exper_name = (TextView) convertView
				.findViewById(R.id.tv_exper_name);
		TextView tv_exper_company = (TextView) convertView
				.findViewById(R.id.tv_exper_company);
		TextView tv_exper_context = (TextView) convertView
				.findViewById(R.id.tv_exper_context);
		Button btn_phone = (Button) convertView
				.findViewById(R.id.btn_phone);
		Button btn_online = (Button) convertView
				.findViewById(R.id.btn_online);

		iv_exper_photo.setLayoutParams(new LayoutParams(width / 4, width / 3));
		mImageLoader.DisplayImage(list.get(position).getAvatar(),
				iv_exper_photo, false);
		tv_exper_name.setText(list.get(position).getName());
		tv_exper_company.setText(list.get(position).getCompany());
		tv_exper_context.setText(list.get(position).getPosition());
		if (!comFunction.isNullorSpace(list.get(position).getMobile())) {
			btn_phone.setBackgroundResource(R.drawable.btn_exhibition_exper_phone);
		} else {
			btn_phone.setBackgroundResource(R.drawable.btn_exhibition_exper_phone_ull);
		}
		final int pos = position;
		btn_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!comFunction.isNullorSpace(list.get(pos).getMobile())) {
					context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
							.parse("tel:" + list.get(pos).getMobile())));
				}
			}
		});
		btn_online.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				if (!comFunction.isNullorSpace(list.get(pos).getMobile())) {
//					context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
//							.parse("tel:" + list.get(pos).getMobile())));
//				}
			}
		});
		// TODO Auto-generated method stub
		return convertView;
	}

}

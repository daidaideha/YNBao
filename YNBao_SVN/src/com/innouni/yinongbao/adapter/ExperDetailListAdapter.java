package com.innouni.yinongbao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.unit.exhibition.ExhibitionTechnologyUnit;
import com.innouni.yinongbao.unit.exper.ExperBlogUnit;
import com.innouni.yinongbao.unit.exper.ExperThreadUnit;

public class ExperDetailListAdapter<T> extends BaseAdapter {
	private LayoutInflater inflater;
	/***
	 * 数据列表
	 */
	private List<T> list;

	public ExperDetailListAdapter(Context context, List<T> list) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
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
		convertView = inflater.inflate(R.layout.adapter_exper_detail_list, null);
		TextView mTextView = (TextView) convertView.findViewById(R.id.tv_exper_list);
		if (list.get(position) instanceof ExperBlogUnit) {
			mTextView.setText(((ExperBlogUnit)list.get(position)).getTitle());
		} else if (list.get(position) instanceof ExperThreadUnit) {
			mTextView.setText(((ExperThreadUnit)list.get(position)).getSubject());
		} else if (list.get(position) instanceof ExhibitionTechnologyUnit) {
			mTextView.setText("[" + ((ExhibitionTechnologyUnit)list.get(position)).getCatname() + "]" + 
					((ExhibitionTechnologyUnit)list.get(position)).getTitle());
		} else {
			mTextView.setText(list.get(position).toString());
		}
		return convertView;
	}

}
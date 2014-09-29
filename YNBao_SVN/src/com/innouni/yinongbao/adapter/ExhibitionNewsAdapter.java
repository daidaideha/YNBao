package com.innouni.yinongbao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.unit.exhibition.ExhibitionNewsUnit;

/***
 * 农资展厅企业新闻适配器
 * @author LinYuLing
 * @UpdateDate 2014-09-29
 */
public class ExhibitionNewsAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	/***
	 * 数据列表
	 */
	private List<ExhibitionNewsUnit> list;

	public ExhibitionNewsAdapter(Context context, List<ExhibitionNewsUnit> list) {
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
		convertView = inflater.inflate(R.layout.adapter_exhibition_news, null);
		TextView mTextView = (TextView) convertView.findViewById(R.id.tv_exper_list);
		mTextView.setText(list.get(position).getTitle());
		return convertView;
	}

}

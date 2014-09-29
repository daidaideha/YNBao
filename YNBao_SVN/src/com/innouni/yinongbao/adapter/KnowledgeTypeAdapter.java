package com.innouni.yinongbao.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.unit.knowledge.KnowledgeTypeUnit;

public class KnowledgeTypeAdapter extends BaseAdapter implements SectionIndexer {
	private Context mContext;
	private List<KnowledgeTypeUnit> list;
	public String mapServerpath;

	public KnowledgeTypeAdapter(Context mContext, List<KnowledgeTypeUnit> list) {
		this.mContext = mContext;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String nickName = list.get(position).getName();
		ViewHolder viewHolder = null;
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.adapter_knowledge_type, null);
		viewHolder = new ViewHolder();
		viewHolder.tvCatalog = (TextView) convertView
				.findViewById(R.id.tv_catalog);
		viewHolder.tvNick = (TextView) convertView.findViewById(R.id.tv_nick);
		viewHolder.tvCode = (TextView) convertView.findViewById(R.id.tv_code);
		String nickName_abc = list.get(position).getLetter();
		if (position == 0) {
			viewHolder.tvCatalog.setVisibility(View.VISIBLE);
			viewHolder.tvCatalog.setText(nickName_abc);
		} else {
			String catalog = list.get(position - 1).getLetter();
			if (nickName_abc.equals(catalog)) {
				viewHolder.tvCatalog.setVisibility(View.GONE);
			} else {
				viewHolder.tvCatalog.setVisibility(View.VISIBLE);
				viewHolder.tvCatalog.setText(nickName_abc);
			}
		}
		viewHolder.tvNick.setText(nickName);
		viewHolder.tvCode.setText(list.get(position).getFid());
		Log.i("", "tag position=" + position);
		return convertView;
	}

	private class ViewHolder {
		TextView tvCatalog;// 目录
		TextView tvNick;// 昵称
		TextView tvCode;
	}

	@Override
	public int getPositionForSection(int section) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			String l = list.get(i).getLetter();
			char firstChar = l.charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

}

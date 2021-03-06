package com.innouni.yinongbao.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.exhibition.ExhibitionDetailActivity;
import com.innouni.yinongbao.activity.exhibition.ExhibitionTypeActivity;
import com.innouni.yinongbao.activity.pest.PestDetailActivity;
import com.innouni.yinongbao.activity.pest.PestTypeActivity;
import com.innouni.yinongbao.activity.video.VideoDesActivity;
import com.innouni.yinongbao.activity.video.VideoTypeActivity;
import com.innouni.yinongbao.unit.exhibition.ExhibitionMainUnit;
import com.innouni.yinongbao.unit.exhibition.ExhibitionUnit;
import com.innouni.yinongbao.unit.pest.PestMainUnit;
import com.innouni.yinongbao.unit.pest.PestUnit;
import com.innouni.yinongbao.unit.video.VideoMainUnit;
import com.innouni.yinongbao.unit.video.VideoUnit;
import com.innouni.yinongbao.view.MyGridView;
import com.innouni.yinongbao.widget.IntentToOther;

/***
 * 农资展厅、害虫图库首页数据适配器
 * 
 * @author LinYuLing
 * @UpdateDate 2014-10-06
 */
public class ExhibitionMainAdapter<T> extends BaseAdapter {
	private LayoutInflater inflater;
	/***
	 * 上下文变量
	 */
	private Context context;
	/***
	 * 数据列表
	 */
	private List<T> list;
	/***
	 * 产品图片宽度
	 */
	private int width;

	public ExhibitionMainAdapter(Context context, List<T> list, int width) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		this.width = width;
	}

	public void setList(List<T> list) {
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

	@SuppressWarnings("rawtypes")
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		System.out.println("get " + position);
		convertView = inflater.inflate(R.layout.adapter_exhibition_main, null);
		TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
		MyGridView gridView = (MyGridView) convertView
				.findViewById(R.id.gridview);
		ExperDetailGVAdapter adapter = null;
		if (list.get(position) instanceof ExhibitionMainUnit) {
			adapter = new ExperDetailGVAdapter<ExhibitionUnit>(context,
					((ExhibitionMainUnit) list.get(position)).getPicturelist(),
					width);
			tv_title.setText(((ExhibitionMainUnit) list.get(position))
					.getCatName());
		} else if (list.get(position) instanceof PestMainUnit) {
			adapter = new ExperDetailGVAdapter<PestUnit>(context,
					((PestMainUnit) list.get(position)).getPicturelist(), width);
			tv_title.setText(((PestMainUnit) list.get(position)).getCatName());
		} else if (list.get(position) instanceof VideoMainUnit) {
			adapter = new ExperDetailGVAdapter<VideoUnit>(context,
					((VideoMainUnit) list.get(position)).getPicturelist(), width);
			tv_title.setText(((VideoMainUnit) list.get(position)).getCatName());
		}

		gridView.setAdapter(adapter);
		final int pos = position;
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int location, long arg3) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				if (list.get(pos) instanceof ExhibitionMainUnit) {
					bundle.putString("id", ((ExhibitionMainUnit) list.get(pos))
							.getPicturelist().get(location).getId());
					new IntentToOther((Activity) context,
							ExhibitionDetailActivity.class, bundle);
				} else if (list.get(pos) instanceof PestMainUnit) {
					bundle.putString("id", ((PestMainUnit) list.get(pos))
							.getPicturelist().get(location).getId());
					new IntentToOther((Activity) context,
							PestDetailActivity.class, bundle);
				} else if (list.get(pos) instanceof VideoMainUnit) {
					bundle.putString("id", ((VideoMainUnit) list.get(pos))
							.getPicturelist().get(location).getId());
					bundle.putString("vid", ((VideoMainUnit) list.get(pos))
							.getPicturelist().get(location).getVid());
					new IntentToOther((Activity) context,
							VideoDesActivity.class, bundle);
				}
			}
		});
		tv_title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				if (list.get(pos) instanceof ExhibitionMainUnit) {
					bundle.putString("id",
							((ExhibitionMainUnit) list.get(pos)).getCatId());
					new IntentToOther((Activity) context,
							ExhibitionTypeActivity.class, bundle);
				} else if (list.get(pos) instanceof PestMainUnit) {
					bundle.putString("id",
							((PestMainUnit) list.get(pos)).getCatId());
					new IntentToOther((Activity) context,
							PestTypeActivity.class, bundle);
				} else if (list.get(pos) instanceof VideoMainUnit) {
					bundle.putString("id",
							((VideoMainUnit) list.get(pos)).getCatId());
					new IntentToOther((Activity) context,
							VideoTypeActivity.class, bundle);
				}
			}
		});
		return convertView;
	}

}

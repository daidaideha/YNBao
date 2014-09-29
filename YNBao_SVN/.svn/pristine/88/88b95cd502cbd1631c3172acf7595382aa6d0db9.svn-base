package com.innouni.yinongbao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.unit.exper.ExperBlogUnit;
import com.innouni.yinongbao.unit.exper.ExperThreadUnit;
import com.innouni.yinongbao.unit.knowledge.KnowledgeAnswersUnit;
import com.innouni.yinongbao.unit.knowledge.KnowledgeUnit;
import com.innouni.yinongbao.view.MyGridView;
import com.innouni.yinongbao.widget.sPreferences;

public class KnowledgeQuestionAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	/***
	 * 上下文对象
	 */
	private Context context;
	/***
	 * 图片加载工具类
	 */
	private ImageLoader mImageLoader;
	/***
	 * 存储数据的sp
	 */
	private sPreferences iSPreferences;
	/***
	 * 数据列表
	 */
	private List<KnowledgeAnswersUnit> list;
	/***
	 * 用来判断是专家解答还是网游评论 0：表示专家解读 1：表示网游评论
	 */
	private int type;
	/***
	 * 图片宽度
	 */
	private int width;

	public KnowledgeQuestionAdapter(Context context,
			List<KnowledgeAnswersUnit> list, int type, int width) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mImageLoader = new ImageLoader(context);
		this.iSPreferences = new sPreferences(context);
		this.list = list;
		this.type = type;
		this.width = width;
	}

	public void setList(List<KnowledgeAnswersUnit> list) {
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
		convertView = inflater.inflate(R.layout.adapter_knowledge_question,
				null);
		ImageView iv_photo = (ImageView) convertView
				.findViewById(R.id.iv_photo);
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		TextView tv_level = (TextView) convertView.findViewById(R.id.tv_level);
		TextView tv_content = (TextView) convertView
				.findViewById(R.id.tv_content);
		TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
		MyGridView mGridView = (MyGridView) convertView
				.findViewById(R.id.gridview);
		GVPhotoAdapter adapter = new GVPhotoAdapter(context, list.get(position)
				.getUrls(), width);
		mGridView.setAdapter(adapter);
		mImageLoader.DisplayImage(list.get(position).getAvatar(), iv_photo,
				false);
		if (type == 0) {
			tv_name.setText(list.get(position).getName());
			tv_level.setText(list.get(position).getGroupname());
		} else {
			tv_name.setText(list.get(position).getAuthor());
		}
		tv_content.setText(list.get(position).getContent());
		tv_date.setText(list.get(position).getAddtime());
		final int pos = position;
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int location,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		return convertView;
	}

}

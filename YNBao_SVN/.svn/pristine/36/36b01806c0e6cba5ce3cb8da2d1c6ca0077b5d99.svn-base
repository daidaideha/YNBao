package com.innouni.yinongbao.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.innouni.yinongbao.cache.ImageLoader;

/***
 * 图片适配器
 * @author LinYuLing
 * @UpdateDate 2014-09-04
 */
public class GVPhotoAdapter extends BaseAdapter {
	private Context context;
	private List<String> list;
	/***
	 * 图片宽度
	 */
	private int width;
	/***
	 * 图片加载器
	 */
	private ImageLoader mImageLoader;
	
	public GVPhotoAdapter(Context context,List<String> list, int width){
		this.context = context;
		this.list = list;
		this.width = width;
		this.mImageLoader = new ImageLoader(context);
	}

	/***
	 * 设置图片列表数据
	 * 
	 * @param list
	 *            图片列表数据
	 */
	public void setListData(List<String> list) {
		this.list.addAll(list);
	}

	/***
	 * 清除图片列表数据
	 */
	public void clearListData() {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView iv = new ImageView(context);
		iv.setLayoutParams(new LayoutParams(width, width));
		mImageLoader.DisplayImage(list.get(position), iv, false);
		return iv;
	}
}

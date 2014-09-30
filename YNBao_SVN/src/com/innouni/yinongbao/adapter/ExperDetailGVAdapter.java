package com.innouni.yinongbao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.unit.exhibition.ExhibitionUnit;
import com.innouni.yinongbao.unit.exper.ExperCompanyUnit;
import com.innouni.yinongbao.unit.pest.PestUnit;
import com.innouni.yinongbao.unit.video.VideoUnit;

/***
 * gridview图片适配器
 * 
 * @author LinYuLing
 * 
 * @param <T>
 *            列表参数类型
 */
public class ExperDetailGVAdapter<T> extends BaseAdapter {
	private LayoutInflater inflater;
	/***
	 * 图片加载工具类
	 */
	private ImageLoader mImageLoader;
	/***
	 * 数据列表
	 */
	private List<T> list;
	/***
	 * 图片宽度
	 */
	private int width;

	public ExperDetailGVAdapter(Context context, List<T> list, int width) {
		this.inflater = LayoutInflater.from(context);
		this.mImageLoader = new ImageLoader(context);
		this.list = list;
		this.width = width;
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
		System.out.println("position: " + position);
		convertView = inflater.inflate(R.layout.adapter_exper_detail_gv, null);
		ImageView mImageView = (ImageView) convertView
				.findViewById(R.id.iv_company);
		TextView mTextView = (TextView) convertView
				.findViewById(R.id.tv_company);
		mImageView.setLayoutParams(new LayoutParams(width, width));
		if (list.get(position) instanceof ExperCompanyUnit) {
			mImageLoader.DisplayImage(
					((ExperCompanyUnit) list.get(position)).getLogo(),
					mImageView, false);
			mTextView.setText(((ExperCompanyUnit) list.get(position))
					.getCompanyname());
		} else if (list.get(position) instanceof ExhibitionUnit) {
			mImageLoader.DisplayImage(
					((ExhibitionUnit) list.get(position)).getThumb(),
					mImageView, false);
			mTextView.setText(((ExhibitionUnit) list.get(position)).getTitle());
		} else if (list.get(position) instanceof PestUnit) {
			mImageLoader.DisplayImage(
					((PestUnit) list.get(position)).getThumb(), mImageView,
					false);
			mTextView.setText(((PestUnit) list.get(position)).getTitle());
		} else if (list.get(position) instanceof VideoUnit) {
			mImageLoader.DisplayImage(
					((VideoUnit) list.get(position)).getThumb(), mImageView,
					false);
			mTextView.setText(((VideoUnit) list.get(position)).getTitle());
		}
		return convertView;
	}

}

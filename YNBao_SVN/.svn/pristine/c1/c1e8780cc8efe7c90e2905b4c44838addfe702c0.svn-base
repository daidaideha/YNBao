package com.innouni.yinongbao.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.innouni.yinongbao.R;

/***
 * 
 * @author: LiuChao
 * @date: 2014-9-29
 * @description:群组推荐页面
 * 
 */
public class GroupGropsRecoFragment extends Fragment implements OnClickListener {

	/***
	 * 粮食作物、经济作物、瓜果类
	 */
	private GridView gvFoodGridView, gvCropsGridView, gvFruitGridView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View recoView = inflater.inflate(R.layout.activity_group_recom,
				container, false);
		initBodyer(recoView);
		return recoView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private void initBodyer(View recoView) {
		// 准备要添加的数据条目
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 9; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("imageItem", R.drawable.bg_group_fruit);// 添加图像资源的ID
			item.put("textItem", "icon" + i);// 按序号添加ItemText
			items.add(item);
		}
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), items,
				R.layout.gridview_group_reco, new String[] { "imageItem",
						"textItem" }, new int[] { R.id.img_group_crop,
						R.id.txt_group_crop });

		gvCropsGridView = (GridView) recoView
				.findViewById(R.id.gv_group_foodcrp);
		gvCropsGridView.setAdapter(adapter);
	}

}

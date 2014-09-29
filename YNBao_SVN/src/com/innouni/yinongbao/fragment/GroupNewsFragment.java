package com.innouni.yinongbao.fragment;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.adapter.GroupListAdapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;

@SuppressLint("NewApi")
public class GroupNewsFragment extends Fragment implements OnClickListener{
	
	private ListView listView ;
	private LayoutInflater inflater;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View grouphotView = inflater.inflate(R.layout.fragment_group_groupnews,
				container, false);
		initBodyer(grouphotView);
		return grouphotView;
	}
	
	private void initBodyer(View grouohotView){
		listView=  (ListView) grouohotView.findViewById(R.id.lv_group_groupnews);
		listView.setAdapter(new GroupListAdapter(getActivity()));
	}
	
	

}

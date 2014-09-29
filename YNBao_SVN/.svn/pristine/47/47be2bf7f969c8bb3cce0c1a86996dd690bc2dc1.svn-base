package com.innouni.yinongbao.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.activity.group.GroupInductionActivity;
import com.innouni.yinongbao.activity.group.GroupMianActivity;
import com.innouni.yinongbao.activity.group.GroupOfListActivity;
import com.innouni.yinongbao.activity.group.SearchGroupActivity;
import com.innouni.yinongbao.adapter.GroupListAdapter;
import com.innouni.yinongbao.widget.IntentToOther;
@SuppressLint("NewApi")
public class GroupHotFragment extends Fragment implements OnClickListener{

	private ListView listView ;
	private LayoutInflater inflater;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View grouphotView = inflater.inflate(R.layout.fragment_group_hotgroup,
				container, false);
		initBodyer(grouphotView);
		return grouphotView;
	}
	private void initBodyer(View grouohotView){
		listView=  (ListView) grouohotView.findViewById(R.id.lv_group_homepage);
		listView.setAdapter(new GroupListAdapter(getActivity()));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle bundle = new Bundle();
				new IntentToOther(getActivity(), GroupInductionActivity.class,bundle);
				
			}
			
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}

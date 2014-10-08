package com.innouni.yinongbao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innouni.yinongbao.R;

public class VideoDetailFragment extends Fragment{
	/***
	 * 标题栏
	 */
	TextView mTitleTextView;
	/***
	 * 视频时间
	 */
	TextView mVideoTimeTextView;
	/***
	 * 视频介绍
	 */
	TextView mVideoDetailTextView;
	/***
	 * 上传时间
	 */
	TextView mUploadTimeTextView;
	/***
	 * 类型
	 */
	TextView mCateGoryTextView;
	/***
	 * 上传者
	 */
	TextView mUploaderTextView;
	/***
	 * 播放次数
	 */
	TextView mVideoPlayTextView;
	/***
	 * 评论
	 */
	TextView mCommentTextView;
	/***
	 * 收藏
	 */
	TextView mCollecTextView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View videoDetailView = inflater.inflate(R.layout.fragment_video_detail,
				container, false);
		initBodyer(videoDetailView);
		return videoDetailView;
	}
	
	public void initBodyer(View view){
		mTitleTextView = (TextView) view.findViewById(R.id.txt_video_title);
		mVideoTimeTextView = (TextView) view.findViewById(R.id.txt_video_time);
		mVideoDetailTextView = (TextView) view.findViewById(R.id.txt_video_descp);
		mUploaderTextView = (TextView) view.findViewById(R.id.txt_video_uploader);
		mVideoPlayTextView = (TextView) view.findViewById(R.id.txt_video_playtimes);
//		mCommentTextView = (TextView) view.findViewById(R.id.txt_video_comment);
	}
	
	
}

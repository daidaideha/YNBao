package com.innouni.yinongbao.activity.video;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.MediaController.OnMyClickListener;
import io.vov.vitamio.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.widget.comFunction;
import com.innouni.yinongbao.widget.sPreferences;

/***
 * 视频库想起界面
 * 
 * @author YuLing
 * @UpdateDate 2014-10-07
 * 
 */
public class VideoDesActivity extends FragmentActivity implements OnInfoListener,
		OnBufferingUpdateListener, OnCompletionListener, OnClickListener {
	/***
	 * 头部返回按钮
	 */
	private RelativeLayout rl_back;
	/***
	 * 头部标题控件
	 */
	private TextView tv_title;
	/***
	 * 视频播放控件
	 */
	private VideoView mVideoView;
	/***
	 * 视频加载进度条
	 */
	private ProgressBar pb;
	/***
	 * downloadRateView：视频加载速度显示控件 loadRateView：视频加载进度显示控件
	 */
	private TextView downloadRateView, loadRateView;
	/***
	 * tab选中文字
	 */
	private TextView tv_des, tv_comment, tv_chance;
	/***
	 * tab按钮
	 */
	private RelativeLayout rl_des, rl_comment, rl_chance;
	/***
	 * tab选中图片
	 */
	private ImageView iv_des, iv_comment, iv_chance;
	/***
	 * 收藏显示控件
	 */
	private ImageView iv_collect;
	/***
	 * 底部按钮控件
	 */
	private RelativeLayout rl_download, rl_refresh, rl_share, rl_collect;
	
	/***
	 * 播放控制器
	 */
	private MediaController mController;
	/***
	 * 存储数据的sp
	 */
	private sPreferences iSPreferences;

	/***
	 * 用来判断是否已收藏
	 */
	private boolean IS_COLLECT = false;
	/***
	 * 视频播放链接
	 */
	private Uri uri;
	/***
	 * 视频播放id
	 */
	private String vid = "";
	/***
	 * 视频id
	 */
	private String id = "";
	/***
	 * 视频播放地址
	 */
	private String path = "http://v.polyv.net/uc/video/getMp4?vid=";
	/***
	 * 视频播放进度时间值
	 */
	private long mPosition;
	
	/***
	 * 收藏操作异步
	 */
	private FocusTask focusTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_play);
		iSPreferences = new sPreferences(this);
		try {
			id = getIntent().getStringExtra("id");
			vid = getIntent().getStringExtra("vid");
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			path += vid;
		}

		initHeader();
		initBodyer();
		initBottom();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (!comFunction.isNullorSpace(vid)) {
			mPosition = mVideoView.getCurrentPosition();
			iSPreferences.updateSp("mPosition", mPosition);
			mVideoView.stopPlayback();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!comFunction.isNullorSpace(vid)) {
			mPosition = iSPreferences.getSp().getLong("mPosition", 0L);
			if (mPosition > 0) {
				mVideoView.seekTo(mPosition);
				//mPosition = 0;
			}
			mVideoView.start();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (!comFunction.isNullorSpace(vid)) {
			iSPreferences.updateSp("mPosition", mPosition);
		}
	}

	/***
	 * 初始化头部控件
	 */
	private void initHeader() {
		rl_back = (RelativeLayout) findViewById(R.id.rl_header_back);
		tv_title = (TextView) findViewById(R.id.tv_header_title);

		tv_title.setText(getString(R.string.tv_header_video));
		rl_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/***
	 * 初始化布局控件
	 */
	private void initBodyer() {
		mVideoView = (VideoView) findViewById(R.id.video);
		pb = (ProgressBar) findViewById(R.id.probar);
		downloadRateView = (TextView) findViewById(R.id.download_rate);
		loadRateView = (TextView) findViewById(R.id.load_rate);
		
		rl_des = (RelativeLayout) findViewById(R.id.rl_video_detail);
		rl_comment = (RelativeLayout) findViewById(R.id.rl_video_comment);
		rl_chance = (RelativeLayout) findViewById(R.id.rl_video_selection);
		
		tv_des = (TextView) findViewById(R.id.tv_video_detail);
		tv_comment = (TextView) findViewById(R.id.tv_video_comment);
		tv_chance = (TextView) findViewById(R.id.tv_video_selection);
		
		iv_des = (ImageView) findViewById(R.id.iv_video_detail);
		iv_comment = (ImageView) findViewById(R.id.iv_video_comment);
		iv_chance = (ImageView) findViewById(R.id.iv_video_selection);
		
		rl_des.setOnClickListener(new onMyTabClickListener());
		rl_comment.setOnClickListener(new onMyTabClickListener());
		rl_chance.setOnClickListener(new onMyTabClickListener());
		
		rl_des.performClick();
		
		initVideo();

	}
	
	/***
	 * 初始化底部控件
	 */
	private void initBottom() {
		rl_download = (RelativeLayout) findViewById(R.id.rl_video_down);
		rl_refresh = (RelativeLayout) findViewById(R.id.rl_video_refresh);
		rl_share = (RelativeLayout) findViewById(R.id.rl_video_share);
		rl_collect = (RelativeLayout) findViewById(R.id.rl_video_collect);
		iv_collect = (ImageView) findViewById(R.id.iv_video_collect);
		
		rl_download.setOnClickListener(this);
		rl_refresh.setOnClickListener(this);
		rl_share.setOnClickListener(this);
		rl_collect.setOnClickListener(this);
	}

	/***
	 * 初始化视频
	 */
	private void initVideo() {
		if (comFunction.isNullorSpace(vid)) {
			pb.setVisibility(View.GONE);
			comFunction.toastMsg("该视频不存在！", this);
			return;
		}
		mController = new MediaController(this);
		uri = Uri.parse(path);
		mVideoView.setVideoURI(uri);
		mVideoView.setMediaController(mController);
		mVideoView.requestFocus();
		mVideoView.setOnInfoListener(this);
		mVideoView.setOnBufferingUpdateListener(this);
		mVideoView.setOnCompletionListener(this);
		mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				mediaPlayer.setPlaybackSpeed(1.0f);
			}
		});
		mController.setOnMyClickListener(new OnMyClickListener() {

			@Override
			public void OnClick() {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		loadRateView.setText(percent + "%");
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		switch (what) {
		case MediaPlayer.MEDIA_INFO_BUFFERING_START:
			if (mVideoView.isPlaying()) {
				mVideoView.pause();
				pb.setVisibility(View.VISIBLE);
				downloadRateView.setText("");
				loadRateView.setText("");
				downloadRateView.setVisibility(View.VISIBLE);
				loadRateView.setVisibility(View.VISIBLE);
			}
			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_END:
			mVideoView.start();
			pb.setVisibility(View.GONE);
			downloadRateView.setVisibility(View.GONE);
			loadRateView.setVisibility(View.GONE);
			break;
		case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
			downloadRateView.setText("" + extra + "kb/s" + "  ");
			break;
		}
		return true;
	}

	/***
	 * 视频播放结束操作
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		iSPreferences.updateSp("mPosition", 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_video_down:
			
			break;
		case R.id.rl_video_refresh:
			
			break;
		case R.id.rl_video_share:
			
			break;
		case R.id.rl_video_collect:
			focus();
			break;
		default:
			break;
		}
	}

	private class onMyTabClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			setTabFoucs(v.getId());
			switch (v.getId()) {
			case R.id.rl_video_detail:
				
				break;
			case R.id.rl_video_comment:
				
				break;
			case R.id.rl_video_selection:
				
				break;
			default:
				break;
			}
		}
		
	}
	
	/***
	 * tab选中事件
	 * @param id 选中控件id
	 */
	private void setTabFoucs(int id) {
		if (id == R.id.rl_video_detail) {
			iv_des.setVisibility(View.VISIBLE);
			tv_des.setTextColor(getResources().getColor(R.color.blue));
		} else {
			iv_des.setVisibility(View.GONE);
			tv_des.setTextColor(Color.BLACK);
		}
		if (id == R.id.rl_video_comment) {
			iv_comment.setVisibility(View.VISIBLE);
			tv_comment.setTextColor(getResources().getColor(R.color.blue));
		} else {
			iv_comment.setVisibility(View.GONE);
			tv_comment.setTextColor(Color.BLACK);
		}
		if (id == R.id.rl_video_selection) {
			iv_chance.setVisibility(View.VISIBLE);
			tv_chance.setTextColor(getResources().getColor(R.color.blue));
		} else {
			iv_chance.setVisibility(View.GONE);
			tv_chance.setTextColor(Color.BLACK);
		}
	}
	
	/***
	 * 收藏、取消收藏操作
	 */
	private void focus() {
		if (comFunction.isWiFi_3G(this)) {
			if (focusTask == null) {
				focusTask = new FocusTask();
				if (IS_COLLECT) {
					focusTask.execute("add_attention_video");
				} else {
					focusTask.execute("cancel_attention_video");
				}
			}
		} else {
			comFunction.toastMsg(getString(R.string.toast_net_link), this);
		}
	}

	/***
	 * 获取数据异步
	 * 
	 * @author LinYuLing
	 * 
	 */
	private class FocusTask extends AsyncTask<String, Void, Void> {
		/***
		 * 等待进度框
		 */
		private ProgressDialog pd;
		private JSONObject jobj;
		private List<NameValuePair> paramsList;
		// /***
		// * 服务器返回类型值 200：成功
		// */
		// private String code;
		/***
		 * 服务器返回提示内容值
		 */
		private String message = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(VideoDesActivity.this);
			pd.setIndeterminate(true);
			pd.setCancelable(true);
			pd.setMessage(getString(R.string.pd_data_upload));
			pd.show();
			paramsList = new ArrayList<NameValuePair>();
			paramsList.add(new BasicNameValuePair("Id", id));
			paramsList.add(new BasicNameValuePair("userId", iSPreferences
					.getSp().getString("memberId", "")));
		}

		@Override
		protected Void doInBackground(String... parms) {
			// TODO Auto-generated method stub
			String requery = comFunction.getDataFromServer(parms[0],
					paramsList, VideoDesActivity.this);
			System.out.println("requery: " + requery);
			try {
				jobj = new JSONObject(requery);
				if (jobj == null) {
					return null;
				}
				// code = jobj.getString("code");
				message = jobj.getString("message");
				String data = jobj.getString("data");
				if (!data.equals("1")) {
					IS_COLLECT = !IS_COLLECT;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			focusTask = null;
			if (pd.isShowing()) {
				pd.dismiss();
			}
			if (message != null) {
				if (IS_COLLECT) {
					iv_collect.setBackgroundResource(R.drawable.img_video_collect);
				} else {
					iv_collect.setBackgroundResource(R.drawable.img_video_collect);
				}
				comFunction.toastMsg(message, VideoDesActivity.this);
			} else {
				comFunction.toastMsg(getString(R.string.toast_net_link),
						VideoDesActivity.this);
			}
			super.onPostExecute(result);
		}
	}
}

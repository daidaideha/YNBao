package com.innouni.yinongbao.activity.person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.cache.ImageLoader;
import com.innouni.yinongbao.widget.IntentToOther;
import com.innouni.yinongbao.widget.sPreferences;

/**
 * 个人中心-->个人资料
 * 
 * @author Hugj
 * 
 */
public class UserInfoActivity extends Activity implements OnClickListener {

	private static int RESULT_MODIFY_NAME = 0; // 调用相机拍照后的请求码
	private static int RESULT_LOAD_IMAGE = 1; // 从图库加载图片后的请求码
	private static int RESULT_TAKE_PHOTO = 2; // 调用相机拍照后的请求码
	private File imageFile; // 上传到服务器的图片文件

	private sPreferences iSPreferences;
	private PopupWindow dialog;
	private RelativeLayout backLayout;
	private TextView titleView;
	private ImageButton submitButton;
	private ImageView headView;
	private TextView nameView, realNameView;
	
	private SubmitTask submitTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_information);
		iSPreferences = new sPreferences(this);

		/**
		 * 初始化TitleBar
		 */
		backLayout = (RelativeLayout) findViewById(R.id.rl_header_back);
		backLayout.setOnClickListener(this);
		titleView = (TextView) findViewById(R.id.tv_header_title);
		titleView.setText("编辑资料");
		submitButton = (ImageButton) findViewById(R.id.ibnt_person_namedone);
		submitButton.setOnClickListener(this);

		/**
		 * 设置用户信息
		 */
		findViewById(R.id.txt_modify).setOnClickListener(this);
		headView = (ImageView) findViewById(R.id.img_person_headview);
		ImageLoader loader = new ImageLoader(this);
		loader.DisplayImage(iSPreferences.getStringValues("avatar"), headView,
				false);
		nameView = (TextView) findViewById(R.id.txt_person_usernameedit);
		nameView.setText(iSPreferences.getStringValues("username"));
		nameView.setOnClickListener(this);
		realNameView = (TextView) findViewById(R.id.txt_person_realnameedit);
		realNameView.setText(iSPreferences.getStringValues("realname"));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_header_back:
			finish();
			break;
		case R.id.ibnt_person_namedone:
			submitModifyInfo();
			break;
		case R.id.txt_modify:
			showPop();
			break;
		case R.id.txt_person_usernameedit:
			// 修改用户名
			Intent intent = new Intent(UserInfoActivity.this, ModifyUserNameActivity.class);
			startActivityForResult(intent, RESULT_MODIFY_NAME);
			break;
		case R.id.tv_album:
			// 从相册选择
			dialog.dismiss();
			Intent intentAlbum = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intentAlbum, RESULT_LOAD_IMAGE);
			break;
		case R.id.tv_photo:
			// 拍照
			dialog.dismiss();
			Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intentPhoto, RESULT_TAKE_PHOTO);
			break;
		case R.id.tv_cancel:
			// 取消
			dialog.dismiss();
			break;
		}
	}

	/**
	 * 提交修改信息
	 */
	private void submitModifyInfo() {
		if (submitTask != null) {
			submitTask.cancel(true);
		}
		submitTask = new SubmitTask();
		submitTask.execute();
	}

	/**
	 * 修改头像
	 */
	private void showPop() {
		LinearLayout layout = (LinearLayout) LayoutInflater.from(
				UserInfoActivity.this).inflate(R.layout.pop_modify_head, null);
		dialog = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		dialog.setOutsideTouchable(true);
		dialog.setFocusable(true);
		dialog.setBackgroundDrawable(new BitmapDrawable());
		dialog.setAnimationStyle(android.R.style.Animation_Dialog);
		dialog.showAtLocation(findViewById(R.id.img_person_headview),
				Gravity.BOTTOM, 0, 0);
		layout.findViewById(R.id.tv_album).setOnClickListener(this);
		layout.findViewById(R.id.tv_photo).setOnClickListener(this);
		layout.findViewById(R.id.tv_cancel).setOnClickListener(this);

		WindowManager.LayoutParams attribute = getWindow().getAttributes();
		attribute.gravity = Gravity.TOP;
		attribute.alpha = 0.5f;
		getWindow().setAttributes(attribute);
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				WindowManager.LayoutParams attribute = getWindow()
						.getAttributes();
				attribute.gravity = Gravity.TOP;
				attribute.alpha = 1.0f;
				getWindow().setAttributes(attribute);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && data != null) {
			if (requestCode == RESULT_LOAD_IMAGE) {
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(data.getData(),
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				String imagePath = cursor.getString(cursor
						.getColumnIndex(filePathColumn[0]));
				cursor.close();
				imageFile = new File(imagePath);
				headView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
			} else if (requestCode == RESULT_TAKE_PHOTO) {
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					Bitmap bitmap = (Bitmap) data.getExtras().get("data");
					// 在SD卡根目录下创建文件夹ynb用于保存照片
					File file = new File(Environment
							.getExternalStorageDirectory().toString(), "ynb");
					if (!file.exists())
						file.mkdirs();
					String fileName = new DateFormat().format(
							"yyyyMMdd_HHmmss",
							Calendar.getInstance(Locale.CHINA))
							+ ".jpg";
					String imagePath = file.getAbsolutePath() + "/" + fileName;
					FileOutputStream fout = null;
					try {
						fout = new FileOutputStream(imagePath);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fout);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} finally {
						try {
							fout.flush();
							fout.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						bitmap.recycle();
					}
					imageFile = new File(imagePath);
					headView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
				} else {
					Toast.makeText(UserInfoActivity.this, "SD卡不可用",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	
	/**
	 * 上传修改信息异步任务
	 */
	private class SubmitTask extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			finish();
		}
	}
}

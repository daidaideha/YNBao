package com.innouni.yinongbao.widget;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Toast;

import com.innouni.yinongbao.R;

@SuppressLint("SimpleDateFormat")
public class comFunction {
	public static TelephonyManager phoneMgr;
	private static File file;

	/** 判断字符串是否为�? **/
	public static boolean isNullorSpace(String str) {
		if ((str != null) && !"".equals(str.trim()))
			return false;
		return true;
	}

	/**
	 * 清除本应用内部缓�?/data/data/com.xxx.xxx/cache)
	 * 
	 * @param context
	 */
	public static void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
	}

	/**
	 * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
	 * 
	 * @param directory
	 */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}

	public static boolean isFileExist(String filepath) {
		File f = new File(filepath);
		if (!f.exists())
			return false;
		return true;
	}

	public static void mkFilePath(String filePath) {
		file = new File(filePath);
		if (!file.exists())
			file.mkdirs();
	}

	public static String getDate() {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String time = sdformat.format(date);
		return time;
	}

	/** 判断wifi 3g 是否正常打开 **/
	public static boolean isWiFi_3G(Context inContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) inContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo == null)
			return false;
		if (!activeNetInfo.isAvailable())
			return false;
		if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI)
			return true;
		if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE)
			return true;
		if (activeNetInfo.getState() == NetworkInfo.State.CONNECTED)
			return true;
		return false;
	}

	/** 判断字符串是否为Email **/
	public static boolean isEmail(String email) {
		if (email.matches("\\w+([-+.']\\w+)*@\\w+\\.\\w+([-.]\\w+)*")
				&& email.length() > 0)
			return true;
		return false;
	}

	/** 判断字符串是否为mobile **/
	public static boolean isMobile(String mobile) {
		if (mobile.matches("1[0-9]{10}"))
			return true;
		return false;
	}

	/** 读写服务�? **/
	public static String query(String weburl, String action,
			List<NameValuePair> params, Context context) {
		return httpUtil.queryStringForPost(weburl, action, params, context);
	}

	/** 读写服务�? **/
	public static String queryForLong(String weburl,
			List<NameValuePair> params, Context context) {
		return httpUtil.queryStringForPostForLong(weburl, params, context);
	}

	/** 读写服务�? **/
	public static String queryForGet(String weburl) {
		return httpUtil.getResultForHttpGet(weburl);
	}

	public static void toastMsg(String msg, Context context) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void downloadMapByBitmap(String fromPath, String savePath) {
		File file = new File(savePath);
		// 判断是否已经存在
		if (!file.exists()) {
			saveBitmap(fromPath, createBitmapByUrl(fromPath), savePath);
		}
	}

	public static Bitmap downAndSaveBitmap(String fromPath, String savePath) {
		Bitmap bmp = createBitmapByUrl(fromPath);
		saveBitmap(fromPath, bmp, savePath);
		return bmp;
	}

	public static Bitmap downloadFileForBitmap(String fileUrl, String savePath) {
		Bitmap bmp = null;
		// error.clear();
		File file = new File(savePath);
		// 判断是否已经存在
		if (!file.exists()) {
			URL url = null;
			HttpURLConnection conn = null;
			DataInputStream dis = null;
			DataOutputStream dos = null;
			try {
				url = new URL(fileUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(0);
				dis = new DataInputStream(conn.getInputStream());
				dos = new DataOutputStream(new FileOutputStream(savePath));
				byte[] buffer = new byte[dis.available()];
				int count = 0;
				while ((count = dis.read(buffer)) > 0) {
					dos.write(buffer, 0, count);
				}
				dos.close();
				dis.close();
			} catch (SocketTimeoutException ste) {
				/*
				 * if(!debugMode){ error.add("网络连接超时,请检查网�?); }else{
				 * error.add("[downloadFile1]:网络连接超时,请检查网�? + "\r\n" +
				 * ste.getMessage()); }
				 */
			} catch (Exception e) {
				// TODO: handle exception
				/*
				 * if(!debugMode){ error.add(e.getMessage()); }else{
				 * error.add("[downloadFile2]:" + e.getMessage()); }
				 */
			} finally {
				if (conn != null)
					conn.disconnect();
				bmp = createBitmapByLocal(savePath);
				// Message msg = NewsListActivity.myhHandler.obtainMessage();
				// Bundle bundle = new Bundle();
				// bundle.putBoolean("update", true);
				// bundle.putString("path", savePath);
				// msg.setData(bundle);
				// NewsListActivity.myhHandler.sendMessage(msg);
			}
		}
		return bmp;
	}

	public static Bitmap createBitmapByLocal(String strUrl) {
		// Log.i("tag", "tagsp="+strUrl);
		if (comFunction.isNullorSpace(strUrl))
			return null;
		final Options options = new Options();
		try {
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeFile(strUrl, options);
		} catch (Exception e) {
		} finally {
		}
		return null;
	}

	public static Bitmap createBitmapByUrl(String strUrl) {
		// URL url = null;
		// HttpURLConnection conn = null;
		InputStream inStream = null;
		final Options options = new Options();
		try {
			// url = new URL(strUrl);
			HttpGet httpRequest = new HttpGet(strUrl);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = (HttpResponse) httpclient
					.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
					entity);
			inStream = bufferedHttpEntity.getContent();
			// bitmap = BitmapFactory.decodeStream(is);

			// conn = (HttpURLConnection)url.openConnection();
			// conn.setConnectTimeout(TIMEOUT);
			// inStream = conn.getInputStream();
			// options.inSampleSize = inSampleSize;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inJustDecodeBounds = false;
			return BitmapFactory.decodeStream(inStream, null, options);
		} catch (SocketTimeoutException ste) {
		} catch (Exception e) {
		} finally {
			closeInputStream(inStream);
			// if(conn != null) conn.disconnect();
		}
		return null;
	}

	public static Bitmap createBitmapForImageGetter(String strUrl,
			Options options) {
		InputStream inStream = null;
		try {
			HttpGet httpRequest = new HttpGet(strUrl);
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = (HttpResponse) httpclient
					.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
					entity);
			inStream = bufferedHttpEntity.getContent();
			return BitmapFactory.decodeStream(inStream, null, options);
		} catch (SocketTimeoutException ste) {
		} catch (Exception e) {
		} finally {
			closeInputStream(inStream);
			// if(conn != null) conn.disconnect();
		}
		return null;
	}

	public static void saveBitmap(String url, Bitmap bitmap, String savePath) {
		BufferedOutputStream outStream = null;
		try {
			outStream = new BufferedOutputStream(new FileOutputStream(savePath));
			if (!url.substring(url.lastIndexOf("/") + 1).toUpperCase()
					.contains("PNG")) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
			} else {
				bitmap.compress(Bitmap.CompressFormat.PNG, 80, outStream);
			}
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
		} finally {
			// if(bitmap != null)
			// bitmap.recycle();
		}
	}

	public static void saveBitmapForJPEG(Bitmap bitmap, String savePath) {
		BufferedOutputStream outStream = null;
		try {
			outStream = new BufferedOutputStream(new FileOutputStream(savePath));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
		} finally {
			// if(bitmap != null)
			// bitmap.recycle();
		}
	}

	@SuppressLint("NewApi")
	public static long getBitmapsize(Bitmap bitmap) {
		if (bitmap == null) {
			return 0;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		}
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();

	}

	private static void closeInputStream(InputStream in) {
		if (null != in) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下载网络文件(包括图片�?
	 * 
	 * @param fileUrl
	 *            文件url地址
	 * @param savePath
	 *            本地保存地址
	 */
	public static void downloadFile(String fileUrl, String savePath,
			Context context, Handler handler) {
		URL url = null;
		HttpURLConnection conn = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			url = new URL(fileUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(0);
			dis = new DataInputStream(conn.getInputStream());
			dos = new DataOutputStream(new FileOutputStream(savePath));
			byte[] buffer = new byte[dis.available()];
			int max = conn.getContentLength();
			int count = 0;
			// mRemoteViews = new RemoteViews(context.getPackageName(),
			// R.layout.download);
			while ((count = dis.read(buffer)) > 0) {
				dos.write(buffer, 0, count);
				int size = dos.size();
				Message msg = handler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putInt("max", max);
				bundle.putInt("size", size);
				msg.setData(bundle);
				handler.sendMessage(msg);
				// sendMsg("title", "now: ", context, size, max);
			}
			dos.close();
			dis.close();
		} catch (SocketTimeoutException ste) {
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}

	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	public static String getDataFromServer(String action,
			List<NameValuePair> paramsList, Context context) {
		String requery = comFunction.query(context.getString(R.string.app_web_url),
				action, paramsList, context);
		if (requery == null)
			requery = null;
		if ("".equals(requery))
			requery = null;
		if (requery.equals("net_err")) {
			requery = null;
		} else if (requery.equals("{\"list\":[]}")) {
			requery = null;
		} else if (requery.equals("{\"list\":false}")) {
			requery = null;
		}
		return requery;
	}

	public static String getDataFromServerForLong(
			List<NameValuePair> paramsList, Context context, String action) {
		String requery = comFunction.queryForLong(
				context.getString(R.string.app_web_url) + action, paramsList,
				context);
		if (requery == null)
			requery = null;
		if ("".equals(requery))
			requery = null;
		if (requery.equals("net_err")) {
			requery = null;
		} else if (requery.equals("{\"list\":[]}")) {
			requery = null;
		} else if (requery.equals("{\"list\":false}")) {
			requery = null;
		}
		return requery;
	}

	public static String getDataFromServerForGet(String weburl) {
		String requery = comFunction.queryForGet(weburl);
		if (requery == null)
			return null;
		if ("".equals(requery)) {
			return null;
		} else if (requery.equals("net_err")) {
			return null;
		} else if (requery.equals("{\"list\":[]}")) {
			return null;
		} else if (requery.equals("{\"list\":false}")) {
			return null;
		}
		return requery;
	}

//	public static SQLiteDatabase dbOpen(Context context) {
//		return SQLiteDatabase.openDatabase(
//				context.getString(R.string.app_path_db_save), null, 0);
//	}

	/***
	 * 获取视图的宽度
	 * @param v 根视图
	 * @return 视图宽度
	 */
	public static int getViewWidth(View v) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		v.measure(w, h);
		return v.getMeasuredWidth();
	}

	/***
	 * 获取视图的高度
	 * @param v 根视图
	 * @return 视图高度
	 */
	public static int getViewHeight(View v) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		v.measure(w, h);
		return v.getMeasuredHeight();
	}

	/***
	 * 视图转换成bitmap对象
	 * @param view 需要转换的视图
	 * @return 转换后的bitmap对象
	 */
	public static Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return 距离：单位为米
	 */
	public static double DistanceOfTwoPoints(double lat1, double lng1,
			double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

//	/***
//	 * 用来存储服务器返回会的json数据
//	 */
//	private static JSONObject jsonObject = null;
//	
//	/***
//	 * 讲map对象转换成JsonObject对象
//	 * @param map 传入的map对象
//	 * @return 转换后的JsonObject对象
//	 */
//	public static JSONObject map2JSONObject(Map<String, String> map) {
//		JSONObject jsonObject = null;
//		if (map != null) {
//			jsonObject = new JSONObject(map);
//		}
//		return jsonObject;
//	}

//	/***
//	 * 与服务器通信，获取数据信息
//	 * @param context 上下文对象
//	 * @param url 链接地址
//	 * @param response 传入的参数（无参数可设置为null）
//	 * @return 请求服务器返回的json数据
//	 */
//	public static JSONObject getDataFromService(Context context, String url, JSONObject response) {
//		jsonObject = null;
//		RequestQueue mQueue = Volley.newRequestQueue(context);
//		mQueue.add(new JsonObjectRequest(Method.POST, url, response,
//				new Response.Listener<JSONObject>() {
//					@Override
//					public void onResponse(JSONObject response) {
//						// TODO Auto-generated method stub
//						jsonObject = response;
//					}
//				}, 
//				new Response.ErrorListener() {
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						// TODO Auto-generated method stub
//						jsonObject = null;
//					}
//				}));
//		mQueue.start();
//		return jsonObject;
//	}
}

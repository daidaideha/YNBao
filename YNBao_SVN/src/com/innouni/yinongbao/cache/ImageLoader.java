package com.innouni.yinongbao.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.innouni.yinongbao.R;
import com.innouni.yinongbao.widget.comFunction;

public class ImageLoader {

	private MemoryCache memoryCache = new MemoryCache();
	private AbstractFileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	// �̳߳�
	private ExecutorService executorService;
	private Context context;
	private boolean flag = false;
	private float width;
	private float height;

	public ImageLoader(Context context) {
		this.context = context;
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5);
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	//
	public void DisplayImage(String url, ImageView imageView,
			boolean isLoadOnlyFromCache) {
		imageViews.put(imageView, url);
		//

		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setImageBitmap(bitmap);
		} else if (!isLoadOnlyFromCache) {

			//
			queuePhoto(url, imageView);
		}
	}

	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p));
	}

	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// �ȴ��ļ������в����Ƿ���
		Bitmap b = null;
		if (f != null && f.exists()) {
			b = decodeFile(f);
		}
		if (b != null) {
			return b;
		}
		// ����ָ����url������ͼƬ
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (Exception ex) {
			Log.e("",
					"getBitmap catch Exception...\nmessage = "
							+ ex.getMessage());
			return null;
		}
	}

	// decode���ͼƬ���Ұ����������Լ����ڴ���ģ�������ÿ��ͼƬ�Ļ����СҲ�������Ƶ�?
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 200;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE
						|| height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			// o2.inPreferredConfig = Bitmap.Config.ARGB_8888;
			// o2.inPurgeable = true;
			// o2.inInputShareable = true;
			// o2.inJustDecodeBounds = false;
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			Bitmap bmp = null;
			if (!photoToLoad.url.trim().replace("null", "").equals("")) {
				String[] tmp_str = photoToLoad.url.trim().split("/");
				// Log.i("tag","tag tmp="+value);
				if (tmp_str.length == 1) {
					bmp = BitmapFactory.decodeResource(context.getResources(),
							Integer.valueOf(photoToLoad.url));
				} else {
					if (comFunction.isFileExist(photoToLoad.url)) {
						bmp = comFunction.createBitmapByLocal(photoToLoad.url);
					} else {
						bmp = getBitmap(photoToLoad.url);
					}
				}
			} else {
//				bmp = BitmapFactory.decodeResource(context.getResources(),
//						R.drawable.default_images);
			}
			memoryCache.put(photoToLoad.url, bmp);
			if (imageViewReused(photoToLoad))
				return;
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	/**
	 * 
	 * 
	 * @param photoToLoad
	 * @return
	 */
	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imageViewReused(photoToLoad))
				return;
			if (bitmap != null) {
				if (flag) {
					bitmap = big(bitmap, width, height);
					// photoToLoad.imageView.setScaleType(ScaleType.MATRIX);
				} else {
					photoToLoad.imageView.setScaleType(ScaleType.FIT_XY);
				}
			}
			photoToLoad.imageView.setImageBitmap(bitmap);

		}
	}

	public static Bitmap big(Bitmap b, float x, float y) {
		int w = b.getWidth();
		int h = b.getHeight();
		float sx = 0;
		if (y == 0 && x != 0) {
			sx = (float) x / w;
		} else if (x == 0 && y != 0) {
			sx = (float) y / h;
		}
		// 要强制转换，不转换我的在这总是死掉。
		// float sy = (float) sx * h;
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sx); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w, h, matrix, true);
		return resizeBmp;
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
			Log.e("", "CopyStream catch Exception...");
		}
	}
}

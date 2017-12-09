package com.dongkesoft.iboss.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.dongkesoft.iboss.constant.Constants;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： ImageUtilsz
 *		2.功能描述：图片工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
@SuppressLint("HandlerLeak")
public class ImageUtilsz {
	public static LinkedHashMap<String, SoftReference<Bitmap>> imageCache = new LinkedHashMap<String, SoftReference<Bitmap>>(
			20);

	/**
	 * 异步加载图片
	 * 
	 * @param context
	 *            context
	 * @param imgView
	 *            imageview控件
	 * @param url
	 *            加载地址
	 * 
	 */
	public static void setBitmap(Context context, ImageView imgView, String url) {
		if (url == null) {
			return;
		}

		if (url.equals("")) {
			return;
		}
		if (url.equals("null")) {
			return;
		}
		try {
			imgView.setTag(url);
			ImageUtilsz iu = new ImageUtilsz();
			Bitmap bitmap;
			// 软引用集合中查找
			bitmap = iu.findBitmapAtCache(url);
			if (bitmap != null) {
				setBitmapToImgView(imgView, bitmap, url);
				return;
			}

			int index = url.lastIndexOf("/");
			String filename = url.substring(index + 1, url.length());
			String path = Constants.SDCARD + filename;
			bitmap = PicUtil.convertToBitmap(path);
			if (bitmap != null && bitmap.getWidth() > 0) {
				setBitmapToImgView(imgView, bitmap, url);
				iu.putBitmapToCache(url, bitmap);
				return;
			}

			SharedPreferences sp = context.getSharedPreferences("setting",
					Context.MODE_PRIVATE);
			boolean picisSetting = sp.getBoolean("picsetting", true);
			if (picisSetting) {
				iu.setBitmapFromNet(context, imgView, url);
			}

		} catch (Exception e) {
		}
	}

	/**
	 * 不经过软引用的查找直接访问网络获取图片用于刷新验证码 异步加载图片
	 * 
	 * @param context
	 *            context
	 * @param imgView
	 *            imageview控件
	 * @param url
	 *            加载地址
	 * 
	 */
	public static void setBitmap2(Context context, ImageView imgView, String url) {
		ImageUtilsz iu = new ImageUtilsz();
		iu.setBitmapFromNet(context, imgView, url);
	}

	/**
	 * 从网络获取
	 * 
	 * @param context
	 * @param imgview
	 * @param imgUrl
	 * @param tag
	 */

	private void setBitmapFromNet(final Context context,
			final ImageView imgview, final String imgUrl) {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.obj != null) {
					final Bitmap bitmap = (Bitmap) msg.obj;
					setBitmapToImgView(imgview, bitmap, imgUrl);
				}
			}
		};
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {

					URL url = new URL(imgUrl);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setConnectTimeout(3000);
					conn.setReadTimeout(3000);
					conn.connect();

					InputStream in = conn.getInputStream();
					BitmapFactory.Options options = new Options();
					options.inSampleSize = 1;// 缩放比例
					Bitmap bitmap = BitmapFactory.decodeStream(in, null,
							options);

					conn.disconnect();
					in.close();
					putBitmapToCache(imgUrl, bitmap);// 保存到软引用中
					int index = imgUrl.lastIndexOf("/");
					String filename = imgUrl.substring(index + 1,
							imgUrl.length());
					PicUtil.savaBitmapToSd(bitmap, filename);
					Message msg = handler.obtainMessage();
					msg.obj = bitmap;
					handler.sendMessage(msg);

				} catch (MalformedURLException e) {
					// e.printStackTrace();
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		};
		ThreadPoolManager.getInstance().addTask(runnable);
	}

	// 显示图片
	public static void setBitmapToImgView(ImageView imgView, Bitmap bitmap,
			String url) {
		String urln = (String) imgView.getTag();
		if (urln.equals(url)) {
			// ImageUtilsyuanjiao.toRoundCorner(bitmap, 10);
			// imgView.setImageBitmap(FileUtil.getRoundedCornerBitmap(bitmap,
			// 8));
			imgView.setImageBitmap(bitmap);
		} else {
			imgView.setImageBitmap(null);
		}

	}

	/**
	 * 软引用中查找
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap findBitmapAtCache(String url) {
		if (imageCache.containsKey(url)) {
			SoftReference<Bitmap> reference = imageCache.get(url);
			Bitmap bitmap = (Bitmap) reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		}
		return null;
	}

	/**
	 * 将bitmap 加入缓存
	 * 
	 * @param url
	 * @param bitmap
	 */
	private void putBitmapToCache(String imgUrl, Bitmap bitmap) {
		SoftReference<Bitmap> sf = new SoftReference<Bitmap>(bitmap);
		imageCache.put(imgUrl, sf);
		bitmap = null;
		System.gc();
	}
}

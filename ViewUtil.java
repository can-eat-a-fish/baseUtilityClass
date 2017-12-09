package com.dongkesoft.iboss.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.dongkesoft.iboss.R;
import com.dongkesoft.iboss.activity.photo.ImagePagerActivity;
import com.dongkesoft.iboss.model.ImagePath;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： ViewUtil
 *		2.功能描述：
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class ViewUtil {
	private static int maxImageSize = 400;

	@SuppressWarnings("deprecation")
	public static String CompressImage(String picturepath, int maximumPixels,
			Context con) {
		Context context = con;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeFile(picturepath, opts);
		opts.inSampleSize = computeSampleSize(opts, -1, maximumPixels);
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inDither = false;
		opts.inTempStorage = new byte[16 * 1024];
		FileInputStream is = null;
		FileOutputStream out = null;
		Bitmap bmp = null;
		try {
			is = new FileInputStream(picturepath);
			bmp = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
			int degree = RotateBitmap.readPictureDegree(picturepath);
			bmp = RotateBitmap.rotaingImageView(degree, bmp);
			String fil = CommonUtil.getRandomFilename(context) + ".jpg";
			String pPath = CommonUtil.getRootFilePath();
			String fileDir = pPath + CommonUtil.DATA_CACHE_PATH;
			File f = new File(fileDir);
			if (!f.exists()) {
				f.mkdir();
			}
			picturepath = fileDir + fil;
			out = new FileOutputStream(picturepath);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return picturepath;
	}

	public int getMaxImageSize() {
		return maxImageSize;
	}

	@SuppressWarnings("static-access")
	public void setMaxImageSize(int maxImageSize) {
		this.maxImageSize = maxImageSize;
	}

	public static Bitmap decodeFile(File f) {
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < maxImageSize
						|| height_tmp / 2 < maxImageSize) {

					break;
				}
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	public static Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}

	public static void imageBrower(int position, List<ImagePath> urls2,
			Context context) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		Bundle b = new Bundle();
		b.putSerializable(ImagePagerActivity.EXTRA_IMAGE_URLS,
				(Serializable) urls2);
		b.putInt(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		intent.putExtras(b);
		
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.s, R.anim.r);  
	}

	// public static void EnlargeImage(String imagePath,int position,String
	// url,Context c){
	// Intent i=new Intent(c,ViewPagerActivity.class);
	// Bundle mBundle=new Bundle();
	// mBundle.putString("imagepath",imagePath);
	// mBundle.putString("imageUrl",url);
	// mBundle.putInt("position",position);
	// i.putExtras(mBundle);
	// ((Activity) c). startActivityForResult(i,100);
	//
	// }

	@SuppressWarnings({ "deprecation", "unused" })
	public static byte[] CompressImageByte(String picturepath,
			int maximumPixels, Context con) {
		Context context = con;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeFile(picturepath, opts);
		// opts.inSampleSize = computeSampleSize(opts, -1, maximumPixels);
		opts.inSampleSize = 2;
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inDither = false;
		opts.inTempStorage = new byte[16 * 1024];
		FileInputStream is = null;
		ByteArrayOutputStream out = null;
		Bitmap bmp = null;
		try {
			is = new FileInputStream(picturepath);
			bmp = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
			int degree = RotateBitmap.readPictureDegree(picturepath);
			bmp = RotateBitmap.rotaingImageView(degree, bmp);
			out = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);

			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				is.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return out.toByteArray();

	}

	public static void FreeBitmap(HashMap<Integer, Bitmap> cache) {
		if (cache.isEmpty()) {
			return;
		}
		for (Bitmap bitmap : cache.values()) {
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();

			}
		}
		cache.clear();
	}

	@SuppressWarnings("deprecation")
	public static Bitmap Compress(String picturepath, int maximumPixels) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeFile(picturepath, opts);
		opts.inSampleSize = computeSampleSize(opts, -1, maximumPixels);
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inDither = false;
		opts.inTempStorage = new byte[16 * 1024];
		FileInputStream is = null;
		Bitmap bmp = null;
		try {
			is = new FileInputStream(picturepath);
			bmp = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				
				is.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return bmp;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	public static void EnlargeImage(String imagePath, int position, String url,
			Context c) {
		 

	}

	// picturepath转换成二进制
	public static byte[] getBitmapByte(String picturepath) {
		Bitmap bitmap = BitmapFactory.decodeFile(picturepath);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	// picturepath转换成二进制(缩略图)
	public static byte[] getCompressBitmapByte(String picturepath,
			int maximumPixels) {
		Bitmap bitmap = Compress(picturepath, maximumPixels);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
}

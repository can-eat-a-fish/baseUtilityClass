package com.dongkesoft.iboss.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore.Images.ImageColumns;

public class BitmapUtils {
	public static final String destFileDir = Environment
			.getExternalStorageDirectory() + "/dongKeUser/";


	
	/**
	 * 根据Uri获得路径
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}
	/**
	 * 递归 获取文件夹大小
	 */
	@SuppressWarnings("unused")
	private static long getFileSize(File file) {
		long size = 0;
		File flist[] = file.listFiles();
		if (flist == null) {
			return 0;
		}
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}
	/**
	 * 压缩指定高宽的bitmap
	 * 
	 * @param orgBitmap
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap getZoomImage(Bitmap orgBitmap, double newWidth,
			double newHeight) {
		if (null == orgBitmap) {
			return null;
		}
		if (orgBitmap.isRecycled()) {
			return null;
		}
		if (newWidth <= 0 || newHeight <= 0) {
			return null;
		}

		// 获取图片的宽和高
		float width = orgBitmap.getWidth();
		float height = orgBitmap.getHeight();
		// 创建操作图片的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(orgBitmap, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
			return bitmap.getAllocationByteCount();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API
																			// 12
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
	}

	/**
	 * 压缩图片（质量压缩）
	 * 
	 * @param bitmap
	 */
	public static File compressImage(Object object) {
		Bitmap bitmap = null;
		if (object instanceof Bitmap) {
			bitmap = (Bitmap) object;
		} else if (object instanceof String) {
			String string = (String) object;
			BitmapFactory.Options options = new BitmapFactory.Options();
			long size = new File(string).length();
			if(size * 1.0 / 1024 / 1024 > 1){
				options.inSampleSize = 4;
			}
			bitmap = BitmapFactory.decodeFile(string, options);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if(baos.toByteArray().length / 1024 > 2048){
			bitmap = getZoomImage(bitmap, bitmap.getWidth() / 3,
					bitmap.getHeight() / 3);
		}
		baos.reset();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > 150) { // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/IBossApp");
		if(!file.exists()){
			file.mkdirs();
		}
		File file2 = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/IBossApp/"+System.currentTimeMillis() + ".png");
		if(!file2.exists()){
			try {
				file2.createNewFile();
			} catch (IOException e) {		
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(file2);
			try {
				fos.write(baos.toByteArray());
				fos.flush();
				fos.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		recycleBitmap(bitmap);
		return file2;
	}

	public static void recycleBitmap(Bitmap... bitmaps) {
		if (bitmaps == null) {
			return;
		}
		for (Bitmap bm : bitmaps) {
			if (null != bm && !bm.isRecycled()) {
				bm.recycle();
			}
		}
	}
	/**
	 * 判断文件是否能形成bitmap
	 * @param path
	 * @return
	 */
	public static boolean isBitmap(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();

		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		if (bitmap == null) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 路径转为Bitmap
	 * @param path
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Bitmap getSDCardImg(String imagePath) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		return BitmapFactory.decodeFile(imagePath, opt);
	}


}

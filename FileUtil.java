package com.dongkesoft.iboss.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.util.Log;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： FileUtil
 *		2.功能描述：文件工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class FileUtil {
	public static final String RECORD_SAVE_PATH = "/iboss/record";
	public static final String TAKE_PHOTO_PATH = "/iboss";

	private static final String TAG = "FileUtil";

	public static String getRecordFilePath(String filename) {
		File f = new File(filename);
		if (f.exists()) {
			return filename;
		}

		return android.os.Environment.getExternalStorageDirectory()
				+ FileUtil.RECORD_SAVE_PATH + "/" + filename;
	}

	public static String getTimeFromInt(int time) {

		if (time <= 0) {
			return "0′0″";
		}
		int secondnd = (time / 1000) / 60;

		String f = String.valueOf(secondnd);

		if (secondnd < 1) {
			int million = (time / 1000) % 60;
			String m = String.valueOf(million);
			return m + "″";
		} else {
			int million = (time / 1000) % 60;
			String m = String.valueOf(million);
			if (million == 0) {
				return f + "′";
			} else {
				return f + "′" + m + "″";
			}

		}
	}

	public static File getCacheFile(String imageUri) {
		File cacheFile = null;
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				String fileName = getFileName(imageUri);
				File dir = new File(sdCardDir.getCanonicalPath() + "yyuts");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				cacheFile = new File(dir, fileName);
				Log.i(TAG, "exists:" + cacheFile.exists() + ",dir:" + dir
						+ ",file:" + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "getCacheFileError:" + e.getMessage());
		}

		return cacheFile;
	}

	public static String getFileName(String path) {
		int index = path.lastIndexOf("/");
		return path.substring(index + 1);
	}

	/**
	 * @param folderPath
	 *            删除文件夹
	 */
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String createFile(Context context, Bitmap mSignBitmap) {
		// ByteArrayOutputStream baos = null;
		String _path = null;
		FileOutputStream out;
		try {
			String pPath = getRootFilePath();
			String fileDir = pPath + TAKE_PHOTO_PATH;
			File file = new File(fileDir);
			if (!file.exists()) {
				file.mkdir();
			}
			String filep = getRandomFilename(context) + ".jpg";
			File f = new File(file.getAbsolutePath(), filep);
			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			_path = f.getAbsolutePath();

			out = new FileOutputStream(f);
			mSignBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return _path;
	}

	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	public static String getRootFilePath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "";
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data";
		}
	}

	public static String getRandomFilename(Context context) {

		return UUID.randomUUID().toString();

	}

	/**
	 * @param path
	 * @return 删除文件夹下内容
	 */
	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 将图片截取为圆角图片
	 * 
	 * @param bitmap
	 *            原图片
	 * @param ratio
	 *            截取比例，如果是8，则圆角半径是宽高的1/8，如果是2，则是圆形图片
	 * @return 圆角矩形图片
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float ratio) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
				bitmap.getHeight() / ratio, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

}

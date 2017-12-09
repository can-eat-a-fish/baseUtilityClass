package com.dongkesoft.iboss.utils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： CameraImage
 *		2.功能描述：图片工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class CameraImage {

	/**
	 * 调用照相功能
	 * */
	public static void imageCaptureAction(Activity context, int requestCode) {
		Uri imageUri = null;
		String fileName = null;
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileName = "image.jpg";

		String pPath = CommonUtil.getRootFilePath();
		String fileDir = pPath + CommonUtil.TAKE_PHOTO_PATH;
		File file = new File(fileDir);
		if (!file.exists()) {
			file.mkdir();
		}
		imageUri = Uri.fromFile(new File(fileDir, fileName));
		// 指定照片保存路径，image.jpg为一个临时文件，每次拍照后这个图片都会被替换
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		context.startActivityForResult(openCameraIntent, requestCode);
	}

	public static void imageCaptureFragmentAction(Fragment context,
			int requestCode) {
		Uri imageUri = null;
		String fileName = null;
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileName = "image.jpg";

		String pPath = CommonUtil.getRootFilePath();
		String fileDir = pPath + CommonUtil.TAKE_PHOTO_PATH;
		File file = new File(fileDir);
		if (!file.exists()) {
			file.mkdir();
		}
		imageUri = Uri.fromFile(new File(fileDir, fileName));
		// 指定照片保存路径，image.jpg为一个临时文件，每次拍照后这个图片都会被替换
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		context.startActivityForResult(openCameraIntent, requestCode);
	}

	/*
	 * 
	 * 照相成功后，回调结果
	 */
	@SuppressWarnings("unused")
	public static File onActivityResult(Context context, int scale) {
		int SCALE = scale;// 照片缩小比例
		String pPath = CommonUtil.getRootFilePath();
		String fileDir = pPath + CommonUtil.TAKE_PHOTO_PATH;
		File file = new File(fileDir);
		if (!file.exists()) {
			file.mkdir();
		}

//		Bitmap newBitmap = com.dongkesoft.iboss.utils.ImageTools.zoomBitmap(
//				bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
		// 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
//		bitmap.recycle();
		
//		File f = com.dongkesoft.iboss.utils.ImageTools.savePhotoToSDCard(
//				bitmap, fileDir, CommonUtil.getRandomFilename(context));
//		bitmap.recycle();
		return BitmapUtils.compressImage(fileDir + "/image.jpg");
	}
}

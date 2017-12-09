/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dongkesoft.iboss.utils;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： RotateBitmap
 *		2.功能描述：图片闪动工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class RotateBitmap {
	public static final String TAG = "RotateBitmap";
	private Bitmap mBitmap;
	private int mRotation;

	public RotateBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
		mRotation = 0;
	}

	public RotateBitmap(Bitmap bitmap, int rotation) {
		mBitmap = bitmap;
		mRotation = rotation % 360;
	}

	public void setRotation(int rotation) {
		mRotation = rotation;
	}

	public int getRotation() {
		return mRotation;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}

	public Matrix getRotateMatrix() {
		// By default this is an identity matrix.
		Matrix matrix = new Matrix();
		if (mRotation != 0) {
			// We want to do the rotation at origin, but since the bounding
			// rectangle will be changed after rotation, so the delta values
			// are based on old & new width/height respectively.
			int cx = mBitmap.getWidth() / 2;
			int cy = mBitmap.getHeight() / 2;
			matrix.preTranslate(-cx, -cy);
			matrix.postRotate(mRotation);
			matrix.postTranslate(getWidth() / 2, getHeight() / 2);
		}
		return matrix;
	}

	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {

		// 旋转图片 动作

		Matrix matrix = new Matrix();
		;
		matrix.postRotate(angle);

		// 创建新的图片

		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,

		bitmap.getWidth(), bitmap.getHeight(), matrix, true);

		return resizedBitmap;

	}

	public boolean isOrientationChanged() {
		return (mRotation / 90) % 2 != 0;
	}

	public int getHeight() {
		if (isOrientationChanged()) {
			return mBitmap.getWidth();
		} else {
			return mBitmap.getHeight();
		}
	}

	public int getWidth() {
		if (isOrientationChanged()) {
			return mBitmap.getHeight();
		} else {
			return mBitmap.getWidth();
		}
	}

	public static int readPictureDegree(String path) {

		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);

			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {

			case ExifInterface.ORIENTATION_ROTATE_90:

				degree = 90;

				break;

			case ExifInterface.ORIENTATION_ROTATE_180:

				degree = 180;

				break;

			case ExifInterface.ORIENTATION_ROTATE_270:

				degree = 270;

				break;

			}

		} catch (IOException e) {

			e.printStackTrace();

		}

		return degree;

	}

	public void recycle() {
		if (mBitmap != null) {
			mBitmap.recycle();
			mBitmap = null;
		}
	}
}

package com.dongkesoft.iboss.utils;

import java.io.File;

import android.content.Context;
import android.util.Log;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称：AbstractFileCache
 *		2.功能描述：图片缓存：抽象类.
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2015/08/26			1.00			新建
 *******************************************************************************/
public abstract class AbstractFileCache {

	/** The dir string. */
	private String dirString;

	/**
	 * Instantiates a new abstract file cache.
	 * 
	 * @param context
	 *            the context
	 */
	public AbstractFileCache(Context context) {

		dirString = getCacheDir();
		boolean ret = FileHelper.createDirectory(dirString);
		Log.e("", "FileHelper.createDirectory:" + dirString + ", ret = " + ret);
	}

	/**
	 * Gets the file.
	 * 
	 * @param url
	 *            the url
	 * @return the file
	 */
	public File getFile(String url) {
		// File f=new File(url);
		File f = new File(getSavePath(url));
		return f;
	}

	public File createFile(String url) {
		File f = new File(getSavePath(url));
		return f;
	}

	public File createLocalFile(String url) {
		File f = new File(getPath(url));
		return f;
	}

	/**
	 * Gets the save path.
	 * 
	 * @param url
	 *            the url
	 * @return the save path
	 */
	public abstract String getSavePath(String url);

	public abstract String getPath(String url);

	/**
	 * Gets the cache dir.
	 * 
	 * @return the cache dir
	 */
	public abstract String getCacheDir();

	public abstract String getPathDir();

	/**
	 * Clear.
	 */
	public void clear() {
		FileHelper.deleteDirectory(dirString);
	}

}

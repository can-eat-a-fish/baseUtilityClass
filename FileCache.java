package com.dongkesoft.iboss.utils;

import android.content.Context;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： FileCache
 *		2.功能描述：图片缓存：文件缓存
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class FileCache extends AbstractFileCache {

	/**
	 * Instantiates a new file cache.
	 * 
	 * @param context
	 *            the context
	 */
	public FileCache(Context context) {
		super(context);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zinchint.imageloader.AbstractFileCache#getSavePath(java.lang.String)
	 */
	@Override
	public String getSavePath(String url) {
		String filename = String.valueOf(url.hashCode());
		return getCacheDir() + filename;
	}

	@Override
	public String getPath(String url) {
		String filename = String.valueOf(url.hashCode());
		return getPathDir() + filename;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zinchint.imageloader.AbstractFileCache#getCacheDir()
	 */
	@Override
	public String getCacheDir() {

		return FileManager.getSaveFilePath();
	}

	@Override
	public String getPathDir() {

		return FileManager.getFilePath();
	}

}

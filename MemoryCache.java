package com.dongkesoft.iboss.utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.util.Log;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： MemoryCache
 *		2.功能描述：图片缓存：内存缓存工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class MemoryCache {

	/** The Constant TAG. */
	private static final String TAG = "MemoryCache";
	// 放入缓存时是个同步操�?
	// LinkedHashMap构�?方法的最后一个参数true代表这个map里的元素将按照最近使用次数由少到多排列，即LRU
	// 这样的好处是如果要将缓存中的元素替换，则先遍历出�?���?��使用的元素来替换以提高效�?
	/** The cache. */
	private Map<String, Bitmap> cache = Collections
			.synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));
	// 缓存中图片所占用的字节，初始0，将通过此变量严格控制缓存所占用的堆内存
	/** The size. */
	private long size = 0;// current allocated size
	// 缓存只能占用的最大堆内存
	/** The limit. */
	private long limit = 1000000;// max memory in bytes

	/**
	 * Instantiates a new memory cache.
	 */
	public MemoryCache() {
		// use 25% of available heap size
		setLimit(Runtime.getRuntime().maxMemory() / 10);
	}

	/**
	 * Sets the limit.
	 * 
	 * @param new_limit
	 *            the new limit
	 */
	public void setLimit(long new_limit) {
		limit = new_limit;
		Log.i(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");
	}

	/**
	 * Gets the.
	 * 
	 * @param id
	 *            the id
	 * @return the bitmap
	 */
	public Bitmap get(String id) {
		try {
			if (!cache.containsKey(id))
				return null;
			return cache.get(id);
		} catch (NullPointerException ex) {
			return null;
		}
	}

	/**
	 * Put.
	 * 
	 * @param id
	 *            the id
	 * @param bitmap
	 *            the bitmap
	 */
	public void put(String id, Bitmap bitmap) {
		try {
			if (cache.containsKey(id))
				size -= getSizeInBytes(cache.get(id));
			cache.put(id, bitmap);
			size += getSizeInBytes(bitmap);
			checkSize();
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	/**
	 * 严格控制堆内存，如果超过将首先替换最近最少使用的那个图片缓存.
	 */
	private void checkSize() {
		Log.i(TAG, "cache size=" + size + " length=" + cache.size());
		if (size > limit) {
			// 先遍历最近最少使用的元素
			Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, Bitmap> entry = iter.next();
				size -= getSizeInBytes(entry.getValue());
				iter.remove();
				if (size <= limit)
					break;
			}
			Log.i(TAG, "Clean cache. New size " + cache.size());
		}
	}

	/**
	 * Clear.
	 */
	public void clear() {
		cache.clear();
	}

	/**
	 * 图片占用的内�?
	 * 
	 * [url=home.php?mod=space&uid=2768922]@Param[/url] bitmap
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @return the size in bytes
	 */
	long getSizeInBytes(Bitmap bitmap) {
		if (bitmap == null)
			return 0;
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}

package com.dongkesoft.iboss.utils;

import android.util.Log;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： LogUtils
 *		2.功能描述：打印Log日志
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class LogUtils {

	/**
	 * 打印内容
	 * 
	 * @param content1
	 * @param content
	 */
	public static void showLog(String content1, String content) {
		Log.e(content1, content);
	}
}

package com.dongkesoft.iboss.utils;

import com.dongkesoft.iboss.constant.Constants;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： FileManager
 *		2.功能描述：文件管理工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class FileManager {

	/**
	 * Gets the save file path.
	 * 
	 * @return the save file path
	 */
	public static String getSaveFilePath() {
		return CommonUtil.getRootFilePath() + Constants.DATA_CACHE_PATH;
	}

	public static String getFilePath() {
		return CommonUtil.getRootFilePath() + Constants.DATA_REAL_PATH;
	}

}

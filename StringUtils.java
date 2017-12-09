package com.dongkesoft.iboss.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： StringUtils
 *		2.功能描述：字符串工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class StringUtils {
	/**
	 * 正则表达式->空字符串
	 */
	public static final String REGEX_STRING_SPACE = "^\\s*$";

	/**
	 * 正则表达式->数字
	 */
	public static final String REGEX_STRING_NUMBER = "^\\d+$";

	/**
	 * 正则表达式->有效数字
	 */
	public static final String REGEX_STRING_VALID_NUMBER = "^(0|([1-9]|-[1-9])\\d*)|(([1-9]|-[1-9])\\d*\\.\\d*)|((0|-0)\\.\\d*)$";

	/**
	 * 正则表达式->中文
	 */
	public static final String REGEX_STRING_CHINESE = "[\u4E00-\u9FFF]";

	/**
	 * 字符串匹配正则表达式
	 * 
	 * @param string
	 * @param regex
	 * @return
	 */
	public static boolean test(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		boolean ret = false;
		if (str == null || "".equals(str)) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 字符串是否不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		boolean ret = !isEmpty(str);
		return ret;
	}

	/**
	 * 获取字符串实际长度（类汉字字符占2个字节），字符串为空返回0
	 * 
	 * @return
	 */
	public static int realLength(String str) {
		int ret = 0;
		if (isNotEmpty(str)) {
			String temp = str.replaceAll(REGEX_STRING_CHINESE, "");
			ret = temp.length() + (str.length() - temp.length()) * 2;
		}
		return ret;
	}

	/**
	 * 获取字符串实际长度（类汉字字符占1个字节），字符串为空返回0
	 * 
	 * @return
	 */
	public static int length(String str) {
		int ret = 0;
		if (isNotEmpty(str)) {
			ret = str.length();
		}
		return ret;
	}

	/**
	 * 字符串是否是数字
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNumber(String num) {
		boolean ret = false;
		if (isNotEmpty(num)) {
			ret = test(num, REGEX_STRING_NUMBER);
		}
		return ret;
	}

	/**
	 * 字符串是否是有效数字
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNumberValid(String num) {
		boolean ret = false;
		if (isNotEmpty(num)) {
			ret = test(num, REGEX_STRING_VALID_NUMBER);
		}
		return ret;
	}

	/**
	 * 字符在字符串数组中位置查询
	 * 
	 * @param arraystr
	 * @param str
	 * @return
	 */
	public static int getFirstIndex(String[] arraystr, String str) {
		int index = -1;
		for (int i = 0; i < arraystr.length; i++) {
			if (arraystr[i].equals(str)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	 public static boolean isDecimal(String orginal){  
	        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);  
	    }
	 
	 public static boolean isMatch(String str, String regex) {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			return matcher.matches();
	}

}

package com.dongkesoft.iboss.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： Md5Utils
 *		2.功能描述：加密解密工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class Md5Utils {
	public static String encode(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] result = messageDigest.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : result) {
				int number = b & 0xff;
				String hexstr = Integer.toHexString(number);
				if (hexstr.length() == 1) {
					sb.append("0");
				}
				sb.append(hexstr);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// can't reach
			return "";
		}

	}
}

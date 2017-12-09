package com.dongkesoft.iboss.utils;

import java.text.DecimalFormat;

public class NumberUtil {
	/**
	 * 科学计数法Double转成正常String类型Double
	 * @param db
	 * @return
	 */
	public static String DoubleToString(Double db) {
		DecimalFormat df = new DecimalFormat("0");
		return df.format(db);
	}
	
}

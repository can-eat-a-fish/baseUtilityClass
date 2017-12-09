package com.dongkesoft.iboss.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： CommonUtil
 *		2.功能描述：常用工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
@SuppressLint("SimpleDateFormat")
public class CommonUtil {
	public static final String TAKE_PHOTO_PATH = "/ibossPicture/";
	public static final String DATA_CACHE_PATH = "/fanjiaju/";
	public static final String REGEX_TEL = "^1(3[0-9]|5[0-35-9]|8[012345-9]|47|7[013678])\\d{8}$";
	public static final String REGEX_PHS = "^0(10|2[0-5789]|\\d{3})\\d{7,8}$";
	public static final String REGEX_INTEGER = "^[1-9]{1}\\d{0,9}?$";
	public static final String REGEX_DECIMAL = "^(([1-9]{1}\\d{0,7})|([0]{1}))(\\.(\\d){1,8})?$";

	public static boolean isDecimal(String s) {

		Pattern p = Pattern.compile(REGEX_INTEGER, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(s);

		Pattern p1 = Pattern.compile(REGEX_DECIMAL, Pattern.CASE_INSENSITIVE);
		Matcher m1 = p1.matcher(s);
		return m.matches() || m1.matches();

	}

	public static boolean IsValidTelephone(String telephone) {
		/**
		 * 手机号码
		 * 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188,147
		 * 联通：130,131,132,152,155,156,185,186 电信：133,1349,153,180,181,189
		 */
		// string mobile =
		// "^1(3[0-9]|5[0-35-9]|8[012345-9]|47|7[013678])\\d{8}$";
		/**
		 * 大陆地区固话及小灵通 区号和电话之间没有- 区号：010,020,021,022,023,024,025,027,028,029
		 * 号码：七位或八位
		 */
		Pattern patterntel = Pattern.compile(REGEX_TEL);
		Matcher matchertel = patterntel.matcher(telephone);
		Pattern patternphs = Pattern.compile(REGEX_PHS);
		Matcher matcherphs = patternphs.matcher(telephone);
		return matchertel.matches() || matcherphs.matches();
	}

	public static boolean isBlank(String str) {
		if (str == null || str.length() == 0
				|| str.toLowerCase().equals("null")) {
			return true;
		}
		return false;
	}
	
	
	public static String totalMoney(double money) {
		  java.math.BigDecimal bigDec = new java.math.BigDecimal(money);
		  double total = bigDec.setScale(2, java.math.BigDecimal.ROUND_HALF_UP)
		    .doubleValue();
		  DecimalFormat df = new DecimalFormat("0.00");
		  return df.format(total);
		 }

	public static void hideSoftInput(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}

	public static String calculateOrderQuantity(int circulateType,
			String decimalPlaces, String orderQuantity, double acreage) {
		long pieces = 0;
		String quantity = "0";
		if (circulateType == 2) {
			double orderQuantityValue = Double.parseDouble(orderQuantity);
			if (acreage > 0) {
				pieces = (long) Math.ceil(orderQuantityValue / acreage);
				double tempquantity = pieces * acreage;
				if (!decimalPlaces.equals("0")) {
					quantity = String.format("%." + decimalPlaces + "f",
							tempquantity);

				} else {
					quantity = String.valueOf((long) Math.round(tempquantity));
				}

			} else {
				if (!decimalPlaces.equals("0")) {
					quantity = String.format("%." + decimalPlaces + "f",
							orderQuantityValue);
				} else {
					quantity = String.valueOf((long) Math
							.round(orderQuantityValue));
				}
			}
		} else {
			double orderQuantityValue = Double.parseDouble(orderQuantity);
			if (!decimalPlaces.equals("0")) {
				quantity = String.format("%." + decimalPlaces + "f",
						orderQuantityValue);
			} else {
				quantity = String.valueOf((long) Math.round(orderQuantityValue));
			}
		}
		return quantity;
	}

	public static long getIntervalDays(String startDate, String endDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long end;
		long days = 0;
		try {
			end = df.parse(endDate).getTime();
			long start = df.parse(startDate).getTime();
			days = (end - start) / (1000 * 60 * 60 * 24) + 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return days;
	}

	public static String commonDateConverter(String datestr) {
		Date date = null;
		String dateString = null;
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format1.parse(datestr);
			dateString = format2.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;

	}

	public static boolean CompareDate(String systemDate, String currentDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date system;
		boolean compareresult = false;
		try {
			system = sdf.parse(systemDate);
			Date current = sdf.parse(currentDate);
			if (current.before(system)) {
				compareresult = false;
			} else {
				compareresult = true;
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return compareresult;

	}

	public static String getRandomFilename(Context context) {

		return UUID.randomUUID().toString();

	}

	public static String getRootFilePath() {
		if (hasSDCard()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "";
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data";
		}
	}

	public static boolean isCurrency(String s) {

		Pattern p = Pattern.compile("^\\d*(.\\d{1,2})?$",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(s);
		return m.matches();

	}

	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	public static boolean CompareServerVersion(String localVersion,
			String ServerVersion) {
		boolean b = false;
		String localVersionNo = localVersion.replace(".", "");
		String serverVersionNo = ServerVersion.replace(".", "");
		if (localVersionNo.startsWith("0")) {
			localVersionNo = localVersionNo.replaceFirst("^0*", "");
		}
		if (serverVersionNo.startsWith("0"))

		{
			serverVersionNo = serverVersionNo.replaceFirst("^0*", "");
		}

		if (Long.parseLong(serverVersionNo) > Long.parseLong(localVersionNo)) {
			b = true;
		}
		return b;

	}

	@SuppressWarnings("static-access")
	public static void setDefault(int defaults, Context context) {
		int NOTIFICATIONS_ID = 1;
		NotificationManager mNotificationManager;

		// PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
		// new Intent(this,MakeBilletActivity.class), 0);
		final Notification notification = new Notification();
		notification.defaults = defaults;
		mNotificationManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(NOTIFICATIONS_ID, notification);
	}

	public static String getCurrentDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日 HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	public static String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = df.format(c.getTime());
		return formattedDate;
	}

	public static Date ParseStringToDateTime(String s) {

		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = formatter.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static Date ParseStringToDate(String s) {

		java.sql.Date d = null;
		d = java.sql.Date.valueOf(s);
		return d;
	}

}

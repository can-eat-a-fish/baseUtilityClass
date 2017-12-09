package com.dongkesoft.iboss.utils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： ActivityUtils
 *		2.功能描述： Activity工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2015/08/26			1.00			新建
 *******************************************************************************/
public class ActivityUtils {
	public static ArrayList<Activity> ALL_ACTIVITY = new ArrayList<Activity>();

	// 退出程序，清除所有Activity
	public static void exitApp() {
		try {
			for (Activity activity : ALL_ACTIVITY) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
		ALL_ACTIVITY.clear();
	}

	// 将Activity加入集合中
	public static void addAppActivity(Activity activity) {
		if (!ALL_ACTIVITY.contains(activity)) {
			ALL_ACTIVITY.add(activity);
		}
	}

	// 将activity从集合中删除
	public static void removeAppActivity(Activity activity) {
		if (ALL_ACTIVITY.contains(activity)) {
			ALL_ACTIVITY.remove(activity);
		}
	}

	// 实现activity跳转
	public static void intentForward(Context context, Class<?> cls) {
		context.startActivity(new Intent(context, cls));
	}

	public static void intentForward(Context context, Intent intent) {
		context.startActivity(intent);
	}

	public static void intentForward(Context context, Class<?> cls,
			Intent intent) {
		context.startActivity(intent.setClass(context, cls));
	}

	// 获取activity名字
	public static Activity getActivityByName(String name) {
		Activity ia = null;
		for (Activity ac : ALL_ACTIVITY) {
			if (ac.getClass().getName().indexOf(name) >= 0) {
				ia = ac;
			}
		}
		return ia;
	}

	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
}

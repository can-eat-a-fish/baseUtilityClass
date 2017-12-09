/**
 * 
 */
package com.dongkesoft.iboss.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Administrator
 * @since 2016年12月22日
 */
public class ToastUtil {
	/**
	 * mToast
	 */
	private static Toast mToast;

	/**
	 * toast 长时间
	 * 
	 * @author Administrator
	 * @since 2016年12月22日
	 * @param context
	 * @param message
	 */
	public static void showLongToast(Context context, String message) {
		closeToast();
		mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		mToast.show();
	}

	/**
	 * toast 短时间
	 * 
	 * @author Administrator
	 * @since 2016年12月22日
	 * @param context
	 * @param message
	 */
	public static void showShortToast(Context context, String message) {
		closeToast();
		mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		mToast.show();
	}

	/**
	 * 关闭
	 * 
	 * @author Administrator
	 * @since 2016年12月22日
	 */
	private static void closeToast() {
		if (mToast != null) {
			mToast.cancel();
			mToast = null;
		}
	}

}

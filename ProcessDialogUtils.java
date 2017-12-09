package com.dongkesoft.iboss.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.view.KeyEvent;

import com.dongkesoft.iboss.R;
import com.dongkesoft.iboss.activity.login.LoginActivity;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： PicUtil
 *		2.功能描述：网络加载dialog
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class ProcessDialogUtils {
	/**
	 * Dialog
	 */
	public static AlertDialog mDialog;

	/**
	 * 显示dialog
	 * 
	 * @param context
	 */
	public static void showProcessDialog(Context context) {

		try {
			if (mDialog != null) {
				mDialog.cancel();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 键盘点击事件
		 */
		OnKeyListener keyListener = new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_HOME
						|| keyCode == KeyEvent.KEYCODE_SEARCH) {
					return true;
				}
				return false;
			}
		};
		try {
			mDialog = new AlertDialog.Builder(context).create();
			mDialog.setCancelable(false); // 点击返回键 不关掉dialog
			mDialog.setOnKeyListener(keyListener);
			mDialog.setCanceledOnTouchOutside(false);
			mDialog.show();
			mDialog.setContentView(R.layout.round_process_dialog);
		} catch (Exception e) {
		}

	}

	public static void showReLoginDialog(final Context context, String message) {
		new AlertDialog.Builder(context).setTitle("异常登录").setMessage(message)
				.setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(context, LoginActivity.class);
						// 设置FLAG!!!表示从当前Activity跳转到intent构造方法中添加LoginActivity的类
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
						// ((Activity)context).finish();
						// SysApplication.getInstance().exit();
					}
				}).create().show();

	}

	/**
	 * 关闭dialog
	 * 
	 * @param context
	 */
	public static void closeProgressDilog() {
		if (mDialog != null) {
			try {
				mDialog.dismiss();
				mDialog = null;
			} catch (Exception e) {
			}

		}
	}

}

package com.dongkesoft.iboss.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;

import com.dongkesoft.iboss.R;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： LogDialog
 *		2.功能描述：日志工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class LogDialog extends Dialog {
	private Window window = null;
	public Button photo = null;
	public Button cancel = null;

	public LogDialog(Context context) {
		super(context);
	}

	public LogDialog(Context context, int theme) {
		super(context, theme);

	}

	public void setDialog(int layoutResID) {
		setContentView(layoutResID);
		photo = (Button) findViewById(R.id.photo);
		cancel = (Button) findViewById(R.id.cancel);
		window = getWindow(); // 得到对话�?
		window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画

		// 设置触摸对话框意外的地方不能取消对话�?
		setCanceledOnTouchOutside(true);
		// 阻止返回键响�?
		setCancelable(true);
	}

	// 如果自定义的Dialog声明为局部变量，就可以调用下面两个方法进行显示和消失，全�?变量则无�?�?
	/**
	 * 显示dialog
	 */
	public void showDialog() {
		show();
	}

	/**
	 * 关闭dialog
	 */
	public void Closedialog() {
		dismiss();
	}
}

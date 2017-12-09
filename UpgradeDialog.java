package com.dongkesoft.iboss.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dongkesoft.iboss.R;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： UpgradeDialog
 *		2.功能描述：
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class UpgradeDialog extends Dialog {
	private Window window = null;
	public Button ok = null;
	public Button cancel = null;
	public TextView tip;
	public TextView desc;

	public UpgradeDialog(Context context) {
		super(context);
	}

	public UpgradeDialog(Context context, int theme) {
		super(context, theme);

	}

	public void setDialog(int layoutResID) {
		setContentView(layoutResID);
		ok = (Button) findViewById(R.id.btOK);
		cancel = (Button) findViewById(R.id.btCancel);
		tip = (TextView) findViewById(R.id.tip);
		desc = (TextView) findViewById(R.id.desc);
		window = getWindow(); // 得到对话�?
		window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
		setCanceledOnTouchOutside(false);
		setCancelable(false);
	}

	// 如果自定义的Dialog声明为局部变量，就可以调用下面两个方法进行显示和消失，全�?��量则无所�?
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

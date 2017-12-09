package com.example.dongke.july.utils;
import com.example.dongke.july.dongke.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;

public class MyDialog extends Dialog {
	private Window window = null;
	public Button photo = null;
	public Button picture = null;
	public Button cancel = null;

	public MyDialog(Context context) {
		super(context);
	}

	public MyDialog(Context context, int theme) {
		super(context, theme);

	}

	public void setDialog(int layoutResID) {
		setContentView(layoutResID);
		picture=(Button) findViewById(R.id.picture);
		photo=(Button) findViewById(R.id.photo);
		cancel=(Button) findViewById(R.id.cancel);
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

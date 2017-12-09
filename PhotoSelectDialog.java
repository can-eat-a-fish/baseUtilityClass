package com.example.dongke.july.utils;
import com.example.dongke.july.dongke.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

public class PhotoSelectDialog extends Dialog {

	private Window window = null;
	public TextView picture;
	public TextView takephoto;
	public TextView cancel;
	public PhotoSelectDialog(Context context) {
		super(context);
	}

	public PhotoSelectDialog(Context context, int theme) {
		super(context, theme);

	}
	public void setDialog(int layoutResID) {
		setContentView(layoutResID);
		picture = (TextView) findViewById(R.id.picture);
		takephoto = (TextView) findViewById(R.id.photo);
		cancel = (TextView) findViewById(R.id.cancel);
		this.setCanceledOnTouchOutside(true);
		window = getWindow(); // 得到对话�?
		window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
		setCancelable(true);
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

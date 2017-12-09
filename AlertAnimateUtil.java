/**
 * 
 */
package com.dongkesoft.iboss.utils;

import android.content.Context;
import android.content.Intent;

import com.dongkesoft.iboss.activity.login.LoginActivity;
import com.dongkesoft.iboss.activity.report.CustomerFillingDetailReportActivity;
import com.dongkesoft.iboss.interfaces.OnDismissListener;
import com.dongkesoft.iboss.interfaces.OnItemClickListener;
import com.dongkesoft.iboss.view.AlertView;

/**
 * @author Administrator
 * @since 2017年1月12日
 */
public class AlertAnimateUtil implements OnItemClickListener, OnDismissListener {

	/**
	 * 只有确定的对话框
	 * 
	 * @author Administrator
	 * @since 2017年1月12日
	 * @param context
	 * @param title
	 * @param message
	 */
	public static void alertShow(Context context, String title, String message) {
		new AlertView(title, message, null, new String[] { "确定" }, null,
				context, AlertView.Style.Alert, new OnItemClickListener() {

					@Override
					public void onItemClick(Object o, int position) {
					}
				}).show();
	}
	
	public static void alertShowBack(final Context context, String title, String message) {
		new AlertView(title, message, null, new String[] { "确定" }, null,
				context, AlertView.Style.Alert, new OnItemClickListener() {

					@Override
					public void onItemClick(Object o, int position) {
						((CustomerFillingDetailReportActivity)context).finish();
					}
				}).show();
	}
	/**
	 * 只有确定的对话框-单点登录
	 * 
	 * @author Administrator
	 * @since 2017年1月12日
	 * @param context
	 * @param title
	 * @param message
	 */
	public static void showReLoginDialog(final Context context, String title,
			String message) {
		new AlertView(title, message, null, new String[] { "确定" }, null,
				context, AlertView.Style.Alert, new OnItemClickListener() {

					@Override
					public void onItemClick(Object o, int position) {
						Intent intent = new Intent(context, LoginActivity.class);
						// 设置FLAG!!!表示从当前Activity跳转到intent构造方法中添加LoginActivity的类
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
					}
				}).show();
	}

	/**
	 * 列表选项对话框
	 * 
	 * @author Administrator
	 * @since 2017年1月12日
	 * @param context
	 */
	public void alertShowList(Context context) {
		new AlertView(null, null, null, new String[] { "高亮按钮1", "高亮按钮2",
				"高亮按钮3" }, new String[] { "其他按钮1", "其他按钮2", "其他按钮3", "其他按钮4",
				"其他按钮5", "其他按钮6", "其他按钮7", "其他按钮8", "其他按钮9", "其他按钮10",
				"其他按钮11", "其他按钮12" }, context, AlertView.Style.Alert, this)
				.show();
	}

	/**
	 * 底部弹出对话框
	 * 
	 * @author Administrator
	 * @since 2017年1月12日
	 * @param context
	 */
	public void alertShowBottomDialog(Context context) {
		new AlertView("标题", null, "取消", new String[] { "高亮按钮1" }, new String[] {
				"其他按钮1", "其他按钮2", "其他按钮3" }, context,
				AlertView.Style.ActionSheet, this).show();
	}

	/**
	 * 底部对话框
	 * 
	 * @author Administrator
	 * @since 2017年1月12日
	 * @param context
	 */
	public void alertShowBottom(Context context) {
		new AlertView("标题", "内容", "取消", null, null, context,
				AlertView.Style.ActionSheet, this).setCancelable(true).show();
	}

	/**
	 * 图像选择对话框
	 * 
	 * @author Administrator
	 * @since 2017年1月12日
	 * @param context
	 */
	public void alertShowImageChoose(Context context) {
		new AlertView("上传头像", null, "取消", null,
				new String[] { "拍照", "从相册中选择" }, context,
				AlertView.Style.ActionSheet, this).show();
	}

	private void closeKeyboard() {
		// 关闭软键盘

	}

	@Override
	public void onItemClick(Object o, int position) {
		closeKeyboard();
		// 判断是否是拓展窗口View，而且点击的是非取消按钮

	}

	@Override
	public void onDismiss(Object o) {
		closeKeyboard();
	}
}

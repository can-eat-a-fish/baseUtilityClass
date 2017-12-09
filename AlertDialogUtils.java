/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： AlertDialogUtils
 *		2.功能描述：AlertDialogdialog
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
package com.dongkesoft.iboss.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.dongkesoft.iboss.R;
import com.dongkesoft.iboss.interfaces.AdapterViewOnItemClickListener;
import com.dongkesoft.iboss.interfaces.OnTextWatcherListener;

/**
 * @author Administrator
 * @since 2017年1月17日
 */
public class AlertDialogUtils {

	/**
	 * Dialog
	 */
	private static AlertDialog mDialog;

	private static ListView listChoose;
	private static EditText etSearch;

	/**
	 * 显示dialog
	 * 
	 * @author Administrator
	 * @since 2017年1月17日
	 * @param context
	 * @param index
	 */
	@SuppressWarnings("deprecation")
	public static void showProcessDialog(Context context, int index) {
		try {
			if (mDialog != null) {
				mDialog.cancel();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			mDialog = new AlertDialog.Builder(context).create();
			mDialog.setCanceledOnTouchOutside(false);
			View view = LayoutInflater.from(context).inflate(
					R.layout.dialog_choose, null);
			mDialog.show();
			mDialog.setContentView(view);
			mDialog.getWindow().setGravity(Gravity.CENTER);
			mDialog.getWindow().setLayout(
					((Activity) context).getWindowManager().getDefaultDisplay()
							.getWidth(),
					((Activity) context).getWindowManager().getDefaultDisplay()
							.getHeight());
			mDialog.setCanceledOnTouchOutside(true);
			mDialog.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			listChoose = (ListView) view.findViewById(R.id.select_list);
			etSearch = (EditText) view.findViewById(R.id.et_search);

			// listChoose.setOnItemClickListener(listener);
		} catch (Exception e) {
		}
	}

	/**
	 * adapter
	 * 
	 * @author Administrator
	 * @since 2017年1月17日
	 * @param adapter
	 */
	public static void setAdapter(BaseAdapter adapter) {
		if (listChoose != null) {
			listChoose.setAdapter(adapter);
		}
	}

	/**
	 * listview 点击监听事件
	 * 
	 * @author Administrator
	 * @since 2017年1月17日
	 * @param listener
	 */
	public static void setOnItemClickListener(
			final AdapterViewOnItemClickListener listener) {
		if (listChoose != null) {
			listChoose
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							if (listener != null) {
								listener.onItemClick(arg0, arg1, arg2, arg3);
							}
						}

					});
		}
	}

	/**
	 * 搜索监听事件
	 * 
	 * @author Administrator
	 * @since 2017年1月17日
	 * @param listener
	 */
	public static void addTextChangedListener(
			final OnTextWatcherListener listener) {
		if (etSearch == null) {
			return;
		}
		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				if (listener != null) {
					listener.afterTextChanged(arg0);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

		});
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

package com.dongkesoft.iboss.utils;

import java.text.NumberFormat;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dongkesoft.iboss.R;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： CommonProgressDialog
 *		2.功能描述：进度条工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class CommonProgressDialog extends AlertDialog {

	public ProgressBar mProgress;
	public TextView mProgressNumber;
	public TextView mProgressPercent;
	public TextView mProgressMessage;
	private int mMax;
	private int mProgressVal;
	private String mProgressNumberFormat;
	private NumberFormat mProgressPercentFormat;

	public CommonProgressDialog(Context context) {
		super(context);
		initFormats();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_progress_dialog);
		mProgress = (ProgressBar) findViewById(R.id.progress);
		mProgressNumber = (TextView) findViewById(R.id.progress_number);
		mProgressPercent = (TextView) findViewById(R.id.progress_percent);
		mProgressMessage = (TextView) findViewById(R.id.progress_message);
		mProgressMessage.setText("正在下载更新..");
	}

	private void initFormats() {
		mProgressNumberFormat = "%2.2fM/%2.2fM";
		mProgressPercentFormat = NumberFormat.getPercentInstance();
		mProgressPercentFormat.setMaximumFractionDigits(0);
	}

	public void setIndeterminate(boolean indeterminate) {
		if (mProgress != null) {
			mProgress.setIndeterminate(indeterminate);
		}

	}

	public void setProgressNumber() {
		mMax = mProgress.getMax();
		mProgressVal = mProgress.getProgress();
		double dProgress = (double) mProgressVal / (double) (1024 * 1024);
		double dMax = (double) mMax / (double) (1024 * 1024);
		String format = mProgressNumberFormat;
		mProgressNumber.setText(String.format(format, dProgress, dMax));
	}

	public void setProgressPercent() {
		if (mProgressPercentFormat != null) {
			double percent = (double) mProgressVal / (double) mMax;
			SpannableString tmp = new SpannableString(
					mProgressPercentFormat.format(percent));
			tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
					tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			mProgressPercent.setText(tmp);
		} else {
			mProgressPercent.setText("");
		}
	}

}
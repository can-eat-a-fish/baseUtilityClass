package com.dongkesoft.iboss.utils;

import android.view.View;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： Compat
 *		2.功能描述：
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class Compat {

	private static final int SIXTY_FPS_INTERVAL = 1000 / 60;

	public static void postOnAnimation(View view, Runnable runnable) {
		// if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
		// SDK16.postOnAnimation(view, runnable);
		// } else {
		view.postDelayed(runnable, SIXTY_FPS_INTERVAL);
		// }
	}

}

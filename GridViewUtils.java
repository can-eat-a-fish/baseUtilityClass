package com.dongkesoft.iboss.utils;

import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/* 
 * Copyright(c) 2016 dongke All rights reserved. / Confidential
 * 类的信息：
 *		1.程序名称： GridViewUtils
 *		2.功能描述：GridView工具类
 * 编辑履历：
 *		作者				日期					版本				修改内容
 *		dongke			2016/12/07			1.00			新建
 *******************************************************************************/
public class GridViewUtils {
	/**
	 * 存储宽度
	 */
	static SparseIntArray mGvWidth = new SparseIntArray();

	/**
	 * 计算GridView的高度
	 * 
	 * @param gridView
	 *            要计算的GridView
	 */
	public static void updateGridViewLayoutParams(GridView gridView,
			int maxColumn) {
		int childs = gridView.getAdapter().getCount();

		if (childs > 0) {
			int columns = childs < maxColumn ? childs % maxColumn : maxColumn;
			gridView.setNumColumns(columns);
			int width = 0;
			int cacheWidth = mGvWidth.get(columns);
			if (cacheWidth != 0) {
				width = cacheWidth;
			} else { // 计算gridview每行的宽度, 如果item小于3则计算所有item的宽度;
						// 否则只计算3个child宽度，因此一行最多3个child。 (这里我们以3为例)
				int rowCounts = childs < maxColumn ? childs : maxColumn;
				for (int i = 0; i < rowCounts; i++) {
					View childView = gridView.getAdapter().getView(i, null,
							gridView);
					childView.measure(0, 0);
					width += childView.getMeasuredWidth();
				}
			}

			ViewGroup.LayoutParams params = gridView.getLayoutParams();
			params.width = width;
			gridView.setLayoutParams(params);
			if (mGvWidth.get(columns) == 0) {
				mGvWidth.append(columns, width);
			}
		}
	}

}

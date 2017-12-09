package com.example.dongke.july.utils;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;

public class Constants {

	public static String serverAction = "/DKService/PDAModuleService";
	public static final String GET_SERVER_VERSION = serverAction
			+ "/DoPDALogin";//
	public static final String SAVE_CHECK_INFO = serverAction
			+ "/AddCheckBarcode";//
	public static final String BAR_CODE_VALID = serverAction + "/CheckBarcode";// 条码检验
	public static final String PRODUCER_NO_VALID = serverAction
			+ "/CheckProcedureUser";// 生产工号检验
	public static final String KILN_CAR_NO_VALID = serverAction
			+ "/CheckKilnCar";// 窑车号检验
	public static final String ADD_WORK_PIECE = serverAction + "/AddWorkPiece";// 计件-保存条码信息
	public static final String GET_REWORK_PROCEDURE = serverAction
			+ "/GetReworkProcedureByBarcode";// 获得返工工序数据
	public static final String GET_GOODS_GRADE = serverAction
			+ "/GetGoodsGradeData";// 获得产品分级数据
	public static final String GET_KILNCAR_NO = serverAction
			+ "/GetKilnCarByBarCode";// 获得产品分级数据
	public static final String StatisticsCollectBarcode = serverAction
			+ "/StatisticsCollectBarcode";// 统计
	public static final String DoPDAOut = serverAction + "/DoPDAOut";// 退出
	public static final String StatisticsProductTrack = serverAction
			+ "/StatisticsProductTrack";// 产品跟踪
	public static final String TestConnection = serverAction
			+ "/TestConnection";// 端口ip测试
	public static final String TestConnectionEx = serverAction
			+ "/TestConnectionEx";// 端口ip测试2
	public static final String DATA_CACHE_PATH = "/iboss/";
	public static final String TAKE_PHOTO_PATH = "/ibossPicture/";
	public static final int zero = 0;
	public static final int NegativeThree = -3;
	public static final int NegativeTwo = -2;
	public static final int PAGE_SIZE = 20;
	public static final int ACTION_RESULT_STATUS = -4;
	public static final int LOG_PAGE_SIZE = 5;
	public static final int MAX_REFRESH_PAGE_SIZE = 40;
	public static final String DATA_REAL_PATH = "/edu/path/";
	public static final String GET_ENDPRODUCT_REWORK_PROCEDURE = serverAction
			+ "/GetReworkProcedureByProcedureID";// 获得返工工序数据
	public static final String ServerVersionReg = "^([0-9]*)\\.([0-9]*)\\.([0-9]*)\\.([0-9]*)$";
	public static ReentrantLock threadlock = new ReentrantLock();
	public static Object obj = new Object();

	public static String getRandomFilename(Context context) {

		return UUID.randomUUID().toString();

	}

}

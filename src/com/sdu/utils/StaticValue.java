package com.sdu.utils;

import android.os.Environment;

public class StaticValue {

	public static boolean is_run = true;
	
	public static final int HOT_SEARCH = 1;
	public static final int REN_QI = 2;
	public static final int ZHOU_APP = 3;
	public static final int ZHOU_GAME = 4;
	
	public static final String URL = "http://192.168.199.239:8080/MarketServer/";
	
	public static final String TARGET= Environment.getExternalStorageDirectory().getAbsolutePath()+"/androidMarket/" + System.currentTimeMillis();

	public static final String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/androidMarket/";
	
	public static final String PAGE = "page";
	
	public static final String AD_LABEL = "adver";
	
	public static final String ERROR_LABEL = "error";
	public static final String APPS_LABEL = "apps";
	
	//表示没有错误
	public static final int ERROR_NO = 0;
	
	//表示数据库错误
	public static final int ERROR_SQL = 1;
	
	//表示有错误
	public static final int ERROR_ERROR = 2;
	
	//表示已经没有数据
	public static final int ERROR_NULL = 3;
	
	//暂停tag
	public static final String TAG_PAUSE = "pause";
	
	//继续tag
	public static final String TAG_RESUME = "resume";
	
	//取消tag
	public static final String TAG_CANCEL = "cancel";
	
	//分享tag
	public static final String TAG_SHARE = "share";
	
	//收藏tag
	public static final String TAG_COLLECT = "collect";
	
	//保存当前用户的用户ID
	public static String USER_ID = "-1";
	
	//保存当前用户的用户名
	public static String USER_NAME = "";
	
	//当前版本
	public static final String VERSION = "2.0.1"; 
	
	
}

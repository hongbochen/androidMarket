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
	
	//��ʾû�д���
	public static final int ERROR_NO = 0;
	
	//��ʾ���ݿ����
	public static final int ERROR_SQL = 1;
	
	//��ʾ�д���
	public static final int ERROR_ERROR = 2;
	
	//��ʾ�Ѿ�û������
	public static final int ERROR_NULL = 3;
	
	//��ͣtag
	public static final String TAG_PAUSE = "pause";
	
	//����tag
	public static final String TAG_RESUME = "resume";
	
	//ȡ��tag
	public static final String TAG_CANCEL = "cancel";
	
	//����tag
	public static final String TAG_SHARE = "share";
	
	//�ղ�tag
	public static final String TAG_COLLECT = "collect";
	
	//���浱ǰ�û����û�ID
	public static String USER_ID = "-1";
	
	//���浱ǰ�û����û���
	public static String USER_NAME = "";
	
	//��ǰ�汾
	public static final String VERSION = "2.0.1"; 
	
	
}

package com.sdu.utils;



import android.util.Log;

/**
 * 
 * @author �鲨
 * @version 1.0
 * @since 2015-05-26
 */
public class AppLog{

	/**
	 * ��ʾ�û���Ҫ�ĵ�����Ϣ
	 * @param msg ��ӡ����ĵ�����Ϣ
	 */
	public static void debug(String msg){
		Log.d("debug",msg);
	}
	
	/**
	 * ��ʾ�û���Ҫ�Ĵ�����Ϣ
	 * @param msg ������Ϣ
	 */
	public static void error(String msg){
		Log.e("error",msg);
	}
	
	/**
	 * ��ʾ�û���Ҫ��״̬��Ϣ
	 * @param msg ״̬��Ϣ
	 */
	public static void state(String msg){
		Log.d("State",msg);
	}
}

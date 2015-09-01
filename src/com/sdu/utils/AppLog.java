package com.sdu.utils;



import android.util.Log;

/**
 * 
 * @author 洪波
 * @version 1.0
 * @since 2015-05-26
 */
public class AppLog{

	/**
	 * 显示用户需要的调试信息
	 * @param msg 打印输出的调试信息
	 */
	public static void debug(String msg){
		Log.d("debug",msg);
	}
	
	/**
	 * 显示用户需要的错误信息
	 * @param msg 错误信息
	 */
	public static void error(String msg){
		Log.e("error",msg);
	}
	
	/**
	 * 显示用户需要的状态信息
	 * @param msg 状态信息
	 */
	public static void state(String msg){
		Log.d("State",msg);
	}
}

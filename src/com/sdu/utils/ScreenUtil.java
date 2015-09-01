package com.sdu.utils;

import android.content.Context;
import android.view.WindowManager;

public class ScreenUtil {
	
	public static int getWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		int width = wm.getDefaultDisplay().getWidth();//фад╩©М╤х
		return width;
	}
	
	public static int getHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		int height = wm.getDefaultDisplay().getHeight();
		return height;
	}

}

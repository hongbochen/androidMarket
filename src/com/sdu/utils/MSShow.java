package com.sdu.utils;

import android.content.Context;
import android.widget.Toast;

public class MSShow {

	public static void show(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}

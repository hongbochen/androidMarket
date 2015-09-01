package com.sdu.manager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sdu.downUtil.DownloadManager;
import com.sdu.utils.BFImageCache;
import com.sdu.utils.NetUtil;

import android.app.Application;
import android.content.Context;

public class MarketApplication extends Application{

	public static Context context;
	
	public static RequestQueue queue;
	
	public static NetUtil nu;
			
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		context = getApplicationContext();
		queue = Volley.newRequestQueue(context);
		BFImageCache.getInstance().initilize(this);
		
		nu = new NetUtil();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

}

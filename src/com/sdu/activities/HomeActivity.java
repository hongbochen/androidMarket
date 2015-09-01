package com.sdu.activities;


import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalBitmap;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.sdu.adapters.AppListAdapter;
import com.sdu.androidmarket.R;
import com.sdu.beans.AdBean;
import com.sdu.beans.AppBriefBean;
import com.sdu.downUtil.DownloadManager;
import com.sdu.downUtil.DownloadService;
import com.sdu.manager.MarketApplication;
import com.sdu.ui.AdGallery;
import com.sdu.ui.LoadingDialog;
import com.sdu.utils.AppLog;
import com.sdu.utils.MSShow;
import com.sdu.utils.StaticValue;
//import com.zxing.activity.CaptureActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.Window;

public class HomeActivity extends BaseActivity {

	private TextView tv_search;
	private TextView tv_contact;
	private ImageView iv_dia;

	private AdGallery app_advers;
	private LinearLayout ovalLayout; // 圆点容器

	private DownloadManager downloadManager;

	/** 图片id的数组,本地测试用 */
	private int[] imageId = new int[] { R.drawable.test, R.drawable.test,
			R.drawable.test, R.drawable.test };

	/** 广告list */
	private ArrayList<AdBean> abList = new ArrayList<AdBean>();

	/** 图片网络路径数组 */
	private ArrayList<String> mris = new ArrayList<String>();

	/* 显示app的ListView */
	private ListView lv_apps;

	/* app ListView的适配器 */
	private AppListAdapter appsAdapter;

	/* 存放数据的array */
	private ArrayList<AppBriefBean> appsList = new ArrayList<AppBriefBean>();

	// 测试加载对话框
	private LoadingDialog ld;

	// 扫描返回的代码
	public static final int CODE_OK = 0;

	private Context mAppContext;

	private DownLoadEventNotifier den;

	//缓冲ImageView
	ImageView iv_loading;
	//加载更多Button
	Button btn_load_more;
	
	//加载动画
	AnimationDrawable anim;
	
	private OnClickListener on = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			btn_load_more = (Button)v;
			RelativeLayout rl = (RelativeLayout)btn_load_more.getParent();
			iv_loading = (ImageView)rl.getChildAt(0);
			iv_loading.setVisibility(View.VISIBLE);
			
			btn_load_more.setText("正在加载...");
			anim = (AnimationDrawable)iv_loading.getBackground();
			anim.start();
			
			den = new DownLoadEventNotifier(new DownInterface() {
				
				@Override
				public void onDownloadSuccess(String result) {
					
					getAppBrief(result);
					
					anim.stop();
					iv_loading.setVisibility(View.GONE);
					btn_load_more.setText("加载更多");
					
					appsAdapter.notifyDataSetChanged();
				}
			});
			
			if (MarketApplication.nu.isConnect(mAppContext)) {

				den.start(MarketApplication.nu.getMoreApp(appsList.size()+1),
						"servlet/Home");
			}
		}
	};
	
	@Override
	public void initWidget() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);

		FinalBitmap.create(this); // android 框架 这里用于加载网络图片

		mAppContext = this.getApplicationContext();

		downloadManager = DownloadService.getDownloadManager(mAppContext);

		tv_search = (TextView) findViewById(R.id.tv_search);
		tv_contact = (TextView) findViewById(R.id.tv_contact);
		iv_dia = (ImageView) findViewById(R.id.iv_dia);

		lv_apps = (ListView) findViewById(R.id.lv_apps);

		ld = LoadingDialog.createDialog(HomeActivity.this);

		appsAdapter = new AppListAdapter(appsList, downloadManager, mAppContext,on);
		lv_apps.setAdapter(appsAdapter);

		tv_search.setOnClickListener(this);
		tv_contact.setOnClickListener(this);
		iv_dia.setOnClickListener(this);

		app_advers = (AdGallery) findViewById(R.id.app_advers);
		ovalLayout = (LinearLayout) findViewById(R.id.ovalLayout);// 获取圆点容器

		// 第二和第三参数 2选1 ,参数2为 图片网络路径数组 ,参数3为图片id的数组,本地测试用 ,2个参数都有优先采用 参数2
		/**
		 * app_advers.start(this, mris, imageId, 3000, ovalLayout,
		 * R.drawable.dot_focused, R.drawable.dot_normal);
		 **/

		app_advers
				.setAdversOnItemClickListener(new AdGallery.AdversOnItemClickListener() {
					public void onItemClick(int curIndex) {
						Intent intent = new Intent(HomeActivity.this,
								AppDetailInfoActivity.class);
						intent.putExtra("APPID", appsList.get(curIndex).getAppID());
						startActivity(intent);
					}
				});

		lv_apps.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(HomeActivity.this,
						AppDetailInfoActivity.class);
				intent.putExtra("APPID", appsList.get(position).getAppID());
				startActivity(intent);
			}
		});

		den = new DownLoadEventNotifier(new DownInterface() {

			@Override
			public void onDownloadSuccess(String result) {
				// TODO Auto-generated method stub
				ld.dismiss();

				if (getAppBrief(result)) {

					app_advers.start(HomeActivity.this, mris, imageId, 3000,
							ovalLayout, R.drawable.dot_focused,
							R.drawable.dot_normal);
				}

				appsAdapter.notifyDataSetChanged();

			}
		});

		if (MarketApplication.nu.isConnect(mAppContext)) {

			ld.show();
			den.start(MarketApplication.nu.getHomeAppBriefJson(),
					"servlet/Home");
		}
	}

	@Override
	public void widgetClick(View v) {
		Intent intent = null;

		switch (v.getId()) {
		case R.id.tv_search:
			intent = new Intent(HomeActivity.this, SearchActivity.class);
			intent.putExtra("key", "null");
			startActivity(intent);
			break;
		case R.id.tv_contact:
			intent = new Intent(HomeActivity.this, MeActivity.class);
			startActivity(intent);
			break;
		case R.id.iv_dia:
			/*
			MSShow.show(getApplicationContext(), "你可以扫描二维码或条形码");
			Intent scanStart = new Intent(HomeActivity.this,
					CaptureActivity.class);
			startActivityForResult(scanStart, CODE_OK);*/
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CODE_OK) {
			String result = data.getExtras().getString("result");
			if (!result.equals("null")) {
				MSShow.show(getApplicationContext(), result);
				Log.e("result", result);
			}

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		appsAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		try {
			if (appsAdapter != null && downloadManager != null) {
				downloadManager.backupDownloadInfoList();
			}
		} catch (DbException e) {
			LogUtils.e(e.getMessage(), e);
		}

		super.onDestroy();
	}

	private boolean getAppBrief(String info) {

		boolean hasAd = false;

		try {
			JSONObject jo = new JSONObject(info);

			int err = jo.getInt(StaticValue.ERROR_LABEL);
			if (err == StaticValue.ERROR_NO) {

				if (jo.has(StaticValue.AD_LABEL)) {
					hasAd = true;

					JSONArray adArray = jo.getJSONArray(StaticValue.AD_LABEL);

					abList.clear();
					mris.clear();

					for (int i = 0; i < adArray.length(); i++) {
						JSONObject temp = adArray.getJSONObject(i);

						String appID = temp.getString("appID");
						String imageUrl = temp.getString("imageUrl");

						abList.add(new AdBean(appID, imageUrl));
						mris.add(imageUrl);
					}

				}

				JSONArray ja = jo.getJSONArray(StaticValue.APPS_LABEL);

				for (int i = 0; i < ja.length(); i++) {
					JSONObject temp = ja.getJSONObject(i);

					String appID = temp.getString("appID");
					String appIconAdd = temp.getString("appIconAdd");
					String appName = temp.getString("appName");
					String appDownCount = temp.getString("appDownCount");
					String appSize = temp.getString("appSize");
					String briefInfo = temp.getString("briefInfo");
					String appAddress = temp.getString("appAddress");

					appsList.add(new AppBriefBean(appID, appIconAdd, appName,
							appDownCount, appSize, briefInfo, appAddress));
				}
			} else {
				return hasAd;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return hasAd;
		}

		return hasAd;

	}
}

package com.sdu.activities;

import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sdu.adapters.AppListAdapter;
import com.sdu.adapters.AppListAdapter2;
import com.sdu.androidmarket.R;
import com.sdu.beans.AppBriefBean;
import com.sdu.downUtil.DownloadManager;
import com.sdu.downUtil.DownloadService;
import com.sdu.manager.MarketApplication;
import com.sdu.ui.LoadingDialog;
import com.sdu.utils.BFImageCache;
import com.sdu.utils.MSShow;
import com.sdu.utils.StaticValue;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HotActivity extends BaseActivity {

	// 热榜容器
	private LinearLayout hotlist;
	// 热榜加载更多
	private TextView loadmorehotapp;

	// 新秀榜容器
	private LinearLayout hot_newlist;
	// 新秀榜加载更多
	private TextView loadmorehotnewapp;

	// 周排行容器
	private LinearLayout hot_weeklylist;
	// 周排行加载更多
	private TextView loadmoreweeklyhotapp;

	// 周游戏榜
	private LinearLayout hot_gamelist;
	// 周游戏排行
	private TextView loadmorehotweeklygame;

	/* 存放数据的array */
	private ArrayList<AppBriefBean> hotAppsList = new ArrayList<AppBriefBean>();
	private ArrayList<AppBriefBean> newAppsList = new ArrayList<AppBriefBean>();
	private ArrayList<AppBriefBean> weekAppsList = new ArrayList<AppBriefBean>();
	private ArrayList<AppBriefBean> gameAppsList = new ArrayList<AppBriefBean>();

	// 测试加载对话框
	private LoadingDialog ld;

	private Context mAppContext;

	private DownLoadEventNotifier den;

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hot);

		mAppContext = HotActivity.this;

		ld = LoadingDialog.createDialog(HotActivity.this);

		hotlist = (LinearLayout) findViewById(R.id.hotlist);
		hot_newlist = (LinearLayout) findViewById(R.id.hot_newlist);
		hot_weeklylist = (LinearLayout) findViewById(R.id.hot_weeklylist);
		hot_gamelist = (LinearLayout) findViewById(R.id.hot_gamelist);

		loadmorehotapp = (TextView) findViewById(R.id.loadmorehotapp);
		loadmorehotnewapp = (TextView) findViewById(R.id.loadmorehotnewapp);
		loadmoreweeklyhotapp = (TextView) findViewById(R.id.loadmoreweeklyhotapp);
		loadmorehotweeklygame = (TextView) findViewById(R.id.loadmorehotweeklygame);

		loadmorehotapp.setOnClickListener(this);
		loadmorehotnewapp.setOnClickListener(this);
		loadmoreweeklyhotapp.setOnClickListener(this);
		loadmorehotweeklygame.setOnClickListener(this);

		den = new DownLoadEventNotifier(new DownInterface() {

			@Override
			public void onDownloadSuccess(String result) {
				getAppBrief(result);
				ld.dismiss();
				
				MSShow.show(mAppContext, ""+hotAppsList.size());
				addView(1);
				addView(2);
				addView(3);
				addView(4);
			}
		});

		ld.show();

		String req = MarketApplication.nu.getHotAppReq();
		den.start(req, "servlet/Hot");

	}

	/**
	 * 添加View,
	 * 
	 * @param type
	 *            1--hot,2--new,3--week,4--game
	 */
	private void addView(int type) {

		LinearLayout ll = null;
		ArrayList<AppBriefBean> al = null;
		switch (type) {
		case 1:
			ll = hotlist;
			al = hotAppsList;
			break;
		case 2:
			ll = hot_newlist;
			al = newAppsList;
			break;
		case 3:
			ll = hot_weeklylist;
			al = weekAppsList;
			break;
		case 4:
			ll = hot_gamelist;
			al = gameAppsList;
			break;
		}

		int size = al.size();
		for (int i = 0; i < size; i++) {
			View v = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.lv_app_item2, null);
			v.setBackgroundResource(R.drawable.item_select);
			v.setClickable(true);
			
			final AppBriefBean appInfo = al.get(i);
			

			NetworkImageView app_image = (NetworkImageView) v
					.findViewById(R.id.app_image);
			TextView tv_app_name = (TextView) v
					.findViewById(R.id.tv_app_name);
			TextView tv_down_count = (TextView) v
					.findViewById(R.id.tv_down_count);
			TextView tv_app_size = (TextView) v
					.findViewById(R.id.tv_app_size);
			TextView tv_brief = (TextView) v
					.findViewById(R.id.tv_brief);
			
			if (appInfo.getAppIconAdd() == null
					|| appInfo.getAppIconAdd().equals("")) {

			} else {
				// ImageUtils.showImageByNetworkImageView(mAppContext,
				// holder.app_image, appInfo.getAppIconAdd());
				ImageLoader imageLoader = new ImageLoader(MarketApplication.queue,
						BFImageCache.getInstance());

				app_image.setImageUrl(appInfo.getAppIconAdd(), imageLoader);
			}

			if (appInfo.getAppName() != null)
				tv_app_name.setText(appInfo.getAppName());

			if (appInfo.getAppDownCount() != null)
				tv_down_count.setText(appInfo.getAppDownCount() + "次下载");

			if (appInfo.getAppSize() != null)
				tv_app_size.setText(appInfo.getAppSize());

			if (appInfo.getBriefInfo() != null)
				tv_brief.setText(appInfo.getBriefInfo());
			
			ll.addView(v);
			
			v.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(HotActivity.this,
							AppDetailInfoActivity.class);
					intent.putExtra("APPID",appInfo.getAppID());
					startActivity(intent);
					
				}
			});
			
			View line = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.layout_line, null);

			ll.addView(line);
		}

		

	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(HotActivity.this, AppShowActivity.class);

		switch (v.getId()) {
		case R.id.loadmorehotapp:
			intent.putExtra("TYPE", StaticValue.HOT_SEARCH);
			startActivity(intent);
			break;
		case R.id.loadmorehotnewapp:
			intent.putExtra("TYPE", StaticValue.REN_QI);
			startActivity(intent);
			break;
		case R.id.loadmoreweeklyhotapp:
			intent.putExtra("TYPE", StaticValue.ZHOU_APP);
			startActivity(intent);
			break;
		case R.id.loadmorehotweeklygame:
			intent.putExtra("TYPE", StaticValue.ZHOU_GAME);
			startActivity(intent);
			break;
		}
	}

	private void getAppBrief(String info) {

		try {
			JSONObject jo = new JSONObject(info);

			int err = jo.getInt(StaticValue.ERROR_LABEL);
			if (err == StaticValue.ERROR_NO) {

				JSONArray ja = jo.getJSONArray("hot");

				for (int i = 0; i < ja.length(); i++) {
					JSONObject temp = ja.getJSONObject(i);

					String appID = temp.getString("appID");
					String appIconAdd = temp.getString("appIconAdd");
					String appName = temp.getString("appName");
					String appDownCount = temp.getString("appDownCount");
					String appSize = temp.getString("appSize");
					String briefInfo = temp.getString("briefInfo");
					String appAddress = temp.getString("appAddress");

					hotAppsList.add(new AppBriefBean(appID, appIconAdd,
							appName, appDownCount, appSize, briefInfo,
							appAddress));
				}

				JSONArray ja1 = jo.getJSONArray("new");

				for (int i = 0; i < ja1.length(); i++) {
					JSONObject temp = ja1.getJSONObject(i);

					String appID = temp.getString("appID");
					String appIconAdd = temp.getString("appIconAdd");
					String appName = temp.getString("appName");
					String appDownCount = temp.getString("appDownCount");
					String appSize = temp.getString("appSize");
					String briefInfo = temp.getString("briefInfo");
					String appAddress = temp.getString("appAddress");

					newAppsList.add(new AppBriefBean(appID, appIconAdd,
							appName, appDownCount, appSize, briefInfo,
							appAddress));
				}

				JSONArray ja2 = jo.getJSONArray("week");

				for (int i = 0; i < ja2.length(); i++) {
					JSONObject temp = ja1.getJSONObject(i);

					String appID = temp.getString("appID");
					String appIconAdd = temp.getString("appIconAdd");
					String appName = temp.getString("appName");
					String appDownCount = temp.getString("appDownCount");
					String appSize = temp.getString("appSize");
					String briefInfo = temp.getString("briefInfo");
					String appAddress = temp.getString("appAddress");

					weekAppsList.add(new AppBriefBean(appID, appIconAdd,
							appName, appDownCount, appSize, briefInfo,
							appAddress));
				}

				JSONArray ja3 = jo.getJSONArray("game");

				for (int i = 0; i < ja3.length(); i++) {
					JSONObject temp = ja3.getJSONObject(i);

					String appID = temp.getString("appID");
					String appIconAdd = temp.getString("appIconAdd");
					String appName = temp.getString("appName");
					String appDownCount = temp.getString("appDownCount");
					String appSize = temp.getString("appSize");
					String briefInfo = temp.getString("briefInfo");
					String appAddress = temp.getString("appAddress");

					gameAppsList.add(new AppBriefBean(appID, appIconAdd,
							appName, appDownCount, appSize, briefInfo,
							appAddress));
				}
				
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

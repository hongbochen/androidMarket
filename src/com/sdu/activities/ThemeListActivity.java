package com.sdu.activities;

import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sdu.adapters.AppListAdapter;
import com.sdu.androidmarket.R;
import com.sdu.beans.AppBriefBean;
import com.sdu.downUtil.DownloadManager;
import com.sdu.downUtil.DownloadService;
import com.sdu.manager.MarketApplication;
import com.sdu.ui.LoadingDialog;
import com.sdu.utils.MSShow;
import com.sdu.utils.StaticValue;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ThemeListActivity extends BaseActivity{

	private TextView tv_list_back;
	
	private ListView lv_apps;

	private String themeID;

	/* app ListView�������� */
	private AppListAdapter appsAdapter;

	/* ������ݵ�array */
	private ArrayList<AppBriefBean> appsList = new ArrayList<AppBriefBean>();

	private DownloadManager downloadManager;

	private Context mAppContext;

	private LoadingDialog ld;

	private DownLoadEventNotifier den;

	// ����ImageView
	ImageView iv_loading;
	// ���ظ���Button
	Button btn_load_more;

	// ���ض���
	AnimationDrawable anim;

	private OnClickListener on = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			btn_load_more = (Button) v;
			RelativeLayout rl = (RelativeLayout) btn_load_more.getParent();
			iv_loading = (ImageView) rl.getChildAt(0);
			iv_loading.setVisibility(View.VISIBLE);

			btn_load_more.setText("���ڼ���...");
			anim = (AnimationDrawable) iv_loading.getBackground();
			anim.start();

			den = new DownLoadEventNotifier(new DownInterface() {

				@Override
				public void onDownloadSuccess(String result) {
					ld.dismiss();
					getAppBrief(result);

					anim.stop();
					iv_loading.setVisibility(View.GONE);
					btn_load_more.setText("���ظ���");

					appsAdapter.notifyDataSetChanged();
				}
			});

			if (MarketApplication.nu.isConnect(mAppContext)) {
				ld.show();
				den.start(
						MarketApplication.nu.getThemeListReq(themeID, ""
								+ (appsList.size() + 1)), "servlet/ThemeList");
			}
		}
	};

	
	@Override
	public void initWidget() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_theme_list);

		Intent intent = getIntent();
		themeID = intent.getStringExtra("ThemeID");
		
		MSShow.show(getApplicationContext(), themeID);

		mAppContext = this.getApplicationContext();

		ld = LoadingDialog.createDialog(ThemeListActivity.this);

		tv_list_back = (TextView) findViewById(R.id.tv_list_back);
		
		downloadManager = DownloadService.getDownloadManager(mAppContext);

		lv_apps = (ListView) findViewById(R.id.lv_apps);
		appsAdapter = new AppListAdapter(appsList, downloadManager,
				mAppContext, on);
		lv_apps.setAdapter(appsAdapter);

		tv_list_back.setOnClickListener(this);
		lv_apps.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(ThemeListActivity.this,
						AppDetailInfoActivity.class);
				intent.putExtra("APPID", appsList.get(position).getAppID());
				startActivity(intent);

			}
		});

		den = new DownLoadEventNotifier(new DownInterface() {

			@Override
			public void onDownloadSuccess(String result) {
				getAppBrief(result);
				ld.dismiss();
				appsAdapter.notifyDataSetChanged();
			}
		});

		if (MarketApplication.nu.isConnect(mAppContext)) {
			ld.show();
			den.start(MarketApplication.nu.getThemeListReq(themeID, "1"),
					"servlet/ThemeList");
		}
		
	}

	@Override
	public void widgetClick(View v) {
		Intent intent = null;

		switch (v.getId()) {
		case R.id.tv_list_back:
			ThemeListActivity.this.finish();
			break;
		}
		
	}

	@Override
	protected void onRestart() {
		appsAdapter.notifyDataSetChanged();
		super.onRestart();
	}
	
	private void getAppBrief(String info) {

		try {
			JSONObject jo = new JSONObject(info);

			int err = jo.getInt(StaticValue.ERROR_LABEL);
			if (err == StaticValue.ERROR_NO) {

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
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	

}

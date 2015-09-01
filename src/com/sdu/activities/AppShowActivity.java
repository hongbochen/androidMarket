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

public class AppShowActivity extends BaseActivity {

	private TextView tv_list_back;
	private TextView tv_list_appName;
	private ListView lv_apps;

	private int type;

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
						MarketApplication.nu.getHotListReq(type, ""
								+ (appsList.size() + 1)), "servlet/HotList");
			}
		}
	};

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_show);

		Intent intent = getIntent();
		type = intent.getIntExtra("TYPE", 0);
		

		mAppContext = this.getApplicationContext();

		ld = LoadingDialog.createDialog(AppShowActivity.this);

		tv_list_back = (TextView) findViewById(R.id.tv_list_back);
		tv_list_appName = (TextView) findViewById(R.id.tv_list_appName);

		setType(type);
		
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
				Intent intent = new Intent(AppShowActivity.this,
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
			den.start(MarketApplication.nu.getHotListReq(type, "1"),
					"servlet/HotList");
		}
	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;

		switch (v.getId()) {
		case R.id.tv_list_back:
			AppShowActivity.this.finish();
			break;
		}
	}

	
	@Override
	protected void onRestart() {
		appsAdapter.notifyDataSetChanged();
		super.onRestart();
	}

	private void setType(int type) {
		switch (type) {
		case StaticValue.HOT_SEARCH:
			tv_list_appName.setText("���Ѱ�");
			break;
		case StaticValue.REN_QI:
			tv_list_appName.setText("��������");
			break;
		case StaticValue.ZHOU_APP:
			tv_list_appName.setText("��Ӧ��");
			break;
		case StaticValue.ZHOU_GAME:
			tv_list_appName.setText("����Ϸ");
			break;
		}
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

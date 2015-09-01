package com.sdu.activities;

import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sdu.adapters.LeftLayoutAdapter;
import com.sdu.androidmarket.R;
import com.sdu.beans.AdBean;
import com.sdu.beans.AppBriefBean;
import com.sdu.beans.LeftItemBean;
import com.sdu.manager.AppManager;
import com.sdu.manager.MarketApplication;
import com.sdu.ui.LoadingDialog;
import com.sdu.utils.HttpDownloader;
import com.sdu.utils.MSShow;
import com.sdu.utils.StaticValue;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MarketTab extends TabActivity {

	private TabHost tabHost;

	// �໬������
	private RelativeLayout rl_header;

	// �໬��ListView
	private ListView lv_left_setting;

	// �໬��ListView��Adapter
	private LeftLayoutAdapter lla;

	// ������������ݵ�����
	private ArrayList<LeftItemBean> leftList = new ArrayList<LeftItemBean>();

	// ��Դ�ļ�
	private Class activitys[] = { HomeActivity.class, SortActivity.class,
			HotActivity.class, RecommandActivity.class, ThemeActivity.class };// ��ת��Activity

	private String title[] = { "��ҳ", "����", "����", "�Ƽ�", "����" };// ���ò˵��ı���

	private int image[] = { R.drawable.tab_home, R.drawable.tab_sort,
			R.drawable.tab_hot, R.drawable.tab_rec, R.drawable.tab_sort };// ���ò˵�

	private LoadingDialog ld;

	private Context mAppContext;

	private DownLoadEventNotifier den;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 0){
				File file = (File)msg.obj;
				
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file),
						"application/vnd.android.package-archive");
				mAppContext.startActivity(intent);
				
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main_tab);

		mAppContext = MarketTab.this;
		ld = LoadingDialog.createDialog(mAppContext);

		den = new DownLoadEventNotifier(new DownInterface() {

			@Override
			public void onDownloadSuccess(String result) {
				ld.dismiss();
				dealResult(result);
			}
		});

		initTab();

		/*
		 * init the sliding menu which cintains the option that bounds the
		 * acount of baidu
		 */
		SlidingMenu slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT); // set the sliding menu in the
												// left of the screen
		slidingMenu.setTouchModeAbove(SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width); // shadow width
		slidingMenu.setShadowDrawable(R.drawable.shadow); // shadow style
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset); // of screen
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.attachToActivity(this, SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setMenu(R.layout.activity_left_layout);

		rl_header = (RelativeLayout) slidingMenu.findViewById(R.id.rl_header);
		rl_header.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MSShow.show(getApplicationContext(), "clicked");
			}
		});

		lv_left_setting = (ListView) slidingMenu
				.findViewById(R.id.lv_left_setting);
		initLeftData();
		lla = new LeftLayoutAdapter(leftList, getApplicationContext());
		lv_left_setting.setAdapter(lla);

		lv_left_setting
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = null;

						switch (position) {
						case 0:
							intent = new Intent(MarketTab.this,
									SettingActivity.class);
							startActivity(intent);
							break;
						case 1:
							ld.show();
							String req = MarketApplication.nu.getVersionReq();
							den.start(req, "servlet/Update");
							break;
						case 2:
							intent = new Intent(MarketTab.this,
									FeedBackActivity.class);
							startActivity(intent);
							break;
						case 3:
							intent = new Intent(MarketTab.this,
									AboutActivity.class);
							startActivity(intent);
							break;
						case 4:
							AppManager.getAppManager().AppExit(mAppContext);
							break;
						}

					}
				});

	}

	private void initTab() {
		Resources resources = getResources();

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this.getLocalActivityManager());

		// ������ǩ
		for (int i = 0; i < activitys.length; i++) {
			// ʵ����һ��view��Ϊtab��ǩ�Ĳ���
			View view = View.inflate(this, R.layout.tab_layout, null);

			// ����imageview
			ImageView imageView = (ImageView) view.findViewById(R.id.tab_image);

			imageView.setBackgroundResource(image[i]);

			// ����textview
			TextView textView = (TextView) view.findViewById(R.id.tab_title);
			textView.setText(title[i]);
			// ������תactivity
			Intent intent = new Intent(this, activitys[i]);

			// ����view����������ת��activity
			TabSpec spec = tabHost.newTabSpec(title[i]).setIndicator(view)
					.setContent(intent);

			// ��ӵ�ѡ�
			tabHost.addTab(spec);
		}

		// tabHost.setCurrentTabByTag("music");���õ�һ�δ�ʱĬ����ʾ�ı�ǩ���ò�����tabHost.newTabSpec("music")�Ĳ�����ͬ
		tabHost.setCurrentTab(0);// ���õ�һ�δ�ʱĬ����ʾ�ı�ǩ��������������ӵ���ǩ�е�˳��λ���Ǵ�0��ʼ��
	}

	private void initLeftData() {
		leftList.clear();
		leftList.add(new LeftItemBean(R.drawable.left_setting, "����"));
		leftList.add(new LeftItemBean(R.drawable.left_check_update, "������"));
		leftList.add(new LeftItemBean(R.drawable.left_feed_back, "һ������"));
		leftList.add(new LeftItemBean(R.drawable.left_about, "����"));
		leftList.add(new LeftItemBean(R.drawable.left_quit, "�˳�����"));
	}

	private void dealResult(String info) {

		try {
			JSONObject jo = new JSONObject(info);

			int err = jo.getInt(StaticValue.ERROR_LABEL);
			if (err == StaticValue.ERROR_NO) {

				String newVersion = jo.getString("newVersion");
				if(newVersion.equals("1")){
					String version = jo.getString("version");
					String appAdd = jo.getString("AppAdd");
					
					update(version, appAdd);
					
				}else{
					MSShow.show(mAppContext, "�������Ѿ������°汾");
				}
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void update(String version,final String add) {
		AlertDialog.Builder builder = new Builder(MarketTab.this);
		builder.setMessage("�����µİ汾\n�汾��:"+version+"\n�Ƿ����ڸ��£�");
		builder.setTitle("��ʾ");
		
		builder.setNegativeButton("ȡ��",new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
			
			
		});
		
		builder.setPositiveButton("ȷ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				MSShow.show(mAppContext, "���ڸ���");
				new Thread(new BackDownload(add)).start();
				
			}
			
			
		});
		
		builder.create().show();
	}
	
	class BackDownload implements Runnable{

		private String add;
		
		public BackDownload(String add){
			this.add = add;
		}
		
		@Override
		public void run() {
			File file = HttpDownloader.downLoadFile(add);
			Message msg = Message.obtain();
			msg.what = 0;
			msg.obj = file;
			
			handler.sendMessage(msg);
		}
		
	}
}

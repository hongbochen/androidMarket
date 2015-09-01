package com.sdu.activities;

import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import java.util.ArrayList;
import java.util.Random;

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
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchActivity extends BaseActivity {

	private TextView tv_search_back;
	private EditText tv_search;
	private ImageView iv_search;
	private LinearLayout ll_search_content;

	private RelativeLayout rl_label_content;
	private RelativeLayout rl_apps_content;

	private ArrayList<String> keyList = new ArrayList<String>();
	
	/**
	private String test[][] = { { "主公莫慌", "全民奇迹", "笑傲江湖" },
			{ "莽荒纪", "天下HD", "剑魂之刃" }, { "刀塔帝国", "新苍弩之间", "君王" },
			{ "游戏盒子", "放开那三国", "海岛奇兵" }, { "血族", "吴尊", "千炮捕鱼" },
			{ "秦时明月", "曹操传", "太极熊猫" } };
	*/
	
	private String test[][];
	
	private int ids[] = { R.color.gold, R.color.red, R.color.peru,
			R.color.darkgray, R.color.chartreuse };

	private Random ran = new Random();

	// 传过来的关键字
	private String keywords;

	// 测试加载对话框
	private LoadingDialog ld;

	private Context mAppContext;

	private DownLoadEventNotifier den;
	
	private ListView lv_apps;
	
	/* app ListView的适配器 */
	private AppListAdapter appsAdapter;

	/* 存放数据的array */
	private ArrayList<AppBriefBean> appsList = new ArrayList<AppBriefBean>();

	private DownloadManager downloadManager;


	// 缓冲ImageView
		ImageView iv_loading;
		// 加载更多Button
		Button btn_load_more;

		// 加载动画
		AnimationDrawable anim;

		private OnClickListener on = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_load_more = (Button) v;
				RelativeLayout rl = (RelativeLayout) btn_load_more.getParent();
				iv_loading = (ImageView) rl.getChildAt(0);
				iv_loading.setVisibility(View.VISIBLE);

				btn_load_more.setText("正在加载...");
				anim = (AnimationDrawable) iv_loading.getBackground();
				anim.start();

				den = new DownLoadEventNotifier(new DownInterface() {

					@Override
					public void onDownloadSuccess(String result) {
						ld.dismiss();
						getAppBrief(result);

						anim.stop();
						iv_loading.setVisibility(View.GONE);
						btn_load_more.setText("加载更多");
					}
				});

				if (MarketApplication.nu.isConnect(mAppContext)) {
					ld.show();
					String req = MarketApplication.nu.getSearchReq(keywords,""+(appsList.size()));
					den.start(req, "servlet/Search");
				}
			}
		};
		
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);

		mAppContext = SearchActivity.this;
		
		Intent intent = getIntent();
		keywords = intent.getStringExtra("key");

		ld = LoadingDialog.createDialog(mAppContext);
		
		downloadManager = DownloadService.getDownloadManager(mAppContext);
		
		tv_search_back = (TextView) findViewById(R.id.tv_search_back);
		tv_search = (EditText) findViewById(R.id.tv_search);
		iv_search = (ImageView) findViewById(R.id.iv_search);
		ll_search_content = (LinearLayout) findViewById(R.id.ll_search_content);

		rl_label_content = (RelativeLayout) findViewById(R.id.rl_label_content);
		rl_apps_content = (RelativeLayout) findViewById(R.id.rl_apps_content);

		tv_search_back.setOnClickListener(this);
		iv_search.setOnClickListener(this);

		lv_apps = (ListView) findViewById(R.id.lv_apps);
		appsAdapter = new AppListAdapter(appsList, downloadManager,
				mAppContext, on);
		lv_apps.setAdapter(appsAdapter);
		
		lv_apps.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SearchActivity.this,
						AppDetailInfoActivity.class);
				intent.putExtra("APPID", appsList.get(position).getAppID());
				startActivity(intent);

			}
		});
		
		den = new DownLoadEventNotifier(new DownInterface() {
			
			@Override
			public void onDownloadSuccess(String result) {
				ld.dismiss();
				
				JSONObject jo = null;
				try {
					jo = new JSONObject(result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(jo.has(StaticValue.APPS_LABEL)){
					rl_apps_content.setVisibility(View.VISIBLE);
					rl_label_content.setVisibility(View.GONE);
					
					getAppBrief(result);
				}else{
					dealRec(result);
					
					
				}
				
			}
		});
		
		if (keywords.equals("null")) {
			rl_apps_content.setVisibility(View.GONE);
			rl_label_content.setVisibility(View.VISIBLE);
			
			//initView();
			
			ld.show();
			String req = MarketApplication.nu.getKeyWordsReq();
			den.start(req, "servlet/Search");
		} else {
			
			ld.show();
			String req = MarketApplication.nu.getSearchReq(keywords,"0");
			den.start(req, "servlet/Search");
		}

	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;

		switch (v.getId()) {
		case R.id.tv_search_back:
			SearchActivity.this.finish();
			break;
		case R.id.iv_search:
			
			if(!tv_search.getText().toString().equals("")){
				ld.show();
				String req = MarketApplication.nu.getSearchReq(tv_search.getText().toString(),"0");
				den.start(req, "servlet/Search");
			}
			
			break;
		}
	}
	
	

	@Override
	protected void onRestart() {
		appsAdapter.notifyDataSetChanged();
		super.onRestart();
	}

	private void initView() {
		for (int i = 0; i < test.length; i++) {
			LinearLayout ll = new LinearLayout(getApplicationContext());
			ll.setOrientation(LinearLayout.HORIZONTAL);

			for (int j = 0; j < test[0].length; j++) {

				final TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundResource(R.drawable.shape_rectangle_thin);
				tv.setGravity(Gravity.CENTER);
				tv.setTextColor(getResources().getColor(R.color.black));

				tv.setText(test[i][j]);
				tv.setTextColor(getResources().getColor(
						ids[ran.nextInt(ids.length)]));

				tv.setClickable(true);

				LinearLayout.LayoutParams params_content = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params_content.leftMargin = 10;
				params_content.topMargin = 10;

				params_content.weight = 1;

				ll.addView(tv, params_content);

				tv.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if(!tv.getText().toString().equals("")){
							ld.show();
							String req = MarketApplication.nu.getSearchReq(tv.getText().toString(),"0");
							den.start(req, "servlet/Search");
						}
						
					
					}
				});
			}

			ll_search_content.addView(ll);
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
				
				appsAdapter.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	private void dealRec(String info){
			try {
				JSONObject jo = new JSONObject(info);

				int err = jo.getInt(StaticValue.ERROR_LABEL);
				if (err == StaticValue.ERROR_NO) {

					JSONArray ja = jo.getJSONArray("rec");

					for (int i = 0; i < ja.length(); i++) {
						String temp = ja.getString(i);

						keyList.add(temp);
					}
					
					//test
					
					int row = keyList.size() / 3;
					if(keyList.size() % 3 > 0)
						row = row + +1;
					
					test = new String[row][3];
					
					for(int i = 0;i < keyList.size();i++){
						int rowID = i / 3;
						int colID = i % 3;
						
						test[rowID][colID] = keyList.get(i); 
					}
					
					
					initView();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
}

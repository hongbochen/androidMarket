package com.sdu.activities;

import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sdu.androidmarket.R;
import com.sdu.beans.AppBriefBean;
import com.sdu.manager.MarketApplication;
import com.sdu.ui.KeywordsFlow;
import com.sdu.ui.LoadingDialog;
import com.sdu.utils.StaticValue;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;

public class RecommandActivity extends BaseActivity {

	/*
	 * public String[] keywords = { "QQ", "Sodino", "APK", "GFW", "Ǧ��", "����",
	 * "���澫��", "MacBook Pro", "ƽ�����", "��ʫ����", "����ŷ TR-100", "�ʼǱ�", "SPY Mouse",
	 * "Thinkpad E40", "�������", "�ڴ�����", "��ͼ", "����", "����", "����", "ͨѶ¼", "������",
	 * "CSDN leak", "��ȫ", "3D", "��Ů", "����", "4743G", "����", "����", "ŷ��", "�����",
	 * "��ŭ��С��", "mmShow", "���׹�����", "iciba", "��ˮ��ϵ", "����App", "������", "365����",
	 * "����ʶ��", "Chrome", "Safari", "�й���Siri", "A5������", "iPhone4S", "Ħ�� ME525",
	 * "���� M9", "�῵ S2500" };
	 */

	private ArrayList<String> keyList = new ArrayList<String>();
	private String[] keywords;

	private KeywordsFlow keywordsFlow;

	// ��ָ���µĵ�Ϊ(x1, y1)��ָ�뿪��Ļ�ĵ�Ϊ(x2, y2)
	float x1 = 0;
	float x2 = 0;
	float y1 = 0;
	float y2 = 0;

	private Context mAppContext;

	private LoadingDialog ld;

	private DownLoadEventNotifier den;

	// �����̴߳���
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			if (msg.what == 0) {
				randomChange();
			}

		}

	};

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rec);

		mAppContext = RecommandActivity.this;

		ld = LoadingDialog.createDialog(RecommandActivity.this);

		keywordsFlow = (KeywordsFlow) findViewById(R.id.keywordsflow);
		keywordsFlow.setDuration(800l);
		keywordsFlow.setOnClickListener(this);
		keywordsFlow.setOnItemClickListener(this);
		// ���
		// feedKeywordsFlow(keywordsFlow, keywords);
		// keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);

		den = new DownLoadEventNotifier(new DownInterface() {

			@Override
			public void onDownloadSuccess(String result) {
				ld.dismiss();

				dealResult(result);

				keywords = new String[keyList.size()];
				for(int i = 0;i < keyList.size();i++){
					keywords[i] = keyList.get(i);
				}

				feedKeywordsFlow(keywordsFlow, keywords);
				keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
			}
		});

		if (MarketApplication.nu.isConnect(mAppContext)) {
			ld.show();
			den.start(MarketApplication.nu.getRecReq(), "servlet/RecList");
		}

	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.keywordsflow:
			randomChange();
			break;
		default:
			String keyword = ((TextView) v).getText().toString();
			Intent intent = new Intent(RecommandActivity.this, SearchActivity.class);
			intent.putExtra("key", keyword);
			startActivity(intent);
			break;
		}
	}

	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		Random random = new Random();
		for (int i = 0; i < KeywordsFlow.MAX; i++) {
			int ran = random.nextInt(arr.length);
			String tmp = arr[ran];
			keywordsFlow.feedKeyword(tmp);
		}
	}

	private void randomChange() {
		Random ran = new Random();
		int i = ran.nextInt(2);

		if (i == 0) {
			keywordsFlow.rubKeywords();
			// keywordsFlow.rubAllViews();
			feedKeywordsFlow(keywordsFlow, keywords);
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
		} else {
			keywordsFlow.rubKeywords();
			// keywordsFlow.rubAllViews();
			feedKeywordsFlow(keywordsFlow, keywords);
			keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
		}
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// // TODO Auto-generated method stub
	// // �̳���Activity��onTouchEvent������ֱ�Ӽ�������¼�
	// if (event.getAction() == MotionEvent.ACTION_DOWN) {
	// // ����ָ���µ�ʱ��
	// x1 = event.getX();
	// y1 = event.getY();
	// }
	// if (event.getAction() == MotionEvent.ACTION_UP) {
	// // ����ָ�뿪��ʱ��
	// x2 = event.getX();
	// y2 = event.getY();
	// if (y1 - y2 > 50) {
	// keywordsFlow.rubKeywords();
	// // keywordsFlow.rubAllViews();
	// feedKeywordsFlow(keywordsFlow, keywords);
	// keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
	// } else if (y2 - y1 > 50) {
	// keywordsFlow.rubKeywords();
	// // keywordsFlow.rubAllViews();
	// feedKeywordsFlow(keywordsFlow, keywords);
	// keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
	// }
	// }
	// return super.onTouchEvent(event);
	// }

	public void dealResult(String info) {
		try {
			JSONObject jo = new JSONObject(info);

			int err = jo.getInt(StaticValue.ERROR_LABEL);
			if (err == StaticValue.ERROR_NO) {

				JSONArray ja = jo.getJSONArray("rec");

				for (int i = 0; i < ja.length(); i++) {
					String temp = ja.getString(i);

					keyList.add(temp);

				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	class TimerThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (StaticValue.is_run) {
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Message msg = Message.obtain();
				msg.what = 0;
				handler.sendMessage(msg);
			}
		}

	}

}

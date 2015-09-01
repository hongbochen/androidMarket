package com.sdu.activities;

import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sdu.adapters.ThemeListAdapter;
import com.sdu.androidmarket.R;
import com.sdu.beans.AdBean;
import com.sdu.beans.AppBriefBean;
import com.sdu.beans.ThemeBean;
import com.sdu.manager.MarketApplication;
import com.sdu.ui.LoadingDialog;
import com.sdu.utils.MSShow;
import com.sdu.utils.StaticValue;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

public class ThemeActivity extends BaseActivity {

	private ListView theme_list;
	private ArrayList<ThemeBean> themeList = new ArrayList<ThemeBean>();

	private ThemeListAdapter tla;

	private LoadingDialog ld;

	private DownLoadEventNotifier den;

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_theme);

		ld = LoadingDialog.createDialog(ThemeActivity.this);

		theme_list = (ListView) findViewById(R.id.theme_list);

		tla = new ThemeListAdapter(themeList, getApplicationContext());
		theme_list.setAdapter(tla);

		den = new DownLoadEventNotifier(new DownInterface() {

			@Override
			public void onDownloadSuccess(String result) {
				ld.dismiss();
				getAppBrief(result);
			}
		});

		ld.show();
		String req = MarketApplication.nu.getThemeReq();
		den.start(req, "servlet/Theme");

		theme_list
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Intent intent = new Intent(ThemeActivity.this,
								ThemeListActivity.class);
						intent.putExtra("ThemeID", themeList.get(position)
								.getThemeID());
						startActivity(intent);

					}
				});
	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub

	}

	private void getAppBrief(String info) {
		try {
			JSONObject jo = new JSONObject(info);

			int err = jo.getInt(StaticValue.ERROR_LABEL);
			if (err == StaticValue.ERROR_NO) {

				JSONArray ja = jo.getJSONArray("theme");

				for (int i = 0; i < ja.length(); i++) {
					JSONObject temp = ja.getJSONObject(i);

					String tid = temp.getString("themeID");
					String iadd = temp.getString("imageAdd");
					String text = temp.getString("themeText");

					themeList.add(new ThemeBean(tid, iadd, text));
				}

				tla.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}

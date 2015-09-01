package com.sdu.activities;

import java.util.ArrayList;

import com.sdu.adapters.PocketAdapter;
import com.sdu.androidmarket.R;
import com.sdu.beans.AppBriefBean;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MeActivity extends BaseActivity {

	private TextView tv_me_back;
	
	private ExpandableListView aboutMeListView;

	private ArrayList<String> groupList;
	private ArrayList<ArrayList<AppBriefBean>> childList;
	
	private PocketAdapter adapter;
	
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_me_info);
		
		tv_me_back = (TextView)findViewById(R.id.tv_me_back);
		
		tv_me_back.setOnClickListener(this);
		
		aboutMeListView = (ExpandableListView)findViewById(R.id.aboutMeListView);
		groupList = new ArrayList<String>();
		groupList.add("          已下载");
		groupList.add("          已评论");
		
		childList = new ArrayList<ArrayList<AppBriefBean>>();
		
		//测试
		ArrayList<AppBriefBean> list1 = new ArrayList<AppBriefBean>();
		list1.add(new AppBriefBean("1", null, "360游戏大厅", "100", "2.34M", "手游福利平台，更有海量精品游戏奥", null));
		list1.add(new AppBriefBean("1", null, "小时代", "100", "2.34M", "手游福利平台，更有海量精品游戏奥", null));
		list1.add(new AppBriefBean("1", null, "360免费wifi", "100", "2.34M", "手游福利平台，更有海量精品游戏奥", null));
		list1.add(new AppBriefBean("1", null, "全民2048", "100", "2.34M", "手游福利平台，更有海量精品游戏奥", null));
		
		ArrayList<AppBriefBean> list2 = new ArrayList<AppBriefBean>();
		list2.add(new AppBriefBean("1", null, "58同城", "100", "2.34M", "手游福利平台，更有海量精品游戏奥", null));
		list2.add(new AppBriefBean("1", null, "开心消消乐", "100", "2.34M", "手游福利平台，更有海量精品游戏奥", null));
		list2.add(new AppBriefBean("1", null, "看点", "100", "2.34M", "手游福利平台，更有海量精品游戏奥", null));
		list2.add(new AppBriefBean("1", null, "神庙逃亡2", "100", "2.34M", "手游福利平台，更有海量精品游戏奥", null));
	
		childList.add(list1);
		childList.add(list2);
		
		adapter = new PocketAdapter(groupList, childList, getApplicationContext());
		aboutMeListView.setAdapter(adapter);
		aboutMeListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		
		switch(v.getId()){
		case R.id.tv_me_back:
			MeActivity.this.finish();
			break;
		}
	}


}

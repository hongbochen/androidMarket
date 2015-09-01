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
		groupList.add("          ������");
		groupList.add("          ������");
		
		childList = new ArrayList<ArrayList<AppBriefBean>>();
		
		//����
		ArrayList<AppBriefBean> list1 = new ArrayList<AppBriefBean>();
		list1.add(new AppBriefBean("1", null, "360��Ϸ����", "100", "2.34M", "���θ���ƽ̨�����к�����Ʒ��Ϸ��", null));
		list1.add(new AppBriefBean("1", null, "Сʱ��", "100", "2.34M", "���θ���ƽ̨�����к�����Ʒ��Ϸ��", null));
		list1.add(new AppBriefBean("1", null, "360���wifi", "100", "2.34M", "���θ���ƽ̨�����к�����Ʒ��Ϸ��", null));
		list1.add(new AppBriefBean("1", null, "ȫ��2048", "100", "2.34M", "���θ���ƽ̨�����к�����Ʒ��Ϸ��", null));
		
		ArrayList<AppBriefBean> list2 = new ArrayList<AppBriefBean>();
		list2.add(new AppBriefBean("1", null, "58ͬ��", "100", "2.34M", "���θ���ƽ̨�����к�����Ʒ��Ϸ��", null));
		list2.add(new AppBriefBean("1", null, "����������", "100", "2.34M", "���θ���ƽ̨�����к�����Ʒ��Ϸ��", null));
		list2.add(new AppBriefBean("1", null, "����", "100", "2.34M", "���θ���ƽ̨�����к�����Ʒ��Ϸ��", null));
		list2.add(new AppBriefBean("1", null, "��������2", "100", "2.34M", "���θ���ƽ̨�����к�����Ʒ��Ϸ��", null));
	
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

package com.sdu.adapters;

import java.util.ArrayList;

import com.sdu.androidmarket.R;
import com.sdu.beans.AppBriefBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PocketAdapter extends BaseExpandableListAdapter{

	private ArrayList<String> groupList;
	private ArrayList<ArrayList<AppBriefBean>> childList;
	private Context context;
	
	public PocketAdapter(ArrayList<String> groupList,ArrayList<ArrayList<AppBriefBean>> childList,Context context){
		this.groupList = groupList;
		this.childList = childList;
		this.context = context;
	}
	
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupHolder gh = null;
		
		if(convertView == null){
			convertView = (View)LayoutInflater.from(context).inflate(R.layout.pocket_group_item, null);
			gh = new GroupHolder();
			gh.tv = (TextView)convertView.findViewById(R.id.pockte_group_name);
			
			convertView.setTag(gh);
		}else{
			gh = (GroupHolder)convertView.getTag();
		}
		
		gh.tv.setText(groupList.get(groupPosition));
		
		return convertView;
		
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChildHolder ch = null;
		
		if(convertView == null){
			convertView = (View)LayoutInflater.from(context).inflate(R.layout.lv_app_item, null);
			ch = new ChildHolder();
			ch.app_image = (ImageView)convertView.findViewById(R.id.app_image);
			ch.tv_app_name = (TextView)convertView.findViewById(R.id.tv_app_name);
			ch.tv_down_count = (TextView)convertView.findViewById(R.id.tv_down_count);
			ch.tv_app_size = (TextView)convertView.findViewById(R.id.tv_app_size);
			ch.tv_brief = (TextView)convertView.findViewById(R.id.tv_brief);
			convertView.setTag(ch);
		}else{
			ch = (ChildHolder)convertView.getTag();
		}
		
		if(childList.get(groupPosition).get(childPosition).getAppName() != null)
			ch.tv_app_name.setText(childList.get(groupPosition).get(childPosition).getAppName());
		
		if(childList.get(groupPosition).get(childPosition).getAppDownCount() != null)
			ch.tv_down_count.setText(childList.get(groupPosition).get(childPosition).getAppDownCount()+"¥Œœ¬‘ÿ");
		
		if(childList.get(groupPosition).get(childPosition).getAppSize() != null)
			ch.tv_app_size.setText(childList.get(groupPosition).get(childPosition).getAppSize());
		
		if(childList.get(groupPosition).get(childPosition).getBriefInfo() != null)
			ch.tv_brief.setText(childList.get(groupPosition).get(childPosition).getBriefInfo());
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	class GroupHolder{
		TextView tv;
	}

	class ChildHolder{
		ImageView app_image;
		TextView tv_app_name;
		TextView tv_down_count;
		TextView tv_app_size;
		TextView tv_brief;
	}
}

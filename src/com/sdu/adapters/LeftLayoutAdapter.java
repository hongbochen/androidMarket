package com.sdu.adapters;

import java.util.ArrayList;

import com.sdu.androidmarket.R;
import com.sdu.beans.LeftItemBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftLayoutAdapter extends BaseAdapter{

	private ArrayList<LeftItemBean> leftList = new ArrayList<LeftItemBean>();
	private LayoutInflater inflater;
	
	public LeftLayoutAdapter(ArrayList<LeftItemBean> leftList,Context context){
		this.leftList = leftList;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return leftList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return leftList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.layout_left_item, null);
			
			holder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
			holder.tv_left_text = (TextView)convertView.findViewById(R.id.tv_left_text);
			
			convertView.setTag(holder);
		}else{
			holder = (Holder)convertView.getTag();
		}
		
		holder.iv_icon.setBackgroundResource(leftList.get(position).getId());
		holder.tv_left_text.setText(leftList.get(position).getName());
		
		return convertView;
	}
	
	class Holder{
		ImageView iv_icon;
		TextView tv_left_text;
	}

}

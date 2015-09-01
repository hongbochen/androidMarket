package com.sdu.adapters;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.sdu.androidmarket.R;
import com.sdu.beans.ThemeBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ThemeListAdapter extends BaseAdapter{

	private ArrayList<ThemeBean> themeList;
	private LayoutInflater inflater;
	private Context context;
	
	public ThemeListAdapter(ArrayList<ThemeBean> themeList,Context context){
		this.themeList = themeList;
		this.inflater = LayoutInflater.from(context);
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return themeList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return themeList.get(position);
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
			convertView = inflater.inflate(R.layout.layout_theme_item, null);
			
			holder.theme_img = (ImageView)convertView.findViewById(R.id.theme_img);
			holder.theme_text = (TextView)convertView.findViewById(R.id.theme_text);
			
			convertView.setTag(holder);
		}else{
			holder = (Holder)convertView.getTag();
		}
		
		FinalBitmap.create(context)
		.display(holder.theme_img,themeList.get(position).getImageAdd(), holder.theme_img.getWidth(),
				holder.theme_img.getHeight(), null, null);
		
		holder.theme_text.setText(themeList.get(position).getThemeText());
		
		return convertView;
	}
	
	class Holder{
		ImageView theme_img;
		TextView theme_text;
	}

}

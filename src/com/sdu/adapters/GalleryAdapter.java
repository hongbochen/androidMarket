package com.sdu.adapters;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.sdu.androidmarket.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter{

	private ArrayList<String> appShootList; /* app½ØÍ¼ÁÐ±í */
	private Context context;
	
	public GalleryAdapter(ArrayList<String> appShootList,Context context){
		this.appShootList = appShootList;
		this.context = context;
		
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return appShootList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return appShootList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView image  ;
		 if (convertView == null ){
		    image = new ImageView(context);
		    image.setLayoutParams(new Gallery.LayoutParams((int)(360),(int)(640)));
		    image.setScaleType(ImageView.ScaleType.FIT_CENTER);  
		 }else{
			image = (ImageView)convertView;  
		 }
		 //image.setImageResource(imagesList.get(position));
		
		 FinalBitmap.create(context)
			.display(image, appShootList.get(position), image.getWidth(),
					image.getHeight(), null, null);
		 
		return image;
	}
	

}

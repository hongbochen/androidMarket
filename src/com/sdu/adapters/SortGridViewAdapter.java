package com.sdu.adapters;
import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdu.androidmarket.R;
import com.sdu.beans.SortItemBean;
import com.sdu.utils.MSShow;


/***************************************************************************
    *
    * Adapter
    *
    ***************************************************************************/
	
public class SortGridViewAdapter extends BaseAdapter {
		private ArrayList<SortItemBean> list;
		private LayoutInflater inflater;
		private Context context;
		
		public SortGridViewAdapter(ArrayList<SortItemBean> list,Context context) {
			this.list = list;
			inflater = LayoutInflater.from(context);
			this.context = context;
		}
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {			
			Holder holder;
			if(convertView == null){
				holder = new Holder();
				convertView = inflater.inflate(R.layout.sort_item_layout, null);
				holder.iv_picture = (ImageView)convertView.findViewById(R.id.iv_picture);
				holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
				
				convertView.setTag(holder);
			}else{
				holder = (Holder)convertView.getTag();
			}
		
			

			 FinalBitmap.create(context)
				.display(holder.iv_picture, list.get(position).getTypeAdd(), holder.iv_picture.getWidth(),
						holder.iv_picture.getHeight(), null, null);
			holder.tv_name.setText(list.get(position).getTypeName());

			return convertView;
		}
		
		class Holder{
			ImageView iv_picture;
			TextView tv_name;
		}
	}
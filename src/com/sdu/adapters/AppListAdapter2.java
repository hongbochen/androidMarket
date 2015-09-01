package com.sdu.adapters;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sdu.androidmarket.R;
import com.sdu.beans.AppBriefBean;
import com.sdu.manager.MarketApplication;
import com.sdu.utils.BFImageCache;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppListAdapter2 extends BaseAdapter{

	/* 储存app信息的数据 */
	private ArrayList<AppBriefBean> appList;
	private LayoutInflater inflate;
	private Context mAppContext;
	
	public AppListAdapter2(ArrayList<AppBriefBean> appList, Context mAppContext) {
		this.appList = appList;
		this.inflate = LayoutInflater.from(mAppContext);
		this.mAppContext = mAppContext;
	}
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return appList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return appList.get(position);
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
		
		AppBriefBean appInfo = appList.get(position);
		
		if (convertView == null) {
			convertView = inflate.inflate(R.layout.lv_app_item2, null);

			holder = new Holder();

			holder.app_image = (NetworkImageView) convertView
					.findViewById(R.id.app_image);
			holder.tv_app_name = (TextView) convertView
					.findViewById(R.id.tv_app_name);
			holder.tv_down_count = (TextView) convertView
					.findViewById(R.id.tv_down_count);
			holder.tv_app_size = (TextView) convertView
					.findViewById(R.id.tv_app_size);
			holder.tv_brief = (TextView) convertView
					.findViewById(R.id.tv_brief);
			holder.rl_down_size = (RelativeLayout) convertView
					.findViewById(R.id.rl_down_size);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		if (appInfo.getAppIconAdd() == null
				|| appInfo.getAppIconAdd().equals("")) {

		} else {
			// ImageUtils.showImageByNetworkImageView(mAppContext,
			// holder.app_image, appInfo.getAppIconAdd());
			ImageLoader imageLoader = new ImageLoader(MarketApplication.queue,
					BFImageCache.getInstance());

			holder.app_image.setImageUrl(appInfo.getAppIconAdd(), imageLoader);
		}

		if (appInfo.getAppName() != null)
			holder.tv_app_name.setText(appInfo.getAppName());

		if (appInfo.getAppDownCount() != null)
			holder.tv_down_count.setText(appInfo.getAppDownCount() + "次下载");

		if (appInfo.getAppSize() != null)
			holder.tv_app_size.setText(appInfo.getAppSize());

		if (appInfo.getBriefInfo() != null)
			holder.tv_brief.setText(appInfo.getBriefInfo());
		
		return convertView;
	}

	class Holder {
		NetworkImageView app_image;
		TextView tv_app_name;
		TextView tv_down_count;
		TextView tv_app_size;
		TextView tv_brief;
		RelativeLayout rl_down_size;
	}

}

package com.sdu.adapters;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.HttpHandler.State;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sdu.androidmarket.R;
import com.sdu.beans.AppBriefBean;
import com.sdu.downUtil.DownloadInfo;
import com.sdu.downUtil.DownloadManager;
import com.sdu.manager.MarketApplication;
import com.sdu.utils.BFImageCache;
import com.sdu.utils.MSShow;
import com.sdu.utils.StaticValue;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppListAdapter extends BaseAdapter {

	/* 储存app信息的数据 */
	private ArrayList<AppBriefBean> appList;
	private LayoutInflater inflate;
	private Context mAppContext;
	private DownloadManager downloadManager;

	private final int TYPE_COUNT = 2;
	private final int FIRST_TYPE = 0;
	private final int SECOND_TYPE = 1;
	
	private int currentType;

	private OnClickListener ocl;

	public AppListAdapter(ArrayList<AppBriefBean> appList,
			DownloadManager downloadManager, Context mAppContext,OnClickListener ocl) {
		this.appList = appList;
		this.downloadManager = downloadManager;
		this.inflate = LayoutInflater.from(mAppContext);
		this.mAppContext = mAppContext;
		this.ocl = ocl;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return appList == null ? 1 : appList.size()+1;
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
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if(position == appList.size())
			currentType = FIRST_TYPE;
		else
			currentType = SECOND_TYPE;
		
		return currentType;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return TYPE_COUNT;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if(getItemViewType(position) == FIRST_TYPE){
			HolderMore hm;
			
			if(convertView == null){
				hm = new HolderMore();
				convertView = inflate.inflate(R.layout.layout_load_more, null);
				
				hm.iv_loading = (ImageView)convertView.findViewById(R.id.iv_loading);
				hm.btn_load_more = (Button)convertView.findViewById(R.id.btn_load_more);
				
				convertView.setTag(hm);
			}else{
				hm = (HolderMore)convertView.getTag();
			}
			
			hm.btn_load_more.setOnClickListener(ocl);
		}else{
			final Holder holder;

			AppBriefBean appInfo = appList.get(position);

			final State state;

			final boolean isRunning = downloadManager.isRunning(appInfo);

			DownloadInfo downloadInfo = null;

			if (isRunning) {
				downloadInfo = downloadManager.getById(appInfo.getAppID());
			}

			if (convertView == null) {
				convertView = inflate.inflate(R.layout.lv_app_item, null);

				holder = new Holder(downloadInfo);

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
				holder.iv_download = (ImageView) convertView
						.findViewById(R.id.iv_download);
				holder.rl_down_size = (RelativeLayout) convertView
						.findViewById(R.id.rl_down_size);
				holder.download_pb = (ProgressBar) convertView
						.findViewById(R.id.download_pb);

				convertView.setTag(holder);
				holder.refresh();
			} else {
				holder = (Holder) convertView.getTag();
				holder.update(downloadInfo);
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

			if (isRunning) {
				state = downloadInfo.getState();

				switch (state) {
				case WAITING:
					holder.rl_down_size.setVisibility(View.GONE);
					holder.download_pb.setVisibility(View.VISIBLE);
					holder.iv_download.setBackgroundResource(R.drawable.pause);
					break;
				case STARTED:
					holder.rl_down_size.setVisibility(View.GONE);
					holder.download_pb.setVisibility(View.VISIBLE);
					holder.iv_download.setBackgroundResource(R.drawable.pause);
					break;
				case LOADING:
					holder.rl_down_size.setVisibility(View.GONE);
					holder.download_pb.setVisibility(View.VISIBLE);
					holder.iv_download.setBackgroundResource(R.drawable.pause);
					break;
				case CANCELLED:
					holder.rl_down_size.setVisibility(View.GONE);
					holder.download_pb.setVisibility(View.VISIBLE);
					holder.iv_download.setBackgroundResource(R.drawable.resume);
					break;
				case SUCCESS:
					holder.rl_down_size.setVisibility(View.VISIBLE);
					holder.download_pb.setVisibility(View.GONE);
					holder.iv_download
							.setBackgroundResource(R.drawable.download_pressed);
					break;
				case FAILURE:
					holder.rl_down_size.setVisibility(View.VISIBLE);
					holder.download_pb.setVisibility(View.GONE);
					holder.iv_download
							.setBackgroundResource(R.drawable.download_pressed);
					break;
				default:
					break;
				}

			} else {
				holder.rl_down_size.setVisibility(View.VISIBLE);
				holder.download_pb.setVisibility(View.GONE);

				holder.iv_download
						.setBackgroundResource(R.drawable.download_pressed);
			}

			holder.iv_download.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isRunning) {
						holder.changeBack();
					} else {
						MSShow.show(mAppContext, "start");
						try {
							downloadManager.addNewDownload(appList.get(position)
									.getAppID(), appList.get(position)
									.getAppAddress(), appList.get(position)
									.getAppName(), StaticValue.TARGET
									+ appList.get(position).getAppName() + ".apk",
									true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
									false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
									null);
						} catch (DbException e) {
							LogUtils.e(e.getMessage(), e);
						}

						AppListAdapter.this.notifyDataSetChanged();
					}
				}
			});

			if (downloadInfo != null) {
				HttpHandler<File> handler = downloadInfo.getHandler();
				if (handler != null) {
					RequestCallBack callBack = handler.getRequestCallBack();
					if (callBack instanceof DownloadManager.ManagerCallBack) {
						DownloadManager.ManagerCallBack managerCallBack = (DownloadManager.ManagerCallBack) callBack;
						//if (managerCallBack.getBaseCallBack() == null) {
							managerCallBack
									.setBaseCallBack(new DownloadRequestCallBack());
						//}
					}
					callBack.setUserTag(new WeakReference<Holder>(holder));
				}
			}
		}
		

		return convertView;
	}

	class Holder {
		NetworkImageView app_image;
		TextView tv_app_name;
		TextView tv_down_count;
		TextView tv_app_size;
		TextView tv_brief;
		ImageView iv_download;
		RelativeLayout rl_down_size;
		ProgressBar download_pb;

		DownloadInfo downloadInfo;

		public Holder(DownloadInfo downloadInfo) {
			this.downloadInfo = downloadInfo;
		}

		public void changeBack() {
			State state = downloadInfo.getState();

			switch (state) {
			case WAITING:
			case STARTED:
			case LOADING:
				try {
					downloadManager.stopDownload(downloadInfo);
				} catch (DbException e) {
					LogUtils.e(e.getMessage(), e);
				}
				break;
			case CANCELLED:
			case FAILURE:
				try {
					downloadManager.resumeDownload(downloadInfo,
							new DownloadRequestCallBack());
				} catch (DbException e) {
					LogUtils.e(e.getMessage(), e);
				}
				AppListAdapter.this.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}

		public void update(DownloadInfo downloadInfo) {
			this.downloadInfo = downloadInfo;
			refresh();
		}

		public void refresh() {
			if (downloadInfo != null) {
				if (downloadInfo.getFileLength() > 0) {
					download_pb
							.setProgress((int) (downloadInfo.getProgress() * 100 / downloadInfo
									.getFileLength()));
				} else {
					download_pb.setProgress(0);
				}

				AppListAdapter.this.notifyDataSetChanged();
			}
		}
	}
	
	class HolderMore{
		ImageView iv_loading;
		Button btn_load_more;
	}

	private class DownloadRequestCallBack extends RequestCallBack<File> {

		@SuppressWarnings("unchecked")
		private void refreshListItem() {
			if (userTag == null)
				return;
			WeakReference<Holder> tag = (WeakReference<Holder>) userTag;
			Holder holder = tag.get();
			if (holder != null) {
				holder.refresh();
			}
		}

		@Override
		public void onStart() {
			refreshListItem();
		}

		@Override
		public void onLoading(long total, long current, boolean isUploading) {
			refreshListItem();
		}

		@Override
		public void onSuccess(ResponseInfo<File> responseInfo) {
			refreshListItem();

			if (userTag == null)
				return;
			WeakReference<Holder> tag = (WeakReference<Holder>) userTag;
			Holder holder = tag.get();
			if (holder != null) {
				holder.refresh();

				DownloadInfo info = holder.downloadInfo;

				File file = new File(info.getFileSavePath());

				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file),
						"application/vnd.android.package-archive");
				mAppContext.startActivity(intent);

				
				try {
					downloadManager.removeDownload(info);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AppListAdapter.this.notifyDataSetChanged();
			}
		}

		@Override
		public void onFailure(HttpException error, String msg) {

			refreshListItem();

			if (userTag == null)
				return;
			WeakReference<Holder> tag = (WeakReference<Holder>) userTag;
			Holder holder = tag.get();
			if (holder != null) {
				holder.refresh();

				DownloadInfo info = holder.downloadInfo;

				try {
					downloadManager.removeDownload(info);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AppListAdapter.this.notifyDataSetChanged();
			}

		}

		@Override
		public void onCancelled() {
			refreshListItem();
		}
	}

}

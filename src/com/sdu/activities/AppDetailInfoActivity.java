package com.sdu.activities;

import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalBitmap;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.HttpHandler.State;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.sdu.adapters.CommentListAdapter;
import com.sdu.adapters.GalleryAdapter;
import com.sdu.androidmarket.R;
import com.sdu.beans.AppDetailBean;
import com.sdu.beans.CommentBeans;
import com.sdu.beans.UserBean;
import com.sdu.downUtil.DownloadInfo;
import com.sdu.downUtil.DownloadManager;
import com.sdu.downUtil.DownloadService;
import com.sdu.manager.MarketApplication;
import com.sdu.ui.CommentDialog;
import com.sdu.ui.LoadingDialog;
import com.sdu.ui.ShootShowDialog;
import com.sdu.utils.BFImageCache;
import com.sdu.utils.MSShow;
import com.sdu.utils.StaticValue;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppDetailInfoActivity extends BaseActivity {

	//下载监听
	private DownloadManager manager;
	//下载按钮
	private Button btn_download;
	
	//分享按键 或者是 暂停按键
	private ImageView iv_share;
	
	//收藏按键 或者是 取消按键
	private ImageView iv_collect; 
	
	// 返回按键
	private TextView tv_app_detail_back;

	private NetworkImageView iv_app_icon;
	
	// app名称
	private TextView tv_app_name;

	// 盛放排名的容器
	private LinearLayout ll_rank;

	// app大小
	private TextView tv_app_size;

	// app下载次数
	private TextView tv_down_count;

	// 点击收起和展开的容器
	private RelativeLayout rl_feature;

	// app的安全特性图片
	private ImageView iv_security;

	// app的安全特性文本
	private TextView tv_security;

	// app是否免费图片
	private ImageView iv_free;

	// app是否免费文本
	private TextView tv_free;

	// app是否有广告图片
	private ImageView iv_adver;

	// app是否有广告文本
	private TextView tv_adver;

	// 控件收起与打开文本
	private TextView tv_show;

	// 空间收起与打开图片
	private ImageView iv_show;

	// app安全性详细图片
	private ImageView iv_security_detail;

	// app安全性的详细文本介绍
	private TextView tv_security_detail;

	// app免费性图片详细介绍
	private ImageView iv_free_detail;

	// app免费性文本详细介绍
	private TextView tv_free_detail;

	// app有无广告图片详细介绍
	private ImageView iv_adver_detail;

	// app有无广告文本详细介绍
	private TextView tv_adver_detail;

	// 盛放被收起的界面的容器
	private RelativeLayout rl_feature_detail;

	/* 定义两个静态变量，用于保存收起和展开 */
	private static final String SHOW = "展开";
	private static final String CLOSE = "收起";

	/* 定义一个数组，用于保存上下箭头 */
	private int upAndDown[] = { R.drawable.img_up_action,
			R.drawable.img_down_action };

	// 用于显示截图的gallery
	private Gallery ga_shoot;

	// Gallery Adapter
	private GalleryAdapter ga;

	// 测试数据
	private ArrayList<String> imagesList = new ArrayList<String>();

	// 用于保存展开或收起文本描述的容器
	private RelativeLayout rl_info_label;

	// 在文本描述中展开或收起的Tv
	private TextView tv_info_show;

	// 在文本描述中，展开或收起的图片
	private ImageView iv_info_show;

	// 文本描述1
	private TextView tv_infor_detail1;

	// 文本描述2
	private TextView tv_infor_detail2;

	// 存放第二个文本描述的容器
	private RelativeLayout rl_detail_infor2;

	ViewPager pager = null;
	PagerTabStrip tabStrip = null;
	ArrayList<View> viewContainter = new ArrayList<View>();
	ArrayList<String> titleContainer = new ArrayList<String>();
	public String TAG = "tag";

	//更新时间
	private TextView tv_time_update;
	//作者
	private TextView tv_author;
	//版本号
	private TextView tv_version;
	
	// 显示评论的界面
	private ListView lv_comments;

	// 评论适配器
	private CommentListAdapter cla;

	// 评论数据，测试
	private ArrayList<CommentBeans> commentList = new ArrayList<CommentBeans>();

	/** 保存上一个activity传过来的app ID **/
	private String appID = "";

	/* app 详细细节介绍 容器 */
	private AppDetailBean adb;

	// 测试加载对话框
	private LoadingDialog ld;

	private DownLoadEventNotifier den;
	
	//显示大图片
	private ShootShowDialog ssd;
	
	private DownloadInfo downloadInfo = null;
	
	//评论按钮 
	private Button btn_comment;
	
	//发表评论
	private DownLoadEventNotifier comDen;

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_app_detail);

		FinalBitmap.create(this); // android 框架 这里用于加载网络图片
		
		manager = DownloadService.getDownloadManager(getApplicationContext());
		
		appID = getIntent().getStringExtra("APPID");

		ld = LoadingDialog.createDialog(AppDetailInfoActivity.this);

		tv_app_detail_back = (TextView) findViewById(R.id.tv_app_detail_back);
		tv_app_detail_back.setOnClickListener(this);

		tv_app_name = (TextView) findViewById(R.id.tv_app_name);
		ll_rank = (LinearLayout) findViewById(R.id.ll_rank);
		tv_app_size = (TextView) findViewById(R.id.tv_app_size);
		tv_down_count = (TextView) findViewById(R.id.tv_down_count);

		pager = (ViewPager) this.findViewById(R.id.viewpager);
		tabStrip = (PagerTabStrip) this.findViewById(R.id.tabstrip);
		// 取消tab下面的长横线
		tabStrip.setDrawFullUnderline(true);
		// 设置tab的背景色
		tabStrip.setBackgroundColor(this.getResources().getColor(
				R.color.mbarcolor));
		// 设置当前tab页签的下划线颜色
		tabStrip.setTabIndicatorColor(this.getResources().getColor(
				R.color.white));
		// tabStrip.setTextSpacing(200);
		tabStrip.setTextColor(this.getResources().getColor(R.color.white));

		View briefView = LayoutInflater.from(this).inflate(
				R.layout.app_brief_tab, null);
		View commentView = LayoutInflater.from(this).inflate(
				R.layout.app_comment_tab, null);

		// viewpager开始添加view
		viewContainter.add(briefView);
		viewContainter.add(commentView);

		// 页签项
		titleContainer.add("简介");
		titleContainer.add("评论");

		pager.setAdapter(new PagerAdapter() {

			// viewpager中的组件数量
			@Override
			public int getCount() {
				return viewContainter.size();
			}

			// 滑动切换的时候销毁当前的组件
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				((ViewPager) container).removeView(viewContainter.get(position));
			}

			// 每次滑动的时候生成的组件
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				((ViewPager) container).addView(viewContainter.get(position));
				return viewContainter.get(position);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getItemPosition(Object object) {
				return super.getItemPosition(object);
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return titleContainer.get(position);
			}
		});

		iv_app_icon = (NetworkImageView)findViewById(R.id.iv_app_icon);
		
		rl_feature_detail = (RelativeLayout) briefView
				.findViewById(R.id.rl_feature_detail);

		tv_show = (TextView) briefView.findViewById(R.id.tv_show);
		iv_show = (ImageView) briefView.findViewById(R.id.iv_show);

		rl_feature = (RelativeLayout) briefView.findViewById(R.id.rl_feature);
		rl_feature.setOnClickListener(this);

		iv_security = (ImageView) briefView.findViewById(R.id.iv_security);
		tv_security = (TextView) briefView.findViewById(R.id.tv_security);
		
		iv_adver = (ImageView)briefView.findViewById(R.id.iv_adver);
		tv_adver = (TextView) briefView.findViewById(R.id.tv_adver);
		
		iv_free = (ImageView)briefView.findViewById(R.id.iv_free);
		tv_free = (TextView) briefView.findViewById(R.id.tv_free);

		iv_free_detail = (ImageView) briefView.findViewById(R.id.iv_free_detail);
		tv_free_detail = (TextView) briefView.findViewById(R.id.tv_free_detail);
		
		iv_security_detail = (ImageView) briefView
				.findViewById(R.id.iv_security_detail);
		tv_security_detail = (TextView) briefView
				.findViewById(R.id.tv_security_detail);
		
		iv_adver_detail = (ImageView) briefView
				.findViewById(R.id.iv_adver_detail);
		tv_adver_detail = (TextView) briefView
				.findViewById(R.id.tv_adver_detail);

		ga_shoot = (Gallery) briefView.findViewById(R.id.ga_shoot);
		ga = new GalleryAdapter(imagesList, getApplicationContext());
		ga_shoot.setAdapter(ga);

		ga_shoot.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ssd = ShootShowDialog.createDialog(AppDetailInfoActivity.this, imagesList.get(position));
				ssd.show();
				
			}
		});
		
		rl_info_label = (RelativeLayout) briefView
				.findViewById(R.id.rl_info_label);
		tv_info_show = (TextView) briefView.findViewById(R.id.tv_info_show);
		iv_info_show = (ImageView) briefView.findViewById(R.id.iv_info_show);
		tv_infor_detail1 = (TextView) briefView
				.findViewById(R.id.tv_infor_detail1);
		tv_infor_detail2 = (TextView) briefView
				.findViewById(R.id.tv_infor_detail2);

		rl_detail_infor2 = (RelativeLayout) briefView
				.findViewById(R.id.rl_detail_infor2);

		rl_info_label.setOnClickListener(this);
		
		tv_time_update = (TextView)briefView.findViewById(R.id.tv_time_update);
		tv_author = (TextView)briefView.findViewById(R.id.tv_author);
		tv_version = (TextView)briefView.findViewById(R.id.tv_version);
		
		btn_download = (Button)briefView.findViewById(R.id.btn_download);
		
		iv_share = (ImageView)briefView.findViewById(R.id.iv_share);
		iv_share.setTag(StaticValue.TAG_PAUSE);
		iv_collect = (ImageView)briefView.findViewById(R.id.iv_collect);
		iv_collect.setTag(StaticValue.TAG_COLLECT);
		
		btn_comment = (Button)commentView.findViewById(R.id.btn_comment);
		
		lv_comments = (ListView) commentView.findViewById(R.id.lv_comments);
		lv_comments.setItemsCanFocus(false);

		cla = new CommentListAdapter(commentList, AppDetailInfoActivity.this);

		lv_comments.setAdapter(cla);

		
		btn_comment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				CommentDialog mbd = CommentDialog.createDialog(AppDetailInfoActivity.this,
						new DownInterface() {

							@Override
							public void onDownloadSuccess(String result) {
								if (result.equals("")) {
									MSShow.show(getApplicationContext(), "请输入评论");
								} else {
									comment(appID, result);
								}

							}
						});

				mbd.show();
				
			}
		});

		den = new DownLoadEventNotifier(new DownInterface() {

			@Override
			public void onDownloadSuccess(String result) {
				// TODO Auto-generated method stub
				ld.dismiss();

				setView(result);

			}
		});

		if (MarketApplication.nu.isConnect(AppDetailInfoActivity.this)) {

			ld.show();
			den.start(MarketApplication.nu.getAppDetailJson(appID),
					"servlet/AppDetail");
		}
	}
	
	

	@Override
	protected void onResume() {
		updateUI();
		super.onResume();
	}



	@Override
	protected void onRestart() {
		updateUI();
		super.onRestart();
	}
	
	// 发表评论回复
	private void comment(String id, final String con) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		final String time = df.format(new Date());
		
		DownLoadEventNotifier den = new DownLoadEventNotifier(
				new DownInterface() {

					@Override
					public void onDownloadSuccess(String result) {
						ld.dismiss();

						JSONObject jo;
						try {
							jo = new JSONObject(result);

							if (jo.getInt(StaticValue.ERROR_LABEL) == StaticValue.ERROR_NO) {

								String cid = jo.getString("cid");
								
								if (StaticValue.USER_ID.equals("-1")) {
									commentList.add(new CommentBeans(cid, "-1", "匿名用户", null, "0", time, con, new ArrayList<UserBean>()));
								} else {
									commentList.add(new CommentBeans(cid, StaticValue.USER_ID, StaticValue.USER_NAME, null, "0", time, con, new ArrayList<UserBean>()));
								}
								
								
								cla.notifyDataSetChanged();

							} else {
								MSShow.show(getApplicationContext(), "回复失败，请稍候重试");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

		ld.show();

		String req = MarketApplication.nu.reqComment(id, con, time);
		den.start(req, "servlet/AppDetail");
	}



	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_app_detail_back:
			AppDetailInfoActivity.this.finish();
			break;
		case R.id.rl_feature:
			openAndClose();
			break;
		case R.id.rl_info_label:
			textOpenAndClose();
			break;
		}
	}

	/**
	 * 用于将app的属性进行展开或关闭的方法
	 */
	private void openAndClose() {
		String showText = tv_show.getText().toString();

		if (showText.equals(SHOW)) {
			rl_feature_detail.setVisibility(View.VISIBLE);
			tv_show.setText(CLOSE);
			iv_show.setBackgroundResource(upAndDown[0]);
		} else {
			rl_feature_detail.setVisibility(View.GONE);
			tv_show.setText(SHOW);
			iv_show.setBackgroundResource(upAndDown[1]);
		}
	}

	/**
	 * 用于将app的文本介绍进行展开或关闭的方法
	 */
	private void textOpenAndClose() {
		String showText = tv_info_show.getText().toString();

		if (showText.equals(SHOW)) {
			rl_detail_infor2.setVisibility(View.VISIBLE);
			tv_info_show.setText(CLOSE);
			iv_info_show.setBackgroundResource(upAndDown[0]);
		} else {
			rl_detail_infor2.setVisibility(View.GONE);
			tv_info_show.setText(SHOW);
			iv_info_show.setBackgroundResource(upAndDown[1]);
		}
	}

	/* 设置内容 */
	private void setView(String result) {

		try {
			JSONObject jo = new JSONObject(result);

			if (jo.getInt(StaticValue.ERROR_LABEL) == StaticValue.ERROR_NO) {
				JSONObject appJson = jo.getJSONObject("appInfo");

				final String appAddress = appJson.getString("appAddress");
				String appAdvers = appJson.getString("appAdvers");
				String appAuthor = appJson.getString("appAuthor");
				String appInfor1 = appJson.getString("appDetailInfor1");
				String appInfor2 = appJson.getString("appDetailInfor2");
				String appDownCount = appJson.getString("appDownCount");
				String appFree = appJson.getString("appFree");
				String appGrade = appJson.getString("appGrade");
				final String appID = appJson.getString("appID");
				String appIconAdd = appJson.getString("appIconAdd");
				final String appName = appJson.getString("appName");
				String appSecurity = appJson.getString("appSecurity");
				
				JSONArray photoArray = appJson.getJSONArray("appShootList");

				for(int i = 0;i < photoArray.length();i++){
					imagesList.add(photoArray.getString(i));
				}
				
				ga.notifyDataSetChanged();
				
				String appSize = appJson.getString("appSize");
				String appUpdateTime = appJson.getString("appUpdateTime");
				String appVersion = appJson.getString("appVersion");
				
				
				JSONArray ja = jo.getJSONArray("comment");
				
				ArrayList<CommentBeans> list = new ArrayList<CommentBeans>();
				
				for(int i = 0;i < ja.length();i++){
					JSONObject temp = ja.getJSONObject(i);
					
					JSONArray backList = temp.getJSONArray("backList");
					
					ArrayList<UserBean> reList = new ArrayList<UserBean>();
					
					for(int j = 0;j < backList.length();j++){
						JSONObject ub = backList.getJSONObject(j);
						
						String userID = ub.getString("userID");
						String userName = ub.getString("userName");
						String replyBack = ub.getString("replyBack");
						
						reList.add(new UserBean(userID, userName, replyBack));
					}
					
					String commentContent = temp.getString("commentContent");
					String commentID = temp.getString("commentID");
					String commentTime = temp.getString("commentTime");
					String userHeadPath = temp.getString("userHeadPath");
					String userID = temp.getString("userID");
					String userName = temp.getString("userName");
					String zanSize = temp.getString("zanSize");
					
					list.add(new CommentBeans(commentID, userID, userName, userHeadPath, zanSize, commentTime, commentContent, reList));
				}
				

				commentList.clear();
				for (int i = 0; i < list.size(); i++) {
					commentList.add(list.get(i));
				}
				
				adb = new AppDetailBean(appID, appIconAdd, appName, appGrade, appSize, appDownCount, appSecurity, appFree, appAdvers, imagesList, appInfor1, appInfor2, appUpdateTime, appAuthor, appVersion, appAddress);

				tv_app_name.setText(adb.getAppName());
				
				ImageLoader imageLoader = new ImageLoader(MarketApplication.queue, BFImageCache.getInstance());
				iv_app_icon.setImageUrl(adb.getAppIconAdd(),imageLoader);
				
				int ran = Integer.parseInt(adb.getAppGrade());
				
				setRank(ran);
				
				tv_app_size.setText(adb.getAppSize());
				tv_down_count.setText(adb.getAppDownCount()+"次下载");
				
				int security = Integer.parseInt(adb.getAppSecurity());
				if(security == 1){
					iv_security.setBackgroundResource(R.drawable.checked);
					tv_security.setText("安全");
					
					iv_security_detail.setBackgroundResource(R.drawable.checked);
					tv_security_detail.setText("安全 ：已经通过安全检测");
				}else{
					iv_security.setBackgroundResource(R.drawable.unchecked);
					tv_security.setText("不安全");
					
					iv_security_detail.setBackgroundResource(R.drawable.unchecked);
					tv_security_detail.setText("安全 ：未通过安全检测");
				}
				
				int free = Integer.parseInt(adb.getAppFree());
				if(free == 1){
					iv_free.setBackgroundResource(R.drawable.checked);
					tv_free.setText("免费");
					
					iv_free_detail.setBackgroundResource(R.drawable.checked);
					tv_free_detail.setText("资费 ：免费");
				}else{
					iv_free.setBackgroundResource(R.drawable.unchecked);
					tv_free.setText("收费");
					
					iv_free_detail.setBackgroundResource(R.drawable.unchecked);
					tv_free_detail.setText("资费 ：收费");
				}
				
				int adver = Integer.parseInt(adb.getAppAdvers());
				if(adver == 1){
					iv_adver.setBackgroundResource(R.drawable.checked);
					tv_adver.setText("无广告");
					
					iv_adver_detail.setBackgroundResource(R.drawable.checked);
					tv_adver_detail.setText("广告 ：无广告");
				}else{
					iv_adver.setBackgroundResource(R.drawable.unchecked);
					tv_adver.setText("有广告");
					
					iv_adver_detail.setBackgroundResource(R.drawable.unchecked);
					tv_adver_detail.setText("广告 ：有广告");
				}
				
				tv_infor_detail1.setText(adb.getAppDetailInfor1());
				tv_infor_detail2.setText(adb.getAppDetailInfor2());
				
				tv_time_update.setText("更新 ："+adb.getAppUpdateTime());
				tv_author.setText("作者 ："+adb.getAppAuthor());
				tv_version.setText("版本 ："+adb.getAppVersion());
				
				updateUI();
				
				cla.notifyDataSetChanged();
				
				btn_download.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						MSShow.show(getApplicationContext(), "start");
						try {
							manager.addNewDownload(appID, appAddress, appName, StaticValue.TARGET
									+ appName + ".apk",
									true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
									false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
									null);
						} catch (DbException e) {
							LogUtils.e(e.getMessage(), e);
						}
						
						updateUI();
						
					}
				});

				iv_share.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String tag = (String)iv_share.getTag();
						
						if(tag.equals(StaticValue.TAG_SHARE)){
							MSShow.show(getApplicationContext(), "share");
							
							Intent intent = new Intent(Intent.ACTION_SEND); 
							intent.setType("text/plain"); 
							intent.putExtra(Intent.EXTRA_SUBJECT, "分享"); 
							
							String str = "我在波波市场上看到了一个很好玩的应用，《"+appName+"》，地址是:"+appAddress+" ,点击下载奥!!";
							intent.putExtra(Intent.EXTRA_TEXT, str);  
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
							startActivity(Intent.createChooser(intent, getTitle())); 
						}else if(tag.equals(StaticValue.TAG_PAUSE)){
							if(downloadInfo != null){
								try {
									manager.stopDownload(downloadInfo);
								} catch (DbException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}else if(tag.equals(StaticValue.TAG_RESUME)){
							try {
								manager.resumeDownload(downloadInfo,
										new DownloadRequestCallBack());
							} catch (DbException e) {
								LogUtils.e(e.getMessage(), e);
							}
						}
					}
					
					
				});
				
				iv_collect.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						String tag = (String)iv_collect.getTag();
						
						if(tag.equals(StaticValue.TAG_COLLECT)){
							MSShow.show(getApplicationContext(), "collect");
						}else if(tag.equals(StaticValue.TAG_CANCEL)){
							MSShow.show(getApplicationContext(), "remove");
							
							try {
								manager.removeDownload(downloadInfo);
								downloadInfo = null;
							} catch (DbException e) {
								MSShow.show(getApplicationContext(), "error");
							}
							
							if(!(manager.isRunning(appID))){
								iv_share.setBackgroundResource(R.drawable.share_pressed);
								iv_share.setTag(StaticValue.TAG_SHARE);
								
								iv_collect.setBackgroundResource(R.drawable.save_pressed);
								iv_collect.setTag(StaticValue.TAG_COLLECT);
								
								btn_download.setText("下载");
								btn_download.setClickable(true);
							}
						}
					}
				});
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateUI(){
		if(manager.isRunning(appID)){
			
			btn_download.setText("正在下载...");
			btn_download.setClickable(false);
			
			iv_share.setBackgroundResource(R.drawable.resume);
			iv_share.setTag(StaticValue.TAG_RESUME);
			
			iv_collect.setBackgroundResource(R.drawable.cancel);
			iv_collect.setTag(StaticValue.TAG_CANCEL);
			
			downloadInfo = manager.getById(appID);
						
			HttpHandler<File> handler = downloadInfo.getHandler();
            if (handler != null) {
                RequestCallBack callBack = handler.getRequestCallBack();
                if (callBack instanceof DownloadManager.ManagerCallBack) {
                    DownloadManager.ManagerCallBack managerCallBack = (DownloadManager.ManagerCallBack) callBack;
                        managerCallBack.setBaseCallBack(new DownloadRequestCallBack());
                    
                }
                
               
            }
		}else{
			iv_share.setTag(StaticValue.TAG_SHARE);
			iv_collect.setTag(StaticValue.TAG_COLLECT);
		}
	}

	private void setRank(int rank) {
		ll_rank.removeAllViews();
		
		for(int i = 0;i < rank;i++){
			ImageView iv = new ImageView(getApplicationContext());
			iv.setLayoutParams(new LayoutParams(30,30));
			iv.setBackgroundResource(R.drawable.star_on);
			
			ll_rank.addView(iv);
		}
		
		for(int i = rank; i < 5; i++){
			ImageView iv = new ImageView(getApplicationContext());
			iv.setLayoutParams(new LayoutParams(30,30));
			iv.setBackgroundResource(R.drawable.star_off);
			
			ll_rank.addView(iv);
		}
	}

	private class DownloadRequestCallBack extends RequestCallBack<File> {

		@SuppressWarnings("unchecked")
        private void refreshListItem() {
			
			if(downloadInfo != null){
				
				State state = downloadInfo.getState();

				switch (state) {
				case WAITING:
					btn_download.setText("正在等待...");
					btn_download.setClickable(false);
					
					iv_share.setBackgroundResource(R.drawable.pause);
					iv_share.setTag(StaticValue.TAG_PAUSE);
					
					iv_collect.setBackgroundResource(R.drawable.cancel);
					iv_collect.setTag(StaticValue.TAG_CANCEL);
					break;
				case STARTED:
					setButtonText();
					
					iv_share.setBackgroundResource(R.drawable.pause);
					iv_share.setTag(StaticValue.TAG_PAUSE);
					
					iv_collect.setBackgroundResource(R.drawable.cancel);
					iv_collect.setTag(StaticValue.TAG_CANCEL);
					
					break;
				case LOADING:
					setButtonText();
					
					iv_share.setBackgroundResource(R.drawable.pause);
					iv_share.setTag(StaticValue.TAG_PAUSE);
					
					iv_collect.setBackgroundResource(R.drawable.cancel);
					iv_collect.setTag(StaticValue.TAG_CANCEL);
					
					break;
				case CANCELLED:
					setButtonText();
					
					iv_share.setBackgroundResource(R.drawable.resume);
					iv_share.setTag(StaticValue.TAG_RESUME);
					
					iv_collect.setBackgroundResource(R.drawable.cancel);
					iv_collect.setTag(StaticValue.TAG_CANCEL);
					
					break;
				case SUCCESS:
					btn_download.setText("点击下载");
					btn_download.setClickable(true);
					
					iv_share.setBackgroundResource(R.drawable.share_pressed);
					iv_share.setTag(StaticValue.TAG_SHARE);
					
					iv_collect.setBackgroundResource(R.drawable.save_pressed);
					iv_collect.setTag(StaticValue.TAG_COLLECT);
					
					File file = new File(downloadInfo.getFileSavePath());
					
					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setAction(android.content.Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file),
							"application/vnd.android.package-archive");
					startActivity(intent);
					
					try {
						manager.removeDownload(downloadInfo);
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case FAILURE:
					MSShow.show(getApplicationContext(), "下载失败");
					btn_download.setText("点击下载");
					btn_download.setClickable(true);
					
					iv_share.setBackgroundResource(R.drawable.share_pressed);
					iv_share.setTag(StaticValue.TAG_SHARE);
					
					iv_collect.setBackgroundResource(R.drawable.save_pressed);
					iv_collect.setTag(StaticValue.TAG_COLLECT);
					
					try {
						manager.removeDownload(downloadInfo);
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				default:
					break;
				}
				
				
			}
        }
		
		private void setButtonText(){
			double pro = 0;
			
			if(downloadInfo.getFileLength() != 0){
				pro = downloadInfo.getProgress() * 100 / downloadInfo.getFileLength();
			}
			
			btn_download.setText("正在下载("+pro+"%)");
			btn_download.setClickable(false);
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
        }

        @Override
        public void onFailure(HttpException error, String msg) {
        	refreshListItem();
        }

        @Override
        public void onCancelled() {
        	refreshListItem();
        }
    }
}

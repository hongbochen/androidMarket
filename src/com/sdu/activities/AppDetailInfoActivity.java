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

	//���ؼ���
	private DownloadManager manager;
	//���ذ�ť
	private Button btn_download;
	
	//������ ������ ��ͣ����
	private ImageView iv_share;
	
	//�ղذ��� ������ ȡ������
	private ImageView iv_collect; 
	
	// ���ذ���
	private TextView tv_app_detail_back;

	private NetworkImageView iv_app_icon;
	
	// app����
	private TextView tv_app_name;

	// ʢ������������
	private LinearLayout ll_rank;

	// app��С
	private TextView tv_app_size;

	// app���ش���
	private TextView tv_down_count;

	// ��������չ��������
	private RelativeLayout rl_feature;

	// app�İ�ȫ����ͼƬ
	private ImageView iv_security;

	// app�İ�ȫ�����ı�
	private TextView tv_security;

	// app�Ƿ����ͼƬ
	private ImageView iv_free;

	// app�Ƿ�����ı�
	private TextView tv_free;

	// app�Ƿ��й��ͼƬ
	private ImageView iv_adver;

	// app�Ƿ��й���ı�
	private TextView tv_adver;

	// �ؼ���������ı�
	private TextView tv_show;

	// �ռ��������ͼƬ
	private ImageView iv_show;

	// app��ȫ����ϸͼƬ
	private ImageView iv_security_detail;

	// app��ȫ�Ե���ϸ�ı�����
	private TextView tv_security_detail;

	// app�����ͼƬ��ϸ����
	private ImageView iv_free_detail;

	// app������ı���ϸ����
	private TextView tv_free_detail;

	// app���޹��ͼƬ��ϸ����
	private ImageView iv_adver_detail;

	// app���޹���ı���ϸ����
	private TextView tv_adver_detail;

	// ʢ�ű�����Ľ��������
	private RelativeLayout rl_feature_detail;

	/* ����������̬���������ڱ��������չ�� */
	private static final String SHOW = "չ��";
	private static final String CLOSE = "����";

	/* ����һ�����飬���ڱ������¼�ͷ */
	private int upAndDown[] = { R.drawable.img_up_action,
			R.drawable.img_down_action };

	// ������ʾ��ͼ��gallery
	private Gallery ga_shoot;

	// Gallery Adapter
	private GalleryAdapter ga;

	// ��������
	private ArrayList<String> imagesList = new ArrayList<String>();

	// ���ڱ���չ���������ı�����������
	private RelativeLayout rl_info_label;

	// ���ı�������չ���������Tv
	private TextView tv_info_show;

	// ���ı������У�չ���������ͼƬ
	private ImageView iv_info_show;

	// �ı�����1
	private TextView tv_infor_detail1;

	// �ı�����2
	private TextView tv_infor_detail2;

	// ��ŵڶ����ı�����������
	private RelativeLayout rl_detail_infor2;

	ViewPager pager = null;
	PagerTabStrip tabStrip = null;
	ArrayList<View> viewContainter = new ArrayList<View>();
	ArrayList<String> titleContainer = new ArrayList<String>();
	public String TAG = "tag";

	//����ʱ��
	private TextView tv_time_update;
	//����
	private TextView tv_author;
	//�汾��
	private TextView tv_version;
	
	// ��ʾ���۵Ľ���
	private ListView lv_comments;

	// ����������
	private CommentListAdapter cla;

	// �������ݣ�����
	private ArrayList<CommentBeans> commentList = new ArrayList<CommentBeans>();

	/** ������һ��activity��������app ID **/
	private String appID = "";

	/* app ��ϸϸ�ڽ��� ���� */
	private AppDetailBean adb;

	// ���Լ��ضԻ���
	private LoadingDialog ld;

	private DownLoadEventNotifier den;
	
	//��ʾ��ͼƬ
	private ShootShowDialog ssd;
	
	private DownloadInfo downloadInfo = null;
	
	//���۰�ť 
	private Button btn_comment;
	
	//��������
	private DownLoadEventNotifier comDen;

	@Override
	public void initWidget() {
		// TODO Auto-generated method stub

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_app_detail);

		FinalBitmap.create(this); // android ��� �������ڼ�������ͼƬ
		
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
		// ȡ��tab����ĳ�����
		tabStrip.setDrawFullUnderline(true);
		// ����tab�ı���ɫ
		tabStrip.setBackgroundColor(this.getResources().getColor(
				R.color.mbarcolor));
		// ���õ�ǰtabҳǩ���»�����ɫ
		tabStrip.setTabIndicatorColor(this.getResources().getColor(
				R.color.white));
		// tabStrip.setTextSpacing(200);
		tabStrip.setTextColor(this.getResources().getColor(R.color.white));

		View briefView = LayoutInflater.from(this).inflate(
				R.layout.app_brief_tab, null);
		View commentView = LayoutInflater.from(this).inflate(
				R.layout.app_comment_tab, null);

		// viewpager��ʼ���view
		viewContainter.add(briefView);
		viewContainter.add(commentView);

		// ҳǩ��
		titleContainer.add("���");
		titleContainer.add("����");

		pager.setAdapter(new PagerAdapter() {

			// viewpager�е��������
			@Override
			public int getCount() {
				return viewContainter.size();
			}

			// �����л���ʱ�����ٵ�ǰ�����
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				((ViewPager) container).removeView(viewContainter.get(position));
			}

			// ÿ�λ�����ʱ�����ɵ����
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
									MSShow.show(getApplicationContext(), "����������");
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
	
	// �������ۻظ�
	private void comment(String id, final String con) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
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
									commentList.add(new CommentBeans(cid, "-1", "�����û�", null, "0", time, con, new ArrayList<UserBean>()));
								} else {
									commentList.add(new CommentBeans(cid, StaticValue.USER_ID, StaticValue.USER_NAME, null, "0", time, con, new ArrayList<UserBean>()));
								}
								
								
								cla.notifyDataSetChanged();

							} else {
								MSShow.show(getApplicationContext(), "�ظ�ʧ�ܣ����Ժ�����");
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
	 * ���ڽ�app�����Խ���չ����رյķ���
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
	 * ���ڽ�app���ı����ܽ���չ����رյķ���
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

	/* �������� */
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
				tv_down_count.setText(adb.getAppDownCount()+"������");
				
				int security = Integer.parseInt(adb.getAppSecurity());
				if(security == 1){
					iv_security.setBackgroundResource(R.drawable.checked);
					tv_security.setText("��ȫ");
					
					iv_security_detail.setBackgroundResource(R.drawable.checked);
					tv_security_detail.setText("��ȫ ���Ѿ�ͨ����ȫ���");
				}else{
					iv_security.setBackgroundResource(R.drawable.unchecked);
					tv_security.setText("����ȫ");
					
					iv_security_detail.setBackgroundResource(R.drawable.unchecked);
					tv_security_detail.setText("��ȫ ��δͨ����ȫ���");
				}
				
				int free = Integer.parseInt(adb.getAppFree());
				if(free == 1){
					iv_free.setBackgroundResource(R.drawable.checked);
					tv_free.setText("���");
					
					iv_free_detail.setBackgroundResource(R.drawable.checked);
					tv_free_detail.setText("�ʷ� �����");
				}else{
					iv_free.setBackgroundResource(R.drawable.unchecked);
					tv_free.setText("�շ�");
					
					iv_free_detail.setBackgroundResource(R.drawable.unchecked);
					tv_free_detail.setText("�ʷ� ���շ�");
				}
				
				int adver = Integer.parseInt(adb.getAppAdvers());
				if(adver == 1){
					iv_adver.setBackgroundResource(R.drawable.checked);
					tv_adver.setText("�޹��");
					
					iv_adver_detail.setBackgroundResource(R.drawable.checked);
					tv_adver_detail.setText("��� ���޹��");
				}else{
					iv_adver.setBackgroundResource(R.drawable.unchecked);
					tv_adver.setText("�й��");
					
					iv_adver_detail.setBackgroundResource(R.drawable.unchecked);
					tv_adver_detail.setText("��� ���й��");
				}
				
				tv_infor_detail1.setText(adb.getAppDetailInfor1());
				tv_infor_detail2.setText(adb.getAppDetailInfor2());
				
				tv_time_update.setText("���� ��"+adb.getAppUpdateTime());
				tv_author.setText("���� ��"+adb.getAppAuthor());
				tv_version.setText("�汾 ��"+adb.getAppVersion());
				
				updateUI();
				
				cla.notifyDataSetChanged();
				
				btn_download.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						MSShow.show(getApplicationContext(), "start");
						try {
							manager.addNewDownload(appID, appAddress, appName, StaticValue.TARGET
									+ appName + ".apk",
									true, // ���Ŀ���ļ����ڣ�����δ��ɵĲ��ּ������ء���������֧��RANGEʱ���������ء�
									false, // ��������󷵻���Ϣ�л�ȡ���ļ�����������ɺ��Զ���������
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
							intent.putExtra(Intent.EXTRA_SUBJECT, "����"); 
							
							String str = "���ڲ����г��Ͽ�����һ���ܺ����Ӧ�ã���"+appName+"������ַ��:"+appAddress+" ,������ذ�!!";
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
								
								btn_download.setText("����");
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
			
			btn_download.setText("��������...");
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
					btn_download.setText("���ڵȴ�...");
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
					btn_download.setText("�������");
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
					MSShow.show(getApplicationContext(), "����ʧ��");
					btn_download.setText("�������");
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
			
			btn_download.setText("��������("+pro+"%)");
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

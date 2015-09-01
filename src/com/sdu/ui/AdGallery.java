package com.sdu.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.sdu.utils.ScreenUtil;

import net.tsz.afinal.FinalBitmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * ���޹�����������
 */
@SuppressWarnings("deprecation")
public class AdGallery extends Gallery implements
		android.widget.AdapterView.OnItemClickListener,
		android.widget.AdapterView.OnItemSelectedListener, OnTouchListener {
	/** ��ʾ��Activity */
	private Context mContext;
	/** ��Ŀ�����¼��ӿ� */
	private AdversOnItemClickListener mAdversOnItemClickListener;
	/** ͼƬ�л�ʱ�� */
	private int mSwitchTime;
	/** �Զ������Ķ�ʱ�� */
	private Timer mTimer;
	/** Բ������ */
	private LinearLayout mOvalLayout;
	/** ��ǰѡ�е��������� */
	private int curIndex = 0;
	/** �ϴ�ѡ�е��������� */
	private int oldIndex = 0;
	/** Բ��ѡ��ʱ�ı���ID */
	private int mFocusedId;
	/** Բ������ʱ�ı���ID */
	private int mNormalId;
	/** ͼƬ��ԴID�� */
	private int[] mAdsId;
	/** ͼƬ����·������ */
	private ArrayList<String> mUris;
	/** ImageView�� */
	List<ImageView> listImgs;

	public AdGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public AdGallery(Context context) {
		super(context);
	}

	public AdGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 *            ��ʾ��Activity ,����Ϊnull
	 * @param mris
	 *            ͼƬ������·������ ,Ϊ��ʱ ���� adsId
	 * @param adsId
	 *            ͼƬ����ԴID ,������
	 * @param switchTime
	 *            ͼƬ�л�ʱ�� д0 Ϊ���Զ��л�
	 * @param ovalLayout
	 *            Բ������ ,��Ϊ��
	 * @param focusedId
	 *            Բ��ѡ��ʱ�ı���ID,Բ��������Ϊ��д0
	 * @param normalId
	 *            Բ������ʱ�ı���ID,Բ������Ϊ��д0
	 */
	public void start(Context context, ArrayList<String> mris, int[] adsId,
			int switchTime, LinearLayout ovalLayout, int focusedId, int normalId) {
		this.mContext = context;
		this.mUris = mris;
		this.mAdsId = adsId;
		this.mSwitchTime = switchTime;
		this.mOvalLayout = ovalLayout;
		this.mFocusedId = focusedId;
		this.mNormalId = normalId;
		ininImages();// ��ʼ��ͼƬ��
		setAdapter(new AdAdapter());
		this.setOnItemClickListener(this);
		this.setOnTouchListener(this);
		this.setOnItemSelectedListener(this);
		this.setSoundEffectsEnabled(false);
		this.setAnimationDuration(700); // ����ʱ��
		this.setUnselectedAlpha(1); // δѡ����Ŀ��͸����
		// ������spacing�ᵼ��onKeyDown()ʧЧ!!! ʧЧonKeyDown()ǰ�ȵ���onScroll(null,1,0)�ɴ���
		setSpacing(0);
		// ȡ�����м� ͼƬ�����������
		setSelection((getCount() / 2 / listImgs.size()) * listImgs.size()); // Ĭ��ѡ���м�λ��Ϊ��ʼλ��
		setFocusableInTouchMode(true);
		initOvalLayout();// ��ʼ��Բ��
		startTimer();// ��ʼ�Զ���������
	}

	/** ��ʼ��ͼƬ�� */
	private void ininImages() {
		listImgs = new ArrayList<ImageView>(); // ͼƬ��
		int len = mUris != null ? mUris.size() : mAdsId.length;
		for (int i = 0; i < len; i++) {
			ImageView imageview = new ImageView(mContext); // ʵ����ImageView�Ķ���
			imageview.setScaleType(ImageView.ScaleType.FIT_XY); // �������ŷ�ʽ
			
			imageview.setLayoutParams(new Gallery.LayoutParams(
					Gallery.LayoutParams.MATCH_PARENT,
					Gallery.LayoutParams.WRAP_CONTENT));
		
			
			if (mUris == null) {// ���ؼ���ͼƬ
				imageview.setImageResource(mAdsId[i]); // ΪImageView����Ҫ��ʾ��ͼƬ
			} else { // �������ͼƬ
				FinalBitmap.create(mContext)
						.display(imageview, mUris.get(i), imageview.getWidth(),
								imageview.getHeight(), null, null);
			}
			listImgs.add(imageview);
		}

	}

	/** ��ʼ��Բ�� */
	private void initOvalLayout() {
		if (mOvalLayout != null && listImgs.size() < 2) {// ���ֻ��һ��ͼʱ����ʾԲ������
			mOvalLayout.getLayoutParams().height = 0;
		} else if (mOvalLayout != null) {
			// Բ��Ĵ�С�� Բ�㴰�ڵ� 70%;
			int Ovalheight = (int) (mOvalLayout.getLayoutParams().height * 0.7);
			// Բ���������߾��� Բ�㴰�ڵ� 20%;
			int Ovalmargin = (int) (mOvalLayout.getLayoutParams().height * 0.2);
			android.widget.LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					Ovalheight, Ovalheight);
			layoutParams.setMargins(Ovalmargin, 0, Ovalmargin, 0);
			for (int i = 0; i < listImgs.size(); i++) {
				View v = new View(mContext); // Ա��
				v.setLayoutParams(layoutParams);
				v.setBackgroundResource(mNormalId);
				mOvalLayout.addView(v);
			}
			// ѡ�е�һ��
			mOvalLayout.getChildAt(0).setBackgroundResource(mFocusedId);
		}
	}

	/** ����ѭ�������� */
	class AdAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			if (listImgs.size() < 2)// ���ֻ��һ��ͼʱ������
				return listImgs.size();
			return Integer.MAX_VALUE;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return listImgs.get(position % listImgs.size()); // ����ImageView
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int kEvent;
		if (isScrollingLeft(e1, e2)) { // ����Ƿ����󻬶�
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else { // ����Ƿ����һ���
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);
		return true;

	}

	/** ����Ƿ����󻬶� */
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > (e1.getX() + 50);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (MotionEvent.ACTION_UP == event.getAction()
				|| MotionEvent.ACTION_CANCEL == event.getAction()) {
			startTimer();// ��ʼ�Զ���������
		} else {
			stopTimer();// ֹͣ�Զ���������
		}
		return false;
	}

	/** ͼƬ�л��¼� */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		curIndex = position % listImgs.size();
		if (mOvalLayout != null && listImgs.size() > 1) { // �л�Բ��
			mOvalLayout.getChildAt(oldIndex).setBackgroundResource(mNormalId); // Բ��ȡ��
			mOvalLayout.getChildAt(curIndex).setBackgroundResource(mFocusedId);// Բ��ѡ��
			oldIndex = curIndex;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	/** ��Ŀ����¼� */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		if (mAdversOnItemClickListener != null) {
			mAdversOnItemClickListener.onItemClick(curIndex);
		}
	}

	/** ������Ŀ����¼������� */
	public void setAdversOnItemClickListener(AdversOnItemClickListener listener) {
		mAdversOnItemClickListener = listener;
	}

	/** ��Ŀ����¼��������ӿ� */
	public interface AdversOnItemClickListener {
		/**
		 * @param curIndex
		 *            //��ǰ��Ŀ�������е��±�
		 */
		void onItemClick(int curIndex);
	}

	/** ֹͣ�Զ��������� */
	public void stopTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
	}

	/** ��ʼ�Զ��������� ͼƬ����1�ŲŹ��� */
	public void startTimer() {
		if (mTimer == null && listImgs.size() > 1 && mSwitchTime > 0) {
			mTimer = new Timer();
			mTimer.schedule(new TimerTask() {
				public void run() {
					handler.sendMessage(handler.obtainMessage(1));
				}
			}, mSwitchTime, mSwitchTime);
		}
	}

	/** ����ʱ�������� */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// ������spacing�ᵼ��onKeyDown()ʧЧ!!!
			// ʧЧonKeyDown()ǰ�ȵ���onScroll(null,1,0)�ɴ���
			onScroll(null, null, 1, 0);
			onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
		}
	};
}

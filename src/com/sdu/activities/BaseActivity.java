package com.sdu.activities;

import com.sdu.manager.AppManager;
import com.sdu.utils.AppLog;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;


/**
 * Ӧ�ó���Activity�Ļ���
 *
 * @author bobo
 * @version 1.0
 * @created 2015-05-26
 */
public abstract class BaseActivity extends Activity implements
        OnClickListener{

    private static final int ACTIVITY_RESUME = 0;
    private static final int ACTIVITY_STOP = 1;
    private static final int ACTIVITY_PAUSE = 2;
    private static final int ACTIVITY_DESTROY = 3;

    public int activityState;

    // �Ƿ�����ȫ��
    private boolean mAllowFullScreen = true;

    public abstract void initWidget();

    public abstract void widgetClick(View v);


    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /* ������� */
    @Override
    public void onClick(View v) {
        widgetClick(v);

    }

    /***************************************************************************
     *
     * ��ӡActivity��������
     *
     ***************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppLog.debug(this.getClass().getName() + "---------onCreat ");

        // ��������
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE); // ȡ������
        }

        AppManager.getAppManager().addActivity(this);
        initWidget();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppLog.state(this.getClass().getName()+ "---------onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = ACTIVITY_RESUME;
        AppLog.state(this.getClass().getName()+ "---------onResume ");
    }

    @Override
    protected void onStop() {
        super.onResume();
        activityState = ACTIVITY_STOP;
        AppLog.state(this.getClass().getName()+ "---------onStop ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ACTIVITY_PAUSE;
        AppLog.state(this.getClass().getName()+ "---------onPause ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AppLog.state(this.getClass().getName()+ "---------onRestart ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityState = ACTIVITY_DESTROY;
        AppLog.state(this.getClass().getName()+ "---------onDestroy ");
        AppManager.getAppManager().finishActivity(this);
    }
}
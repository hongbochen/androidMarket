package com.sdu.activities;

import java.io.File;

import com.sdu.androidmarket.R;
import com.sdu.ui.LoadingDialog;
import com.sdu.utils.MSShow;
import com.sdu.utils.StaticValue;

import android.provider.ContactsContract.Directory;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity extends BaseActivity {

	private TextView tv_setting_back;
	private Button btn_clear;
	
	private LoadingDialog ld;
	
	@Override
	public void initWidget() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings);
		
		tv_setting_back = (TextView)findViewById(R.id.tv_setting_back);
		btn_clear = (Button)findViewById(R.id.btn_clear);
		
		tv_setting_back.setOnClickListener(this);
		btn_clear.setOnClickListener(this);

		ld = LoadingDialog.createDialog(SettingActivity.this);
	}

	@Override
	public void widgetClick(View v) {
		switch(v.getId()){
		case R.id.tv_setting_back:
			SettingActivity.this.finish();
			break;
		case R.id.btn_clear:
			ld.show();
			clearCache();
			ld.dismiss();
			
			MSShow.show(getApplicationContext(), "Çå³ý»º´æ³É¹¦!!");
			break;
		}

	}

	private void clearCache(){
		File file = new File(StaticValue.ROOT_DIR);
		
		if(file.isDirectory()){
			String list[] = file.list();
			
			for(int i = 0;i < list.length;i++){
				File temp = new File(StaticValue.ROOT_DIR+list[i]);
				
				if(temp.exists())
					temp.delete();
			}
			
		}
	}
}

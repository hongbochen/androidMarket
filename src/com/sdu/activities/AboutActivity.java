package com.sdu.activities;

import com.sdu.androidmarket.R;

import android.view.View;
import android.view.Window;

public class AboutActivity extends BaseActivity {

	
	@Override
	public void initWidget() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_about);

	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub

	}

}

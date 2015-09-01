package com.sdu.activities;

import com.sdu.androidmarket.R;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;

public class DiacodeActivity extends BaseActivity {

	private TextView tv_diacode_back;
	
	@Override
	public void initWidget() {
		// TODO Auto-generated method stub
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_diacode);
		
		tv_diacode_back = (TextView)findViewById(R.id.tv_diacode_back);
		
		tv_diacode_back.setOnClickListener(this);
	}

	@Override
	public void widgetClick(View v) {
		// TODO Auto-generated method stub
		
		Intent intent = null;
		
		switch(v.getId()){
		case R.id.tv_diacode_back:
			DiacodeActivity.this.finish();
			break;
		}

	}

}

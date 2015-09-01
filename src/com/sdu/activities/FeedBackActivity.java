package com.sdu.activities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import com.sdu.androidmarket.R;
import com.sdu.manager.MarketApplication;
import com.sdu.ui.LoadingDialog;
import com.sdu.utils.MSShow;
import com.sdu.utils.StaticValue;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FeedBackActivity extends BaseActivity {

	private TextView tv_feed_back_back;
	private EditText et_text_reply;
	private Button btn_feed_back;
	
	private LoadingDialog ld;
	
	private DownLoadEventNotifier den;
	
	@Override
	public void initWidget() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feed_back);
		
		tv_feed_back_back = (TextView)findViewById(R.id.tv_feed_back_back);
		et_text_reply = (EditText)findViewById(R.id.et_text_reply);
		btn_feed_back = (Button)findViewById(R.id.btn_feed_back);
		
		tv_feed_back_back.setOnClickListener(this);
		btn_feed_back.setOnClickListener(this);
		
		ld = LoadingDialog.createDialog(FeedBackActivity.this);
		den = new DownLoadEventNotifier(new DownInterface() {
			
			@Override
			public void onDownloadSuccess(String result) {
				ld.show();
				
				try{
					JSONObject jo = new JSONObject(result);
					int err = jo.getInt(StaticValue.ERROR_LABEL);
					if(err == StaticValue.ERROR_NO){
						MSShow.show(getApplicationContext(), "我们会及时处理您的反馈!!");
					}else{
						MSShow.show(getApplicationContext(), "反馈失败，请稍候重试!!");
					}
					
					FeedBackActivity.this.finish();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
			}
		});
		
	}

	@Override
	public void widgetClick(View v) {
		switch(v.getId()){
		case R.id.tv_feed_back_back:
			FeedBackActivity.this.finish();
			break;
		case R.id.btn_feed_back:
			String back = et_text_reply.getText().toString();
			if(back.equals("")){
				MSShow.show(getApplicationContext(), "请输入内容!!");
			}else{
				if(MarketApplication.nu.isConnect(getApplicationContext())){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					String time = df.format(new Date());
					
					String req = MarketApplication.nu.getFeedBackReq(back,time);
					ld.show();
					den.start(req, "servlet/FeedBack");
				}
			}
			break;
		}

	}

}

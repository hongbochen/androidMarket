package com.sdu.ui;

import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import com.sdu.androidmarket.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class MessageBackDialog extends Activity {

	// 提交按钮
	private Button commitBtn;

	// commentID
	private String commentID;

	// 当前的类型
	private String type;

	// appID
	private String appID;

	// 测试加载对话框
	private LoadingDialog ld;

	private DownLoadEventNotifier den;

	// 编辑评论
	private EditText et_text_reply;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_reply_layout);

		Intent intent = getIntent();
		type = intent.getStringExtra("TYPE");

		if (type.equals("BACK")) {
			commentID = intent.getStringExtra("CID");
		} else {
			appID = intent.getStringExtra("AID");
		}

		initView();
		setListener();
	}

	private void initView() {
		ld = LoadingDialog.createDialog(getApplicationContext());

		et_text_reply = (EditText) findViewById(R.id.et_text_reply);
		commitBtn = (Button) findViewById(R.id.btn_commit);

		den = new DownLoadEventNotifier(new DownInterface() {

			@Override
			public void onDownloadSuccess(String result) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void setListener() {
		commitBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

}

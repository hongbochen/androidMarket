package com.sdu.activities;

import interfaces.DownInterface;
import interfaces.DownLoadEventNotifier;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sdu.adapters.SortGridViewAdapter;
import com.sdu.androidmarket.R;
import com.sdu.beans.SortItemBean;
import com.sdu.manager.MarketApplication;
import com.sdu.ui.LoadingDialog;
import com.sdu.utils.StaticValue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.Window;
import android.widget.*;

public class SortActivity extends BaseActivity {
	private GridView gv;

	private ArrayList<SortItemBean> list = new ArrayList<SortItemBean>();
	private LoadingDialog ld;
	
	private DownLoadEventNotifier den;
	
	private SortGridViewAdapter sgva;
	
	@Override
	public void initWidget() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sort);
		initView();
		setListener();
	}

	@Override
	public void widgetClick(View v) {
	}

	private void initView() {
		
		ld = LoadingDialog.createDialog(SortActivity.this);
		
		gv = (GridView) findViewById(R.id.gv_game);
		sgva = new SortGridViewAdapter(list,
				getApplicationContext());
		gv.setAdapter(sgva);
		
		den = new DownLoadEventNotifier(new DownInterface() {
			
			@Override
			public void onDownloadSuccess(String result) {
				ld.dismiss();
				
				dealResult(result);
				
			}
		});
		
		ld.show();
		String req = MarketApplication.nu.getSortsReq();
		den.start(req, "servlet/Sort");
	}
	
	private void setListener(){
		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SortActivity.this,SortListShowActivity.class);
				intent.putExtra("TYPENAME", list.get(position).getTypeName());
				intent.putExtra("TYPEID", list.get(position).getTypeID());
				startActivity(intent);
				
			}
		});
	}
	
	private void dealResult(String result){
		try {
			JSONObject jo = new JSONObject(result);
			int err = jo.getInt(StaticValue.ERROR_LABEL);
			
			if(err == StaticValue.ERROR_NO){
				JSONArray ja = jo.getJSONArray("sorts");
				
				for(int i = 0;i < ja.length();i++){
					JSONObject obj = ja.getJSONObject(i);
					
					String id = obj.getString("typeID");
					String name = obj.getString("typeName");
					String add = obj.getString("typeAdd");
					
					list.add(new SortItemBean(id, name, add));
				}
				
				sgva.notifyDataSetChanged();
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

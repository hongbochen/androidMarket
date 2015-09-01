package com.sdu.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class UIHelper {

	public static void setListViewHeightBasedOnChildern(ListView listview){
		ListAdapter listAdapter = listview.getAdapter();
		
		if(listAdapter == null){
			return;
		}
		
		int totalHeight = 0;
		for(int i = 0;i < listAdapter.getCount();i++){
			View listItem = listAdapter.getView(i, null, listview);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		
		ViewGroup.LayoutParams params = listview.getLayoutParams();
		params.height = totalHeight + (listview.getDividerHeight() * (listAdapter.getCount()-1));
		listview.setLayoutParams(params);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

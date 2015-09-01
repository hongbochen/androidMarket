package com.sdu.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class NetUtil {

	public boolean isConnect(Context context) {

		boolean connected = false;

		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();
		if (wifi | internet) {
			connected = true;
		}

		return connected;
	}

	public String getAppBriefJson(int page) {
		JSONObject jo = new JSONObject();

		try {
			jo.put(StaticValue.PAGE, page);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}

	public String getHomeAppBriefJson() {
		JSONObject jo = new JSONObject();

		try {
			jo.put("Home", "Home");

			jo.put(StaticValue.PAGE, 1);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}

	public String getAppDetailJson(String appID) {
		JSONObject jo = new JSONObject();

		try {
			jo.put("TYPE", "DETAIL");
			jo.put("APPID", appID);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();

	}

	public String getMoreApp(int page) {
		JSONObject jo = new JSONObject();

		try {
			jo.put(StaticValue.PAGE, page);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}

	/**
	 * 回复评论
	 * @param commentID 被恢复的评论的ID
	 * @param comment   回复的内容
	 * @return			返回请求数据
	 */
	public String reqCommentComment(String commentID,String comment) {
		JSONObject jo = new JSONObject();

		try {
			jo.put("TYPE","CommentComment");
			jo.put("userID", StaticValue.USER_ID);
			jo.put("cid", commentID);
			jo.put("content",comment);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String reqComment(String id,String comment,String comTime) {
		JSONObject jo = new JSONObject();

		try {
			jo.put("TYPE","Comment");
			jo.put("userID", StaticValue.USER_ID);
			jo.put("aid", id);
			jo.put("content",comment);
			jo.put("comTime",comTime);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String reqZan(String cid){
		JSONObject jo = new JSONObject();

		try {
			jo.put("TYPE","Zan");
			jo.put("cid", cid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String getSortsReq(){
		JSONObject jo = new JSONObject();

		try {
			jo.put("TYPE","Sort");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String getTypeListReq(String typeID,String page){
		JSONObject jo = new JSONObject();

		try {
			jo.put("typeID",typeID);
			jo.put("page",page);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String getHotAppReq(){
		JSONObject jo = new JSONObject();

		try {
			jo.put("hot","hot");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String getHotListReq(int type,String page){
		JSONObject jo = new JSONObject();

		try {
			jo.put("type",""+type);
			jo.put("page",page);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String getRecReq(){
		JSONObject jo = new JSONObject();

		try {
			jo.put("rec","rec");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String getThemeReq(){
		JSONObject jo = new JSONObject();

		try {
			jo.put("theme","theme");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String getThemeListReq(String themeID,String page){
		JSONObject jo = new JSONObject();

		try {
			jo.put("themeID",themeID);
			jo.put("page",page);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String getSearchReq(String keywords,String page){
		JSONObject jo = new JSONObject();

		try {
			jo.put("keywords",keywords);
			jo.put("page",page);
			jo.put("type","search");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String getKeyWordsReq(){
		JSONObject jo = new JSONObject();

		try {
			jo.put("type","key");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}

	public String getVersionReq(){
		JSONObject jo = new JSONObject();

		try {
			jo.put("version",StaticValue.VERSION);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
	
	public String getFeedBackReq(String back,String time){
		JSONObject jo = new JSONObject();

		try {
			jo.put("back",back);
			jo.put("time", time);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jo.toString();
	}
}

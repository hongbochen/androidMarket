package com.sdu.beans;

public class AdBean {

	private String appID;
	private String imageUrl;
	
	public AdBean(String appID, String imageUrl) {
		super();
		this.appID = appID;
		this.imageUrl = imageUrl;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}

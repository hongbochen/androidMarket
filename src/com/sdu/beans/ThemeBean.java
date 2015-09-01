package com.sdu.beans;

public class ThemeBean {

	private String themeID;
	private String ImageAdd;
	private String themeText;
	
	public ThemeBean(String themeID,String imageAdd, String themeText) {
		super();
		this.themeID = themeID;
		ImageAdd = imageAdd;
		this.themeText = themeText;
	}
	
	

	public String getThemeID() {
		return themeID;
	}



	public void setThemeID(String themeID) {
		this.themeID = themeID;
	}



	public String getImageAdd() {
		return ImageAdd;
	}

	public void setImageAdd(String imageAdd) {
		ImageAdd = imageAdd;
	}

	public String getThemeText() {
		return themeText;
	}

	public void setThemeText(String themeText) {
		this.themeText = themeText;
	}
	
	
}

package com.sdu.beans;

import java.util.ArrayList;

public class AppDetailBean {

	private String appID; /* app的唯一标识 */
	private String appIconAdd; /* app的图标的地址 */
	private String appName; /* app的名称 */
	private String appGrade;	/* app评分 */
	private String appSize; /* app的大小 */
	private String appDownCount; /* app的下载次数 */
	private String appSecurity; /* app安全性 */
	private String appFree;	/* app免费性 */
	private String appAdvers; /* app广告性 */
	private ArrayList<String> appShootList; /* app截图列表 */
	private String appDetailInfor1; /* app详细描述1 */
	private String appDetailInfor2; /* app详细信息2 */
	private String appUpdateTime;	/* app更新时间 */
	private String appAuthor;	/* app作者 */
	private String appVersion;	/* app版本号 */
	private String appAddress; /* app的下载地址 */
	
	
	public AppDetailBean(String appID, String appIconAdd, String appName,
			String appGrade, String appSize, String appDownCount,
			String appSecurity, String appFree, String appAdvers,
			ArrayList<String> appShootList, String appDetailInfor1,
			String appDetailInfor2, String appUpdateTime, String appAuthor,
			String appVersion,
			String appAddress) {
		super();
		this.appID = appID;
		this.appIconAdd = appIconAdd;
		this.appName = appName;
		this.appGrade = appGrade;
		this.appSize = appSize;
		this.appDownCount = appDownCount;
		this.appSecurity = appSecurity;
		this.appFree = appFree;
		this.appAdvers = appAdvers;
		this.appShootList = appShootList;
		this.appDetailInfor1 = appDetailInfor1;
		this.appDetailInfor2 = appDetailInfor2;
		this.appUpdateTime = appUpdateTime;
		this.appAuthor = appAuthor;
		this.appVersion = appVersion;
		this.appAddress = appAddress;
	}


	public String getAppID() {
		return appID;
	}


	public void setAppID(String appID) {
		this.appID = appID;
	}


	public String getAppIconAdd() {
		return appIconAdd;
	}


	public void setAppIconAdd(String appIconAdd) {
		this.appIconAdd = appIconAdd;
	}


	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}


	public String getAppGrade() {
		return appGrade;
	}


	public void setAppGrade(String appGrade) {
		this.appGrade = appGrade;
	}


	public String getAppSize() {
		return appSize;
	}


	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}


	public String getAppDownCount() {
		return appDownCount;
	}


	public void setAppDownCount(String appDownCount) {
		this.appDownCount = appDownCount;
	}


	public String getAppSecurity() {
		return appSecurity;
	}


	public void setAppSecurity(String appSecurity) {
		this.appSecurity = appSecurity;
	}


	public String getAppFree() {
		return appFree;
	}


	public void setAppFree(String appFree) {
		this.appFree = appFree;
	}


	public String getAppAdvers() {
		return appAdvers;
	}


	public void setAppAdvers(String appAdvers) {
		this.appAdvers = appAdvers;
	}


	public ArrayList<String> getAppShootList() {
		return appShootList;
	}


	public void setAppShootList(ArrayList<String> appShootList) {
		this.appShootList = appShootList;
	}


	public String getAppDetailInfor1() {
		return appDetailInfor1;
	}


	public void setAppDetailInfor1(String appDetailInfor1) {
		this.appDetailInfor1 = appDetailInfor1;
	}


	public String getAppDetailInfor2() {
		return appDetailInfor2;
	}


	public void setAppDetailInfor2(String appDetailInfor2) {
		this.appDetailInfor2 = appDetailInfor2;
	}


	public String getAppUpdateTime() {
		return appUpdateTime;
	}


	public void setAppUpdateTime(String appUpdateTime) {
		this.appUpdateTime = appUpdateTime;
	}


	public String getAppAuthor() {
		return appAuthor;
	}


	public void setAppAuthor(String appAuthor) {
		this.appAuthor = appAuthor;
	}


	public String getAppVersion() {
		return appVersion;
	}


	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppAddress() {
		return appAddress;
	}


	public void setAppAddress(String appAddress) {
		this.appAddress = appAddress;
	}
	
	
	
	
}

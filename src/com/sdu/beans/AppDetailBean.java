package com.sdu.beans;

import java.util.ArrayList;

public class AppDetailBean {

	private String appID; /* app��Ψһ��ʶ */
	private String appIconAdd; /* app��ͼ��ĵ�ַ */
	private String appName; /* app������ */
	private String appGrade;	/* app���� */
	private String appSize; /* app�Ĵ�С */
	private String appDownCount; /* app�����ش��� */
	private String appSecurity; /* app��ȫ�� */
	private String appFree;	/* app����� */
	private String appAdvers; /* app����� */
	private ArrayList<String> appShootList; /* app��ͼ�б� */
	private String appDetailInfor1; /* app��ϸ����1 */
	private String appDetailInfor2; /* app��ϸ��Ϣ2 */
	private String appUpdateTime;	/* app����ʱ�� */
	private String appAuthor;	/* app���� */
	private String appVersion;	/* app�汾�� */
	private String appAddress; /* app�����ص�ַ */
	
	
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

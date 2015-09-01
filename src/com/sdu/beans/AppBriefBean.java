package com.sdu.beans;

public class AppBriefBean {

	private String appID; /* app的唯一标识 */
	private String appIconAdd; /* app的图标的地址 */
	private String appName; /* app的名称 */
	private String appDownCount; /* app的下载次数 */
	private String appSize; /* app的大小 */
	private String briefInfo; /* app的简介 */
	private String appAddress; /* app的下载地址 */

	public AppBriefBean(String appID, String appIconAdd, String appName,
			String appDownCount, String appSize, String briefInfo,
			String appAddress) {
		super();
		this.appID = appID;
		this.appIconAdd = appIconAdd;
		this.appName = appName;
		this.appDownCount = appDownCount;
		this.appSize = appSize;
		this.briefInfo = briefInfo;
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

	public String getAppDownCount() {
		return appDownCount;
	}

	public void setAppDownCount(String appDownCount) {
		this.appDownCount = appDownCount;
	}

	public String getAppSize() {
		return appSize;
	}

	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}

	public String getBriefInfo() {
		return briefInfo;
	}

	public void setBriefInfo(String briefInfo) {
		this.briefInfo = briefInfo;
	}

	public String getAppAddress() {
		return appAddress;
	}

	public void setAppAddress(String appAddress) {
		this.appAddress = appAddress;
	}
	
	

}

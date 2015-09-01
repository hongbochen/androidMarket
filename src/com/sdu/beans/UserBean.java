package com.sdu.beans;

public class UserBean {

	private String userID;
	private String userName;
	private String replyBack;

	public UserBean(String userID, String userName, String replyBack) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.replyBack = replyBack;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReplyBack() {
		return replyBack;
	}

	public void setReplyBack(String replyBack) {
		this.replyBack = replyBack;
	}

}

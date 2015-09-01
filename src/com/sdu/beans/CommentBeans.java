package com.sdu.beans;

import java.util.ArrayList;

public class CommentBeans {

	private String commentID;
	private String userID;
	private String userName;
	private String userHeadPath;
	private String zanSize;
	private String commentTime;
	private String commentContent;
	private ArrayList<UserBean> backList;
	
	public CommentBeans(String commentID, String userID, String userName,
			String userHeadPath,String zanSize, String commentTime,
			String commentContent, ArrayList<UserBean> backList) {
		super();
		this.commentID = commentID;
		this.userID = userID;
		this.userName = userName;
		this.userHeadPath = userHeadPath;
		this.zanSize = zanSize;
		this.commentTime = commentTime;
		this.commentContent = commentContent;
		this.backList = backList;
	}

	public String getCommentID() {
		return commentID;
	}

	public void setCommentID(String commentID) {
		this.commentID = commentID;
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

	public String getUserHeadPath() {
		return userHeadPath;
	}

	public void setUserHeadPath(String userHeadPath) {
		this.userHeadPath = userHeadPath;
	}

	public String getZanSize() {
		return zanSize;
	}

	public void setZanSize(String zanSize) {
		this.zanSize = zanSize;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public ArrayList<UserBean> getBackList() {
		return backList;
	}

	public void setBackList(ArrayList<UserBean> backList) {
		this.backList = backList;
	}
	
	
}

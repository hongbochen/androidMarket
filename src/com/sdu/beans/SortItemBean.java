package com.sdu.beans;

public class SortItemBean {

	private String typeID;
	private String typeName;
	private String typeAdd;
	
	public SortItemBean(String typeID, String typeName, String typeAdd) {
		super();
		this.typeID = typeID;
		this.typeName = typeName;
		this.typeAdd = typeAdd;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeAdd() {
		return typeAdd;
	}

	public void setTypeAdd(String typeAdd) {
		this.typeAdd = typeAdd;
	}
	
	
}

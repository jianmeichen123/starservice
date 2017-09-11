package com.galaxy.im.bean;

import com.galaxy.im.common.db.BaseEntity;

public class Test extends BaseEntity{
	private static final long serialVersionUID = 977029683004406685L;
	private String name;
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}

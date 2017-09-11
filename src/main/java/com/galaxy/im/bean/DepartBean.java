package com.galaxy.im.bean;

import com.galaxy.im.common.db.BaseEntity;

public class DepartBean extends BaseEntity{
	private static final long serialVersionUID = 6690353902265107047L;
	private String depName;
	private long companyId;
	
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}

package com.galaxy.im.bean.project;

import java.util.List;

public class ProjectBeanVo extends ProjectBean {

	private static final long serialVersionUID = 3585242629760257141L;
	
	//查询参数：redis缓存里的项目移交id
	private List<Long> projectIdList;
	
	//模糊查询项目名称
	private String pName;
	
	private long loginUserId;

	public List<Long> getProjectIdList() {
		return projectIdList;
	}

	public void setProjectIdList(List<Long> projectIdList) {
		this.projectIdList = projectIdList;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}


}

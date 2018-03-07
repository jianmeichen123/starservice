package com.galaxy.im.bean.project;

import com.galaxy.im.common.db.PagableEntity;

public class ProjectBean extends PagableEntity{
	private static final long serialVersionUID = 8726988409307762736L;
	
	private String projectName;			//项目名称
	
	private String belongTo;			//属于

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

}

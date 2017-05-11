package com.galaxy.im.bean.schedule;

import com.galaxy.im.common.db.BaseEntity;

public class ScheduleInfo extends BaseEntity{
	private static final long serialVersionUID = -6443062953917242468L;
	private long parentId;
	private int type;
	private long nameId;
	private String name;
	private int projectType;
	private int projectId;
	private String startTime;
	private String endTime;
	private String isAllday;
	private long wakeupId;
	private String remark;
	private int significance;
	
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getNameId() {
		return nameId;
	}
	public void setNameId(long nameId) {
		this.nameId = nameId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProjectType() {
		return projectType;
	}
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIsAllday() {
		return isAllday;
	}
	public void setIsAllday(String isAllday) {
		this.isAllday = isAllday;
	}
	public long getWakeupId() {
		return wakeupId;
	}
	public void setWakeupId(long wakeupId) {
		this.wakeupId = wakeupId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getSignificance() {
		return significance;
	}
	public void setSignificance(int significance) {
		this.significance = significance;
	}   
}

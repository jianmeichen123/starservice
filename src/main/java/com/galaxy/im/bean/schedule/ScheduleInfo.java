package com.galaxy.im.bean.schedule;

import com.galaxy.im.common.db.PagableEntity;

public class ScheduleInfo extends PagableEntity{
	private static final long serialVersionUID = -6443062953917242468L;
	private Long parentId;
	private int type;
	private Long nameId;
	private String name;
	private int projectType;
	private Long projectId;
	private String startTime;
	private String endTime;
	private String isAllday;
	private long wakeupId;
	private String remark;
	private int significance;
	private String callonAddress;
	private Long callonPerson;
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getNameId() {
		return nameId;
	}
	public void setNameId(Long nameId) {
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
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
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
	public String getCallonAddress() {
		return callonAddress;
	}
	public void setCallonAddress(String callonAddress) {
		this.callonAddress = callonAddress;
	}
	public Long getCallonPerson() {
		return callonPerson;
	}
	public void setCallonPerson(Long callonPerson) {
		this.callonPerson = callonPerson;
	}
}

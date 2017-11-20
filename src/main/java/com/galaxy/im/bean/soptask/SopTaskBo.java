package com.galaxy.im.bean.soptask;

import java.util.List;

public class SopTaskBo extends  SopTask{

	private static final long serialVersionUID = 1L;
	private String createUname;// 业务对象中扩展的字段
	private String projectName;//业务扩展字段---项目名称
	private List<String>  ids;//业务扩展字段---项目ids
	private List<Long> projectIds;
	private String taskDeadlineformat;
	private String caozuo;
	private List<String> taskStatusList; //任务状态  完成、待完成
	private List<String> taskFlagList;	//任务FLAG modifyed by zhangzl
	private String nameLike;
	private String statusFlag;
	private String caozuohtml;
	private String hours;
	private String orderRemark;
	private String flagUrl;
	private String concatcode;
	private String assignUidName; //认领人的名字(配合APP端)
	
	/**
	 * 任务列表的已认领和待认领数字
	 */
	private Long rwdCount; //待认领任务的数字
	private Long rwyCount; //已认领任务的数字
	
	public String getAssignUidName() {
		return assignUidName;
	}

	public void setAssignUidName(String assignUidName) {
		this.assignUidName = assignUidName;
	}

	public String getCreateUname() {
		return createUname;
	}

	public void setCreateUname(String createUname) {
		this.createUname = createUname;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<String> getTaskStatusList() {
		return taskStatusList;
	}

	public void setTaskStatusList(List<String> taskStatusList) {
		this.taskStatusList = taskStatusList;
	}

	public String getTaskDeadlineformat() {
		return taskDeadlineformat;
	}

	public void setTaskDeadlineformat(String taskDeadlineformat) {
		this.taskDeadlineformat = taskDeadlineformat;
	}

	public String getCaozuo() {
		return caozuo;
	}

	public void setCaozuo(String caozuo) {
		this.caozuo = caozuo;
	}

	public String getNameLike() {
		return nameLike == null ? null : nameLike.trim();
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike == null ? null : nameLike.trim();
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getCaozuohtml() {
		return caozuohtml;
	}
	
	public void setCaozuohtml(String caozuohtml) {
		this.caozuohtml = caozuohtml;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	public List<String> getTaskFlagList() {
		return taskFlagList;
	}

	public void setTaskFlagList(List<String> taskFlagList) {
		this.taskFlagList = taskFlagList;
	}

	public String getFlagUrl() {
		return flagUrl;
	}

	public void setFlagUrl(String flagUrl) {
		this.flagUrl = flagUrl;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getConcatcode() {
		return concatcode;
	}

	public void setConcatcode(String concatcode) {
		this.concatcode = concatcode;
	}

	public List<Long> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<Long> projectIds) {
		this.projectIds = projectIds;
	}

	public Long getRwdCount() {
		return rwdCount;
	}

	public void setRwdCount(Long rwdCount) {
		this.rwdCount = rwdCount;
	}

	public Long getRwyCount() {
		return rwyCount;
	}

	public void setRwyCount(Long rwyCount) {
		this.rwyCount = rwyCount;
	}


	
	
}

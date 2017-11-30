package com.galaxy.im.bean.meeting;

import com.galaxy.im.bean.project.MeetingScheduling;

public class MeetingSchedulingBo extends MeetingScheduling {
	private static final long serialVersionUID = 1L;
	
	private String extendFiled; // 业务对象中扩展的字段
	
	private String startTime;   //定义app端排期筛选的开始时间
	
	private String endTime;     //定义app端排期筛选的结束时间
	
	private String nameLike;	//模糊查询条件匹配

	//新增的字段用于app端进行待排期和已排期的数字统计
	private Long CountscheduleStatusy;
	private Long CountscheduleStatusd;
	
	private Long uid;
	private Long projectDepartid; 
	
	private String dayTime; 
	//当日的事项用
	private String dateTime; 
	//获取传入的年
	private String year; 
	//获取传入的月
	private String month;
	

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getExtendFiled() {
		return extendFiled;
	}

	public void setExtendFiled(String extendFiled) {
		this.extendFiled = extendFiled;
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

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getProjectDepartid() {
		return projectDepartid;
	}

	public void setProjectDepartid(Long projectDepartid) {
		this.projectDepartid = projectDepartid;
	}

	public String getDayTime() {
		return dayTime;
	}

	public void setDayTime(String dayTime) {
		this.dayTime = dayTime;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Long getCountscheduleStatusy() {
		return CountscheduleStatusy;
	}

	public void setCountscheduleStatusy(Long countscheduleStatusy) {
		CountscheduleStatusy = countscheduleStatusy;
	}

	public Long getCountscheduleStatusd() {
		return CountscheduleStatusd;
	}

	public void setCountscheduleStatusd(Long countscheduleStatusd) {
		CountscheduleStatusd = countscheduleStatusd;
	}




	
}
package com.galaxy.im.bean.talk;

import com.galaxy.im.common.db.BaseEntity;

public class TalkRecordDetailBean extends BaseEntity{

	private static final long serialVersionUID = -1515654189271517276L;
	
	private String viewDate;			//开始时间
	private String callonName;			//拜访人：拜访人姓名（项目归属）
	private String viewTarget;			//访谈对象
	private String projectName;			//关联项目
	
	private String fileKey;				//档案阿里云存储Key
	private long fileLength;			//档案大小
	private String bucketName;			//档案bucketName
	private String fileName;			//档案名称
	
	private String viewNotes;			//访谈纪要
	
	public String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getViewDate() {
		return viewDate;
	}
	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}
	public String getCallonName() {
		return callonName;
	}
	public void setCallonName(String callonName) {
		this.callonName = callonName;
	}
	public String getViewTarget() {
		return viewTarget;
	}
	public void setViewTarget(String viewTarget) {
		this.viewTarget = viewTarget;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getViewNotes() {
		return viewNotes;
	}
	public void setViewNotes(String viewNotes) {
		this.viewNotes = viewNotes;
	}
	public long getFileLength() {
		return fileLength;
	}
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}
	
	
	
}

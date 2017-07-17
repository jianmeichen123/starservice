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
	
	private Long interviewResultId;		//访谈结论id
	private String interviewResultCode;	//访谈结论code
	private String interviewResult;		//访谈结论
	private Long resultReasonId;		//结论原因id
    private String resultReasonCode;	//结论原因code
    private String resultReason;		//结论原因
    private String reasonOther;			//其他原因
    
	
	public Long getInterviewResultId() {
		return interviewResultId;
	}
	public void setInterviewResultId(Long interviewResultId) {
		this.interviewResultId = interviewResultId;
	}
	public String getInterviewResultCode() {
		return interviewResultCode;
	}
	public void setInterviewResultCode(String interviewResultCode) {
		this.interviewResultCode = interviewResultCode;
	}
	public String getInterviewResult() {
		return interviewResult;
	}
	public void setInterviewResult(String interviewResult) {
		this.interviewResult = interviewResult;
	}
	public Long getResultReasonId() {
		return resultReasonId;
	}
	public void setResultReasonId(Long resultReasonId) {
		this.resultReasonId = resultReasonId;
	}
	public String getResultReasonCode() {
		return resultReasonCode;
	}
	public void setResultReasonCode(String resultReasonCode) {
		this.resultReasonCode = resultReasonCode;
	}
	public String getResultReason() {
		return resultReason;
	}
	public void setResultReason(String resultReason) {
		this.resultReason = resultReason;
	}
	public String getReasonOther() {
		return reasonOther;
	}
	public void setReasonOther(String reasonOther) {
		this.reasonOther = reasonOther;
	}
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

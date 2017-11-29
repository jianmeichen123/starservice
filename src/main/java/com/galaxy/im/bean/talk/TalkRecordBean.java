package com.galaxy.im.bean.talk;

import java.util.Date;

import com.galaxy.im.common.db.PagableEntity;

public class TalkRecordBean extends PagableEntity{
	private static final long serialVersionUID = 7937887405956613675L;
	private String projectName;		//项目名称
	private long projectId;			//关联项目id
    private long fileId;			//关联附件id
	private Date viewDate;			//记录时间
	private String viewDateStr;		//记录时间
	private String viewTarget;      //访谈对象
    private String viewNotes;		//访谈纪要
    private String viewNotesText;	//访谈纪要文本值
    private long scheduleId;		//拜访id
    
    private String fileKey;				//档案阿里云存储Key
	private long fileLength;			//档案大小
	private String bucketName;			//档案bucketName
	private String fileName;			//档案名称
	
	private Long interviewResultId;		//访谈结论id
	private String interviewResultCode;	//访谈结论code
	private String interviewResult;		//访谈结论
	private Long resultReasonId;		//结论原因id
    private String resultReasonCode;	//结论原因code
    private String resultReason;		//结论原因
    private String reasonOther;			//其他原因
    
    private int interviewValid; 		//0表示有效，1表示无效
    
    
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

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
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

	public TalkRecordBean(){
	}
    
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	public String getViewNotes() {
		return viewNotes;
	}
	public void setViewNotes(String viewNotes) {
		this.viewNotes = viewNotes;
	}
	public String getViewNotesText() {
		return viewNotesText;
	}
	public void setViewNotesText(String viewNotesText) {
		this.viewNotesText = viewNotesText;
	}
	
	public long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
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

	public String getViewDateStr() {
		return viewDateStr;
	}

	public void setViewDateStr(String viewDateStr) {
		this.viewDateStr = viewDateStr;
	}

	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public int getInterviewValid() {
		return interviewValid;
	}

	public void setInterviewValid(int interviewValid) {
		this.interviewValid = interviewValid;
	}

}

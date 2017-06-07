package com.galaxy.im.bean.meeting;

import java.util.Date;

import com.galaxy.im.common.db.PagableEntity;

public class MeetingRecordBean extends PagableEntity{

	private static final long serialVersionUID = 1941430542667798497L;

	private long projectId;				//关联项目id
    private long fileId;				//关联附件id
	private Date meetingDate;			//会议时间
	private String meetingDateStr;		//会议时间
	private String meetingType;			//会议类型
	private Long meetingResultId;		//会议结论id
	private String meetingResult;		//会议结论
	private String meetingNotes;		//会议纪要
	private int recordType;				//是否为创意的标识
	private String participant;			//参会人
	private Long createUid;				//创建人id
    private int meetValid; 				//0表示有效，1表示无效
    private String meetingNotesText;	//会议记录的纯文本值
    private Long meetingName;			//会议名称
    private Long resultReasonId;		//结论原因id
    private String resultReason;		//结论原因
    private String reasonOther;			//其他原因
	
	private String fileKey;				//档案阿里云存储Key
	private long fileLength;			//档案大小
	private String bucketName;			//档案bucketName
	private String fileName;			//档案名称
	
	
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
	public Date getMeetingDate() {
		return meetingDate;
	}
	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}
	public String getMeetingDateStr() {
		return meetingDateStr;
	}
	public void setMeetingDateStr(String meetingDateStr) {
		this.meetingDateStr = meetingDateStr;
	}
	public String getMeetingType() {
		return meetingType;
	}
	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}
	public String getMeetingResult() {
		return meetingResult;
	}
	public void setMeetingResult(String meetingResult) {
		this.meetingResult = meetingResult;
	}
	public String getMeetingNotes() {
		return meetingNotes;
	}
	public void setMeetingNotes(String meetingNotes) {
		this.meetingNotes = meetingNotes;
	}
	public int getRecordType() {
		return recordType;
	}
	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}
	public String getParticipant() {
		return participant;
	}
	public void setParticipant(String participant) {
		this.participant = participant;
	}
	public Long getCreateUid() {
		return createUid;
	}
	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}
	public int getMeetValid() {
		return meetValid;
	}
	public void setMeetValid(int meetValid) {
		this.meetValid = meetValid;
	}
	public String getMeetingNotesText() {
		return meetingNotesText;
	}
	public void setMeetingNotesText(String meetingNotesText) {
		this.meetingNotesText = meetingNotesText;
	}
	public Long getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(Long meetingName) {
		this.meetingName = meetingName;
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
	public Long getMeetingResultId() {
		return meetingResultId;
	}
	public void setMeetingResultId(Long meetingResultId) {
		this.meetingResultId = meetingResultId;
	}
	public Long getResultReasonId() {
		return resultReasonId;
	}
	public void setResultReasonId(Long resultReasonId) {
		this.resultReasonId = resultReasonId;
	}
	
}

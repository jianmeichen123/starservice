package com.galaxy.im.bean.schedule;

import com.galaxy.im.common.db.BaseEntity;

public class ScheduleDetailBean extends BaseEntity{

	private static final long serialVersionUID = 1752080437114935090L;
	
	private String startTime;				//开始时间
	private String endTime;					//结束时间
	private String remark;					//备注
	private String schedultMatter;			//拜访事项
	private String callonAddress;			//拜访地址
	
	private long contactId;					//拜访对象id
	private String contactName;				//拜访对象名称
	private String contactPost;				//拜访对象职位
	private String contactPhone;			//拜访对象电话
	private String contactCompany;			//拜访对象公司地址
	
	private long projectId;					//关联项目id
	private String projectName;				//关系项目名称
	private String industryOwnName;			//项目行业
	private String financeStatusName;		//融资阶段
	
	private long significanceId;			//重要性id
	private String significanceName;		//重要性
	private long wakeupId;					//提醒通知id
	private String wakeupTime;				//提醒通知
	
	private long interviewCount;			//历史访谈记录个数
	
	private int interviewFalg;				//访谈标识，1：已访谈，0：未访谈
	private String interviewContent;		//已访谈，未访谈
	
	private String fileKey;					//档案阿里云存储Key
	private long fileLength;				//档案大小
	private String bucketName;				//档案bucketName
	private String fileName;				//档案名称
	private String viewNotes;				//访谈纪要
	private long talkRecordId;				//访谈记录id
	
	private long transferFlag;				//项目是否移交

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

	public String getViewNotes() {
		return viewNotes;
	}

	public void setViewNotes(String viewNotes) {
		this.viewNotes = viewNotes;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCallonAddress() {
		return callonAddress;
	}

	public void setCallonAddress(String callonAddress) {
		this.callonAddress = callonAddress;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPost() {
		return contactPost;
	}

	public void setContactPost(String contactPost) {
		this.contactPost = contactPost;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(String contactCompany) {
		this.contactCompany = contactCompany;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getIndustryOwnName() {
		return industryOwnName;
	}

	public void setIndustryOwnName(String industryOwnName) {
		this.industryOwnName = industryOwnName;
	}

	public String getFinanceStatusName() {
		return financeStatusName;
	}

	public void setFinanceStatusName(String financeStatusName) {
		this.financeStatusName = financeStatusName;
	}

	public String getSignificanceName() {
		return significanceName;
	}

	public void setSignificanceName(String significanceName) {
		this.significanceName = significanceName;
	}

	public String getWakeupTime() {
		return wakeupTime;
	}

	public void setWakeupTime(String wakeupTime) {
		this.wakeupTime = wakeupTime;
	}

	public String getSchedultMatter() {
		return schedultMatter;
	}

	public void setSchedultMatter(String schedultMatter) {
		this.schedultMatter = schedultMatter;
	}

	public int getInterviewFalg() {
		return interviewFalg;
	}

	public void setInterviewFalg(int interviewFalg) {
		this.interviewFalg = interviewFalg;
	}

	public String getInterviewContent() {
		return interviewContent;
	}

	public void setInterviewContent(String interviewContent) {
		this.interviewContent = interviewContent;
	}

	public long getInterviewCount() {
		return interviewCount;
	}

	public void setInterviewCount(long interviewCount) {
		this.interviewCount = interviewCount;
	}

	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public long getSignificanceId() {
		return significanceId;
	}

	public void setSignificanceId(long significanceId) {
		this.significanceId = significanceId;
	}

	public long getWakeupId() {
		return wakeupId;
	}

	public void setWakeupId(long wakeupId) {
		this.wakeupId = wakeupId;
	}

	public long getTalkRecordId() {
		return talkRecordId;
	}

	public void setTalkRecordId(long talkRecordId) {
		this.talkRecordId = talkRecordId;
	}

	public long getTransferFlag() {
		return transferFlag;
	}

	public void setTransferFlag(long transferFlag) {
		this.transferFlag = transferFlag;
	}

}
